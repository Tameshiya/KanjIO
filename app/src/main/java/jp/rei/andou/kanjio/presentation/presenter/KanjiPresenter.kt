package jp.rei.andou.kanjio.presentation.presenter

import android.util.Log
import jp.rei.andou.kanjio.domain.KanjiInteractor
import jp.rei.andou.kanjio.presentation.view.KanjiListView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.launch
import model.KanjiGroup
import javax.inject.Inject

/*todo
    1) 漢字検索 (漢字、音読み、くんよみ　入力）
   3）特別なマークを読み込む。漢字の英語訳を出力。
 */
class KanjiPresenter @Inject constructor(
    private val interactor: KanjiInteractor
) : CommonPresenter<KanjiListView>() {

    fun init() {
        Log.e("INIT", "START")
        MainScope().launch {
            renderCurrentKanjiList(interactor.getCurrentKanjiGroup())
        }
        Log.e("INIT", "FAILED")
    }

    suspend fun setNewKanjiGroup(kanjiGroup: KanjiGroup) {
        interactor.changeKanjiGroup(kanjiGroup)
        renderCurrentKanjiList(kanjiGroup)
    }

    private suspend fun renderCurrentKanjiList(kanjiGroup: KanjiGroup? = null) {
        view?.setTitle(kanjiGroup ?: interactor.getCurrentKanjiGroup())
        interactor.getCurrentKanjiGroupLevel()
            .flatMapMerge { level -> return@flatMapMerge interactor.getKanjiListByLevel(level) }
            .collect { list ->
                view?.showList(list)
            }
    }

    fun getKanjiGroupLevels(): Flow<Int> = interactor.getCurrentKanjiGroupLevel()

    suspend fun changeNewKanjiGroupLevel(level: Int) {
        view?.setTitle(interactor.getCurrentKanjiGroup())
        interactor.getKanjiListByLevel(level)
            .collect { list ->
                view?.showList(list)
            }
    }
}