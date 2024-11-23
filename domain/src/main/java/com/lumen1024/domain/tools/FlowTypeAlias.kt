package com.lumen1024.domain.tools

import kotlinx.coroutines.flow.Flow

typealias FlowResult<T> = Flow<Result<T>>
typealias FlowResultList<T> = Flow<Result<List<T>>>
typealias FlowList<T> = Flow<List<T>>
typealias FlowMap<K, V> = Flow<Map<K, V>>