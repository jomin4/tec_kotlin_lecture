// 섹션 4 - 강의 2: 코틀린에서 data class & 접근 지정자
// 실행:  .\run.ps1 section04\Lesson02.kt

fun main() {
    // ============================================================
    // ① toString() 자동 — data 안 붙인 클래스와 비교
    // ============================================================
    val plain = PlainPoint(1, 2)
    val data = Point(1, 2)
    println("일반 클래스 toString: $plain")   // PlainPoint@1b6d3586 (주소)
    println("data 클래스 toString: $data")     // Point(x=1, y=2)  ← 예쁨!

    // ============================================================
    // ② equals()/hashCode() 자동 — 내용으로 비교
    // ============================================================
    val a = Point(1, 2)
    val b = Point(1, 2)      // 다른 객체지만 내용 같음
    println("a == b : ${a == b}")     // true  (data라 내용 비교)
    println("a === b: ${a === b}")    // false (다른 객체)

    val p1 = PlainPoint(1, 2)
    val p2 = PlainPoint(1, 2)
    println("일반 클래스 p1 == p2 : ${p1 == p2}")   // false (기본 equals=주소)

    // ============================================================
    // ③ copy() — 일부만 바꿔 복제 (불변 객체 업데이트의 정석)
    // ============================================================
    val origin = Point(0, 0)
    val moved = origin.copy(y = 5)     // x는 그대로, y만 5로
    println("원본: $origin, 복제본: $moved")

    // ============================================================
    // ④ 구조 분해 (componentN) — 한 번에 여러 변수로 꺼내기
    // ============================================================
    val (x, y) = moved
    println("구조 분해: x=$x, y=$y")

    // ============================================================
    // ⑤ 접근 지정자 — private 멤버는 밖에서 못 본다
    // ============================================================
    val account = BankAccount("홍길동")
    account.deposit(5000)
    println("잔액 조회: ${account.getBalance()}")
    // println(account.balance)   // ❌ 에러: balance는 private
}

// data 안 붙인 일반 클래스 (비교용)
class PlainPoint(val x: Int, val y: Int)

// data class — equals/hashCode/toString/copy/componentN 자동 생성
data class Point(val x: Int, val y: Int)

// 접근 지정자 예제: balance는 감추고, 메서드로만 조작
class BankAccount(val owner: String) {
    private var balance: Int = 0        // 외부에서 직접 접근 불가

    fun deposit(amount: Int) {
        if (amount > 0) balance += amount
    }
    fun getBalance(): Int = balance     // 읽기는 메서드로만 허용
}
