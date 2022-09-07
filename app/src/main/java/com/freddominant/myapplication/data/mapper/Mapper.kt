package com.freddominant.myapplication.data.mapper

interface Mapper<I,O> {
    fun map(input: I): O
}