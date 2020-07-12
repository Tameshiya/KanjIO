package jp.rei.andou.kanjio.presentation

import jp.rei.andou.kanjio.data.KanjiGroupLevel
import jp.rei.andou.kanjio.domain.KanjiInteractor
import jp.rei.andou.kanjio.domain.UserInteractor
import jp.rei.andou.kanjio.mainDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/*todo
    1) 漢字検索 (漢字、音読み、くんよみ　入力）
   3）特別なマークを読み込む。漢字の英語訳を出力。
 */
class KanjiPresenter constructor(
    private val kanjiInteractor: KanjiInteractor,
    private val userInteractor: UserInteractor
) : CommonPresenter<KanjiListView>(), CoroutineScope {

    override val coroutineContext: CoroutineContext = mainDispatcher() //todo + exceptionHandler

    fun startFlow() {
        launch {
            userInteractor.getCurrentKanjiGroupLevel()
                .also(::renderCurrentKanjiList)
        }
    }

    fun renderCurrentKanjiList(kanjiLevel: KanjiGroupLevel) {
        //todo move to different SelectorDialogPresenter
        //view?.setTitle(kanjiGroup ?: kanjiInteractor.getCurrentKanjiGroup())
        kanjiInteractor.getKanjiListByLevel(kanjiLevel.levelId)
            .also { list -> view?.showList(list) }
    }

}