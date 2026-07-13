// 고급편 섹션3(복잡한 함수형) - 강의3: 함수 합성 (andThen / compose, 부분 적용)
// 실행:  .\run.ps1 section11\Lesson03.kt

// (A)->B 와 (B)->C 를 이어 (A)->C 로 만드는 확장함수 (코틀린 기본 내장 아님 → 직접)
// this = 첫 함수, next = 이어붙일 함수. infix 라 "a andThen b" 중위 표기 가능.
infix fun <A, B, C> ((A) -> B).andThen(next: (B) -> C): (A) -> C =
    { a -> next(this(a)) }        // a를 this에 넣고, 그 결과를 next에 넣음

// compose는 순서 반대: this(prev(a))  → prev 먼저, this 나중
infix fun <A, B, C> ((B) -> C).compose(prev: (A) -> B): (A) -> C =
    { a -> this(prev(a)) }

fun main() {
    // ① 함수를 값으로 담기 (복습 — 함수도 값)
    val plus2  = { x: Int -> x + 2 }
    val times3 = { x: Int -> x * 3 }

    // ② andThen — f 먼저, g 나중 → g(f(x))
    val plusThenTimes = plus2 andThen times3     // (x+2)*3
    println("(5+2)*3 = ${plusThenTimes(5)}")     // 21

    // ③ compose — g 먼저, f 나중 → f(g(x))  (순서 반대)
    val timesThenPlus = plus2 compose times3     // (x*3)+2
    println("(5*3)+2 = ${timesThenPlus(5)}")     // 17

    // ④ 여러 개 이어붙이기 — 텍스트 처리 파이프라인
    val trim    = { s: String -> s.trim() }
    val lower   = { s: String -> s.lowercase() }
    val exclaim = { s: String -> "$s!" }
    val process = trim andThen lower andThen exclaim
    println("process = '${process("  HELLO  ")}'")   // 'hello!'

    // ⑤ 부분 적용(partial application) — 설정을 고정한 새 함수를 "반환"
    val double = multiplier(2)    // factor=2 로 고정된 함수
    val triple = multiplier(3)    // factor=3 로 고정된 함수
    println("double(10) = ${double(10)}, triple(10) = ${triple(10)}")  // 20, 30

    // ⑥ 합성 + 부분적용 조합
    val doubleThenPlus2 = double andThen plus2   // (x*2)+2
    println("(10*2)+2 = ${doubleThenPlus2(10)}") // 22
}

// factor를 받아, "그 factor로 곱하는 함수"를 돌려준다 (함수를 반환하는 함수)
fun multiplier(factor: Int): (Int) -> Int = { x -> x * factor }
