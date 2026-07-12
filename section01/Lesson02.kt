// 섹션 1 - 강의 2: 코틀린에서 null을 다루는 방법
// 실행:  .\run.ps1 section01\Lesson02.kt

fun main() {
    // 1) non-null 타입 vs nullable 타입
    val safe: String = "코틀린"     // null 불가
    // val bad: String = null       // ❌ 컴파일 에러 (주석 풀어보면 에러남)
    val nullable: String? = null    // ? 붙이면 null 허용
    println("safe = $safe, nullable = $nullable")

    // 2) 안전 호출 ?.  → null이면 실행 안 하고 그냥 null 반환
    val name: String? = null
    println("name?.length = ${name?.length}")     // null (NPE 안 남!)
    // println(name.length)   // ❌ 컴파일 에러: nullable은 그냥 .length 못 씀

    val realName: String? = "David"
    println("realName?.length = ${realName?.length}")  // 5

    // 3) 엘비스 연산자 ?:  → 왼쪽이 null이면 오른쪽 값 사용
    val len1 = name?.length ?: 0
    val len2 = realName?.length ?: 0
    println("len1 = $len1 (null이라 기본값 0)")
    println("len2 = $len2")

    // 4) 조합: null이면 기본값, 아니면 실제 값
    val display = name ?: "이름 없음"
    println("display = $display")

    // 5) ?.let  → null이 아닐 때만 블록 실행 (it = 그 값)
    realName?.let {
        println("let 블록 실행됨: 이름은 $it, 길이 ${it.length}")
    }
    name?.let {
        println("이 줄은 절대 안 찍힘 (name이 null이라 블록 통째로 건너뜀)")
    }

    // 6) !! 널 아님 단언 → 아래 주석 풀면 실행 중 NPE 발생 (일부러 확인해보기)
    // val boom = name!!.length
    // println(boom)
    println("끝. (6번은 주석 풀면 NPE 나는지 직접 확인해보세요)")
}
