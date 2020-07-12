package jp.rei.andou.kanjio.data

import jp.rei.andou.kanjio.entities.GroupsLevelsQueries
import jp.rei.andou.kanjio.entities.Groups_levels as DBGroupLevel
import jp.rei.andou.kanjio.entities.KanjiGroupsQueries
import jp.rei.andou.kanjio.entities.Kanji_groups as DBKanjiGroup

class KanjiGroupRepositoryImpl(
    private val kanjiGroupsQueries: KanjiGroupsQueries,
    private val groupsLevelsQueries: GroupsLevelsQueries
) : KanjiGroupRepository {

    override fun getKanjiGroupsList(): List<KanjiGroup> {
        return kanjiGroupsQueries.getGroups().executeAsList().map{ it.toKanjiGroup() }
    }

    override fun getKanjiGroupLevelsList(groupId: Int): List<KanjiGroupLevel> {
        return groupsLevelsQueries.groupLevels(groupId.toLong())
            .executeAsList()
            .map { it.toKanjiGroupLevel() }
    }

    override fun getDefaultKanjiGroupLevel(): KanjiGroupLevel {
        return groupsLevelsQueries.defaultGroupLevel().executeAsOne().toKanjiGroupLevel()
    }

    override fun getAllKanjiGroupLevels(): List<KanjiGroupLevel> {
        return groupsLevelsQueries.allLevels().executeAsList().map { it.toKanjiGroupLevel() }
    }

    private fun DBKanjiGroup.toKanjiGroup(): KanjiGroup = KanjiGroup(id.toInt(), name, description)

    private fun DBGroupLevel.toKanjiGroupLevel(): KanjiGroupLevel {
        return KanjiGroupLevel(
            levelId = id.toInt(),
            level = level.toInt(),
            groupId = group_id.toInt(),
            name = name
        )
    }
}