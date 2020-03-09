package jp.rei.andou.kanjio.presentation.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import jp.rei.andou.kanjio.presentation.view.CommonView

@Suppress("LeakingThis")
abstract class CommonPresenter<T : CommonView> : LifecycleObserver {

    var view: T? = null

    fun attachView(view: T, lifecycle: Lifecycle? = null) {
        this.view = view
        lifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun releaseView() {
        view = null
    }
}
