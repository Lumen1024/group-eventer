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
        try {
            collection.add(group.toGroupDto()).await()
            return Result.success(Unit)
        } catch (e: Exception) {
            val message = "Failed add group: " + (e.message ?: "no exception message provided")
            return Result.failure(Exception(message))
        }
    }

    override suspend fun deleteGroup(groupId: String): Result<Unit> {
        try {
            collection.document(groupId).delete().await()
            return Result.success(Unit)
        } catch (e: Exception) {
            val message = "Failed delete group: " + (e.message ?: "no exception message provided")
            return Result.failure(Exception(message))
        }
    }

    //  TODO: map dto fields
    override suspend fun updateGroup(
        groupId: String, data: Map<String, Any>
    ): Result<Unit> {
        try {
            collection.document(groupId).update(data).await()
            Result.success(Unit)

        } catch (e: Exception) {
            val message = "Failed update group: " + (e.message ?: "no exception message provided")
            return Result.failure(Exception(message))
        }
    }

    override fun listenChanges(
        groupIds: List<String>, callback: (List<RepositoryObjectChange<Group>>) -> Unit
    ): Result<() -> Unit> {
        if (groupIds.isEmpty()) {
            return Result.failure(Throwable("Ids must not be empty"))
        }

        val query = collection.whereIn("id", groupIds)

        val registration = query.addSnapshotListener { snapshot, e ->
            if (e != null) return@addSnapshotListener

            val changes = snapshot?.documentChanges?.map { firebaseChange ->
                val changeDto = firebaseChange.toRepositoryObjectChange<GroupDto>()
                RepositoryObjectChange(changeDto.type, changeDto.data?.toGroup())
                changeDto.copy(_data = changeDto.data.toGroup())
            } ?: return@addSnapshotListener

            callback(changes.filterNotNull())
        }

        return Result.success { registration.remove() }
    }

    override fun listenChanges(
        groupId: String, callback: (RepositoryObjectChange<Group>) -> Unit
    ): Result<() -> Unit> {
        val query = collection.document(groupId)
        val registration = query.addSnapshotListener { snapshot, e ->

        }
        return Result.success { registration.remove() }
    }
}