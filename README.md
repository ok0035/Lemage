
# 레미지

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Build Status](https://img.shields.io/badge/build-passing-brightgreen)

## 소개
이 어플리케이션의 이름은 레미지입니다. 
이 앱은 이미지 검색 및 북마크 기능을 제공합니다. 

[주요 기능] 
- **이미지 검색**: 원하는 키워드를 입력하면 1초 후 자동으로 검색 결과를 확인할 수 있습니다.
- **북마크 기능**: 마음에 드는 이미지를 북마크에 추가하여 나중에 쉽게 찾아볼 수 있습니다.

## 설치
앱을 실행시키기 위해선 반드시 안드로이드 스튜디오가 필요합니다.

1. 리포지토리를 클론합니다:
    ```sh
    git clone https://github.com/ok0035/Lemage.git
    ```
2. Android Studio에서 프로젝트를 엽니다.


## 라이브러리

| 카테고리           | 라이브러리             | 설명                                               |
|--------------------|------------------------|----------------------------------------------------|
| **UI**             | [Compose](https://developer.android.com/jetpack/compose) | Jetpack Compose를 사용한 UI 구성.                    |
|                    | [Material3](https://material.io/develop/android) | Material Design 3 적용.                             |
| **Image**          | [Coil](https://coil-kt.github.io/coil/) | 이미지 로딩 및 표시.                                |
| **DI**             | [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) | 의존성 주입 관리.                                    |
| **Architecture**   | [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) | UI 관련 데이터 관리.                                 |
| **Local Database** | [Room](https://developer.android.com/training/data-storage/room) | 로컬 데이터베이스 저장 및 관리.                      |
| **Network**        | [Retrofit](https://square.github.io/retrofit/) | 네트워크 통신 처리.                                 |
|                    | [okHttpClient](https://square.github.io/okhttp/) | HTTP 요청 관리.                                      |
|                    | [Sandwich](https://github.com/skydoves/Sandwich) | 네트워크 응답 처리 도구.                             |
| **Data parsing**   | [Gson](https://github.com/google/gson) | JSON 데이터 파싱.                                    |
| **Async**          | [Coroutine Flow](https://developer.android.com/kotlin/flow) | 비동기 데이터 스트림 처리.                           |
| **Pagination**     | [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) | 페이징된 데이터 로드 및 표시.                        |


## 라이선스
이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](./LICENSE) 파일을 참고하세요.

## 연락처
프로젝트에 대한 문의사항이 있으면 [이메일](zerodeg93@gmail.com)로 연락해 주세요.
