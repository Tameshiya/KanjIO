package jp.rei.andou.kanjio.presentation.presenter

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.rei.andou.kanjio.data.model.KanjiGroup
import jp.rei.andou.kanjio.domain.KanjiInteractor
import jp.rei.andou.kanjio.presentation.view.KanjiListView
import javax.inject.Inject

/*todo
    1) 漢字検索 (漢字、音読み、くんよみ　入力）
   3）特別なマークを読み込む。漢字の英語訳を出力。
 */
class KanjiPresenter @Inject constructor(
    private val interactor: KanjiInteractor
) : CommonPresenter<KanjiListView>() {

    //todo clean
    private val compositeDisposable = CompositeDisposable()

    init {
        interactor.getCurrentKanjiGroupLevel()
            .subscribe { level ->
                renderKanjiList(interactor.getCurrentKanjiGroup(), level)
            }
            .composeDisposable()
    }

    fun setNewKanjiGroup(kanjiGroup: KanjiGroup) {
        interactor.changeKanjiGroup(kanjiGroup)
        interactor.getCurrentKanjiGroupLevel()
            .subscribe { level ->
                renderKanjiList(kanjiGroup, level)
            }
            .composeDisposable()
    }

    private fun renderKanjiList(kanjiGroup: KanjiGroup? = null, level: Int) {
        view?.setTitle(kanjiGroup ?: interactor.getCurrentKanjiGroup())
        interactor.getKanjiListByLevel(level)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                view?.showList(list)
            }
            .composeDisposable()
    }

    fun getKanjiGroupLevels(): Single<Int> {
        return interactor.getCurrentKanjiGroupLevel()
    }

    fun changeNewKanjiGroupLevel(level: Int) {
        renderKanjiList(level = level)
    }

    private fun Disposable.composeDisposable() {
        compositeDisposable.addAll(this)
    }
}