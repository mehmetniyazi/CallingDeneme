package com.example.callingdeneme.data

// model/UserInfo.kt
data class UserInfo(
    val phoneNumber: String,
    val displayName: String
)


// utils/UserDataStore.kt
object UserDataStore {
    val knownUsers = listOf(
        UserInfo("6505551212", "Ahmet - Uygulama Üyesi"),
        UserInfo("+905322223333", "Mehmet - Gold Üye")
    )

    fun findUserByNumber(number: String): UserInfo? {
        return knownUsers.find { it.phoneNumber == number }
    }
}

