// 섹션 6 - 강의 2: sealed class (enum의 확장판, when + is 소진검사)
// 실행:  .\run.ps1 section06\Lesson02.kt

fun main() {
    // ============================================================
    // ① sealed class로 "결과"를 모델링
    //    경우마다 담는 데이터가 다르다 → enum으로는 안 됨
    // ============================================================
    val results: List<Result> = listOf(
        Result.Success("홍길동"),              // 성공 → 데이터
        Result.Failure("서버 오류", 500),       // 실패 → 메시지 + 코드
        Result.Loading,                         // 로딩 → 데이터 없음 (object)
    )

    for (r in results) {
        println(handle(r))
    }

    // ============================================================
    // ② is로 스마트캐스트 확인
    //    when 안에서 타입이 좁혀지면 그 타입의 프로퍼티에 바로 접근
    // ============================================================
    val one: Result = Result.Failure("타임아웃", 408)
    if (one is Result.Failure) {
        // 여기서 one은 이미 Result.Failure로 스마트캐스트됨 → .code 바로 접근
        println("에러코드만 뽑기: ${one.code}")
    }

    // ============================================================
    // ③ UI 상태도 sealed로 자주 모델링한다 (실무 예)
    // ============================================================
    println("--- 화면 상태 ---")
    println(render(ScreenState.Loading))
    println(render(ScreenState.Content(listOf("코틀린", "자바"))))
    println(render(ScreenState.Error("네트워크 끊김")))
}

// ============================================================
// when 표현식으로 각 하위 타입 처리
// - is로 분기하면 그 블록 안에서 스마트캐스트 (r.data, r.code 바로 접근)
// - 모든 하위 타입을 다루면 else 불필요 (소진 검사)
//   → Result에 하위 타입을 추가하면 이 when이 컴파일 에러가 난다
// ============================================================
fun handle(r: Result): String = when (r) {
    is Result.Success -> "성공: ${r.data}"                  // r → Success로 캐스트
    is Result.Failure -> "실패(${r.code}): ${r.message}"     // r → Failure로 캐스트
    Result.Loading    -> "로딩중..."                         // object는 is 없이 값 비교
    // ← else 없음. 세 하위 타입을 모두 다뤘으므로.
}

// ============================================================
// sealed class: 하위 타입을 같은 파일/패키지 안에 "봉인"
// - data class : 데이터를 담는 경우
// - data object: 담을 데이터가 없는 경우 (인스턴스 하나면 충분)
// ============================================================
sealed class Result {
    data class Success(val data: String) : Result()
    data class Failure(val message: String, val code: Int) : Result()
    data object Loading : Result()
}

// ============================================================
// 실무 단골: 화면 상태 모델링
// ============================================================
sealed class ScreenState {
    data object Loading : ScreenState()
    data class Content(val items: List<String>) : ScreenState()
    data class Error(val reason: String) : ScreenState()
}

fun render(state: ScreenState): String = when (state) {
    ScreenState.Loading   -> "[스피너 표시]"
    is ScreenState.Content -> "[목록 ${state.items.size}개: ${state.items.joinToString()}]"
    is ScreenState.Error   -> "[에러 배너: ${state.reason}]"
}
