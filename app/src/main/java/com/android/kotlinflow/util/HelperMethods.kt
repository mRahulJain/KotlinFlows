package com.android.kotlinflow.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

fun doLongRunningTask(): Flow<Int> {
    return flow {
        delay(2000)
        val randomNumber = (0..2).random()
        if (randomNumber == 0) {
            throw IOException()
        } else if (randomNumber == 1) {
            throw IndexOutOfBoundsException()
        }
        delay(2000)
        emit(randomNumber)
    }
}