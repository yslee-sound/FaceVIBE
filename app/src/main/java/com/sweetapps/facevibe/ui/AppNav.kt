package com.sweetapps.facevibe.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.io.File
import kotlin.random.Random

@Composable
fun AppNav() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "home") {
        composable("home") {
            CameraMainScreen(
                onCaptured = { file ->
                    val path = Uri.encode(file.absolutePath)
                    // 변경: 촬영 후 미리보기 화면으로 이동
                    nav.navigate("preview?path=$path")
                },
                onHistoryClick = { record ->
                    val encoded = Uri.encode(record.imagePath)
                    // fortune 파라미터가 필요한 라우트에 기본값(-1)을 전달
                    nav.navigate("result?type=${record.result.type}&score=${record.result.score}&path=$encoded&fortune=-1")
                }
            )
        }
        // 새로 추가: 미리보기 화면
        composable(
            route = "preview?path={path}",
            arguments = listOf(
                navArgument("path") { type = NavType.StringType; nullable = false }
            )
        ) { backStackEntry ->
            val path = backStackEntry.arguments?.getString("path")!!
            val decoded = Uri.decode(path)
            PreviewScreen(
                imagePath = decoded,
                onBack = { nav.popBackStack() },
                onAnalyzeClick = { p ->
                    val encoded = Uri.encode(p)
                    nav.navigate("analyzing?path=$encoded")
                }
            )
        }
        composable(
            route = "analyzing?path={path}",
            arguments = listOf(
                navArgument("path") { type = NavType.StringType; nullable = false }
            )
        ) { backStackEntry ->
            val path = backStackEntry.arguments?.getString("path")!!
            AnalyzingScreen(
                imagePath = Uri.decode(path),
                onFinished = { result ->
                    val decodedPath = Uri.decode(path)
                    // 분석 완료 시 히스토리에 추가
                    runCatching { AnalysisHistoryRepository.add(File(decodedPath), result) }
                    // 결과 화면으로 이동: 분석 화면은 제거하고, 미리보기는 유지
                    val type = result.type
                    val score = result.score
                    val p = Uri.encode(decodedPath)
                    val fortuneIndex = Random.nextInt(0, 12) // 랜덤 인덱스 생성 (0..11)
                    nav.popBackStack() // analyzing 제거
                    nav.navigate("result?type=$type&score=$score&path=$p&fortune=$fortuneIndex")
                },
                onBack = { nav.popBackStack() }
            )
        }
        composable(
            route = "result?type={type}&score={score}&path={path}&fortune={fortune}",
            arguments = listOf(
                navArgument("type") { type = NavType.StringType },
                navArgument("score") { type = NavType.IntType },
                navArgument("path") { type = NavType.StringType },
                // 정수형 NavType은 nullable을 사용하면 런타임 에러가 발생하므로 제거합니다.
                navArgument("fortune") { type = NavType.IntType; defaultValue = -1 }
            )
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: "INFP"
            val score = backStackEntry.arguments?.getInt("score") ?: 85
            val path = backStackEntry.arguments?.getString("path")
            val fortuneArg = backStackEntry.arguments?.getInt("fortune") ?: -1
            val fortuneIndex = if (fortuneArg >= 0) fortuneArg else null
            ResultScreen(
                path?.let { Uri.decode(it) },
                type,
                score,
                fortuneIndex,
                onBack = { nav.popBackStack() }
            )
        }
    }
}
