// 고급편 섹션1(제네릭) - 강의2: 변성 (in/out, 공변·반공변·무공변)
// 실행:  .\run.ps1 section09\Lesson02.kt

fun main() {
    // ============================================================
    // ① 무공변(invariant) — 기본. String<:Any 라도 List 사이엔 상속관계 없음
    // ============================================================
    val strings: MutableList<String> = mutableListOf("a", "b")
    // val anys: MutableList<Any> = strings   // ❌ 컴파일 에러 (MutableList는 무공변)
    //   ↑ 허용됐다면 anys.add(42) 후 strings[2]가 Int가 되는 참사 → 그래서 막는다
    println("무공변: strings = $strings")

    // ============================================================
    // ② 공변(out) — 읽기전용 List는 List<out E> → 상속관계가 흐른다
    // ============================================================
    val readList: List<String> = listOf("x", "y")
    val anyList: List<Any> = readList          // ✅ List는 out → 공변 (넣기 불가라 안전)
    println("공변: size=${anyList.size}, first=${anyList[0]}")

    printAll(listOf("코틀린", "자바"))            // List<String>을 List<Any> 자리에 그대로 전달

    // ============================================================
    // ③ 직접 만든 공변 클래스 (생산자) — Producer<String>을 Producer<Any>로
    // ============================================================
    val stringProducer: Producer<String> = StringProducer()
    val anyProducer: Producer<Any> = stringProducer   // ✅ out이라 공변
    println("생산: ${anyProducer.produce()}")

    // ============================================================
    // ④ 반공변(in) — 소비자. Consumer<Any>를 Consumer<String>으로
    //    "Any를 다 처리하면 String도 당연히 처리 가능"
    // ============================================================
    val anyConsumer: Consumer<Any> = AnyConsumer()
    val stringConsumer: Consumer<String> = anyConsumer   // ✅ in이라 반공변
    stringConsumer.consume("hello")

    // ============================================================
    // ⑤ 실전 감각 — 복사 함수에 out/in을 함께
    //    from(생산자)은 out T, to(소비자)는 in T
    // ============================================================
    val src: List<Int> = listOf(1, 2, 3)
    val dst = mutableListOf<Number>()
    copy(src, dst)                             // List<Int> → MutableList<Number> OK
    println("복사 결과: $dst")
}

fun printAll(items: List<Any>) {              // List<out Any>라 List<String>도 받음
    items.forEach { println("  - $it") }
}

// out T: T를 "생산만"(반환) → 공변. T를 파라미터로 못 받음
interface Producer<out T> {
    fun produce(): T
}
class StringProducer : Producer<String> {
    override fun produce(): String = "문자열"
}

// in T: T를 "소비만"(파라미터) → 반공변. T를 반환 못 함
interface Consumer<in T> {
    fun consume(item: T)
}
class AnyConsumer : Consumer<Any> {
    override fun consume(item: Any) = println("  소비: $item")
}

// 실전: 생산 측은 out(? extends), 소비 측은 in(? super)
fun <T> copy(from: List<out T>, to: MutableList<in T>) {
    from.forEach { to.add(it) }
}
