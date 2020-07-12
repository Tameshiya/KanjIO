package jp.rei.andou.kanjio.data

interface UserRepository {

    fun selectStudyingKanjiGroupLevel(kanjiGroupLevel: KanjiGroupLevel)

    fun getCurrentKanjiGroupLevel(): KanjiGroupLevel?
}