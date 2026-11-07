# Bike_app
네이티브 Android 자전거 관리 앱입니다. 화면은 Android 네이티브 UI(Java)로 구성되며, 체크 기록은 기기에 저장됩니다.

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
