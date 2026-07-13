// 고급편 섹션4(DSL) - 강의1: 수신객체 지정 람다 (T.() -> R)
// 실행:  .\run.ps1 section12\Lesson01.kt

fun main() {
    // ============================================================
    // ① 일반 람다 (T)->Unit — 객체가 파라미터 → it 으로 접근
    // ============================================================
    val sb1 = build1 { it.append("일반 람다: it 필요") }
    println(sb1)

    // ============================================================
    // ② 수신객체 지정 람다 T.()->Unit — 객체가 this → append 바로 호출
    // ============================================================
    val sb2 = build2 { append("수신객체 람다: this 생략") }   // this.append
    println(sb2)

    // ============================================================
    // ③ 이게 apply의 정체 (섹션8 복습)
    //    inline fun <T> T.apply(block: T.() -> Unit): T
    // ============================================================
    val sb3 = StringBuilder().apply { append("apply도 결국 T.()->Unit") }
    println(sb3)

    // ============================================================
    // ④ 미니 DSL — 수신객체 람다로 "나만의 문법"
    //    menu { ... } 안이 Menu의 this 세상 → item(...)이 문법처럼 읽힘
    // ============================================================
    val lunch = menu {
        item("김치찌개", 8000)
        item("된장찌개", 7000)
        item("공기밥", 1000)
    }
    lunch.print()
}

// 일반 람다: StringBuilder를 인자로 넘김 → 람다 안에서 it 으로 접근
fun build1(block: (StringBuilder) -> Unit): StringBuilder {
    val sb = StringBuilder()
    block(sb)
    return sb
}

// 수신객체 지정 람다: sb를 "수신객체"로 실행 → 람다 안에서 this(생략) 로 append
fun build2(block: StringBuilder.() -> Unit): StringBuilder {
    val sb = StringBuilder()
    sb.block()          // = build2에 넘긴 람다를 sb를 this로 삼아 실행
    return sb
}

// ---- 미니 DSL ----
class Menu {
    private val items = mutableListOf<Pair<String, Int>>()
    fun item(name: String, price: Int) {
        items.add(name to price)
    }
    fun print() = items.forEach { println("  ${it.first} : ${it.second}원") }
}

// menu { ... } : 블록 안에서 this=Menu → item(...) 을 접두어 없이 호출
fun menu(block: Menu.() -> Unit): Menu {
    val m = Menu()
    m.block()
    return m
}
