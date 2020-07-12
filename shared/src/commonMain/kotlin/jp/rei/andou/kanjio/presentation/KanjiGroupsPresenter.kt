package jp.rei.andou.kanjio.presentation

import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.data.KanjiGroupLevel
import jp.rei.andou.kanjio.domain.KanjiInteractor
import jp.rei.andou.kanjio.domain.UserInteractor
import jp.rei.andou.kanjio.mainDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class KanjiGroupsPresenter(
    private val userInteractor: UserInteractor,
    private val kanjiInteractor: KanjiInteractor
) : CommonPresenter<KanjiGroupView>(), CoroutineScope {

    override val coroutineContext: CoroutineContext = mainDispatcher() //todo + exceptionHandler

    var onKanjiGroupLevelSelectedListener: ((KanjiGroupLevel) -> Unit)? = null

    fun showKanjiGroupsView() {
        launch {
            val kanjiGroupLevelsMap: Map<KanjiGroup, List<KanjiGroupLevel>> =
                kanjiInteractor.getGroupsAssociatedLevelsList()
            view?.showKanjiGroupView(
                kanjiGroupLevelsMap,
                userInteractor.getCurrentKanjiGroupLevel().groupId
            )
        }
    }

    fun selectKanjiGroupLevel(kanjiGroupLevel: KanjiGroupLevel) {
        launch {
            userInteractor.selectKanjiGroupLevel(kanjiGroupLevel)
        }
    }

}