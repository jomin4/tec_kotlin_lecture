// 고급편 섹션2 - 보충: 생성자 주입(val) vs 필드 주입(lateinit)
// "생성자 주입을 하면 왜 lateinit이 필요 없나"를 눈으로 확인
// 실행:  .\run.ps1 section10\InjectionStyles.kt

class Repository {
    fun find(): String = "DB에서 가져온 데이터"
}

// ① 생성자 주입 — val. 만드는 순간 이미 주입 완료 → lateinit 불필요 (권장·네 방식)
//    자바의 private final Repository repo + 생성자 와 100% 동일
class ServiceA(private val repo: Repository) {
    fun run(): String = "A: ${repo.find()}"
}

// ② 필드 주입 흉내 — 생성자 없이 나중에 대입 → non-null이라 lateinit 필요 (비권장)
class ServiceB {
    private lateinit var repo: Repository
    fun inject(r: Repository) { repo = r }        // 프레임워크가 나중에 꽂아주는 흉내
    fun run(): String = "B: ${repo.find()}"
}

fun main() {
    // ① 생성자 주입: 만드는 순간 완성 → 바로 사용 가능. 주입 순서 실수 자체가 불가능.
    val a = ServiceA(Repository())
    println(a.run())                    // A: DB에서 가져온 데이터

    // ② 필드 주입: 만들고 → 주입하고 → 사용. 순서를 지켜야 함(안 지키면 예외).
    val b = ServiceB()
    // println(b.run())                 // 💥 주입 전 접근 → UninitializedPropertyAccessException
    b.inject(Repository())
    println(b.run())                    // B: DB에서 가져온 데이터

    // 결론: ①처럼 생성자 주입하면 repo는 항상 val·항상 완성 상태 → lateinit 쓸 일이 없다.
}
