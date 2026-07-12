// 고급편 섹션2(지연과 위임) - 강의1: 지연 초기화 (by lazy, lateinit)
// 실행:  .\run.ps1 section10\Lesson01.kt

fun main() {
    // ============================================================
    // ① by lazy — 처음 접근할 때 한 번만 계산 후 캐시 (val 전용)
    // ============================================================
    println("=== lazy ===")
    val config = Config()
    println("Config 생성됨 (dbUrl은 아직 계산 안 됨)")   // 여기까진 람다 실행 X

    println("첫 접근  : ${config.dbUrl}")   // 여기서 람다 실행 → 계산 로그 뜸
    println("둘째 접근: ${config.dbUrl}")   // 캐시 → 계산 로그 안 뜸 (한 번만)

    // ============================================================
    // ② lateinit — non-null var를 나중에 대입 (주로 DI/프레임워크)
    // ============================================================
    println("=== lateinit ===")
    val service = UserService()
    println("초기화 전 isReady = ${service.isReady()}")   // false
    // println(service.repoName())   // 💥 대입 전 접근 → UninitializedPropertyAccessException

    service.init("MySqlRepository")                        // 나중에 대입
    println("초기화 후 isReady = ${service.isReady()}")   // true
    println("repo = ${service.repoName()}")               // MySqlRepository
}

class Config {
    // 처음 접근 시 딱 한 번 계산 → 이후 캐시. 안 쓰면 아예 계산 안 됨.
    val dbUrl: String by lazy {
        println("  [dbUrl 계산: 무거운 초기화 실행]")
        "jdbc:mysql://localhost:3306/app"
    }
}

class UserService {
    // non-null인데 지금은 값이 없다 → 나중에 init()으로 주입
    private lateinit var repo: String

    fun init(name: String) {
        repo = name
    }

    // 초기화 됐는지 확인 (lateinit 전용)
    fun isReady(): Boolean = this::repo.isInitialized

    fun repoName(): String = repo
}
