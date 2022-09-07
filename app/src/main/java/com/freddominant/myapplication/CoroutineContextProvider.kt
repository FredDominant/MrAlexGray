package com.freddominant.myapplication

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoroutineContextProvider @Inject constructor() {
    val io = Dispatchers.IO
}