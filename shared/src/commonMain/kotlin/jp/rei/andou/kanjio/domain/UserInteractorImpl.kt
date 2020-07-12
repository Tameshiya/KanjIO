package jp.rei.andou.kanjio.domain

import jp.rei.andou.kanjio.data.KanjiGroupLevel
import jp.rei.andou.kanjio.data.KanjiGroupRepository
import jp.rei.andou.kanjio.data.UserRepository

class UserInteractorImpl(
    private val userRepository: UserRepository,
    private val groupRepository: KanjiGroupRepository
) : UserInteractor {

    //todo cache
    override fun getCurrentKanjiGroupLevel(): KanjiGroupLevel {
        return userRepository.getCurrentKanjiGroupLevel() ?: groupRepository.getDefaultKanjiGroupLevel()
    }

    override fun selectKanjiGroupLevel(kanjiGroupLevel: KanjiGroupLevel) {
        userRepository.selectStudyingKanjiGroupLevel(kanjiGroupLevel)
    }
}