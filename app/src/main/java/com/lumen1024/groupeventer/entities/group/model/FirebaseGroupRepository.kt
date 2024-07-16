package com.lumen1024.groupeventer.entities.group.model

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.firestore
import com.lumen1024.groupeventer.shared.model.RepositoryException
import com.lumen1024.groupeventer.shared.model.RepositoryObjectChange
import com.lumen1024.groupeventer.shared.model.toRepositoryException
import com.lumen1024.groupeventer.shared.model.toRepositoryObjectChange
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseGroupRepository @Inject constructor(
    firebase: Firebase,
) : GroupRepository {
    private val collection = firebase.firestore.collection("groups")

    override suspend fun addGroup(group: Group) {
        collection.document(group.id).set(group).await()
    }

    @Deprecated("not work")
    override suspend fun getGroup(groupId: String): Result<Group> {
        try {
            val group = collection
                .document(groupId) // todo: use query by id field
                .get()
                .await()
                .toObject(GroupDto::class.java)?.toGroup()

            if (group == null) {
                return Result.failure(
                    GroupRepositoryException(
                        RepositoryException.Code.UNKNOWN,
                        "Group is null"
                    )
                )
            }

            return Result.success(group)
        } catch (e: Exception) {
            Log.e("repository", e.message, e)
            if (e is FirebaseFirestoreException) {
                return Result.failure(e.toRepositoryException())
            }

            return Result.failure(
                GroupRepositoryException(
                    RepositoryException.Code.UNKNOWN,
                    e.message
                )
            )
        }
    }

    override suspend fun getGroup(name: String, password: String?): Result<Group> {
        val query = collection
            .whereEqualTo("name", name)
            .let {
                return@let if (!password.isNullOrEmpty()) it.whereEqualTo(
                    "password",
                    password
                ) else it
            }
            .get()
            .await()

        if (query.isEmpty)
            return Result.failure(
                GroupRepositoryException(
                    code = RepositoryException.Code.NOT_FOUND,
                    message = "" // todo
                )
            )

        val group = query.documents[0].toObject(GroupDto::class.java)!!.toGroup() // up check
        return Result.success(group)
    }

    override suspend fun getGroups(groupIds: List<String>): Result<List<Group>> {
        try {
            val groupsQuery =
                if (groupIds.isNotEmpty()) collection.whereIn("id", groupIds)
                else collection

            val groups = groupsQuery
                .get()
                .await()
            return Result.success(groups.toObjects(GroupDto::class.java).map { it.toGroup() })
        } catch (e: Exception) {
            Log.e("repository", e.message, e)
            if (e is FirebaseFirestoreException) {
                return Result.failure(e.toRepositoryException())
            }

            return Result.failure(
                GroupRepositoryException(
                    RepositoryException.Code.UNKNOWN,
                    e.message
                )
            )
        }
    }

    override suspend fun updateGroup(
        groupId: String,
        data: Map<String, Any>,
    ): Result<Void> {
        return try {
            Result.success(
                collection
                    .document(groupId)
                    .update(data)
                    .await()
            )
        } catch (e: Exception) {
            Log.e("repository", e.message, e)
            if (e is FirebaseFirestoreException) {
                return Result.failure(e.toRepositoryException())
            }

            return Result.failure(
                GroupRepositoryException(
                    RepositoryException.Code.UNKNOWN,
                    e.message
                )
            )
        }
    }

    override suspend fun removeGroup(groupId: String): Result<Void> {
        return try {
            Result.success(
                collection
                    .document(groupId)
                    .delete()
                    .await()
            )
        } catch (e: Exception) {
            Log.e("repository", e.message, e)
            if (e is FirebaseFirestoreException) {
                return Result.failure(e.toRepositoryException())
            }

            return Result.failure(
                GroupRepositoryException(
                    RepositoryException.Code.UNKNOWN,
                    e.message
                )
            )
        }
    }

    override fun listenChanges(
        groupIds: List<String>,
        callback: (List<RepositoryObjectChange<Group?>>) -> Unit,
    ): () -> Unit {
        if (groupIds.isEmpty()) {
            throw Exception("Ids list must not be empty")
        }

        val registration = collection.whereIn("id", groupIds).addSnapshotListener { snapshot, e ->
            if (e !== null) {
                return@addSnapshotListener
            }

            callback(snapshot!!.documentChanges.map { it ->
                val change = it.toRepositoryObjectChange<GroupDto>()
                val mappedChane =
                    RepositoryObjectChange(change.type, change.data?.toGroup())
                return@map mappedChane
            })
        }

        return { registration.remove() }
    }

    override fun listenGroupChanges(groupId: String, callback: (Group) -> Unit) {
        TODO("Not yet implemented")
    }
}