package jp.rei.andou.kanjio.presentation

import jp.rei.andou.kanjio.domain.KanjiInteractor
import model.KanjiGroup

/*todo
    1) 漢字検索 (漢字、音読み、くんよみ　入力）
   3）特別なマークを読み込む。漢字の英語訳を出力。
 */
class KanjiPresenter(
    private val widget: KanjiListWidget,
    private val interactor: KanjiInteractor
) {

    init {
        renderKanjiList(interactor.getCurrentKanjiGroup(), interactor.getCurrentKanjiGroupLevel())
    }

    fun setNewKanjiGroup(kanjiGroup: KanjiGroup) {
        interactor.changeKanjiGroup(kanjiGroup)
        renderKanjiList(kanjiGroup, interactor.getCurrentKanjiGroupLevel())
    }

    private fun renderKanjiList(kanjiGroup: KanjiGroup? = null, level: Int) {
        widget.setTitle(kanjiGroup ?: interactor.getCurrentKanjiGroup())
        widget.showList(interactor.getKanjiListByLevel(level))
    }

    fun getKanjiGroupLevels(): Int {
        return interactor.getCurrentKanjiGroupLevel()
    }

    fun changeNewKanjiGroupLevel(level: Int) {
        renderKanjiList(level = level)
    }

}