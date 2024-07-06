
# 레미지

![](https://img.shields.io/badge/Flatform-Android-yellowgreen)</br>
![](https://img.shields.io/badge/Language-Kotlin-blue)</br>
![](https://img.shields.io/badge/Architecture-MVVM-skyblue) ![](https://img.shields.io/badge/-CleanArchitecture-skyblue)</br>
![](https://img.shields.io/badge/Network-Retrofit-red)</br>
![](https://img.shields.io/badge/DI-Hilt-Green)

<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/ok0035/Lemage/assets/19370688/e5e3e29b-a379-4b34-957f-d34332b5a9a6" style="width: 22%;" />
  <img src="https://github.com/ok0035/Lemage/assets/19370688/ac7387c2-8b37-4898-8fa1-d7ec7b58b62c" style="width: 22%;" />
  <img src="https://github.com/ok0035/Lemage/assets/19370688/01289032-50df-4035-b84a-8609c46fd190" style="width: 22%;" />
  <img src="https://github.com/ok0035/Lemage/assets/19370688/6ca2fb9b-2879-4bc2-b941-cb39a264ac04" style="width: 22%;" />
</div>

<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/ok0035/Lemage/assets/19370688/841fc31c-6b55-4f85-be92-8437b3482238" style="width: 44%;" />
  <img src="https://github.com/ok0035/Lemage/assets/19370688/d08aca85-6c41-48ed-90c7-4208ed968dad" style="width: 44%;" />
</div>



## 소개
이 어플리케이션의 이름은 레미지입니다. 
이 앱은 이미지 검색 및 북마크 기능을 제공합니다.
클린 아키텍처와 MVVM을 기반으로 만들어졌으며 모든 화면은 Jetpack Compose로 구현했습니다.


### [주요 기능] 
- **이미지 검색**: 원하는 키워드를 입력하면 1초 후 자동으로 검색 결과를 확인할 수 있습니다.
- **북마크 기능**: 마음에 드는 이미지를 북마크에 추가하여 키워드별로 쉽게 찾아볼 수 있습니다.
- **다국어 지원**: 언어 설정에 따라 한글과 영어를 지원합니다.
- **다크 테마 대응**: 라이트/다크 모드에 따라 앱의 테마가 변경됩니다.
- **가로 모드 대응**: 가로 모드시 타이틀을 보이지 않도록 하고, 더 많은 개수의 이미지를 볼 수 있습니다.


## 권장 버전 및 스펙

- **Minimum SDK Version**: 26
- **Compile SDK Version**: 34
- **Target SDK Version**: 34
- **Java Version**: 11


## 설치
앱을 실행시키기 위해선 반드시 안드로이드 스튜디오가 필요합니다.

1. 리포지토리를 클론합니다:
    ```sh
    git clone https://github.com/ok0035/Lemage.git
    ```
2. Android Studio에서 프로젝트를 엽니다.

## 아키텍처 개요

"Lemage" 프로젝트는 MVVM 패턴과 Clean Architecture 원칙을 결합하여 설계되었습니다.

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


| 카테고리               | 라이브러리                                                                                    | 설명                                                    |
| ------------------ | ---------------------------------------------------------------------------------------- | -----------------------------------------------------      |
| **UI**             | [Compose](https://developer.android.com/jetpack/compose)                                 | Jetpack Compose를 사용한 UI 구성 라이브러리.                     |
|                    | [Material3](https://material.io/develop/android)                                         | Material Design 3에서 제공하는 테마 및 아이콘을 활용.              |
| **Image**          | [Coil](https://coil-kt.github.io/coil/)                                                  | 안정적인 이미지 로딩 및 표시를 위해 Coil 사용.                     |
| **DI**             | [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)         | 의존성 주입을 쉽게 관리하기 위한 라이브러리.                             |
| **AAC**            | [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)        | UI와 비즈니스 로직의 분리를 위해 AAC ViewModel 사용.              |
| **Local Database** | [Room](https://developer.android.com/training/data-storage/room)                         | 북마크 기능을 구현하기 위한 경량 ORM(객체 관계 매핑) 데이터베이스.         |
| **Network**        | [Retrofit](https://square.github.io/retrofit/)                                           | 간편한 API 호출 및 비동기 네트워크 통신에 용이한 라이브러리.                |
|                    | [okHttpClient](https://square.github.io/okhttp/)                                         | HTTP 요청을 효율적으로 관리하기 위한 클라이언트.                      |
|                    | [Sandwich](https://github.com/skydoves/Sandwich)                                         | 네트워크 응답을 간편하게 처리하기 위한 라이브러리.                      |
| **Data parsing**   | [Gson](https://github.com/google/gson)                                                   | JSON 데이터를 객체로 파싱하기 위한 라이브러리.                          |
| **Async**          | [Coroutine Flow](https://developer.android.com/kotlin/flow)                              | 비동기 데이터 스트림을 처리하기 위한 Kotlin Coroutine 기반 라이브러리.     |
| **Pagination**     | [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) | 페이징된 데이터를 효율적으로 로드하고 표시하기 위한 라이브러리.             |



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

**요약**
| 특성 | HotFlow | ColdFlow |
|------|---------|----------|
| 활성화 시점 | 항상 활성 | 구독 시 활성 |
| 상태 유지 | 최신 상태 유지 | 구독 시마다 새로 시작 |
| 구독자 간 독립성 | 없음 | 있음 |

### StateFlow와 SharedFlow
StateFlow와 SharedFlow는 모두 HotFlow입니다. 두 Flow의 가장 큰 차이는 상태의 보존 여부로써 StateFlow는 객체 생성시 초기값을 반드시 지정해야하며,</br>
SharedFlow는 초기값 없이 객체 생성이 가능합니다. StateFlow는 LiveData와 유사하지만 Coroutine 친화적이라는 특징이 있습니다.<br>
LiveData와 마찬가지로 상태를 저장하고 관찰하는 용도로 사용되며, 같은 값이 중복 호출되지 않습니다.</br>
</br>
반면에 SharedFlow는 상태를 저장하지 않으며 같은 값일지라도 여러번 방출될 수 있다는 특정이 있습니다.</br>
이런 특징 때문에 StateFlow는 상태를 저장하고 UI를 변경하는데 많이 사용되는 반면,</br>
SharedFlow는 토스트 메시지 처럼 결과값에 따라 이벤트를 처리해야할 때 자주 사용됩니다. 아래는 예제 코드입니다.<br>

```kotlin
package com.zerosword.feature_main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerosword.domain.entity.FavoriteModel
import com.zerosword.domain.reporitory.FavoriteRepository
import com.zerosword.domain.state.ToastState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    // HotFlow: StateFlow 사용
    private var _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> get() = _isConnected.asStateFlow()

    // HotFlow: SharedFlow 사용
    private val _toastState: MutableSharedFlow<ToastState> = MutableSharedFlow(0)
    val toastState: SharedFlow<ToastState> get() = _toastState.asSharedFlow()

    //...

    init {
        viewModelScope.launch {
            connection.isConnected.collectLatest { isConnected ->
                _isConnected.value = isConnected
                if (isConnected) {
                    loadFavoritesByKeyword()
                }
            }
        }
    }
}
```

### StateFlow 사용 예제 (HotFlow)

```kotlin
    LaunchedEffect(viewModel.isConnected) {
        viewModel.isConnected.collectLatest {
            isConnected.value = it
        }
    }

    //... 인터넷 연결 상태에 따른 UI 변경

    if (!isConnected.value) NetworkErrorScreen()
```

### SharedFlow 사용 예제 (HotFlow)

```kotlin
    LaunchedEffect(Unit) {
        viewModel.toastState.collect {

            //SharedFlow에서 받은 State에 따른 토스트 메시지 출력
            val toastMessage = when (it) {
                DELETED_SELECTED_ITEM_FROM_BOOKMARK -> {
                    val selectedItemCount = viewModel.deleteItemList.value.size
                    String.format(
                        context.getString(R.string.deleted_n_items_from_bookmark),
                        selectedItemCount
                    )
                }
                NO_DELETABLE_ITEMS -> context.getString(R.string.no_deletable_items)
                PAGING_ERROR -> context.getString(R.string.paging_error_msg)
                ADDED_TO_BOOKMARK -> context.getString(R.string.added_to_bookmark)
                DELETED_FROM_BOOKMARK -> context.getString(R.string.deleted_from_bookmark)
                else -> ""
            }
            if (toastMessage.isNotEmpty()) context.toast(toastMessage)
        }

    }
```

**요약**
| 특성 | StateFlow | SharedFlow |
|------|------------|------------|
| 초기값 | 초기값을 반드시 지정해야 함 | 초기값 없이 객체 생성 가능 |
| 상태 보존 | 상태를 보존함 | 상태를 보존하지 않음 |
| 중복 호출 | 같은 값이 중복 호출되지 않음 | 같은 값이 여러 번 방출될 수 있음 |
| 주요 용도 | 상태를 저장하고 UI를 변경하는 데 사용 | 결과값에 따라 이벤트를 처리하는 데 사용 |
| LiveData와의 유사성 | LiveData와 유사하며 Coroutine 친화적 | 이벤트를 처리하는 데 특화 |

## Android ViewModel

### Owner
`Owner`는 `ViewModel`의 생명 주기를 관리하는 객체를 의미합니다. `ViewModel`은 특정 생명 주기 범위 내에서 데이터를 저장하고 관리하는데,
이 생명 주기 범위를 설정하는 것이 `Owner`의 역할입니다. 예를 들어, `Activity`나 `Fragment`는 각각의 생명 주기를 가지고 있으며, 이들 중 하나가 `ViewModel`의 `Owner`가 됩니다.</br>
</br>
ViewModel의 owner를 설정하는 이유는 화면 전환이나 구성 변경 (예: 화면 회전) 시에도 `ViewModel`의 데이터가 유지되도록 하기 위함입니다. 
`Owner`로 설정된 객체의 생명 주기 동안 `ViewModel`은 재사용되며, `Owner`가 파괴될 때 `ViewModel`도 함께 파괴됩니다.

### Compose Navigation과 Dagger Hilt를 같이 사용하는 경우의 Owner 설정

`Compose Navigation`과 `Dagger Hilt`를 함께 사용할 때, `NavBackStackEntry`를 `Owner`로 사용하여 `ViewModel`이 특정 화면의 생명 주기를 따르도록 할 수 있습니다. 이를 통해 화면 이동 시에도 `ViewModel`의 데이터가 유지됩니다.
`Hilt`를 사용하여 `ViewModel` 인스턴스를 가져올 때, `hiltViewModel()` 함수를 사용할 수 있습니다. 이 함수는 현재 `NavBackStackEntry`를 `Owner`로 사용하여 `ViewModel`을 생성합니다.

다음은 `Compose Navigation`과 `Dagger Hilt`를 함께 사용하는 자세한 예제입니다.
`Compose Navigation`에서 `hiltViewModel()` 함수를 사용할 때 `Owner`를 설정하는 방법은 두 가지입니다.</br></br>

**1. `NavBackStackEntry`를 `owner`로 설정하는 경우**</br></br>

```kotlin
@Composable  
fun NavigationGraph(  
    modifier: Modifier = Modifier,  
    navController: NavHostController,  
) {  
    
    NavHost(  
        modifier = modifier,  
        navController = navController,  
        startDestination = Routes.Search.route,  
    ) {  
  
        composable(route = Routes.Search.route) {  
            val searchViewModel: SearchViewModel = hiltViewModel(it) //navBackStackEntry 로 owner 설정
            SearchScreen(searchViewModel, navController)  
        }  
        composable(route = Routes.Bookmark.route) {  
            val bookmarkViewModel: BookmarkViewModel = hiltViewModel(it) //navBackStackEntry 로 owner 설정
            BookmarkScreen(  
                bookmarkViewModel,  
                navController  
            )  
        }  
        ...  
	}
}
```

- `ViewModel`의 생명주기는 해당 `NavBackStackEntry`의 생명주기를 따르게 됩니다.
- 특정 화면에 대한 `ViewModel` 인스턴스가 해당 화면의 `NavBackStackEntry`와 연결됩니다.
- 화면의 구성이 변경(ex. 화면 회전)되어도 `NavBackStackEntry`가 유지되는 한 `ViewModel` 인스턴스는 유지됩니다.
- 하지만 화면이 완전히 Destroy 되고 다시 생성될 경우 (다른 화면으로 이동 후 돌아왔을 때)</br>
`NavBackStackEntry`가 다시 생성되고, 그에 따라 새로운 `ViewModel` 인스턴스가 생성됩니다.</br></br>



**2. `NavBackStackEntry`를 `Owner`로 설정하지 않는 경우 (기본적으로 `LocalContext`가 `Owner`가 됩니다.)**</br></br>

```kotlin
@Composable  
fun NavigationGraph(  
    modifier: Modifier = Modifier,  
    navController: NavHostController,  
) {  

    val searchViewModel: SearchViewModel = hiltViewModel() //기본적으로 LocalContext가 Owner가 됨
    val bookmarkViewModel: BookmarkViewModel = hiltViewModel() //기본적으로 LocalContext가 Owner가 됨
    
    NavHost(  
        modifier = modifier,  
        navController = navController,  
        startDestination = Routes.Search.route,  
    ) {  
  
        composable(route = Routes.Search.route) {  
            SearchScreen(searchViewModel, navController)  
        }  
        composable(route = Routes.Bookmark.route) {  
            BookmarkScreen(  
                bookmarkViewModel,  
                navController  
            )  
        }  
        ...
	}
}
```


- `NavBackStackEntry`를 `Owner`로 설정하지 않는 경우 `ViewModel`의 생명주기는 `LocalContext`의 생명주기를 따르게 됩니다.
- 일반적으로 `LocalContext`는 `Activity`나 `Fragment`의 생명주기를 따릅니다.
- `NavBackStackEntry`를 `Owner`로 했을 때와 마찬가지로 화면의 구성이 변경(ex. 화면 회전)되어도 `ViewModel` 인스턴스는 유지됩니다.
- `NavBackStackEntry`를 `Owner`로 했을 때와 다른 점은 다른 화면으로 이동 후 돌아왔을 때에도 `ViewModel`의 생명주기는 `LocalContext`를 따르므로 `ViewModel` 인스턴스가 그대로 유지됩니다.


	
예를 들어 해당 프로젝트의 검색 탭과 북마크 탭을 왔다갔다 할 때 차이가 발생하게 됩니다.</br>
`LocalContext`를 따를 경우 탭을 이미지 검색 후 북마크 탭을 갔다가 돌아온 이후에도 결과가 남아있는 반면,</br>
`NavBackStackEntry`를 `Owner`로 설정했을 경우 검색탭으로 돌아왔을 때 화면이 초기화 되게 됩니다.</br>
해당 프로젝트를 작업할 때에도 어떻게 할지 고민을 많이 했는데 탭이 두 개 밖에 존재하지 않고</br>
번갈아가며 볼 일이 많을 것으로 예상되어 Local Context를 Owner로 설정했습니다.</br></br>


| 비교 항목 | NavBackStackEntry를 Owner로 설정한 경우 | NavBackStackEntry를 Owner로 설정하지 않은 경우 (기본적으로 LocalContext가 Owner가 됨) |
|-----------|-------------------------------------------|---------------------------------------------------------------------------------------|
| ViewModel의 생명 주기 | 해당 NavBackStackEntry의 생명 주기를 따름 | LocalContext의 생명 주기를 따름 (일반적으로 Activity나 Fragment) |
| ViewModel 인스턴스 연결 | 특정 화면의 NavBackStackEntry와 연결됨 | Activity나 Fragment와 연결됨 |
| 화면 구성 변경 (예: 화면 회전) | NavBackStackEntry가 유지되는 한 ViewModel 인스턴스 유지 | ViewModel 인스턴스 유지 |
| 화면 이동 후 돌아올 때 | 화면이 완전히 Destroy되고 다시 생성되므로 새로운 ViewModel 인스턴스 생성 | ViewModel 인스턴스가 그대로 유지됨 |
| 탭 간 이동 시 | 검색 탭에서 북마크 탭으로 이동 후 다시 검색 탭으로 돌아오면 화면이 초기화됨 | 검색 탭에서 북마크 탭으로 이동 후 다시 검색 탭으로 돌아와도 결과가 남아 있음 |
| 사용 예 | 특정 화면에 대한 독립적인 ViewModel 인스턴스가 필요할 때 유용 | 여러 화면에서 동일한 ViewModel 인스턴스를 공유할 때 유용 |


## Android Paging3


### `PagingSource` 클래스

`PagingSource` 클래스는 데이터 페이징을 처리하는 기본 단위입니다. 다양한 데이터 소스(ex. 네트워크, 데이터베이스)에서 데이터를 페이지 단위로 로드하기 위해 사용됩니다.</br>
`PagingSource`를 사용하면 큰 데이터 세트를 작은 청크로 나눠 효율적으로 로드할 수 있습니다.

`PagingSource`는 다음과 같은 두 가지 제네릭 타입을 사용합니다:
- `Key`: 페이지를 식별하는 데 사용되는 키의 타입입니다. 일반적으로 페이지 번호나 데이터의 ID를 사용합니다.
	- 해당 프로젝트에선 페이지 번호를 사용했습니다.
- `Value`: 로드된 데이터의 타입입니다. 예를 들어, `KakaoImageModel` 객체가 될 수 있습니다.
	- 해당 프로젝트에선 Kakao 이미지 모델을 사용했습니다.

</br></br>
---
### `load` 메서드

`load` 메서드는 데이터를 로드하는 로직을 정의합니다. 이 메서드는 `LoadParams<Key>`를 인자로 받아 데이터를 로드하고, `LoadResult<Key, Value>`를 반환합니다.

#### `LoadParams<Key>`

`LoadParams`는 `load` 메서드에 전달되는 파라미터로, 다음과 같은 속성을 가집니다:
- `key`: 현재 페이지를 식별하는 키입니다. 첫 로드 시에는 `null`일 수 있습니다.
- `loadSize`: 로드할 데이터의 크기입니다. 일반적으로 페이지당 항목 수를 나타냅니다.
- `placeholdersEnabled`: 플레이스홀더 사용 여부를 나타내는 부울 값입니다.

#### `LoadResult<Key, Value>`

`LoadResult`는 `load` 메서드의 결과를 나타내며, 다음 두 가지 서브클래스가 있습니다:
- `LoadResult.Page<Key, Value>`: 로드된 데이터와 이전/다음 페이지 키를 포함합니다.
- `LoadResult.Error`: 로드 중 발생한 에러를 포함합니다.

</br></br>
---
### `getRefreshKey` 메서드


`getRefreshKey` 메서드는 데이터 새로고침 시 사용되는 키를 반환합니다. 이 메서드는 일반적으로 현재 상태에서 적절한 페이지 키를 계산하기 위해 사용됩니다.

#### 파라미터: `PagingState<Key, Value>`

`getRefreshKey` 메서드의 파라미터는 `PagingState<Key, Value>` 객체입니다. `PagingState`는 현재 페이징 상태를 나타내며, 다음과 같은 속성을 가집니다:
- `pages`: 로드된 페이지 목록입니다. 각 페이지는 `LoadResult.Page<Key, Value>` 객체입니다.
- `anchorPosition`: 현재 표시되는 항목의 위치입니다. `null`일 수 있습니다.
- `config`: `PagingConfig` 객체로, 페이징 구성 정보를 포함합니다.
- `itemCount`: 현재 로드된 총 항목 수입니다.

#### 리턴 값: `Key?`

`getRefreshKey` 메서드는 새로고침 시 사용할 키를 반환합니다. 이 키는 일반적으로 `null`이 아닌 값을 반환해야 합니다. 반환된 키는 새로고침 작업을 시작하는 위치를 결정하는 데 사용됩니다.

</br></br>
---
### 예제 코드

다음은 해당 프로젝트에 실제 적용된 `PagingSource`와 `getRefreshKey` 코드입니다.

```kotlin
class KakaoImagePagingSource(
    private val kakaoService: KakaoService,
    private val query: String,
    private val sort: String,
    private val onError: (errorMsg: String) -> Unit = {}
) : PagingSource<Int, KakaoImageModel.DocumentModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KakaoImageModel.DocumentModel> {
        return try {
            val page = params.key ?: 1
            val response = kakaoService.searchImage(
                query = query,
                sort = sort,
                page = page,
                size = params.loadSize,
            ).suspendOnFailure {
                onError(this.message())
            }

            val model = response.getOrNull()?.toDomainModel()
                ?: return LoadResult.Error(Exception("Empty response"))

            LoadResult.Page(
                data = model.documents,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (model.meta?.isEnd == true) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, KakaoImageModel.DocumentModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
```

### 예제 설명

1. **load 메서드**:
    - `params.key`가 `null`이면 첫 페이지(1)를 로드합니다.
    - API 호출을 통해 데이터를 로드합니다.
    - 로드된 데이터를 `LoadResult.Page` 객체로 반환합니다. 이전 페이지 키(`prevKey`)와 다음 페이지 키(`nextKey`)를 설정합니다.
    - 에러가 발생하면 `LoadResult.Error`를 반환합니다.

2. **getRefreshKey 메서드**:
    - `state.anchorPosition`을 사용하여 현재 데이터의 중앙 위치를 찾습니다.
    - `state.closestPageToPosition(anchorPosition)`를 사용하여 해당 위치에 가장 가까운 페이지를 가져옵니다.
    - 이전 키(`prevKey`)가 있으면 `prevKey + 1`을 반환하고, 그렇지 않으면 다음 키(`nextKey - 1`)를 반환합니다.


## 라이선스
이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](./LICENSE) 파일을 참고하세요.

## 이메일
프로젝트에 대한 문의사항이 있으면 [이메일](zerodeg93@gmail.com)로 연락해 주세요.
