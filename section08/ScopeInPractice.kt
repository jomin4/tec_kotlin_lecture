// 섹션 8 - 보충: 스코프 함수 "실무 배치도"
// 회원 가입 요청 처리 하나에 let/run/with/apply/also가 실제로 놓이는 자리를 담았다.
// 실행:  .\run.ps1 section08\ScopeInPractice.kt

// ── 도메인 모델 (실무의 Entity/DTO 대역) ──────────────────────
data class User(
    var id: Long = 0,
    var name: String = "",
    var email: String = "",
    var age: Int = 0,
)

// ── 가짜 저장소 (실무의 JPA Repository 대역) ───────────────────
object UserRepository {
    private val store = mutableMapOf<Long, User>()
    private var seq = 0L

    fun save(u: User): User {
        // [apply] 저장 직전 id를 채워서 "객체 그대로" 반환 → 빌더처럼 흐른다
        val saved = u.apply { id = ++seq }
        store[saved.id] = saved
        return saved
    }

    // 조회는 "없을 수 있다" → 반환 타입이 User? (nullable)
    fun findByEmail(email: String): User? = store.values.find { it.email == email }
}

fun main() {
    // JSON을 파싱했다 치고 받은 원시 요청 (전부 String)
    val request = mapOf("name" to "홍길동", "email" to "gil@corp.io", "age" to "30")

    // ① [apply] 요청 → User 객체 초기화 (자바의 세터 나열 대체)
    // ② [also]  저장 전 검증 + 로깅을 흐름 중간에 끼움 (값은 그대로 통과)
    val user = User().apply {
        name = request["name"] ?: ""
        email = request["email"] ?: ""
        age = request["age"]?.toIntOrNull() ?: 0    // 파싱 실패 시 0
    }.also {
        require(it.age >= 19) { "미성년 가입 불가" }   // 검증(위반 시 예외)
        println("[LOG] 가입 요청 수신: ${it.name}")     // 로깅 (it으로 참조)
    }

    // 저장 (Repository 내부에서 apply로 id 부여)
    val saved = UserRepository.save(user)
    println("저장됨: $saved")

    // ③ [?.let] 조회 결과(nullable)를 null 안전하게 다룸
    //    있으면 환영 문구, 없으면 ?: 로 대체 → if(x!=null) 블록을 압축
    val greeting = UserRepository.findByEmail("gil@corp.io")?.let {
        "환영합니다, ${it.name}님 (id=${it.id})"       // it은 non-null로 스마트캐스트
    } ?: "가입 이력 없음"
    println(greeting)

    val missing = UserRepository.findByEmail("none@corp.io")?.let { "찾음" } ?: "없음"
    println("none@corp.io 조회 → $missing")

    // ④ [with] 한 객체(saved)에서 여러 값을 꺼내 응답 메시지 조립
    val response = with(saved) {
        """{ "id": $id, "name": "$name", "adult": ${age >= 19} }"""
    }
    println("응답: $response")

    // ⑤ [run] 객체를 받아 "결과 값"을 계산 (nullable이면 ?.run)
    val emailDomain = saved.email.run { substringAfter("@") }
    println("이메일 도메인: $emailDomain")
}
