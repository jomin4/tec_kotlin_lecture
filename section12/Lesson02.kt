// 고급편 섹션4(DSL) - 강의2: 빌더 DSL 심화 (중첩 빌더, @DslMarker)
// 실행:  .\run.ps1 section12\Lesson02.kt

// @DslMarker: 이 어노테이션이 붙은 빌더들끼리 중첩되면,
// 안쪽 스코프에서 바깥 수신객체의 멤버를 "암묵적으로" 못 부르게 막는다 (스코프 안전)
@DslMarker
annotation class HtmlDsl

// ---- 가장 안쪽: 문단들을 담는 Body ----
@HtmlDsl
class Body {
    private val lines = mutableListOf<String>()
    fun p(text: String) {                 // body { } 안에서 this=Body → p() 호출
        lines.add("  <p>$text</p>")
    }
    fun render(): String = "<body>\n" + lines.joinToString("\n") + "\n</body>"
}

// ---- 바깥: Body 블록을 받는 Html ----
@HtmlDsl
class Html {
    private var bodyPart = ""
    fun body(block: Body.() -> Unit) {     // Body를 this로 갖는 람다를 받음
        bodyPart = Body().apply(block).render()   // Body 만들고 block을 this=Body로 실행
    }
    fun render(): String = "<html>\n$bodyPart\n</html>"
}

// 진입점: html { } 안에서 this=Html
fun html(block: Html.() -> Unit): Html = Html().apply(block)

fun main() {
    // 중첩 DSL — 블록마다 this가 Html → Body 로 갈아끼워진다
    val page = html {          // this = Html
        body {                 // this = Body
            p("안녕하세요")     // this=Body 라서 p() 바로 호출
            p("코틀린 DSL")
        }
    }
    println(page.render())

    // ── @DslMarker가 막아주는 실수 (주석 풀면 컴파일 에러) ──
    // html {
    //     body {
    //         body { }   // ❌ @HtmlDsl 덕분에 컴파일 에러 (바깥 Html.body 암묵 호출 차단)
    //     }
    // }
}
