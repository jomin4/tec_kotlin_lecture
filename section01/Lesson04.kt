// 섹션 1 - 강의 4: 코틀린에서 연산자를 다루는 방법
// 실행:  .\run.ps1 section01\Lesson04.kt

fun main() {
    // ============================================================
    // ① == 와 === — 자바와 의미가 뒤바뀐다 (핵심!)
    //    ==  : 내용(값) 비교  = Java의 .equals()
    //    === : 주소(참조) 비교 = Java의 ==
    // ============================================================
    val a = Money(1000)
    val b = Money(1000)      // 값은 같지만 '다른 객체'
    val c = a                // c는 a와 '같은 객체'를 가리킴

    println("a == b  : ${a == b}")    // true  (내용 비교 → 금액 같음)
    println("a === b : ${a === b}")   // false (다른 객체, 주소 다름)
    println("a === c : ${a === c}")   // true  (같은 객체)

    // ============================================================
    // ② 비교 연산자 — Comparable 구현하면 <, >, >=, <= 가 compareTo 호출
    // ============================================================
    val m1 = Money(1000)
    val m2 = Money(2000)
    println("m1 < m2 : ${m1 < m2}")   // true  (내부적으로 compareTo)
    println("m1 > m2 : ${m1 > m2}")   // false

    // ============================================================
    // ③ 연산자 오버로딩 — 내 클래스에 + 를 직접 정의 (operator fun plus)
    // ============================================================
    val sum = m1 + m2                 // Money(3000)
    println("m1 + m2 = ${sum.amount}원")

    // ============================================================
    // ④ 범위(range) · 포함(in) · 중위함수(infix)
    // ============================================================
    val score = 85
    println("85 in 60..100 : ${score in 60..100}")   // true (합격 범위 안?)
    println("85 in 0..59   : ${score in 0..59}")     // false

    // 범위를 for에 활용
    print("1..5 순회: ")
    for (i in 1..5) print("$i ")
    println()
    print("5 downTo 1 순회: ")
    for (i in 5 downTo 1) print("$i ")   // downTo 는 중위함수
    println()
}

// Comparable 구현 + plus 연산자 오버로딩을 가진 간단한 돈 클래스
class Money(val amount: Int) : Comparable<Money> {

    // == 비교 시 호출되는 내용 비교 규칙 (Java의 equals에 해당)
    override fun equals(other: Any?): Boolean {
        if (other !is Money) return false
        return this.amount == other.amount
    }
    override fun hashCode(): Int = amount

    // < > 비교 시 자동 호출됨
    override fun compareTo(other: Money): Int {
        return this.amount.compareTo(other.amount)
    }

    // + 연산자 정의 (operator 키워드 필수)
    operator fun plus(other: Money): Money {
        return Money(this.amount + other.amount)
    }
}
