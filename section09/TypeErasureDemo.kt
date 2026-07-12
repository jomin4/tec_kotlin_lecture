// 고급편 섹션1(제네릭) - 보충: 타입 소거(type erasure) 데모
// "is Box<Int>는 왜 안 되고 is Box<*>만 되나"를 눈으로 확인
// 실행:  .\run.ps1 section09\TypeErasureDemo.kt

class Box<T>(val value: T)

// reified: 소거를 우회 — inline 함수에서만 가능 (T를 런타임까지 살림)
inline fun <reified T> Box<*>.holds(): Boolean = this.value is T

fun main() {
    val intBox = Box(42)
    val strBox = Box("hi")

    // ① <T>는 런타임에 지워진다 → Box<Int>와 Box<String>이 같은 클래스
    println("intBox 클래스        = ${intBox::class.simpleName}")       // Box
    println("intBox∙strBox 동일? = ${intBox::class == strBox::class}") // true

    // ② is Box<*> 만 가능 (껍데기가 Box인지만 검사)
    val any: Any = intBox
    println("any is Box<*> ?      = ${any is Box<*>}")                 // true
    // println(any is Box<Int>)   // ❌ 컴파일 에러: Cannot check for instance of erased type

    // ③ as 캐스트는 T를 실제로 검사하지 않는다(unchecked) → 틀려도 이 줄은 통과
    val hidden: Any = strBox
    @Suppress("UNCHECKED_CAST")
    val wrong = hidden as Box<Int>     // strBox인데 Box<Int>로 캐스트 — 경고만 뜨고 통과!
    println("잘못된 캐스트도 이 줄은 통과함 (T는 런타임에 안 봄)")
    // val boom = wrong.value + 1       // 💥 여기서야 ClassCastException (value는 실제 String)

    // ④ reified로 소거 우회 — 원소 객체의 실제 타입은 살아있으니 검사 가능
    println("intBox.holds<Int>()    = ${intBox.holds<Int>()}")        // true
    println("intBox.holds<String>() = ${intBox.holds<String>()}")     // false
    println("strBox.holds<String>() = ${strBox.holds<String>()}")     // true
}
