package com.liyaan.myffmepgfirst.carhome.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubjectEntity(
    @PrimaryKey
    val id:Int,
    val title:String,
    val link:String,
    val envelopePic:String,
    val projectLink:String,
    val page:Int = 0
)
