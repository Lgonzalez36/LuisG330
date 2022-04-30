package com.example.worddictionaryapp.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Word (
   var word: String,
   val shortDef1: String,
   var shortDef2: String,
   var shortDef3: String,
   var img: String,
   var status: Boolean = false
)