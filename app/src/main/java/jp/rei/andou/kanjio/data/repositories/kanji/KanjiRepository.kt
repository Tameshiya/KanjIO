package jp.rei.andou.kanjio.data.repositories.kanji

import io.reactivex.Maybe
import jp.rei.andou.kanjio.data.entities.Kanji

interface KanjiRepository {

    fun getKanjiByLevel(level: Int): Maybe<List<Kanji>>
}