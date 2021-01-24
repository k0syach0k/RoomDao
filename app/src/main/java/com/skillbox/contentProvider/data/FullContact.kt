package com.skillbox.contentProvider.data

data class FullContact(
    val id: Long,
    val prefixName: String,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val suffixName: String,
    val phoneNumbers: List<String>,
    val email: List<String>
)
