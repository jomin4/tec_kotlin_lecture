// 섹션 4 - 강의 1: 코틀린에서 클래스 (생성자와 프로퍼티)
// 실행:  .\run.ps1 section04\Lesson01.kt

fun main() {
    // ============================================================
    // ① 객체 생성 — 자바와 달리 'new' 키워드가 없다!
    // ============================================================
    val person = Person("김코틀린", 30)

    // ② 프로퍼티 접근 — getName()/setAge() 없이 필드처럼
    println("이름: ${person.name}")     // 내부적으로 getter 호출
    println("나이: ${person.age}")
    person.age = 31                     // 내부적으로 setter 호출 (var라서 가능)
    println("한 살 더: ${person.age}")
    // person.name = "다른이름"          // ❌ 에러: name은 val(읽기전용)

    // ③ 커스텀 getter — 저장된 값이 아니라 '계산'해서 반환
    println("성인인가? ${person.isAdult}")

    // ④ init 블록의 검증 — 나이가 음수면 생성 시 예외
    println("--- 잘못된 나이로 생성 시도 ---")
    try {
        val wrong = Person("음수맨", -5)   // init에서 걸림
        println(wrong.name)
    } catch (e: IllegalArgumentException) {
        println("생성 실패: ${e.message}")
    }

    // ⑤ 커스텀 setter — 값 넣을 때 가공/검증
    val account = Account("홍길동")
    account.balance = 1000
    println("잔액: ${account.balance}")
    account.balance = -999               // 커스텀 setter가 막아서 0으로
    println("음수 입력 후 잔액: ${account.balance}")
}

// ============================================================
// 주 생성자: 클래스 이름 옆 괄호. val/var 붙이면 그대로 프로퍼티가 된다
// 이 한 줄에 필드 + 생성자 + getter/setter가 다 들어있다
// ============================================================
class Person(val name: String, var age: Int) {

    // init: 객체가 만들어질 때 실행 (주로 검증)
    init {
        require(age >= 0) { "나이는 음수일 수 없습니다: $age" }
    }

    // 커스텀 getter: age를 이용해 매번 계산 (별도 저장 안 함)
    val isAdult: Boolean
        get() = age >= 20
}

// 커스텀 setter 예제
class Account(val owner: String) {
    var balance: Int = 0
        set(value) {
            // 음수가 들어오면 0으로 (검증/가공)
            field = if (value < 0) 0 else value   // field = 실제 저장 공간(backing field)
        }
}
