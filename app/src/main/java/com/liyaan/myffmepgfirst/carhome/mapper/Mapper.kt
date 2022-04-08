package com.liyaan.myffmepgfirst.carhome.mapper

interface Mapper<I,O> {
    fun map(input:I):O
}