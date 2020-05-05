package jp.rei.andou.kanjio.presentation

import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.domain.KanjiInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge

/*todo
    1) 漢字検索 (漢字、音読み、くんよみ　入力）
   3）特別なマークを読み込む。漢字の英語訳を出力。
 */
class KanjiPresenter constructor(
    private val interactor: KanjiInteractor
) : CommonPresenter<KanjiListView>() {

    suspend fun startFlow() {
        renderCurrentKanjiList(interactor.getCurrentKanjiGroup())
    }

    fun test() {
        println("test")
    }

    suspend fun setNewKanjiGroup(kanjiGroup: KanjiGroup) {
        //interactor.changeKanjiGroup(kanjiGroup)
        renderCurrentKanjiList(kanjiGroup)
    }

    suspend fun changeNewKanjiGroupLevel(level: Int) {
        view?.setTitle(interactor.getCurrentKanjiGroup())
        interactor.getKanjiListByLevel(level)
            .collect { list ->
                view?.showList(list)
            }
    }

    fun getKanjiGroupLevels(): Flow<Int> {
        return interactor.getCurrentKanjiGroupLevel()
    }

    private suspend fun renderCurrentKanjiList(kanjiGroup: KanjiGroup? = null) {
        view?.setTitle(kanjiGroup ?: interactor.getCurrentKanjiGroup())
        interactor.getCurrentKanjiGroupLevel()
            .flatMapMerge { level -> return@flatMapMerge interactor.getKanjiListByLevel(level) }
            .collect { list ->
                view?.showList(list)
            }
    }

}