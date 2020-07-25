package jp.rei.andou.kanjio.presentation

import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.data.KanjiGroupLevel
import jp.rei.andou.kanjio.domain.KanjiInteractor
import jp.rei.andou.kanjio.domain.UserInteractor
import jp.rei.andou.kanjio.mainDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class KanjiGroupsPresenter(
    private val userInteractor: UserInteractor,
    private val kanjiInteractor: KanjiInteractor
) : CommonPresenter<KanjiGroupView>(), CoroutineScope {

    @InternalCoroutinesApi
    override val coroutineContext: CoroutineContext = mainDispatcher() //todo + exceptionHandler

    var onKanjiGroupLevelSelectedListener: ((KanjiGroupLevel) -> Unit)? = null

    private lateinit var kanjiGroupsModel: KanjiGroupsModel

    init {
        launch {
            val kanjiGroupLevelsMap: Map<KanjiGroup, List<KanjiGroupLevel>> =
                kanjiInteractor.getGroupsAssociatedLevelsList()
            kanjiGroupsModel = KanjiGroupsModel(
                kanjiGroupLevelsMap,
                userInteractor.getCurrentKanjiGroupLevel().groupId
            )
        }
    }

    fun updateLevels(newPosition: Int) {
        kanjiGroupsModel.kanjiGroups[newPosition]
            .let { newKanjiGroup -> kanjiGroupsModel.kanjiGroupLevels[newKanjiGroup] }
            ?.let { view?.updateLevels(it.map {level -> level.name }) }
    }

    fun submitKanjiGroup(groupPosition: Int, levelPosition: Int) {
        val selectedGroup: KanjiGroup = kanjiGroupsModel.kanjiGroups.getOrNull(groupPosition) ?: return
        val kanjiGroupLevel: KanjiGroupLevel = kanjiGroupsModel.kanjiGroupLevels[selectedGroup]
            ?.getOrNull(levelPosition) ?: return
        selectKanjiGroupLevel(kanjiGroupLevel)
        onKanjiGroupLevelSelectedListener?.invoke(kanjiGroupLevel)
        view?.updateTitle(getTitle(selectedGroup, kanjiGroupLevel))
    }

    fun renderKanjiGroupsView() {
        view?.showKanjiGroupsDialog(
            kanjiGroupsModel.kanjiGroups.map { it.title },
            kanjiGroupsModel.getCurrentKanjiGroupLevel()?.map { it.name } ?: emptyList()
        )
        view?.updateTitle(
            getTitle(
                kanjiGroupsModel.currentKanjiGroup ?: return,
                kanjiGroupsModel.getCurrentKanjiGroupLevel()?.firstOrNull() ?: return
            )
        )
    }

    private fun getTitle(kanjiGroup: KanjiGroup, kanjiGroupLevel: KanjiGroupLevel): String {
        //todo resources????
        return "${kanjiGroup.title} (${kanjiGroupLevel.name})"
    }

    private fun selectKanjiGroupLevel(kanjiGroupLevel: KanjiGroupLevel) = launch {
            userInteractor.selectKanjiGroupLevel(kanjiGroupLevel)
    }
}