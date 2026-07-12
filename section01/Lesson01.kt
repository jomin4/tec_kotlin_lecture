// 섹션 1 - 강의 1: 코틀린에서 변수를 다루는 방법
// 실행:  kotlinc Lesson01_변수.kt -include-runtime -d lesson01.jar && kotlin -jar lesson01.jar
//   또는 간단히:  kotlin Lesson01_변수.kt   (스크립트처럼)  ← 아래 main 사용

fun main() {
    // 1) val = 값을 바꿀 수 없는 변수 (Java의 final)
    val number = 10          // 타입을 안 써도 코틀린이 Int로 추론
    // number = 20           // ❌ 컴파일 에러: val은 재할당 불가
    println("val number = $number")

    // 2) var = 값을 바꿀 수 있는 변수
    var count = 1
    count = 2                // ✅ OK
    println("var count = $count")

    // 3) 타입을 직접 명시할 수도 있음
    val name: String = "코틀린"
    println("name = $name")

    // 4) val 이라도 '내부' 값은 바꿀 수 있다 (참조가 고정될 뿐)
    val list = mutableListOf(1, 2, 3)
    list.add(4)              // ✅ list라는 '참조'는 그대로, 내용만 변경
    println("list = $list")

    // 5) 초기값 없이 선언 후 나중에 대입도 가능 (타입은 명시해야 함)
    val message: String
    message = "나중에 대입"
    println("message = $message")
}
