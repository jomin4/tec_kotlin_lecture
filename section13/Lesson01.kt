// 고급편 섹션5(어노테이션과 리플렉션) - 강의1: 어노테이션 만들기 + 리플렉션 기초
// 실행:  .\run.ps1 section13\Lesson01.kt
// 참고: 여기선 "자바 리플렉션"(JDK 내장)만 써서 별도 라이브러리 없이 돌아간다.
//       코틀린 전용 리플렉션(::class.memberProperties 등)은 kotlin-reflect 의존성이 필요.

import java.lang.reflect.Field

// ============================================================
// ① 어노테이션 직접 만들기 (자바의 @interface 에 해당)
//    @Retention(RUNTIME) : 컴파일 후에도 살아남아 실행 중에 읽을 수 있게
//    @Target(FIELD)      : 필드에 붙일 수 있는 어노테이션
// ============================================================
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Label(val text: String)   // 값(text)을 받는 어노테이션

// @field: 를 붙여 "자바 필드"에 어노테이션이 달리게 한다 (리플렉션으로 읽으려고)
class User(
    @field:Label("이름") val name: String,
    @field:Label("나이") val age: Int,
    val internalId: String,                  // 어노테이션 없는 필드
)

fun main() {
    val user = User("홍길동", 30, "u-001")

    // ============================================================
    // ② 리플렉션 — 실행 중에 이 객체의 클래스 구조를 들여다본다
    // ============================================================
    val clazz = user.javaClass               // 이 객체의 클래스 정보(Class)
    println("클래스 이름 = ${clazz.simpleName}")

    // 필드들을 하나씩 훑으며, @Label이 있으면 그 텍스트를, 없으면 필드명을 라벨로
    for (field: Field in clazz.declaredFields) {
        field.isAccessible = true                        // private 필드 접근 허용
        val ann = field.getAnnotation(Label::class.java) // 이 필드에 붙은 @Label 읽기
        val label = ann?.text ?: field.name              // 없으면 필드 이름
        val value = field.get(user)                      // 런타임에 값도 꺼냄
        println("$label = $value")
    }

    // ============================================================
    // ③ 이게 프레임워크의 축소판 — 어노테이션을 리플렉션으로 읽어 "동작"을 만든다
    //    아래는 위 원리로 만든 미니 직렬화기 (객체 → 라벨: 값 문자열)
    // ============================================================
    println("--- 미니 직렬화 ---")
    println(labelledString(user))
}

// @Label 이 붙은 필드만 골라 "라벨=값" 으로 이어붙인다
fun labelledString(obj: Any): String =
    obj.javaClass.declaredFields
        .also { fields -> fields.forEach { it.isAccessible = true } }
        .mapNotNull { field ->
            val ann = field.getAnnotation(Label::class.java) ?: return@mapNotNull null
            "${ann.text}=${field.get(obj)}"
        }
        .joinToString(", ")
