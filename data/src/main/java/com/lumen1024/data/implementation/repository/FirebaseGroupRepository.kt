package com.lumen1024.data.implementation.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.lumen1024.data.dto.GroupDto
import com.lumen1024.data.dto.toGroup
import com.lumen1024.data.dto.toGroupDto
import com.lumen1024.data.toRepositoryObjectChange
import com.lumen1024.data.tryCatchDerived
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.RepositoryObjectChange
import com.lumen1024.domain.data.transform
import com.lumen1024.domain.repository.GroupRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseGroupRepository @Inject constructor(
    firebase: Firebase,
) : GroupRepository {
    private val collection = firebase.firestore.collection("groups")

    override suspend fun getGroupById(id: String): Result<Group> {
        val query = collection.document(id).get()

        return tryCatchDerived("Failed get group by id") {
            val group = query.await().toObject(GroupDto::class.java)?.toGroup()
                ?: throw Exception("Group not found")
            return@tryCatchDerived group
        }
    }

    override suspend fun getGroupByCredentials(
        name: String, password: String?
    ): Result<Group> {
        val query = collection
            .whereEqualTo(GroupDto::name.name, name)
            .whereEqualTo(GroupDto::password.name, password ?: "")

        return tryCatchDerived("Failed get group by credentials") {
            val group = query.get().await().toObjects(GroupDto::class.java).map { it.toGroup() }
                .takeIf { it.isNotEmpty() }?.get(0)
                ?: throw Exception("Group not found")

            return@tryCatchDerived group
        }
    }

    override suspend fun getGroupsByIds(groupIds: List<String>): Result<List<Group>> {
        val query = collection.whereIn("id", groupIds)

        return tryCatchDerived("Failed get groups by ids") {
            val groups = query.get().await().toObjects(GroupDto::class.java).map { it.toGroup() }
            return@tryCatchDerived groups
        }
    }

    override suspend fun addGroup(group: Group): Result<Unit> {
        val task = collection.add(group.toGroupDto())
        return tryCatchDerived("Failed add group") { task.await() }
    }

    override suspend fun deleteGroup(groupId: String): Result<Unit> {
        val task = collection.document(groupId).delete()
        return tryCatchDerived("Failed delete group") { task.await() }
    }

    //  TODO: map dto fields
    override suspend fun updateGroup(
        groupId: String, data: Map<String, Any>
    ): Result<Unit> {
        val task = collection.document(groupId).update(data)
        return tryCatchDerived("Failed update group") { task.await() }
    }

    override fun listenChanges(
        groupIds: List<String>, callback: (List<RepositoryObjectChange<Group>>) -> Unit
    ): Result<() -> Unit> {
        if (groupIds.isEmpty())
            return Result.failure(Exception("Can't listen changes, groupIds must not be empty"))

        val query = collection.whereIn("id", groupIds)

        return tryCatchDerived("Can't listen changes") {
            val registration = query.addSnapshotListener { snapshot, e ->
                if (e != null) return@addSnapshotListener

                val changes = snapshot?.documentChanges?.mapNotNull { firebaseChange ->
                    val changesDto = firebaseChange.toRepositoryObjectChange<GroupDto>()
                        ?: return@mapNotNull null
                    return@mapNotNull changesDto.transform { it.toGroup() }
                } ?: return@addSnapshotListener

                callback(changes)
            }
            return@tryCatchDerived  { registration.remove() }
        }
    }

    override fun listenChanges(
        groupId: String, callback: (Group) -> Unit
    ): Result<() -> Unit> {
        val query = collection.document(groupId)

        return tryCatchDerived("Can't listen changes") {
            val registration = query.addSnapshotListener { snapshot, e ->
                if (e != null) return@addSnapshotListener
                val data = snapshot?.toObject(GroupDto::class.java)?.toGroup()
                    ?: return@addSnapshotListener
                callback(data)
            }
            return@tryCatchDerived { registration.remove() }
        }
    }
}