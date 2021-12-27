package com.example.hello.utils

import com.fasterxml.jackson.databind.ObjectMapper

class JsonHelper {
    companion object {
        var objectMapper = ObjectMapper()

        @JvmStatic
        fun objectToJson(obj: Any?): String{
            return objectMapper.writeValueAsString(obj)
        }
    }
}