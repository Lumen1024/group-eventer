package com.lumen1024.data

import kotlinx.coroutines.flow.Flow

typealias FlowResult<T> = Flow<Result<T>>
typealias FlowResultList<T> = Flow<Result<List<T>>>