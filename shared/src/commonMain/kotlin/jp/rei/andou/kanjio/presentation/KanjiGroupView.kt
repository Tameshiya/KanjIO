package jp.rei.andou.kanjio.presentation

interface KanjiGroupView : CommonView {

    fun showKanjiGroupsDialog(
        kanjiGroupsLabels: List<String>,
        initialKanjiGroupsLevelsLabels: List<String>
    )

    fun updateLevels(kanjiLevelsLabels: List<String>)

    fun updateTitle(kanjiGroupTitle: String)

}
