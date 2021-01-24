package com.skillbox.contentProvider.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PartialContact(
    val id: Long,
    val displayName: String
) : Parcelable
