package com.sweetapps.facevibe.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Suppress("UNUSED_PARAMETER")
@Composable
fun ResultScreen(
    imagePath: String?,
    type: String,
    score: Int,
    // 외부에서 전달된 운세 인덱스 (없으면 null)
    fortuneIndex: Int? = null,
    onBack: () -> Unit
) {
    // score는 현재 화면에 직접 사용되지 않음(분석 결과 정보로 보존).
    // @Suppress("UNUSED_PARAMETER")로 미사용 경고를 억제합니다.
    var bmp by remember { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) } // 오류 메시지 상태 추가

    LaunchedEffect(imagePath) {
        if (imagePath != null) {
            runCatching {
                BitmapFactory.decodeFile(imagePath)
            }.onSuccess { decoded ->
                bmp = decoded?.asImageBitmap()
            }.onFailure {
                errorMessage = "이미지를 불러오는 중 오류가 발생했습니다." // 오류 메시지 설정
            }
        }
    }

    if (errorMessage != null) {
        // 에러 화면도 전체 백그라운드가 흰색이므로 동일하게 처리
        Surface(color = Color.White) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage!!, color = Color.Black)
            }
        }
        return
    }

    // --- 운세 데이터 및 상태 추가 ---
    val fortunes = listOf(
        "오늘은 작은 직감이 큰 기회를 부른다." to "직감이 가는 쪽으로 소소하게 움직이면 뜻밖의 성과와 기회를 마주할 가능성이 큽니다. 상황을 열린 마음으로 관찰해 보세요.",
        "금전운이 살짝 따뜻해진다." to "작은 금전적 흐름이 생길 수 있으니 충동 지출을 자제하고 계획적으로 저축하거나 현명하게 투자해 보세요. 장기적 관점이 중요합니다.",
        "누군가의 도움으로 일이 풀린다." to "혼자서 버티기보다 주변의 도움을 받아보세요. 예상치 못한 조력자가 나타나 문제 해결의 실마리를 제공할 것입니다.",
        "감정 표현이 관계를 더 깊게 만든다." to "솔직한 감정 표현 한 마디가 오해를 풀고 관계를 더 단단하게 만듭니다. 타인의 입장도 배려하며 진심을 전해 보세요.",
        "창의적인 아이디어가 떠오른다." to "떠오르는 아이디어는 즉시 메모하고 실행 가능한 작은 단계로 정리해두면 나중에 큰 자산이 됩니다. 꾸준히 다듬어 보세요.",
        "과로 조심, 휴식이 필요하다." to "지금은 무리하지 말고 의도적으로 휴식을 취하세요. 짧은 휴식과 규칙적인 수면이 생산성과 기분 회복에 큰 도움이 됩니다.",
        "새로운 만남이 도움이 된다." to "예상치 못한 만남이 유익한 인연으로 이어질 수 있습니다. 열린 태도로 대화하면 뜻밖의 기회가 찾아올 것입니다.",
        "결정은 서두르지 말라." to "중요한 선택은 잠시 멈추고 정보를 더 수집한 뒤 신중히 판단하세요. 급한 결정은 나중에 후회로 이어질 수 있습니다.",
        "여행이나 외출이 기분 전환에 좋다." to "일상에서 벗어난 환경이 창의력과 기분 전환에 큰 도움을 줍니다. 짧은 외출이라도 계획해 보세요.",
        "소소한 실수가 큰 교훈이 된다." to "작은 실수도 귀중한 학습 기회입니다. 원인을 분석하고 재발 방지를 정리하면 더 큰 성과로 이어질 수 있습니다.",
        "오늘은 감사가 더 큰 행운을 부른다." to "감사의 표현은 관계를 돈독히 하고 새로운 기회를 불러옵니다. 주변에 고마움을 전하며 좋은 에너지를 나누세요.",
        "뜻하지 않은 행운이 한 번 스치고 간다." to "작은 가능성이라도 놓치지 마세요. 준비된 사람이 기회를 잡습니다. 관심을 기울이면 행운으로 연결될 수 있습니다."
    )
    var shownFortuneIndex by remember { mutableStateOf<Int?>(fortuneIndex) }
    // 메인 헤드라인(그라데이션 카드에 표시할 텍스트) — 운세가 선택되면 운세 문구를, 아니면 MBTI 타입을 표시
    val mainHeadline = if (shownFortuneIndex != null && shownFortuneIndex in fortunes.indices) {
        fortunes[shownFortuneIndex!!].first
    } else {
        type
    }
    // 화면 구성 정보로 content 박스 크기와 텍스트 크기 결정
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp
    val screenWidthDp = configuration.screenWidthDp
    val minBoxHeight = (screenHeightDp * 0.12f).dp
    val maxBoxHeight = (screenHeightDp * 0.32f).dp
    val contentFont = when {
        screenWidthDp >= 420 -> 20.sp
        screenWidthDp >= 360 -> 18.sp
        else -> 16.sp
    }
    val contentLineHeight = (contentFont.value * 1.3f).sp
    // --------------------------------

    // captureScreenBitmap 및 공유/저장 기능은 UI에서 제거되어 관련 코드를 정리했습니다.

    Surface(color = Color.White) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 상단바 (상태바 안전영역 패딩 적용)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(WindowInsets.statusBars.asPaddingValues())
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로", tint = Color.Black)
                }
                Text(
                    text = "분석 결과",
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

            // 얼굴 이미지(또는 플레이스홀더)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(320.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF0F0F0)),
                contentAlignment = Alignment.Center
            ) {
                if (bmp != null) {
                    Image(
                        bitmap = bmp!!,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(text = "이미지 로딩 중", color = Color(0xFF6B6B6B))
                }
            }

            Spacer(Modifier.height(16.dp))

            // VIBE 카드
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFFFE934C),
                                    Color(0xFFE456AC),
                                    Color(0xFF6B60F4)
                                )
                            )
                        )
                        .padding(18.dp)
                        .fillMaxWidth()
                ) {
                    Column {
                        Text(text = "당신의 VIBE 유형은?", color = Color.White.copy(alpha = 0.85f), fontSize = 14.sp)
                        Spacer(Modifier.height(8.dp))
                        // 메인 헤드라인: 선택된 운세 한 줄(또는 타입)
                        Text(
                            text = mainHeadline,
                            color = Color.White,
                            fontSize = 20.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                     }
                 }
             }

            Spacer(Modifier.height(8.dp))

            // --- 선택된 운세가 있으면 설명만 크게 표시 (더 많은 내용 노출) ---
            if (shownFortuneIndex != null && shownFortuneIndex in fortunes.indices) {
                val pair = fortunes[shownFortuneIndex!!]
                // 화면 비율을 고려해 content 박스와 텍스트를 동적으로 설정
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .heightIn(min = minBoxHeight, max = maxBoxHeight),
                    color = Color(0xFF132027),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    val scroll = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .verticalScroll(scroll)
                    ) {
                        Text(
                            text = pair.second,
                            color = Color(0xFF9BD0C9),
                            fontSize = contentFont,
                            lineHeight = contentLineHeight
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))
            }
            // ----------------------------------
         }
     }
 }
