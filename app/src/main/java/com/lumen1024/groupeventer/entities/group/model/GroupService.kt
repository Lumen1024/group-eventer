package com.lumen1024.groupeventer.entities.group.model

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.lumen1024.groupeventer.shared.model.RepositoryObjectChange
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.internal.toImmutableList
import javax.inject.Inject

class GroupService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val groupRepository: GroupRepository,
) {
//    private val _groups = mutableStateListOf<Group>()
//    val groups get() = MutableStateFlow(_groups.toImmutableList()).asStateFlow()
//
//    init {
//        groupRepository.listenChanges(emptyList()) { repositoryObjectChanges ->
//            for (change in repositoryObjectChanges) {
//                if (change.data === null) {
//                    continue
//                }
//
//                when (change.type) {
//                    RepositoryObjectChange.Type.ADDED -> _groups.add(change.data!!)
//
//                    RepositoryObjectChange.Type.MODIFIED -> _groups[
//                        _groups.indexOfFirst { group -> group.id === change.data!!.id }
//                    ] = change.data!!
//
//                    RepositoryObjectChange.Type.REMOVED -> _groups.remove(change.data!!)
//                }
//            }
//        }
//    }
}