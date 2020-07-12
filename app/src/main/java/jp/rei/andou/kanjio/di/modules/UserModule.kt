package jp.rei.andou.kanjio.di.modules

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import jp.rei.andou.kanjio.data.*
import jp.rei.andou.kanjio.domain.UserInteractor
import jp.rei.andou.kanjio.domain.UserInteractorImpl

@Module
class UserModule {

    @Provides
    fun provideUserRepository(sharedPreferences: SharedPreferences): UserRepository {
        return UserRepositoryImpl(KanjiPreferences(sharedPreferences))
    }

    @Provides
    fun provideUserInteractor(
        userRepository: UserRepository,
        groupRepository: KanjiGroupRepository
    ): UserInteractor {
        return UserInteractorImpl(userRepository, groupRepository)
    }
}
