package com.example.worddictionaryapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "word_table")
data class Dictionary(
    @PrimaryKey(autoGenerate = false)
    var word: String,

    @ColumnInfo(name = "short_def1")
    val shortDef1: String,

    @ColumnInfo(name = "short_def2")
    var shortDef2: String,

    @ColumnInfo(name = "short_def3")
    var shortDef3: String,

    @ColumnInfo(name = "img_Url")
    var img: String,

    @ColumnInfo(name = "status")
    var status: Boolean = false
)