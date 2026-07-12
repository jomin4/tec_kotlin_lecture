// 고급편 섹션2(지연과 위임) - 강의2: 위임 프로퍼티 (by)
// 실행:  .\run.ps1 section10\Lesson02.kt

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

fun main() {
    // ============================================================
    // ① 커스텀 위임 — by 오른쪽 객체가 getValue/setValue를 가지면 됨
    //    프로퍼티를 읽고 쓸 때 그 함수들이 대신 호출된다
    // ============================================================
    val demo = Demo()
    println("읽기: ${demo.name}")      // → LoggingDelegate.getValue 호출
    demo.name = "홍길동"                // → LoggingDelegate.setValue 호출
    println("읽기: ${demo.name}")

    // ============================================================
    // ② observable — 값이 바뀔 때마다 콜백 (로깅·추적)
    // ============================================================
    println("=== observable ===")
    var score: Int by Delegates.observable(0) { prop, old, new ->
        println("  ${prop.name}: $old -> $new")   // 바뀔 때마다 실행
    }
    score = 50    // score: 0 -> 50
    score = 90    // score: 50 -> 90

    // ============================================================
    // ③ vetoable — 변경을 "거부"할 수 있음 (false 반환 시 롤백)
    // ============================================================
    println("=== vetoable ===")
    var age: Int by Delegates.vetoable(20) { _, old, new ->
        new >= 0    // 음수면 false → 변경 거부(old 유지)
    }
    age = 30
    println("  age = $age")   // 30 (허용됨)
    age = -5
    println("  age = $age")   // 30 (거부돼서 그대로)

    // ============================================================
    // ④ by map — 프로퍼티 값을 Map에서 꺼냄 (설정·JSON 매핑)
    // ============================================================
    println("=== by map ===")
    val json = mapOf("id" to 1, "title" to "코틀린 고급")
    val post = Post(json)
    println("  post.id = ${post.id}, post.title = ${post.title}")
}

// 커스텀 위임 객체: getValue(읽기) + setValue(쓰기, var용)
class LoggingDelegate {
    private var value: String = "(초기값)"
    operator fun getValue(ref: Any?, prop: KProperty<*>): String {
        println("  [get] ${prop.name} 읽음 -> $value")
        return value
    }
    operator fun setValue(ref: Any?, prop: KProperty<*>, newValue: String) {
        println("  [set] ${prop.name}: $value -> $newValue")
        value = newValue
    }
}

class Demo {
    var name: String by LoggingDelegate()   // name의 get/set을 위임
}

// by map: 프로퍼티 이름을 키로 Map에서 값을 읽어온다
class Post(map: Map<String, Any?>) {
    val id: Int by map
    val title: String by map
}
