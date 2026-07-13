// 고급편 섹션5(어노테이션과 리플렉션) - 강의2: 리플렉션 실전 = 미니 검증 프레임워크
// 실행:  .\run.ps1 section13\Lesson02.kt
// 스프링 @Valid / Bean Validation 의 축소판: 규칙은 어노테이션으로 선언, 검사는 리플렉션으로.

import java.lang.reflect.Field

// ============================================================
// ① 검증 규칙 어노테이션 2개 (필드에 붙는 표식, 런타임까지 생존)
// ============================================================
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class NotBlank                       // 값 없는 규칙

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Min(val value: Int)            // 기준값을 받는 규칙

// ============================================================
// ② 검증 대상 — 필드에 규칙을 "선언"만 해둔다
// ============================================================
class Form(
    @field:NotBlank val name: String,
    @field:Min(1)   val age: Int,
)

// ============================================================
// ③ 검증기 — 리플렉션으로 규칙을 읽어 실제 검사 (프레임워크 역할)
// ============================================================
fun validate(obj: Any): List<String> {
    val errors = mutableListOf<String>()
    for (field: Field in obj.javaClass.declaredFields) {
        field.isAccessible = true
        val value = field.get(obj)                          // 런타임 값

        // @NotBlank 규칙: String인데 비어있으면 위반
        if (field.isAnnotationPresent(NotBlank::class.java) && value is String && value.isBlank()) {
            errors.add("${field.name}: 비어있으면 안 됨")
        }

        // @Min 규칙: 값을 가진 어노테이션 → 기준(min.value)과 비교
        field.getAnnotation(Min::class.java)?.let { min ->
            if (value is Int && value < min.value) {
                errors.add("${field.name}: ${min.value} 이상이어야 함 (현재 $value)")
            }
        }
    }
    return errors
}

fun main() {
    val bad  = Form("", 0)
    val good = Form("홍길동", 30)

    println("bad  → ${validate(bad)}")     // 두 규칙 다 위반
    println("good → ${validate(good)}")    // 통과 → 빈 리스트

    // 검증 결과에 따라 분기 (실무의 if (errors.isNotEmpty()) return badRequest ...)
    val errors = validate(bad)
    if (errors.isEmpty()) println("bad 저장 OK")
    else println("bad 거부됨: ${errors.size}건 → ${errors.joinToString("; ")}")
}
