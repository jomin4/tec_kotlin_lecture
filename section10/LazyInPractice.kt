// 고급편 섹션2 - 보충: lazy & lateinit "실무 배치도"
// lazy = 비싼 자원을 처음 쓸 때만 생성·재사용 / lateinit = 의존성을 나중에 주입
// 실행:  .\run.ps1 section10\LazyInPractice.kt

// ── 만드는 데 비용이 큰 자원 대역 (정규식 컴파일/템플릿 로딩/커넥션 흉내) ──
class HeavyRegex(pattern: String) {
    init { println("  [HeavyRegex 컴파일: '$pattern' — 비싼 작업]") }
    private val regex = Regex(pattern)
    fun matches(s: String): Boolean = regex.matches(s)
}

// ── 저장소 (실무의 Repository 대역) ──
class UserStore {
    private val emails = mutableListOf<String>()
    fun add(e: String) { emails.add(e) }
    fun count(): Int = emails.size
}

// ── 서비스: lazy로 무거운 자원 지연 생성, lateinit로 의존성 주입 ──
class SignupService {
    // ① lazy: 이메일 검증기는 "첫 검증 때" 딱 한 번 만든다.
    //    가입 요청이 0건이면 아예 안 만들어짐 → 불필요한 비용 절약.
    //    실무: 정규식/ObjectMapper/DB 커넥션풀/Logger 같은 무거운 싱글턴에 단골.
    private val emailChecker: HeavyRegex by lazy { HeavyRegex("[\\w.]+@[\\w.]+") }

    // ② lateinit: 저장소는 외부(DI 컨테이너/설정/테스트 셋업)가 나중에 주입.
    //    실무: 스프링 @Autowired lateinit var, 테스트 @BeforeEach의 lateinit var sut.
    private lateinit var repo: UserStore

    fun wire(store: UserStore) { repo = store }   // DI 흉내

    fun signup(email: String): String {
        check(::repo.isInitialized) { "repo가 주입되지 않았습니다" }  // 주입 전 방어
        if (!emailChecker.matches(email)) return "거부: 이메일 형식 오류 ($email)"
        repo.add(email)
        return "가입 완료: $email (총 ${repo.count()}명)"
    }
}

fun main() {
    val service = SignupService()
    println("서비스 생성됨 (emailChecker 미생성 · repo 미주입)")

    // 주입 전 사용하면 check()에 막힌다 (안전장치)
    // println(service.signup("a@b.com"))   // 💥 IllegalStateException: repo가 주입되지 않았습니다

    service.wire(UserStore())               // ② 의존성 주입
    println("repo 주입 완료")

    // 첫 가입 → 이 순간 emailChecker(lazy)가 처음 만들어진다 (컴파일 로그 1회만)
    println(service.signup("hong@corp.io"))
    println(service.signup("bad-email"))    // emailChecker 재사용 → 컴파일 로그 안 뜸
    println(service.signup("kim@corp.io"))
}
