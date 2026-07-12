// 섹션 6 - 강의 1: enum class (자바 enum과 비교, when과의 궁합)
// 실행:  .\run.ps1 section06\Lesson01.kt

fun main() {
    // ============================================================
    // ① enum 상수와 프로퍼티 접근
    //    - 자바: c.getCapital()  /  코틀린: c.capital (프로퍼티)
    // ============================================================
    val korea = Country.KOREA
    println("${korea.name} → 수도 ${korea.capital}, 코드 ${korea.code}")
    println("이름: ${korea.name}, 순서(ordinal): ${korea.ordinal}")

    // ============================================================
    // ② 전체 목록 순회 — entries (신형) / valueOf
    //    자바: Country.values() / Country.valueOf("JAPAN")
    // ============================================================
    println("--- 모든 나라 ---")
    for (c in Country.entries) {                 // entries = 모든 상수 (신형 API)
        println("${c.ordinal}: ${c.name} (${c.code})")
    }
    val japan = Country.valueOf("JAPAN")         // 문자열 → enum 상수
    println("valueOf(\"JAPAN\") = ${japan.capital}")

    // ============================================================
    // ③ 핵심: when 소진 검사 (exhaustive)
    //    모든 상수를 다루면 else 불필요.
    //    → enum에 상수를 추가하면 이 when이 컴파일 에러로 알려준다.
    // ============================================================
    for (c in Country.entries) {
        val lang = describeLanguage(c)
        println("${c.name}의 언어: $lang")
    }

    // ============================================================
    // ④ enum 안에 함수도 넣을 수 있다 (자바와 동일)
    // ============================================================
    println("--- 신호등 ---")
    for (signal in TrafficLight.entries) {
        println("${signal.name}: ${signal.action()}")
    }
}

// enum 상수를 받아 값을 반환하는 when 표현식 (else 없음 = 소진 검사 통과)
fun describeLanguage(c: Country): String = when (c) {
    Country.KOREA -> "한국어"
    Country.JAPAN -> "일본어"
    Country.USA   -> "영어"
    // ← 여기에 else가 없다. 모든 상수를 다뤘기 때문.
    //    Country에 상수 하나 추가하면 이 when이 컴파일 에러가 난다.
}

// ============================================================
// enum class: 생성자 파라미터를 val 프로퍼티로 (getter 자동)
// 프로퍼티/함수가 있으면 상수 목록 끝에 세미콜론(;) 필요
// ============================================================
enum class Country(
    val capital: String,
    val code: String,
) {
    KOREA("서울", "KR"),
    JAPAN("도쿄", "JP"),
    USA("워싱턴", "US"),
    ;
}

// enum 안에 추상 함수를 두고 상수마다 다르게 구현할 수도 있다
enum class TrafficLight {
    RED   { override fun action() = "멈춰" },
    YELLOW{ override fun action() = "대기" },
    GREEN { override fun action() = "출발" },
    ;
    abstract fun action(): String
}
