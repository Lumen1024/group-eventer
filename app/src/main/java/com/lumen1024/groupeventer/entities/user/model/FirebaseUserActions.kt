package com.lumen1024.groupeventer.entities.user.model

import com.google.firebase.firestore.FieldValue
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.model.GroupColor
import com.lumen1024.groupeventer.entities.group.model.GroupRepository
import com.lumen1024.groupeventer.entities.group.model.toGroupEventDto
import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent
import javax.inject.Inject

class FirebaseUserActions @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val groupRepository: GroupRepository,
    private val userStateHolder: UserStateHolder,
) : UserActions {

    override suspend fun joinGroup(name: String, password: String): Result<Unit> {
        val userData = userStateHolder.userData.value
            ?: return Result.failure(Throwable("UserData is null"))

        val group = groupRepository.get(name, password)
            .fold(onSuccess = { it }, onFailure = { return Result.failure(it) })

        if (group.id in userData.groups) {
            return Result.failure(Throwable("You already in this group"))
        }

        userDataRepository.update(
            id = userData.id,
            data = mapOf(
                "groups" to userData.groups + group.id
            )
        ).onFailure { return Result.failure(it) }

        groupRepository.update(
            id = group.id,
            data = mapOf(
                "people" to group.people + userData.id
            )
        ).onFailure { return Result.failure(it) }

        return Result.success(Unit)
    }

    override suspend fun leaveGroup(id: String): Result<Unit> {
        val userData = userStateHolder.userData.value
            ?: return Result.failure(Throwable("UserData is null"))

        val group = groupRepository.get(id)
            .fold(onSuccess = { it }, onFailure = { return Result.failure(it) })

        if (group.id !in userData.groups) {
            return Result.failure(Throwable("You are not in this group"))
        }

        userDataRepository.update(
            id = userData.id,
            data = mapOf(
                "groups" to userData.groups - group.id
            )
        ).onFailure { return Result.failure(it) }

        val isLast = group.people.isEmpty()
        val isAdmin = group.admin == userData.id

        if (isLast) {
            groupRepository.remove(
                id = group.id
            ).onFailure { return Result.failure(it) }

        } else if (isAdmin) {
            val newAdmin = group.people[0]
            groupRepository.update(
                id = group.id,
                mapOf(
                    Group::admin.name to newAdmin
                )
            ).onFailure { return Result.failure(it) }

            // remove from common people
            groupRepository.update(
                id = group.id,
                mapOf(
                    Group::people.name to group.people - newAdmin
                )
            ).onFailure { return Result.failure(it) }

        } else {
            groupRepository.update(
                id = group.id,
                data = mapOf(
                    "people" to group.people - userData.id
                )
            ).onFailure { return Result.failure(it) }

        }
        return Result.success(Unit)
    }

    override suspend fun createGroup(
        name: String,
        password: String,
        color: GroupColor,
    ): Result<Unit> {
        if (groupRepository.get(name = name, password = null).isSuccess) {
            return Result.failure(Throwable("Group with same name already exist"))
        } // now we cant create group with same name

        val userData = userStateHolder.userData.value
            ?: return Result.failure(Throwable("UserData is null"))

        val group = Group(
            name = name,
            password = password,
            admin = userData.id,
            color = color
        )

        groupRepository.add(group)

        userDataRepository.update(
            id = userData.id,
            data = mapOf(
                "groups" to userData.groups + group.id
            )
        ).onFailure { return Result.failure(it) }

        return Result.success(Unit)
    }

    override suspend fun updateGroup(groupId: String, data: Map<String, Any>): Result<Unit> {
        groupRepository.update(groupId, data)
            .onFailure { e -> return Result.failure(e) }

        return Result.success(Unit)
    }

    override suspend fun createEvent(event: GroupEvent, group: Group): Result<Unit> {
        if (!userInGroup(group)) return Result.failure(Throwable("User not in group")) // todo

        groupRepository.update(
            id = group.id,
            data = mapOf(
                "events" to (group.events.map { it.toGroupEventDto() } + event.toGroupEventDto())
            )
        ).onFailure { return Result.failure(it) }
        return Result.success(Unit)
    }

    override suspend fun updateEvent(event: GroupEvent): Result<Unit> {
        val targetGroup = userStateHolder.groups.value.let {
            it.forEach { group ->
                if (event in group.events)
                    return@let group
            }
            return@let null
        } ?: return Result.failure(Throwable("User groups don't have this event"))

        val id = userStateHolder.userData.value?.id
            ?: return Result.failure(Throwable("UserData is null"))

        if (id != event.creator) return Result.failure(Throwable("User is not event creator"))

        groupRepository.update(
            targetGroup.id, mapOf(
                Group::events.name to FieldValue.arrayUnion(event) // maybe not work
            )
        ).onFailure { return Result.failure(it) }

        return Result.success(Unit)
    }

    override suspend fun deleteEvent(event: GroupEvent): Result<Unit> {
        val targetGroup = userStateHolder.groups.value.let {
            it.forEach { group ->
                if (event in group.events)
                    return@let group
            }
            return@let null
        } ?: return Result.failure(Throwable("User groups don't have this event"))

        val id = userStateHolder.userData.value?.id
            ?: return Result.failure(Throwable("UserData is null"))

        if (id != event.creator) return Result.failure(Throwable("User is not event creator"))

        groupRepository.update(
            targetGroup.id, mapOf(
                Group::events.name to FieldValue.arrayRemove(event)
            )
        ).onFailure { e -> return Result.failure(e) }
        return Result.success(Unit)
    }

    private fun userInGroup(group: Group): Boolean {
        return userStateHolder.userData.value?.groups
            ?.let { return@let (group.id in it) }
            ?: false
    }
}