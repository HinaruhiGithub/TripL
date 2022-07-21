package com.example.tripjump.Category

import javax.inject.Singleton

class Collector constructor(): IGetViewList, IGetURLStrFromViewCategory {
    private val viewList: List<IGetViewCategory>
    private val searchList: List<IGetURLStringCategory>

    init {
        // ここにテストリストの作成をする。
        val raamen = OnlyKeywordCategory(listOf("ラーメン", "油そば"), "ラーメン")
        val cafe = OnlyKeywordCategory(listOf("喫茶店", "コーヒー", "パーラー", "カフェ"), "カフェ")
        val izakaya = OnlyKeywordCategory(listOf("居酒屋", "酒"), "居酒屋")
        val ge_sen = OnlyKeywordCategory(listOf("ゲームセンター", "ゲーセン"), "ゲームセンター")
        val italian = OnlyKeywordCategory(listOf("イタリアン", "スパゲティ", "パスタ", "ピザ"), "イタリアン")

//        val cafe = KeywordAndTypeCategory(listOf("喫茶店", "コーヒー", "パーラー"), listOf("cafe"), "カフェ")

        viewList = listOf<IGetViewCategory>(raamen, cafe, izakaya, ge_sen, italian)
        searchList = listOf<IGetURLStringCategory>(raamen, cafe, izakaya, ge_sen, italian)
    }

    override fun GetAllCategoryView(): List<IGetViewCategory> {
        return viewList
    }

    override fun GetURLStrFromViewCategory(category: IGetViewCategory): IGetURLStringCategory {
        searchList.forEach { obj ->
            if(obj === category) return obj
        }
        throw NullPointerException("そんなもんないわ")
    }
}