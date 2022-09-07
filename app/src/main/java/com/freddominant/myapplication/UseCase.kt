package com.freddominant.myapplication

interface UseCase<T, R> {
    suspend fun execute(arg: T): Result<R>
}