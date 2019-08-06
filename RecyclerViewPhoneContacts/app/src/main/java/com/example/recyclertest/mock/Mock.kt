package com.example.recyclertest.mock

data class Mock(var name: String, var value: Int) {

    fun getValue(): String {
        return value.toString()
    }


}