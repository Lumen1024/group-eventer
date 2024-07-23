package com.lumen1024.groupeventer.entities.group.model

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.lumen1024.groupeventer.shared.model.RepositoryObjectChange
import com.lumen1024.groupeventer.shared.model.toRepositoryObjectChange
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseGroupRepository @Inject constructor(
    firebase: Firebase,
) : GroupRepository {

    private val collection = firebase.firestore.collection("groups")

    override suspend fun add(group: Group): Result<Unit> {
        try {
            collection.document(group.id).set(group).await()
            return Result.success(Unit)

        } catch (e: Throwable) {
            return Result.failure(e)
        }
    }

    override suspend fun get(id: String): Result<Group> {
        try {
            val group = collection
                .document(id)
                .get().await()
                .toObject(GroupDto::class.java)
                ?.toGroup()
                ?: return Result.failure(Throwable("No group with this id"))

            return Result.success(group)

        } catch (e: Throwable) {
            return Result.failure(e)
        }
    }

    override suspend fun get(name: String, password: String?): Result<Group> {
        val query = collection
            .whereEqualTo(GroupDto::name.name, name)
            .whereEqualTo(GroupDto::password.name, password)

        try {
            val group = query
                .get().await()
                .documents.getOrNull(0)
                ?.toObject(GroupDto::class.java)?.toGroup()
                ?: return Result.failure(Throwable("No group with this credits"))
            return Result.success(group)

        } catch (e: Throwable) {
            return Result.failure(e)
        }
    }

    override suspend fun getList(ids: List<String>): Result<List<Group>> {
        val query =
            if (ids.isNotEmpty()) collection.whereIn("id", ids)
            else collection

        return try {
            val groups = query
                .get().await()
                .toObjects(GroupDto::class.java).map { it.toGroup() }
            Result.success(groups)

        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun update(
        id: String,
        data: Map<String, Any>,
    ): Result<Unit> {
        return try {
            collection.document(id).update(data)
                .await()
            Result.success(Unit)

        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun remove(id: String): Result<Unit> {
        return try {
            collection
                .document(id).delete()
                .await()
            Result.success(Unit)

        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override fun listenList(
        ids: List<String>,
        callback: (List<RepositoryObjectChange<Group?>>) -> Unit,
    ): Result<() -> Unit> {

        if (ids.isEmpty()) {
            return Result.failure(Throwable("Ids must not be empty"))
        }

        val query = collection.whereIn("id", ids)

        val registration = query.addSnapshotListener { snapshot, e ->
            if (e != null) return@addSnapshotListener

            val changes = snapshot?.documentChanges?.map { firebaseChange ->
                val changeDto = firebaseChange.toRepositoryObjectChange<GroupDto>()
                RepositoryObjectChange(changeDto.type, changeDto.data?.toGroup())

            } ?: return@addSnapshotListener

            callback(changes)
        }

        return Result.success { registration.remove() }
    }
}