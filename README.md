# Bike_app
모바일 지원 자전거 관리 앱
https://docs.google.com/document/d/1WQfF7bzj-HXkR1FWrOjQq1YTlucKlkQAd892hSH6NBo/edit?usp=sharing

## 웹에서 실행

```sh
npm install
npm run dev
```

터미널에 표시되는 로컬 주소를 브라우저에서 엽니다.

## Android 앱 빌드

필요한 도구: Node.js 22 이상, JDK 21, Android Studio 및 Android SDK.

```sh
npm install
npm run android:sync
npm run android:open
```

Android Studio에서 프로젝트를 열고 **Build > Build Bundle(s) / APK(s) > Build APK(s)**를 선택해 디버그 APK를 만들 수 있습니다. 또는 Windows에서는 `cd android` 후 `.\gradlew.bat assembleDebug`를 실행합니다. 생성된 APK는 `android/app/build/outputs/apk/debug/app-debug.apk`에 있습니다.

Play 스토어 배포에는 디버그 APK 대신 서명된 릴리스 번들(AAB)을 생성해야 합니다.
