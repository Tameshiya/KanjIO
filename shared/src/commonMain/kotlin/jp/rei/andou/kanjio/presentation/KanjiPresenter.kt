package jp.rei.andou.kanjio.presentation

import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.domain.KanjiInteractor
import jp.rei.andou.kanjio.mainDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/*todo
    1) 漢字検索 (漢字、音読み、くんよみ　入力）
   3）特別なマークを読み込む。漢字の英語訳を出力。
 */
class KanjiPresenter constructor(
    private val interactor: KanjiInteractor
) : CommonPresenter<KanjiListView>(), CoroutineScope {

    override val coroutineContext: CoroutineContext = mainDispatcher() //todo + exceptionHandler

    fun startFlow() {
        launch {
            interactor.getCurrentKanjiGroup()
                .also(::renderCurrentKanjiList)
        }
    }

    fun setNewKanjiGroup(kanjiGroup: KanjiGroup) {
        launch {
            //interactor.changeKanjiGroup(kanjiGroup)
            renderCurrentKanjiList(kanjiGroup)
        }
    }

    fun changeNewKanjiGroupLevel(level: Int) {
        launch {
            view?.setTitle(interactor.getCurrentKanjiGroup())
            interactor.getKanjiListByLevel(level)
                .also { list -> view?.showList(list) }
        }
    }

    fun getKanjiGroupLevels(): Int {
        return interactor.getCurrentKanjiGroupLevel()
    }

    private fun renderCurrentKanjiList(kanjiGroup: KanjiGroup? = null) {
        view?.setTitle(kanjiGroup ?: interactor.getCurrentKanjiGroup())
        interactor.getCurrentKanjiGroupLevel()
            .let(interactor::getKanjiListByLevel)
            .also { list -> view?.showList(list) }
    }

}