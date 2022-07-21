package com.example.tripjump.Category


interface  IGetURLStringCategory {
    fun GetString(): List<String>
}

interface IGetViewCategory {
    fun GetCaption(): String
//    fun GetMark()   //おいおい欲しいね
}

data class OnlyKeywordCategory(val keyword: List<String>, val caption: String) : IGetURLStringCategory, IGetViewCategory{
    override fun GetString(): List<String> {
/*        var result = ""
        keyword.forEach { str ->
            result += str + "+"
        }
        // "+"を取り除く
        result = result.take(result.length - 1)
        return "&keyword=" + result*/
        return keyword
    }

    override fun GetCaption(): String {
        return caption
    }
}

data class OnlyTypeCategory(val type: List<String>, val caption: String) : IGetURLStringCategory, IGetViewCategory {
    override fun GetString(): List<String> {
/*    override fun GetString(): String {
        var result = ""
        type.forEach { str ->
            result += str + "+"
        }
        // "+"を取り除く
        result = result.take(result.length - 1)
        return "&type=" + result
    }*/
        return type
    }

    override fun GetCaption(): String {
        return caption
    }
}

data class KeywordAndTypeCategory(val keyword: List<String>, val type: List<String>, val caption: String) : IGetURLStringCategory, IGetViewCategory {
    override fun GetString(): List<String> {
/*    override fun GetString(): String {
        var keywordResult = ""
        type.forEach { str ->
            keywordResult += str + "+"
        }
        // "+"を取り除く
        keywordResult = keywordResult.take(keywordResult.length - 1)

        var typeResult = ""
        type.forEach { str ->
            typeResult += str + "+"
        }
        // "+"を取り除く
        typeResult = typeResult.take(typeResult.length - 1)
        return "&keyword=" + keywordResult + "&type=" + keywordResult*/
        return keyword
    }
    override fun GetCaption(): String {
        return caption
    }
}