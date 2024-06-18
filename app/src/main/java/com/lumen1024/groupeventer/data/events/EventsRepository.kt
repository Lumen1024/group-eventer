package com.lumen1024.groupeventer.data.events

interface EventsRepository {
    fun getEvents(groupId: Int)
    fun addEvent(groupId: Int, event: GroupEvent)
}