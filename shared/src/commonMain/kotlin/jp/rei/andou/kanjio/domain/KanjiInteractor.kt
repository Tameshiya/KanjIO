package jp.rei.andou.kanjio.domain

import jp.rei.andou.kanjio.data.Kanji
import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.data.KanjiGroupLevel

//UseCaseずつに分ける。
interface KanjiInteractor {
    
    fun getKanjiListByLevel(level: Int): List<Kanji>

    fun getGroupsList(): List<KanjiGroup>

    fun getGroupsAssociatedLevelsList(): Map<KanjiGroup, List<KanjiGroupLevel>>
}