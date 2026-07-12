// 섹션 5 - 강의 2: 인터페이스 (interface)
// 실행:  .\run.ps1 section05\Lesson02.kt

fun main() {
    // ============================================================
    // ① 인터페이스 구현 + default 메서드
    // ============================================================
    val sparrow = Sparrow("참새")
    println("${sparrow.name}: ${sparrow.fly()}")   // override한 fly()
    println(sparrow.describe())                     // default 메서드 그대로 사용
    println("최대 고도: ${sparrow.maxHeight}m")       // 인터페이스 프로퍼티(기본 getter)

    // ============================================================
    // ② 다중 구현 — 코틀린은 인터페이스 여러 개를 : 뒤에 콤마로
    //    (자바: implements A, B / 코틀린: : A, B)
    // ============================================================
    val duck = Duck("오리")
    println("--- 오리는 날고 헤엄친다 ---")
    println(duck.fly())
    println(duck.swim())

    // ============================================================
    // ③ 다형성 — 인터페이스 타입으로 담기
    //    실제 객체의 fly()가 호출된다
    // ============================================================
    val flyers: List<Flyable> = listOf(Sparrow("제비"), Duck("청둥오리"))
    println("--- 날 수 있는 것들 ---")
    for (f in flyers) {
        println(f.fly())
    }

    // ============================================================
    // ④ 다중 구현 시 default 충돌 → super<타입>으로 지목
    // ============================================================
    val robot = FlyingSwimmingRobot()
    println(robot.move())   // 두 default를 직접 골라 조합
}

// ============================================================
// 인터페이스: abstract 안 붙여도 fun은 기본 추상
// - describe(): 몸통이 있으면 default 메서드 (자바의 default 키워드 불필요)
// - maxHeight: 추상 프로퍼티. get()으로 기본값 제공 (backing field 없음!)
// ============================================================
interface Flyable {
    fun fly(): String                       // 추상 메서드 (자식이 구현)

    fun describe(): String = "이 객체는 날 수 있다"   // default 메서드

    val maxHeight: Int                       // 추상 프로퍼티
        get() = 1000                         // getter로 기본값 (저장 아님, 계산)
}

interface Swimmable {
    fun swim(): String
}

// 인터페이스 하나 구현: implements 대신 : 하나
class Sparrow(val name: String) : Flyable {
    override fun fly(): String = "$name 이(가) 푸드덕 난다"
    // describe(), maxHeight는 default를 그대로 씀 → override 안 해도 됨
}

// 다중 구현: : Flyable, Swimmable  (콤마로 나열)
class Duck(val name: String) : Flyable, Swimmable {
    override fun fly(): String = "$name 이(가) 낮게 난다"
    override fun swim(): String = "$name 이(가) 헤엄친다"
    override val maxHeight: Int = 50         // 프로퍼티를 직접 override(값 지정)
}

// ============================================================
// default 메서드 충돌 해결: super<타입>.메서드()
// 두 인터페이스가 같은 이름의 default move()를 가질 때,
// 컴파일러가 override를 강제한다 → 안에서 super로 골라 조합
// ============================================================
interface Flyer {
    fun move(): String = "날아서 이동"
}

interface Swimmer {
    fun move(): String = "헤엄쳐서 이동"
}

class FlyingSwimmingRobot : Flyer, Swimmer {
    override fun move(): String =
        "${super<Flyer>.move()} + ${super<Swimmer>.move()}"   // 둘 다 지목해 조합
}
