// 고급편 섹션3(복잡한 함수형) - 강의1: 인라인 함수 (inline / reified / non-local return)
// 실행:  .\run.ps1 section11\Lesson01.kt

fun main() {
    // ============================================================
    // ① inline 고차함수 — block 람다가 객체로 안 만들어지고 본문이 복사됨
    // ============================================================
    val result = runTwice {
        println("  실행!")
        42
    }
    println("결과 = $result")        // 실행! 두 번 찍히고 결과 = 42

    // ============================================================
    // ② non-local return — inline 람다 속 return은 "바깥 함수"를 끝낸다
    //    forEach가 inline이라 가능 (일반 람다면 컴파일 에러)
    // ============================================================
    println("첫 짝수(1,3,4,5,6) = ${firstEven(listOf(1, 3, 4, 5, 6))}")  // 4
    println("첫 짝수(1,3,5)     = ${firstEven(listOf(1, 3, 5))}")        // null

    // ============================================================
    // ③ reified — inline 덕분에 T가 살아있어 is T 가능 (섹션9 복습)
    // ============================================================
    val mixed = listOf(1, "two", 3.0, "four")
    println("첫 String = ${mixed.firstIsInstance<String>()}")   // two

    // ============================================================
    // ④ 표준 컬렉션 함수는 전부 inline → 람다 오버헤드 0
    // ============================================================
    var sum = 0
    listOf(1, 2, 3, 4).forEach { sum += it }   // 익명 객체 안 만들고 본문 복사
    println("합 = $sum")                        // 10

    // ============================================================
    // ⑤ crossinline — 람다를 다른 실행 맥락(Runnable)에 넘길 땐 non-local return 금지
    // ============================================================
    doAsync { println("  비동기처럼 실행: $it") }   // 비동기처럼 실행: 결과
}

// inline: block을 객체로 안 만들고 본문을 호출부에 복사. 두 번 실행 후 두번째 결과 반환
inline fun <T> runTwice(block: () -> T): T {
    block()
    return block()
}

// non-local return 시연: forEach 람다 속 return이 이 함수를 끝낸다
fun firstEven(nums: List<Int>): Int? {
    nums.forEach { if (it % 2 == 0) return it }
    return null
}

// reified: inline이라 T가 런타임에 살아있음 → it is T 가능
inline fun <reified T> List<*>.firstIsInstance(): T? =
    this.firstOrNull { it is T } as T?

// crossinline: inline은 하되, action 안에서 non-local return을 막음(다른 맥락에 넘기므로)
inline fun doAsync(crossinline action: (String) -> Unit) {
    val task = Runnable { action("결과") }
    task.run()
}
