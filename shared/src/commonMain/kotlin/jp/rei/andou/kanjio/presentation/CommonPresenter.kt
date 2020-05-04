package jp.rei.andou.kanjio.presentation

abstract class CommonPresenter<T : CommonView> {

    protected var view: T? = null

    fun attachView(view: T) {
        this.view = view
    }

}