# 스플래시 화면 구현 완료 ✅

## 변경된 파일들

### 1. `gradle/libs.versions.toml`
- 스플래시 화면 라이브러리 버전 추가 (1.0.1)

### 2. `app/build.gradle.kts`
- `androidx.core:core-splashscreen` 의존성 추가

### 3. `app/src/main/res/values/themes.xml`
- `Theme.App.Starting` 스플래시 테마 추가
- 배경색, 아이콘, 전환 설정 포함

### 4. `app/src/main/res/drawable/splash_icon.xml`
- 임시 스플래시 아이콘 생성 (얼굴 이모지 스타일)
- **SVG 파일로 교체 필요**

### 5. `app/src/main/AndroidManifest.xml`
- 앱과 MainActivity 테마를 `Theme.App.Starting`으로 변경

### 6. `app/src/main/java/com/sweetapps/facevibe/MainActivity.kt`
- `installSplashScreen()` 호출 추가

## SVG 파일 추가 방법

### 가장 쉬운 방법: Android Studio 사용

1. Android Studio에서 프로젝트 열기
2. `app/src/main/res/drawable` 폴더 우클릭
3. **New → Vector Asset** 선택
4. **Local file (SVG, PSD)** 선택
5. SVG 파일 선택
6. Name을 `splash_icon`으로 입력
7. **Next → Finish**

### 온라인 변환 (Android Studio 없이)

1. https://svg2vector.com/ 방문
2. SVG 파일 업로드
3. 생성된 XML 코드 복사
4. `app/src/main/res/drawable/splash_icon.xml` 내용 교체

## 커스터마이징

### 배경색 변경
`app/src/main/res/values/colors.xml`에 새 색상 추가:
```xml
<color name="splash_background">#YOUR_COLOR</color>
```

`app/src/main/res/values/themes.xml`에서 적용:
```xml
<item name="windowSplashScreenBackground">@color/splash_background</item>
```

### 스플래시 지속 시간 제어 (선택사항)

`MainActivity.kt`에서 더 정교한 제어:
```kotlin
private var keepSplashScreen = true

override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen().apply {
        setKeepOnScreenCondition {
            keepSplashScreen
        }
    }
    super.onCreate(savedInstanceState)
    
    // 데이터 로딩 후
    lifecycleScope.launch {
        // 초기화 작업...
        delay(2000) // 2초 예시
        keepSplashScreen = false
    }
    // ...
}
```

## 다음 단계

1. ✅ SVG 파일을 Vector Drawable로 변환
2. ✅ 색상 테마 커스터마이징
3. ✅ 앱 빌드 및 테스트
4. ✅ 필요시 스플래시 지속 시간 조정

## 빌드 및 실행

```bash
# 디버그 APK 빌드
gradlew.bat assembleDebug

# 기기에 설치
gradlew.bat installDebug

# 또는 Android Studio에서 Run 버튼 클릭
```

---
자세한 내용은 `docs/SPLASH_SCREEN_GUIDE.md`를 참고하세요.

