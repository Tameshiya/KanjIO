package jp.rei.andou.kanjio.presentation

import android.util.Log
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.rei.andou.kanjio.data.entities.Kanji
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
            .subscribeWith(object : MaybeObserver<List<Kanji>> {

                override fun onSubscribe(d: Disposable) {
                    Log.e("YEAH", "???")
                }

                override fun onError(e: Throwable) {
                    Log.e("FUUUCK", e.message ?: "whaaat?")
                }

                override fun onSuccess(t: List<Kanji>) {
                    Log.e("YEAH", "!!!")
                    widget.showList(t)
                }

                override fun onComplete() {
                    Log.e("COMPLETED", "timeout?")
                }

            })
            /*.subscribe { list ->
                widget.showList(list)
            }
            .composeDisposable()*/
    }

    private fun Disposable.composeDisposable() {
        compositeDisposable.addAll(this)
    }

}