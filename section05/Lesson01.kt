// 섹션 5 - 강의 1: 코틀린에서 상속 (open / override / 추상클래스)
// 실행:  .\run.ps1 section05\Lesson01.kt

fun main() {
    // ============================================================
    // ① 추상 클래스를 상속한 자식들
    // ============================================================
    val dog = Dog("바둑이")
    val cat = Cat("나비")

    // 자식이 override한 sound()
    println("${dog.name}: ${dog.sound()}")
    println("${cat.name}: ${cat.sound()}")

    // 부모의 일반 함수는 그대로 물려받음
    println(dog.breathe())
    println(cat.breathe())

    // ============================================================
    // ② 다형성 — 부모 타입으로 자식을 담을 수 있다
    // ============================================================
    val animals: List<Animal> = listOf(Dog("초코"), Cat("치즈"), Dog("몽이"))
    println("--- 동물들의 소리 ---")
    for (animal in animals) {
        // 실제 객체에 맞는 sound()가 호출됨 (다형성)
        println("${animal.name}: ${animal.sound()}")
    }

    // ============================================================
    // ③ open 클래스 상속 + 프로퍼티 override
    // ============================================================
    val circle = Circle(5.0)
    val square = Square(4.0)
    println("원 넓이: ${circle.area}")
    println("정사각형 넓이: ${square.area}")
}

// ============================================================
// 추상 클래스: abstract 멤버는 자식이 반드시 구현
// (추상 클래스는 open을 안 붙여도 상속 가능)
// ============================================================
abstract class Animal(val name: String) {
    abstract fun sound(): String              // 몸통 없음 → 자식이 구현
    fun breathe(): String = "$name 이(가) 숨을 쉰다"  // 일반 함수는 물려줌
}

class Dog(name: String) : Animal(name) {       // : Animal(name) = 부모 생성자 호출
    override fun sound(): String = "멍멍"        // override 필수
}

class Cat(name: String) : Animal(name) {
    override fun sound(): String = "야옹"
}

// ============================================================
// open 클래스 + open 프로퍼티 override
// ============================================================
open class Shape {
    open val area: Double = 0.0               // open이라 자식이 재정의 가능
}

class Circle(val radius: Double) : Shape() {
    override val area: Double
        get() = 3.14 * radius * radius
}

class Square(val side: Double) : Shape() {
    override val area: Double
        get() = side * side
}
