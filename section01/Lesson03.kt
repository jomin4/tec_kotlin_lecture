// 섹션 1 - 강의 3: 코틀린에서 Type(타입)을 다루는 방법
// 실행:  .\run.ps1 section01\Lesson03.kt

fun main() {
    // ============================================================
    // ① 기본 타입 & 형변환 — 자동 변환이 '없다'
    // ============================================================
    val i = 10
    // val l: Long = i        // ❌ 컴파일 에러: type mismatch (주석 풀어 확인)
    val l: Long = i.toLong()  // ✅ 명시적 변환
    val d: Double = i.toDouble()
    println("i=$i, l=$l, d=$d")

    // 문자 '5'를 숫자 5로? String은 .toInt()
    val numStr = "42"
    val num = numStr.toInt()
    println("문자열 \"$numStr\" + 8 = ${num + 8}")

    // ============================================================
    // ② is / as / as? — 타입 확인과 캐스팅
    // ============================================================
    val obj: Any = "코틀린"       // Any = Java의 Object

    println("obj is String? ${obj is String}")    // true
    println("obj !is Int? ${obj !is Int}")        // true

    // as: 강제 캐스팅 (실패하면 예외)
    val s = obj as String
    println("as로 캐스팅한 길이: ${s.length}")

    // as?: 안전 캐스팅 (실패하면 null)
    val maybeInt = obj as? Int          // String을 Int로? → 실패 → null
    println("obj as? Int = $maybeInt")

    // ============================================================
    // ③ 스마트 캐스트 — is로 확인하면 자동으로 그 타입 취급
    // ============================================================
    printLength("안녕하세요")   // String이 들어옴
    printLength(12345)         // Int가 들어옴
    printLength(3.14)          // 그 외

    // ============================================================
    // ④ Unit — 반환값 없는 함수 (Java의 void)
    // ============================================================
    val result = sayHi()       // sayHi는 Unit 반환
    println("sayHi()의 반환 타입은 Unit: $result")
}

// 스마트 캐스트 예제: 파라미터가 Any라 뭐든 받는다
fun printLength(value: Any) {
    if (value is String) {
        // 이 블록 안에서 value는 자동으로 String (캐스팅 불필요!)
        println("문자열이네요. 길이 = ${value.length}")
    } else if (value is Int) {
        // 여기선 value가 Int로 스마트 캐스트
        println("정수네요. 제곱 = ${value * value}")
    } else {
        println("String도 Int도 아닌: $value")
    }
}

// Unit 반환 함수 (: Unit 은 생략 가능 — 안 쓰면 자동으로 Unit)
fun sayHi(): Unit {
    println("Hi!")
}
