package com.skillbox.roomdao.model.folder

import androidx.room.TypeConverter

class FolderConverter {

    @TypeConverter
    fun convertFolderToString(folder: Folder): String = folder.name

    @TypeConverter
    fun convertStringToFolder(string: String): Folder = Folder.valueOf(string)
}