
# 레미지

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Build Status](https://img.shields.io/badge/build-passing-brightgreen)

<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/ok0035/Lemage/assets/19370688/e5e3e29b-a379-4b34-957f-d34332b5a9a6" style="width: 45%;" />
  <img src="https://github.com/ok0035/Lemage/assets/19370688/ac7387c2-8b37-4898-8fa1-d7ec7b58b62c" style="width: 45%;" />
</div>

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

## 아키텍처 개요

"Lemage" 프로젝트는 MVVM 패턴과 Clean Architecture 원칙을 결합하여 설계되었습니다. 이 아키텍처는 모듈화, 테스트 용이성, 유지 보수성에 중점을 두고 있습니다.

### MVVM (Model-View-ViewModel)

- **Model**: 데이터 로직 및 비즈니스 규칙을 처리합니다.
- **View**: UI를 렌더링하고 사용자 상호작용을 수신합니다.
- **ViewModel**: Model과 View를 연결하고 UI 관련 데이터를 관리합니다.

### Clean Architecture 레이어

- **Data Module**: 데이터 소스 처리, 네트워크 통신, API 호출 등을 담당합니다.
- **Domain Module**: 비즈니스 로직과 애플리케이션 엔티티를 캡슐화합니다.
- **Feature Module**: UI 구성 요소와 ViewModel을 포함합니다.
- **App Module**: 메인 컴포넌트 (Activity 및 Fragment)를 포함합니다.

### 의존성 흐름

1. **View**는 **ViewModel**에 의존합니다.
2. **ViewModel**은 **Repository**에 의존합니다.
3. **Repository**는 **Data Source**에 의존합니다.

이 아키텍처는 각 레이어를 독립적으로 유지하고, 쉽게 교체할 수 있도록 설계되었습니다. 이를 통해 테스트와 유지 보수가 용이합니다.

## 프로젝트 구조

```
lemage/
├── app/
├── data/
├── domain/
├── feature/
└── ...
```

각 디렉토리는 해당 레이어의 역할을 담당하며, 모듈화된 구조를 가집니다.

### 주요 기술 스택


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

## Kotlin Coroutine Flow

### ColdFlow와 HotFLow

#### HotFlow

HotFlow는 항상 활성 상태이며 여러 소비자가 동시에 데이터를 수신할 수 있는 흐름입니다.</br>
HotFlow는 생산자가 활성화된 이후부터 데이터를 계속 방출하며, 구독자가 언제 시작되었는지와 관계없이 구독자는 현재 상태를 즉시 받습니다.</br>
대표적인 예로는 `StateFlow`가 있습니다. `StateFlow`는 상태를 가지며, 현재 상태를 계속해서 방출합니다.</br>
구독자가 새로운 값을 받을 때마다 최신 상태를 받을 수 있습니다.</br>

**특징**
- 항상 활성 상태
- 현재 상태나 최신 값을 유지
- 새로운 구독자는 최신 값을 즉시 수신

```kotlin

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val stateFlow = MutableStateFlow(0)

    // 구독자 1
    launch {
        stateFlow.collect { value ->
            println("구독자 1: $value")
        }
    }

    // 값 업데이트
    stateFlow.value = 1
    stateFlow.value = 2

    // 구독자 2
    launch {
        stateFlow.collect { value ->
            println("구독자 2: $value")
        }
    }

    // 값 업데이트
    stateFlow.value = 3
}

```

출력 과정 및 결과 요약
- 초기값 0으로 stateFlow 생성.
- 구독자 1이 stateFlow를 구독하여 초기값 0을 출력.
- stateFlow.value를 1로 업데이트하면 구독자 1이 1을 출력.
- stateFlow.value를 2로 업데이트하면 구독자 1이 2를 출력.
- 구독자 2가 stateFlow를 구독하여 현재 값 2를 출력.
- stateFlow.value를 3으로 업데이트하면 구독자 1과 구독자 2가 3을 출력.

```
구독자 1: 0
구독자 1: 1
구독자 1: 2
구독자 2: 2
구독자 1: 3
구독자 2: 3
```

#### ColdFlow
ColdFlow는 구독자가 있을 때에만 데이터를 방출합니다. 각 구독자는 자신의 독립적인 데이터 스트림을 받으며,</br>
구독할 때마다 스트림이 새로 시작됩니다. 즉, 생산자는 구독자가 없으면 데이터를 생성하지 않습니다.</br>

**특징**
- 구독자가 있을 때만 활성화
- 각 구독자는 독립적인 데이터 스트림을 수신
- 구독 시마다 스트림이 새로 시작

```kotlin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val coldFlow: Flow<Int> = flow {
        for (i in 1..3) {
            delay(100) // 데이터를 방출하기 전에 지연
            emit(i)
        }
    }

    // 구독자 1
    launch {
        coldFlow.collect { value ->
            println("구독자 1: $value")
        }
    }

    // 구독자 2
    launch {
        coldFlow.collect { value ->
            println("구독자 2: $value")
        }
    }
}

```

출력 과정 및 결과 요약
- coldFlow는 1부터 3까지 순차적으로 값을 방출하며 각 방출 전에 100ms 지연.
- 구독자 1이 coldFlow를 구독하여 값을 출력.
- 구독자 2도 동시에 coldFlow를 구독하여 값을 출력.
- 구독자 1과 구독자 2가 비동기적으로 구독했으므로 1, 1, 2, 2, 3, 3과 같은 결과 출력


```
구독자 1: 1
구독자 2: 1
구독자 1: 2
구독자 2: 2
구독자 1: 3
구독자 2: 3
```

요약
| 특성 | HotFlow | ColdFlow |
|------|---------|----------|
| 활성화 시점 | 항상 활성 | 구독 시 활성 |
| 상태 유지 | 최신 상태 유지 | 구독 시마다 새로 시작 |
| 구독자 간 독립성 | 없음 | 있음 |

## 라이선스
이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](./LICENSE) 파일을 참고하세요.

## 이메일
프로젝트에 대한 문의사항이 있으면 [이메일](zerodeg93@gmail.com)로 연락해 주세요.
