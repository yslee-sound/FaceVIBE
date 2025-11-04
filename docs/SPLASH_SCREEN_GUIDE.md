# 스플래시 화면 SVG 교체 방법

## 현재 상태
- 스플래시 화면이 성공적으로 구현되었습니다
- 임시 아이콘(`splash_icon.xml`)이 사용 중입니다

## SVG 파일을 스플래시 아이콘으로 교체하는 방법

### 방법 1: Android Studio의 Vector Asset 도구 사용 (권장)

1. Android Studio에서 프로젝트를 엽니다
2. `app/src/main/res/drawable` 폴더를 우클릭
3. **New > Vector Asset** 선택
4. **Local file (SVG, PSD)** 라디오 버튼 선택
5. Path에서 SVG 파일을 선택
6. Name을 `splash_icon`으로 입력 (기존 파일 덮어쓰기)
7. **Next > Finish** 클릭

### 방법 2: 온라인 변환기 사용

1. https://svg2vector.com/ 또는 https://inloop.github.io/svg2android/ 방문
2. SVG 파일을 업로드
3. 생성된 Vector Drawable XML 코드를 복사
4. `app/src/main/res/drawable/splash_icon.xml` 파일의 내용을 교체

### 방법 3: 수동으로 교체

SVG 파일을 `app/src/main/res/drawable/` 폴더에 복사한 후:
```
app/src/main/res/drawable/your_svg_file.svg
```

그리고 `app/src/main/res/values/themes.xml`에서 아이콘 참조를 변경:
```xml
<item name="windowSplashScreenAnimatedIcon">@drawable/your_svg_file</item>
```

## 색상 커스터마이징

`app/src/main/res/values/themes.xml`에서 다음 항목을 수정할 수 있습니다:

```xml
<style name="Theme.App.Starting" parent="Theme.SplashScreen">
    <!-- 스플래시 배경색 변경 -->
    <item name="windowSplashScreenBackground">@color/your_color</item>
    
    <!-- 아이콘 변경 -->
    <item name="windowSplashScreenAnimatedIcon">@drawable/your_icon</item>
    
    <!-- 아이콘 배경색 (선택사항) -->
    <item name="windowSplashScreenIconBackgroundColor">@color/your_color</item>
</style>
```

새로운 색상은 `app/src/main/res/values/colors.xml`에 추가할 수 있습니다.

## 테스트

앱을 빌드하고 실행하면 스플래시 화면이 표시됩니다:
```bash
./gradlew installDebug
```

## 참고사항

- Android 12(API 31) 이상에서는 네이티브 스플래시 화면 API를 사용합니다
- 이전 버전(API 26-30)에서는 SplashScreen 호환 라이브러리가 동일한 경험을 제공합니다
- 스플래시 아이콘의 권장 크기는 288dp x 288dp입니다
- 아이콘 주변에는 66dp의 안전 영역이 있습니다(실제 아이콘 크기는 152dp x 152dp 권장)

