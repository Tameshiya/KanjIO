package jp.rei.andou.kanjio.data.model

import jp.rei.andou.kanjio.data.dao.KanjiDao
import jp.rei.andou.kanjio.data.repositories.kanji.*

fun KanjiGroup.toRepository(kanjiDao: KanjiDao) = when(this) {
    KanjiGroup.JLPT -> JLPTKanjiRepository(kanjiDao)
    KanjiGroup.REVISED_JLPT -> RevisedJLPTKanjiRepository(kanjiDao)
    KanjiGroup.FREQ -> FreqKanjiRepository(kanjiDao)
    KanjiGroup.HEISIG -> HeisigKanjiRepository(kanjiDao)
    KanjiGroup.REVISED_HEISIG -> RevisedHeisigKanjiRepository(kanjiDao)
    KanjiGroup.JOUYOU -> JouyouKanjiRepository(kanjiDao)
    KanjiGroup.JOUYOU_REVISED -> RevisedJouyouKanjiRepository(kanjiDao)
    KanjiGroup.KANKEN -> KankenKanjiRepository(kanjiDao)
    KanjiGroup.KIC -> KicKanjiRepository(kanjiDao)
    KanjiGroup.KKLC -> KklcKanjiRepository(kanjiDao)
}

fun KanjiRepository.whatGroup(): KanjiGroup = when(this) {
    is JLPTKanjiRepository -> KanjiGroup.JLPT
    is RevisedJLPTKanjiRepository -> KanjiGroup.REVISED_JLPT
    is FreqKanjiRepository -> KanjiGroup.FREQ
    is HeisigKanjiRepository -> KanjiGroup.HEISIG
    is RevisedHeisigKanjiRepository -> KanjiGroup.REVISED_HEISIG
    is JouyouKanjiRepository -> KanjiGroup.JOUYOU
    is RevisedJouyouKanjiRepository -> KanjiGroup.JOUYOU_REVISED
    is KankenKanjiRepository -> KanjiGroup.KANKEN
    is KicKanjiRepository -> KanjiGroup.KIC
    is KklcKanjiRepository -> KanjiGroup.KKLC
    else -> TODO("make via sealed classes")
}