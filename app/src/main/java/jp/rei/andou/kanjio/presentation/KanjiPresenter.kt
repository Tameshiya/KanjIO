package jp.rei.andou.kanjio.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.rei.andou.kanjio.data.repositories.KanjiRepository

class KanjiPresenter(
    widget: KanjiListWidget,
    repository: KanjiRepository
) {

    //todo clean
    private val compositeDisposable = CompositeDisposable()

    init {
        repository.getKanjiListByJLPTLevel(2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                widget.showList(list)
            }
            .composeDisposable()
    }

    private fun Disposable.composeDisposable() {
        compositeDisposable.addAll(this)
    }

}