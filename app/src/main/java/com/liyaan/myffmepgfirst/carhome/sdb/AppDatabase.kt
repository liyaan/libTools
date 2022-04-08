package com.liyaan.myffmepgfirst.carhome.sdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.liyaan.myffmepgfirst.carhome.entity.SubjectEntity

@Database(
    entities = [SubjectEntity::class],version = 1,exportSchema = false
)
abstract class AppDatabase:RoomDatabase() {
    abstract fun subjectDao():SubjectDao
}