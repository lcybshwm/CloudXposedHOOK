package com.CloudHook.kele


import java.util.regex.Pattern.matches

object Type {
    private const val INT_PATTERN = """^-?[0-9]\d*$"""
    private const val LONG_PATTERN = """^-?[1-9]\d*[l|L]$"""
    private const val BOOLEAN_PATTERN = """(?i)true|false"""
    private const val STRING_PATTERN = """^-?0?[1-9]\d*[s|S]$"""
    private const val NULL_PATTERN = """(?i)null"""
    private const val STRING_EMPTY_PATTERN = """(?i)empty|空"""
    private const val BYTE_PATTERN = """^-?[1-9]\d*[b|B]$"""
    private const val SHORT_PATTERN = """^-?[1-9]\d*(?i)short$"""

    fun getDataTypeValue(value: String) = when{
        matches(BOOLEAN_PATTERN,value) -> value.toBoolean()
        matches(INT_PATTERN,value) -> value.toInt()
        matches(LONG_PATTERN,value) -> value.replace(Regex("""[l|L]"""),"").toLong()
        matches(STRING_PATTERN,value) -> value.replace(Regex("""[s|S]"""),"")
        matches(NULL_PATTERN,value) -> null
        matches(STRING_EMPTY_PATTERN,value) -> ""
        matches(BYTE_PATTERN,value) -> value.replace(Regex("""[b|B]"""),"").toByte()
        matches(SHORT_PATTERN,value) -> value.replace(Regex("""[(?i)short]"""),"").toShort()
        else -> value
    }
    fun getClassType(className:String) = when(className){
        "byte" -> Byte::class.java
        "int" -> Int::class.java
        "short" -> Short::class.java
        "long" -> Long::class.java
        "float" -> Float::class.java
        "double" -> Double::class.java
        "boolean" -> Boolean::class.java
        "string" -> String::class.java
        else -> null
    }
}