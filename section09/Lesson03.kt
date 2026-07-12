// 고급편 섹션1(제네릭) - 강의3: 마무리 (reified, star projection) → 제네릭 섹션 종료
// 실행:  .\run.ps1 section09\Lesson03.kt

fun main() {
    // ============================================================
    // ① reified — 소거 우회. inline 함수에서 T를 런타임까지 살림
    // ============================================================
    val mixed: List<Any> = listOf(1, "two", 3, "four", 5)
    println("Int만    = ${mixed.filterByType<Int>()}")     // [1, 3, 5]
    println("String만 = ${mixed.filterByType<String>()}")  // [two, four]

    // 표준 라이브러리에도 같은 원리가 이미 있다: filterIsInstance
    println("stdlib   = ${mixed.filterIsInstance<String>()}") // [two, four]

    // T::class 도 reified 덕분에 가능
    println("타입이름 = ${typeName<Double>()}")             // Double

    // ============================================================
    // ② star projection <*> — 타입을 모를 때 "뭔가 하나로 정해졌지만 unknown"
    // ============================================================
    val boxes: List<Box<*>> = listOf(Box(1), Box("hi"), Box(3.14))  // 서로 다른 Box를 한 리스트에
    boxes.forEach { box ->
        val v = box.get()      // 읽기 OK — 타입은 Any? (구체 타입 모르니 안전하게)
        println("  box 값 = $v (${v?.let { it::class.simpleName }})")
    }
    // boxes[0].set(...)        // ❌ <*>엔 쓰기 불가 (실제 T를 몰라서)

    // ============================================================
    // ③ star projection 파라미터 — 원소 타입은 몰라도 되는 함수
    // ============================================================
    printSize(listOf(1, 2, 3))       // List<Int>
    printSize(setOf("a", "b"))       // Set<String>  둘 다 Collection<*>로 받음
}

class Box<T>(private val value: T) {
    fun get(): T = value
}

// reified: <T>를 런타임에 검사 가능 (inline 필수). List<Any>에서 T 타입만 추려냄
inline fun <reified T> List<Any>.filterByType(): List<T> =
    this.filter { it is T }.map { it as T }

// reified 로 T::class 접근
inline fun <reified T> typeName(): String = T::class.simpleName ?: "?"

// star projection: 원소 타입 몰라도 공통 연산(size 등)은 가능
fun printSize(c: Collection<*>) {
    val firstType = c.firstOrNull()?.let { it::class.simpleName }
    println("  크기 = ${c.size}, 첫 원소 타입 = $firstType")
}
