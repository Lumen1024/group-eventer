package com.lumen1024.data.implementation.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.lumen1024.data.dto.GroupDto
import com.lumen1024.data.dto.toGroup
import com.lumen1024.data.dto.toGroupDto
import com.lumen1024.data.tools.tryCatchDerived
import com.lumen1024.domain.FlowList
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.repository.GroupRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseGroupRepository @Inject constructor(
    firebase: Firebase,
) : GroupRepository {
    private val collection = firebase.firestore.collection("groups")

    override fun getGroupById(id: String): Flow<Group?> = callbackFlow {
        val query = collection.document(id)

        val registration = query.addSnapshotListener { snapshot, e ->
            if (e != null) return@addSnapshotListener

            val group = snapshot?.toObject(GroupDto::class.java)?.toGroup()
            trySend(group)
        }
        awaitClose { registration.remove() }
    }

    override fun getGroupByCredentials(
        name: String, password: String?
    ): Flow<Group?> = callbackFlow {
        val query = collection
            .whereEqualTo(GroupDto::name.name, name)
            .whereEqualTo(GroupDto::password.name, password ?: "")

        val registration = query.addSnapshotListener { snapshot, e ->
            query.addSnapshotListener { snapshot, e ->
                if (e != null) return@addSnapshotListener
                val group = snapshot?.toObjects(GroupDto::class.java)?.map { it.toGroup() }
                    ?.takeIf { it.isNotEmpty() }?.get(0)
                trySend(group)
            }
        }
        awaitClose { registration.remove() }
    }

    override fun getGroupsByIds(groupIds: List<String>): FlowList<Group> = callbackFlow {
        val query = collection.whereIn("id", groupIds)

        val registration = query.addSnapshotListener { snapshot, e ->
            if (e != null) return@addSnapshotListener
            val groups = snapshot?.toObjects(GroupDto::class.java)?.map { it.toGroup() }
            trySend(groups ?: emptyList())
        }
        awaitClose { registration.remove() }
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
}