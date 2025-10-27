package com.sweetapps.facevibe.ui

import kotlin.math.abs
import java.io.File

/** 분석 결과 모델 (간단 MVP) */
data class VibeResult(val type: String, val score: Int)

/**
 * 간이 VIBE 추정기: 파일명 해시로 결정적 결과 생성 (MVP 더미)
 */
fun analyzeVibe(file: File): VibeResult {
    val types = listOf(
        "INTJ","INTP","ENTJ","ENTP",
        "INFJ","INFP","ENFJ","ENFP",
        "ISTJ","ISFJ","ESTJ","ESFJ",
        "ISTP","ISFP","ESTP","ESFP"
    )
    val hash = abs(file.name.hashCode())
    val type = types[hash % types.size]
    val score = 70 + (hash % 31) // 70~100
    return VibeResult(type, score)
}

