package com.lumen1024.groupeventer.entities.group.model

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseGroupRepository @Inject constructor(
    private val firebase: Firebase
) : GroupRepository {
    override suspend fun addGroup(group: Group) {
        firebase.firestore.collection("groups").add(group).await()
    }

    override suspend fun getGroup(groupId: String): Result<Group> {
        try {
            val group = firebase.firestore
                .collection("groups")
                .document(groupId)
                .get()
                .await()
                .toObject(Group::class.java)

            if (group === null) {
                return Result.failure(GroupException.Unknown("Group is null"))
            }

            return Result.success(group)
        } catch (e: Exception) {
            if (e is FirebaseFirestoreException) {
                return Result.failure(e.toGroupException())
            }

            return Result.failure(GroupException.Unknown(e.message))
        }
    }

    override suspend fun getGroups(): Result<List<Group>> {
        try {
            val groups = firebase.firestore
                .collection("groups")
                .get()
                .await()
            return Result.success(groups.toObjects(Group::class.java))
        } catch (e: Exception) {
            if (e is FirebaseFirestoreException) {
                return Result.failure(e.toGroupException())
            }

            return Result.failure(GroupException.Unknown(e.message))
        }
    }

    override suspend fun updateGroup(
        groupId: String,
        data: Map<String, Any>
    ): Result<Void> {
        return try {
            Result.success(
                firebase.firestore
                    .collection("groups")
                    .document(groupId)
                    .update(data)
                    .await()
            )
        } catch (e: Exception) {
            if (e is FirebaseFirestoreException) {
                return Result.failure(e.toGroupException())
            }

            return Result.failure(GroupException.Unknown(e.message))
        }
    }

    override suspend fun removeGroup(groupId: String): Result<Void> {
        return try {
            Result.success(
                firebase.firestore.collection("groups")
                    .document(groupId)
                    .delete()
                    .await()
            )
        } catch (e: Exception) {
            if (e is FirebaseFirestoreException) {
                return Result.failure(e.toGroupException())
            }

            return Result.failure(GroupException.Unknown(e.message))
        }
    }

    override fun listenGroupChanges(groupId: String, callback: (Group) -> Unit) {
        TODO("Not yet implemented")
    }
}