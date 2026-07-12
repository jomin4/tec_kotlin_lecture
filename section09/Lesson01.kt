// 고급편 섹션1(제네릭) - 강의1: 제네릭 기초 (타입 파라미터, 제네릭 클래스/함수, 타입 제약)
// 실행:  .\run.ps1 section09\Lesson01.kt

fun main() {
    // ============================================================
    // ① 제네릭 클래스 — 타입 T를 나중에(생성 시) 정한다
    //    자바: class Box<T> { private T value; ... }
    // ============================================================
    val intBox = Box(42)              // T = Int 로 추론 → Box<Int>
    val strBox = Box("코틀린")         // T = String → Box<String>
    println("intBox = ${intBox.get()}, strBox = ${strBox.get()}")
    // 꺼낼 때 캐스트가 필요 없다. get()의 반환 타입이 이미 Int/String.
    val n: Int = intBox.get()         // 캐스트 없이 바로 Int
    println("n + 1 = ${n + 1}")       // 43

    // ============================================================
    // ② 제네릭 함수 — fun 뒤에 <T>를 먼저 선언
    // ============================================================
    println("firstOf = ${firstOf(listOf("a", "b", "c"))}")  // a
    println("myLast  = ${listOf(1, 2, 3).myLast()}")        // 3 (제네릭 확장함수)

    // ============================================================
    // ③ 타입 파라미터 2개 — <A, B> (Map<K,V>가 이 구조)
    // ============================================================
    val entry = MyPair("age", 30)     // MyPair<String, Int>
    println("entry = ${entry.first} -> ${entry.second}")

    // ============================================================
    // ④ 타입 제약 (upper bound): T는 Number의 하위타입만
    //    자바: <T extends Number>
    // ============================================================
    println("sum(Int)    = ${sumAll(listOf(1, 2, 3))}")       // 6.0
    println("sum(Double) = ${sumAll(listOf(1.5, 2.5))}")      // 4.0
    // sumAll(listOf("a", "b"))   // ❌ String은 Number 아님 → 컴파일 에러

    // ============================================================
    // ⑤ 제네릭이 없다면? — Any + 캐스트 (자바 제네릭 이전 방식, 위험)
    // ============================================================
    val anyBox = AnyBox("hello")
    val s = anyBox.value as String    // 직접 캐스트 필요
    println("anyBox = $s (길이 ${s.length})")
    // val bad = anyBox.value as Int   // 💥 런타임 ClassCastException (컴파일은 통과)

    // ============================================================
    // ⑥ 타입 소거 확인 — 런타임엔 <T>가 지워진다
    // ============================================================
    println("intBox is Box<*> ? ${intBox is Box<*>}")  // true (Box<Int>로는 검사 불가)
}

// 제네릭 클래스: T는 타입 파라미터(관례상 T)
class Box<T>(private val value: T) {
    fun get(): T = value
}

// 제네릭 함수: fun 뒤 <T> 선언 후 파라미터/반환에 사용
fun <T> firstOf(list: List<T>): T = list[0]

// 제네릭 확장 함수
fun <T> List<T>.myLast(): T = this[this.size - 1]

// 두 개의 타입 파라미터
class MyPair<A, B>(val first: A, val second: B)

// 타입 제약: T는 Number의 하위타입만 → .toDouble() 호출 가능
fun <T : Number> sumAll(list: List<T>): Double = list.sumOf { it.toDouble() }

// 제네릭 없이 Any로 담으면: 꺼낼 때 캐스트 필요 + 타입 안전하지 않음
class AnyBox(val value: Any)
