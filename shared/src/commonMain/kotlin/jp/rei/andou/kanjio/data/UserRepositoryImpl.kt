package jp.rei.andou.kanjio.data

class UserRepositoryImpl(private val kanjiPreferences: KanjiPreferences) : UserRepository {

    override fun selectStudyingKanjiGroupLevel(kanjiGroupLevel: KanjiGroupLevel) {
        return kanjiPreferences.writeKanjiGroupLevel(kanjiGroupLevel)
    }

    override fun getCurrentKanjiGroupLevel(): KanjiGroupLevel? {
        return kanjiPreferences.readKanjiGroupLevel()
    }
}