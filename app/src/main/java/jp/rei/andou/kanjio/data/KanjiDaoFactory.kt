package jp.rei.andou.kanjio.data

import jp.rei.andou.kanjio.data.dao.KanjiDao
import model.KanjiGroup

class KanjiDaoFactory(private val database: KanjiDatabase) {
    
    fun createKanjiDaoFor(kanjiGroup: KanjiGroup): KanjiDao = when(kanjiGroup) {
        KanjiGroup.JLPT -> database.getJlptKanjiDao()
        KanjiGroup.REVISED_JLPT -> database.getRevisedJlptKanjiDao()
        KanjiGroup.FREQ -> database.getFreqKanjiDao()
        KanjiGroup.HEISIG -> database.getHeisigKanjiDao()
        KanjiGroup.REVISED_HEISIG -> database.getRevisedHeisigDao()
        KanjiGroup.JOUYOU -> database.getJouyouKanjiDao()
        KanjiGroup.JOUYOU_REVISED -> database.getRevisedJouyouKanjiDao()
        KanjiGroup.KANKEN -> database.getKankenKanjiDao()
        KanjiGroup.KIC -> database.getKicKanjiDao()
        KanjiGroup.KKLC -> database.getKklcKanjiDao()
    }

}
