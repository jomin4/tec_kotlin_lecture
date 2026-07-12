// 섹션 7 - 강의 1: 컬렉션 (List / Set / Map, 읽기전용 vs 가변, 널 가능 컬렉션)
// 실행:  .\run.ps1 section07\Lesson01.kt

fun main() {
    // ============================================================
    // ① List — 순서 O, 중복 O
    //    자바: List<String> list = new ArrayList<>();  → 항상 가변
    //    코틀린: 읽기전용(listOf) / 가변(mutableListOf)을 "타입으로" 구분
    // ============================================================
    val names = listOf("홍길동", "김철수", "홍길동")   // List<String> : 읽기 전용
    println("names = $names")                          // [홍길동, 김철수, 홍길동] (중복 유지)
    println("첫번째 = ${names[0]}")                     // 인덱스 접근 = 연산자 오버로딩 (get)
    println("크기 = ${names.size}, '김철수' 있나? = ${"김철수" in names}")

    // names.add("이순신")   // ❌ 읽기전용 List엔 add가 아예 없음 → 컴파일 에러
    //                        //    (자바 List.of()는 런타임에 터지지만, 코틀린은 컴파일에 막음)

    val editable = mutableListOf("A", "B")              // MutableList<String> : 가변
    editable.add("C")                                  // ✅ 추가
    editable.removeAt(0)                               // ✅ 인덱스 0 삭제
    println("editable = $editable")                    // [B, C]

    // ============================================================
    // ② Set — 순서 X, 중복 자동 제거
    // ============================================================
    val ids = setOf(1, 2, 2, 3, 3, 3)                  // Set<Int>
    println("ids = $ids")                              // [1, 2, 3]  ← 중복 사라짐
    println("2 포함? = ${2 in ids}")                    // 조회는 in / contains

    // ============================================================
    // ③ Map — 키-값. "키 to 값" 은 Pair를 만드는 중위함수 (섹션1 연산자 복습)
    // ============================================================
    val capitals = mapOf(
        "한국" to "서울",
        "일본" to "도쿄",
    )                                                  // Map<String, String>
    println("한국의 수도 = ${capitals["한국"]}")           // 서울  ← map[key]
    println("없는 키 = ${capitals["미국"]}")              // null  ← 없으면 null 반환

    val scores = mutableMapOf("국어" to 90)              // MutableMap<String, Int>
    scores["수학"] = 85                                  // 추가 (set)
    scores["국어"] = 95                                  // 덮어쓰기
    println("scores = $scores")                        // {국어=95, 수학=85}

    // Map 순회: key/value 구조분해 (섹션4 data class 복습)
    for ((subject, score) in scores) {
        println("  $subject → $score")
    }

    // ============================================================
    // ④ 널(null)이 "어디에" 붙느냐 — 자바엔 없던 구분
    // ============================================================
    val withNullElem: List<Int?> = listOf(1, null, 3)  // 원소가 널 가능
    val sum = withNullElem.filterNotNull().sum()       // null 걸러내고 합 → 4
    println("null 원소 제거 후 합 = $sum")

    val maybeList: List<Int>? = null                   // 리스트 자체가 널 가능
    println("리스트 크기(안전호출) = ${maybeList?.size}")  // null (?. 로 NPE 회피)
    println("null이면 빈 리스트로 = ${maybeList ?: emptyList()}") // ?: 로 기본값
}
