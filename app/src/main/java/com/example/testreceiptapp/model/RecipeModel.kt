package com.example.testreceiptapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "RecipeModel", indices = [Index("id")])
data class RecipeModel (
   @PrimaryKey(autoGenerate = true)
   var id: Int,
   var title: String,
   var type_id: Int,
   var type_desc: String,
   var time: Int,
   var description: String,
   var ingredient: String,
   var recipe: String,
   @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
   var image: ByteArray? = null
)
