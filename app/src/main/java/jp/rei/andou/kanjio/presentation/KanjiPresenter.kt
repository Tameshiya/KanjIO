package jp.rei.andou.kanjio.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.rei.andou.kanjio.data.model.KanjiGroup
import jp.rei.andou.kanjio.domain.KanjiInteractor

/*todo
    1) 漢字検索 (漢字、音読み、くんよみ　入力）
    2)　漢字リスト選択
   3）特別なマークを読み込む。漢字の英語訳を出力。
 */
class KanjiPresenter(
    private val widget: KanjiListWidget,
    private val interactor: KanjiInteractor
) {

    //todo clean
    private val compositeDisposable = CompositeDisposable()

    init {
        renderKanjiList(interactor.getCurrentKanjiGroup())
    }

    private fun Disposable.composeDisposable() {
        compositeDisposable.addAll(this)
    }

    fun setNewKanjiGroup(kanjiGroup: KanjiGroup) {
        interactor.changeKanjiGroup(kanjiGroup)
        renderKanjiList(kanjiGroup)
    }

    private fun renderKanjiList(kanjiGroup: KanjiGroup) {
        widget.setTitle(kanjiGroup)
        interactor.getKanjiListByLevel(2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                widget.showList(list)
            }
            .composeDisposable()
    }

}