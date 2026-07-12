// 섹션 8 - 강의 3: 스코프 함수 (let / run / with / apply / also) — 커리큘럼 마지막 강의
// 실행:  .\run.ps1 section08\Lesson03.kt

class Person {
    var name: String = ""
    var age: Int = 0
    override fun toString() = "Person(name=$name, age=$age)"
}

fun main() {
    // ============================================================
    // ① apply — 객체 설정 후 "객체 자신"을 반환 (this로 멤버 접근, this 생략)
    //    자바 빌더 패턴을 대체한다
    // ============================================================
    val p = Person().apply {
        name = "홍길동"      // this.name = ...  (this 생략 가능)
        age = 30
    }                       // ← Person 객체 그대로 반환
    println("apply → $p")   // Person(name=홍길동, age=30)

    // ============================================================
    // ② let — null 아닐 때만 실행 + "람다 결과" 반환 (it으로 접근)
    //    섹션1의 ?.let 복습. NPE 없이 안전하게.
    // ============================================================
    val nickname: String? = "gildong"
    val len = nickname?.let {
        println("  닉네임 있음: $it")   // it = nickname
        it.length                      // 마지막 줄 = 반환값
    }
    println("let → 길이=$len")          // 7  (gildong = 7글자)

    val nothing: String? = null
    nothing?.let { println("이 줄은 실행 안 됨") }   // null이라 블록 통째로 skip
    println("null이면 let 블록 skip 확인")

    // ============================================================
    // ③ also — "객체 자신"을 반환 (it으로 접근). 흐름 안 끊고 부수효과 삽입
    //    로깅·검증을 체이닝 중간에 끼울 때
    // ============================================================
    val result = listOf(1, -2, 3, -4, 5)
        .filter { it > 0 }
        .also { println("  also: 양수 ${it.size}개 = $it") }  // 중간 로깅, 흐름 유지
        .map { it * 10 }
    println("also 체이닝 → $result")    // [10, 30, 50]

    // ============================================================
    // ④ run — this로 접근 + "람다 결과" 반환. 객체로 작업 후 값 계산
    // ============================================================
    val summary = p.run {
        // 여기선 p가 this → name, age 바로 접근
        "이름 ${name.length}자, ${if (age >= 20) "성인" else "미성년"}"
    }
    println("run → $summary")           // 이름 3자, 성인

    // ============================================================
    // ⑤ with — run과 비슷하나 "객체를 인자로" 받음 (확장함수 아님)
    //    한 객체에 여러 작업을 묶어 읽기
    // ============================================================
    val desc = with(p) {
        // p가 this
        "[$name / $age세]"
    }
    println("with → $desc")             // [홍길동 / 30세]

    // ============================================================
    // ⑥ 실전 조합 — apply로 만들고 also로 로깅
    // ============================================================
    val user = Person().apply {
        name = "김철수"
        age = 17
    }.also {
        println("  생성됨: $it")         // 만든 직후 로깅, user엔 객체가 담김
    }
    println("최종 user → $user")
}
