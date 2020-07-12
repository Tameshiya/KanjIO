package jp.rei.andou.kanjio.domain

import jp.rei.andou.kanjio.data.*

class KanjiInteractorImpl(
    private var kanjiRepository: KanjiRepository,
    private var groupRepository: KanjiGroupRepository
) : KanjiInteractor {

    private var kanjiGroupsCache: List<KanjiGroup>? = null
    private var groupsAssociatedLevelsMap: Map<KanjiGroup, List<KanjiGroupLevel>>? = null

    override fun getKanjiListByLevel(level: Int): List<Kanji> {
        return kanjiRepository.getKanjiByLevel(level)
    }

    override fun getGroupsList(): List<KanjiGroup> {
        return kanjiGroupsCache ?: groupRepository.getKanjiGroupsList().let { groupList ->
            kanjiGroupsCache = groupList
            return@let groupList
        }
    }

    override fun getGroupsAssociatedLevelsList(): Map<KanjiGroup, List<KanjiGroupLevel>> {
        val allKanjiGroupLevels: Map<Int, List<KanjiGroupLevel>> = groupRepository.getAllKanjiGroupLevels()
            .groupBy{ it.groupId }
        return groupsAssociatedLevelsMap ?:
        getGroupsList()
            .let { groupsList ->
                val result = allKanjiGroupLevels.mapKeys { groupId -> groupsList.find { it.id == groupId.key }!! }
                groupsAssociatedLevelsMap = result
                return@let result
            }
    }
}