// 섹션 8 - 강의 2: 컬렉션 함수형 처리 (filter / map / forEach 및 동료들)
// 실행:  .\run.ps1 section08\Lesson02.kt

data class Person(val name: String, val age: Int, val city: String)

fun main() {
    val nums = listOf(1, 2, 3, 4, 5, 6)

    // ============================================================
    // ① filter — 조건 참인 것만. 개수가 줄어든다. (새 리스트 반환)
    //    자바: nums.stream().filter(n -> n%2==0).collect(toList())
    // ============================================================
    val evens = nums.filter { it % 2 == 0 }
    println("짝수만 = $evens")               // [2, 4, 6]
    println("원본 그대로 = $nums")             // [1,2,3,4,5,6]  ← filter는 원본 안 건드림

    // ============================================================
    // ② map — 각 원소 변환. 개수는 그대로, 값이 바뀐다.
    // ============================================================
    val squares = nums.map { it * it }
    println("제곱 = $squares")                // [1, 4, 9, 16, 25, 36]

    // ③ 체이닝 — filter 후 map (실무 기본기)
    val result = nums
        .filter { it % 2 == 0 }              // [2, 4, 6]
        .map { it * 10 }                     // [20, 40, 60]
    println("짝수 x10 = $result")

    // ============================================================
    // ④ forEach — 각 원소로 작업. 반환값 없음(Unit). 출력/부수효과용.
    // ============================================================
    print("forEach: ")
    nums.filter { it > 4 }.forEach { print("$it ") }  // 5 6
    println()

    // ============================================================
    // ⑤ 자주 쓰는 동료들 — 판단/집계/정렬/그룹
    // ============================================================
    println("any(>5)   = ${nums.any { it > 5 }}")    // true  (하나라도 있나)
    println("all(>0)   = ${nums.all { it > 0 }}")    // true  (전부 그런가)
    println("none(>10) = ${nums.none { it > 10 }}")  // true  (하나도 없나)
    println("count(짝) = ${nums.count { it % 2 == 0 }}") // 3
    println("합계      = ${nums.sumOf { it }}")       // 21
    println("첫 3초과  = ${nums.find { it > 3 }}")    // 4 (없으면 null)

    // ============================================================
    // ⑥ 객체 컬렉션에 실전 적용 (data class 복습)
    // ============================================================
    val people = listOf(
        Person("홍길동", 30, "서울"),
        Person("김철수", 17, "부산"),
        Person("이영희", 25, "서울"),
        Person("박민수", 40, "부산"),
    )

    // 성인 이름만 뽑아 정렬
    val adults = people
        .filter { it.age >= 20 }             // 성인만
        .sortedBy { it.age }                 // 나이 오름차순
        .map { it.name }                     // 이름만 추출
    println("성인(나이순) = $adults")          // [이영희, 홍길동, 박민수]

    // 도시별로 묶기 → Map<String, List<Person>>
    val byCity = people.groupBy { it.city }
    byCity.forEach { (city, members) ->
        println("  $city : ${members.map { it.name }}")
    }

    // 평균 나이 (합계 / 개수)
    val avg = people.sumOf { it.age }.toDouble() / people.size
    println("평균 나이 = $avg")               // 28.0

    // ============================================================
    // ⑦ 큰 데이터라면 asSequence()로 lazy 처리 (자바 Stream과 동일 동작)
    // ============================================================
    val lazyResult = nums.asSequence()
        .filter { it % 2 == 0 }              // 중간 리스트 안 만들고
        .map { it * 10 }                     // 원소 하나씩 파이프 통과
        .toList()                            // 마지막에 리스트로 수거
    println("sequence 결과 = $lazyResult")    // [20, 40, 60]
}
