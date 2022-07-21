package com.example.tripjump.langageData

/*
時間があったら多言語化対応したい。
 */
data class LanguageDataClass(val language: String): IGetLanguageString {
    override fun GetString(): String {
        return "&language=" + language
    }
}

interface IGetLanguageString{
    fun GetString(): String
}