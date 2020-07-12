package jp.rei.andou.kanjio.data

interface KanjiGroupRepository {

    fun getKanjiGroupsList(): List<KanjiGroup>

    fun getKanjiGroupLevelsList(groupId: Int): List<KanjiGroupLevel>

    fun getDefaultKanjiGroupLevel(): KanjiGroupLevel

    fun getAllKanjiGroupLevels(): List<KanjiGroupLevel>
}
