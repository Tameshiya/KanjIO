package jp.rei.andou.kanjio

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual fun mainDispatcher(): CoroutineDispatcher = Dispatchers.Main