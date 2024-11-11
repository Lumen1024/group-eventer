//package com.lumen1024.domain.usecase
//
//import com.lumen1024.domain.data.Event
//import com.lumen1024.domain.data.Group
//import com.lumen1024.domain.data.GroupColor
//import com.lumen1024.domain.data.TimeRange
//import com.lumen1024.domain.data.User
//import java.time.Instant
//
//interface UserActions {
//    suspend fun joinGroup(name: String, password: String): Result<Unit>
//    suspend fun createGroup(name: String, password: String, color: GroupColor): Result<Unit>
//    suspend fun leaveGroup(id: String): Result<Unit>
//
//    suspend fun updateGroup(groupId: String, data: Map<String, Any>): Result<Unit>
//    suspend fun transferAdministrator(groupId: String, user: User): Result<Unit>
//    suspend fun removeUserFromGroup(groupId: String, user: User): Result<Unit>
//
//    suspend fun voteEventTime(eventId: String, time: TimeRange): Result<Unit>
//    suspend fun setFinalEventTime(eventId: String, time: Instant): Result<Unit>
//
//    suspend fun createEvent(event: Event, group: Group): Result<Unit>
//    suspend fun updateEvent(event: Event): Result<Unit>
//    suspend fun deleteEvent(event: Event): Result<Unit>
//}