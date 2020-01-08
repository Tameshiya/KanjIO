package jp.rei.andou.kanjio.data.mappers

import jp.rei.andou.kanjio.data.entities.Kanji

fun Kanji.toKanjiModel(): model.Kanji = model.Kanji(code, kunReading, level, meaning, onReading)