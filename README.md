# Bike_app
자전거 점검 기록과 상황별 관리 방법을 제공하는 Android 및 웹 앱입니다.

## 웹 앱

`web/index.html`을 브라우저에서 열거나, 저장소 루트에서 `python -m http.server 8000`을 실행한 뒤 `http://localhost:8000/web/`을 엽니다. 날짜별 체크 기록은 현재 브라우저의 로컬 저장소에만 저장되며, Android 앱이나 다른 기기와 동기화되지 않습니다. 브라우저 데이터를 삭제하면 기록도 삭제됩니다.

### GitHub Pages 배포

저장소의 **Settings > Pages > Build and deployment > Source**에서 **GitHub Actions**를 선택합니다. 이후 `main` 브랜치의 `web/` 변경 시 또는 Actions의 **Deploy web app to GitHub Pages**를 수동 실행하면 `web/` 내용이 배포됩니다. 저장소가 프라이빗인 경우 GitHub 플랜 및 Pages 접근 권한에 따라 설정 가능 여부가 달라질 수 있습니다.

## 개발 환경

- JDK 21
- Android SDK Platform 36
- Android SDK Build Tools 36

## 디버그 APK 빌드

Android Studio에서 `android` 폴더를 열고 **Build > Build Bundle(s) / APK(s) > Build APK(s)**를 선택하거나, 저장소 루트에서 다음 명령을 실행합니다.

```sh
cd android
./gradlew assembleDebug
```

Windows에서는 `android` 폴더에서 `.\gradlew.bat assembleDebug`를 실행합니다. APK는 `android/app/build/outputs/apk/debug/app-debug.apk`에 생성됩니다.

Play 스토어 배포에는 서명된 릴리스 번들(AAB)을 사용하세요.
