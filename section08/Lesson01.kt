// 섹션 8 - 강의 1: 람다 (함수를 값으로, 함수타입, it, 트레일링 람다, 고차함수, ::)
// 실행:  .\run.ps1 section08\Lesson01.kt

fun main() {
    // ============================================================
    // ① 함수를 변수에 담는다 (자바: Function<Integer,Integer> f = x -> x*2)
    //    타입 (Int) -> Int = "Int 받아 Int 반환"
    // ============================================================
    val dbl: (Int) -> Int = { x -> x * 2 }
    println("dbl(3) = ${dbl(3)}")          // 6   ← 변수를 함수처럼 호출
    println("dbl.invoke(3) = ${dbl.invoke(3)}") // 6   ← invoke로도 호출됨(같은 것)

    // 파라미터 2개: 이름 필수, 마지막 줄이 반환값
    val add: (Int, Int) -> Int = { a, b -> a + b }
    println("add(2,5) = ${add(2, 5)}")     // 7

    // 인자 없고 반환 없음: () -> Unit  (자바 void / Runnable)
    val hello: () -> Unit = { println("안녕, 람다") }
    hello()

    // ============================================================
    // ② it — 파라미터가 1개면 이름 생략하고 it
    // ============================================================
    val square: (Int) -> Int = { it * it }  // { x -> x * x } 와 동일
    println("square(4) = ${square(4)}")    // 16

    // ============================================================
    // ③ 고차 함수 — 함수를 "파라미터로 받는" 함수
    //    op 자리에 동작을 갈아끼운다 → 재사용성 UP
    // ============================================================
    println("calc +  : ${calc(10, 4) { a, b -> a + b }}") // 14
    println("calc -  : ${calc(10, 4) { a, b -> a - b }}") // 6
    println("calc *  : ${calc(10, 4) { a, b -> a * b }}") // 40

    // ============================================================
    // ④ 트레일링 람다 — 마지막 인자가 람다면 괄호 밖으로
    //    실무에서 filter/map/forEach가 전부 이 문법
    // ============================================================
    val nums = listOf(1, 2, 3, 4, 5)
    // 정석: repeatPrint(3, { println("...") })  →  트레일링으로:
    repeatPrint(3) { i -> println("  $i 번째 실행") }

    // ============================================================
    // ⑤ 함수 참조 (::) — 이미 있는 함수를 람다로 안 감싸고 그대로 넘김
    //    { n -> triple(n) }  →  ::triple
    // ============================================================
    println("람다로   : ${apply3(5) { n -> triple(n) }}") // 15
    println("함수참조로: ${apply3(5, ::triple)}")           // 15  ← ::triple

    // 함수를 "리턴"할 수도 있다
    val plus10 = adder(10)                 // (Int)->Int 를 돌려받음
    println("plus10(7) = ${plus10(7)}")    // 17
}

// 고차함수: 마지막 파라미터가 함수 타입 → 트레일링 람다 대상
fun calc(a: Int, b: Int, op: (Int, Int) -> Int): Int = op(a, b)

fun repeatPrint(n: Int, action: (Int) -> Unit) {
    for (i in 1..n) action(i)
}

fun apply3(x: Int, op: (Int) -> Int): Int = op(x)

fun triple(n: Int): Int = n * 3

// 함수를 반환하는 함수: 반환 타입이 (Int)->Int
fun adder(base: Int): (Int) -> Int = { x -> base + x }
