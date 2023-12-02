package com.android.kotlinflow.util

object Constants {

    const val MAIN_SCREEN = "main_screen"
    const val OTHER_SCREEN_ARGUMENT = "type"
    const val OTHER_SCREEN = "other_screen"

    const val SINGLE_API_CALL_SCREEN = "SINGLE API CALL"
    const val SERIES_API_CALL_SCREEN = "SERIES API CALL"
    const val PARALLEL_API_CALL_SCREEN = "PARALLEL API CALL"
    const val ROOM_DB_SCREEN = "ROOM DB"
    const val RETRY_SCREEN = "RETRY"
    const val RETRY_WHEN_SCREEN = "RETRY WHEN"
    const val CATCH_EXCEPTION_HANDLING_SCREEN = "CATCH EXCEPTION HANDLING"
    const val EMIT_ALL_EXCEPTION_HANDLING_SCREEN = "EMIT ALL EXCEPTION HANDLING"
    const val SHARED_FLOW_SCREEN = "SHARED FLOW"

    val list = listOf(
        SINGLE_API_CALL_SCREEN,
        SERIES_API_CALL_SCREEN,
        PARALLEL_API_CALL_SCREEN,
        ROOM_DB_SCREEN,
        RETRY_SCREEN,
        RETRY_WHEN_SCREEN,
        CATCH_EXCEPTION_HANDLING_SCREEN,
        EMIT_ALL_EXCEPTION_HANDLING_SCREEN,
        SHARED_FLOW_SCREEN
    )

    const val ROOM_TABLE_NAME = "API_USERS"
    const val ROOM_DATABASE_VERSION = 1
    const val ROOM_DATABASE_NAME = "API_USERS_DATABASE"
}