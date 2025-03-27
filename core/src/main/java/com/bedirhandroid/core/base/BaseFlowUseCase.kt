package com.bedirhandroid.core.base

import kotlinx.coroutines.flow.Flow

abstract class BaseFlowUseCase<P,R> {
    operator fun invoke(params: P) : Flow<R> = execute(params)

    protected abstract fun execute(params: P) : Flow<R>
}