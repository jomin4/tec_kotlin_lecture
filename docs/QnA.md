# 📚 코틀린 학습 Q&A 모음

학습하며 생긴 궁금증과 답변을 차곡차곡 정리하는 문서.
새 질문은 아래에 계속 추가된다. (최신이 아래로)

## 목차
- [Q1. Windows에서 코틀린 실습 코드를 어떻게 실행하나?](#q1)
- [Q2. 한글 출력이 계속 깨지는 이유는?](#q2)
- [Q3. .kt / .class / out.jar 은 각각 어떻게 작동하나?](#q3)
- [Q4. "NPE 없이" 라는 말이 무슨 뜻인가?](#q4)
- [Q5. equals/hashCode/toString을 자바 DTO에서 왜 짰나?](#q5)
- [Q6. 코틀린은 왜 enum에 class를 붙였나?](#q6)
- [Q7. enum은 주로 어떤 역할로 쓰이나?](#q7)
- [Q8. val이 뭐였지? (val인데 배열 원소가 왜 바뀌나)](#q8)
- [Q9. it은 뭐야?](#q9)
- [Q10. 스코프 함수 5개 각각 언제 쓰나? (구체 사용법)](#q10)
- [Q11. 왜 `is Box<Int>`는 안 되고 `is Box<*>`만 되나 (타입 소거)](#q11)
- [Q12. as 캐스트는 뭔데? (as / as? / is 스마트캐스트)](#q12)
- [Q13. `init`/`Regex`와 `check(::repo.isInitialized){}` 한 줄 뜯어보기](#q13)
- [Q14. 생성자 주입만 썼는데 lazy/lateinit이 왜 필요 없었나?](#q14)
- [Q15. inline은 실제로 뭐가 달라지나? (역컴파일로 본 본문 복사·non-local return·crossinline)](#q15)
- [Q16. `block: () -> Int` — 함수를 파라미터로 받는 문법 뜯어보기](#q16)
- [Q17. 람다가 객체(Function0)로 바뀌는 "마법"의 정체](#q17)
- [Q18. `StringBuilder.() -> Unit` 에서 `->` 와 `.()` 를 어떻게 해석하나](#q18)
- [Q19. DSL은 주로 HTML/CSS 작성할 때 쓰나?](#q19)

---

<a id="q1"></a>
## Q1. Windows에서 코틀린 실습 코드를 어떻게 실행하나?
- 📅 2026-07-08 · 🔗 섹션1 강의1

**질문**
> 실습코드 window 환경에서 어떻게 실행해?

**답변**
코틀린도 JVM 언어라 **컴파일 → 실행** 2단계다.
1. 컴파일: `kotlinc section01\Lesson01.kt -include-runtime -d out.jar`  → `.kt`를 `.jar`로
2. 실행: `java -jar out.jar`

편하게 하려고 헬퍼 스크립트를 만들어 둠:
- PowerShell: `.\run.ps1 section01\Lesson01.kt`
- Git Bash: `./run.sh section01/Lesson01.kt`

주의: 파일명은 **ASCII**(한글 X). 실무에선 보통 IntelliJ의 ▶ 버튼이 이 과정을 자동으로 대신해준다.

---

<a id="q2"></a>
## Q2. 한글 출력이 계속 깨지는 이유는?
- 📅 2026-07-08 · 🔗 섹션1 강의1

**질문**
> 지금 한글 텍스트는 인코딩이 계속 깨지는데?

**답변**
**출력 인코딩과 터미널 읽기 인코딩의 미스매치** 때문.
- Java는 UTF-8 바이트로 출력하는데, 한국어 Windows 콘솔 기본 코드페이지는 **949(CP949)** 라 UTF-8을 CP949로 잘못 해석 → `肄뷀?由?` 처럼 깨짐.
- 해결: 터미널도 UTF-8로 맞추면 됨. 터미널에 `chcp 65001` 한 줄. `run.ps1`이 이걸 자동 적용하도록 수정함.
- 영구 설정: PowerShell 프로필에 `[Console]::OutputEncoding = [System.Text.Encoding]::UTF8` 추가하거나, Windows 설정에서 "Unicode UTF-8 사용(Beta)" 체크.

---

<a id="q3"></a>
## Q3. .kt / .class / out.jar 은 각각 어떻게 작동하나?
- 📅 2026-07-08 · 🔗 섹션1 강의1 · (IntelliJ만 쓰다 날것의 파일을 처음 봄)

**질문**
> 이 캡처한 부분에서 .jar, .kt, out.jar은 어떻게 작동하는거야? 맨날 인텔리제이에서 세팅만 해놓다가 이런 형태는 처음봐서

**답변**
흐름: `Lesson01.kt` → (kotlinc) → `Lesson01Kt.class` → (묶기) → `out.jar` → (java) → 실행

- **`.kt`** = 사람이 쓰는 원본 소스 코드(텍스트). 이것만 "진짜 내 코드", 나머지는 자동 생성물.
- **`.class`** = `kotlinc`가 컴파일한 **바이트코드**. JVM만 읽음. 파일 밖 `main()`을 코틀린이 `파일명+Kt`(→`Lesson01Kt`) 클래스로 감싼다(자바는 모든 게 클래스 안에 있어야 하므로).
- **`.jar`** = 사실상 **zip**. 안에 `.class` + 코틀린 표준 라이브러리 + `MANIFEST.MF`(설명서). `MANIFEST.MF`가 "main()은 Lesson01Kt에 있다"고 java에게 알려줘 `java -jar out.jar`로 바로 실행됨.
- `-include-runtime` 때문에 코틀린 런타임 전체가 들어가 크기가 5.6MB. 덕분에 코틀린 미설치 PC에서도 java만 있으면 실행.
- **IntelliJ**는 이 3단계를 ▶ 버튼 하나로 `build/`·`out/` 숨은 폴더에서 자동 처리했을 뿐, 원리는 동일.
- `.class`·`.jar`는 언제든 재생성 가능한 산출물 → git에선 보통 `.gitignore`로 제외.

---

<a id="q4"></a>
## Q4. "NPE 없이" 라는 말이 무슨 뜻인가?
- 📅 2026-07-08 · 🔗 섹션1 강의2

**질문**
> NPE 없이 라는 말이 어떤 말이야?

**답변**
**NPE = NullPointerException**(널 포인터 예외). 자바에서 가장 흔한 런타임 에러.
- 원인: `null`(값 없음)인 변수에 대고 `.length()` 같은 걸 호출할 때 터짐. "없는 사람한테 키 물어보는 격".
- 무서운 점: **컴파일은 통과하고 실행 중에 갑자기** 터져서 앱이 죽음(crash).

```java
String name = null;
name.length();   // 💥 NPE
```

코틀린의 안전호출 `?.`는 "왼쪽이 null이면 실행하지 말고 그냥 null 반환"이라, 자바였으면 NPE로 죽을 상황을 **죽지 않고 조용히 null 반환**하며 넘어간다.

```kotlin
val name: String? = null
name?.length   // 결과: null (프로그램 안 죽음)
```

→ **"NPE 없이"** = "NullPointerException으로 프로그램이 뻗지 않고" 라는 뜻.

---

<a id="q5"></a>
## Q5. equals/hashCode/toString을 자바 DTO에서 왜 짰나?
- 📅 2026-07-10 · 🔗 섹션4 강의2 · 🖼️ `diagrams/q05_why-equals-hashcode-tostring.svg`

**질문**
> equals(), hashCode(), toString() 이런걸 왜 짰지? 기존 자바에서? dto에서 왜?

**답변**
`Object`가 주는 기본 구현은 전부 **주소(정체성) 기준**인데, DTO는 **값을 담는 그릇**이라 **내용 기준** 동작이 필요해서 재정의했다.
- **toString()**: 재정의 안 하면 로그에 `Point@1b6d3586`(주소)만 찍혀 값을 못 봄 → `Point(x=1, y=2)`로 만들어 디버깅.
- **equals()**: 기본은 주소 비교라 `new Point(1,2).equals(new Point(1,2))` → false. "내용이 같으면 같다"로 재정의해야 `list.contains()`, 값 비교가 정상 작동.
- **hashCode()**: `HashMap`/`HashSet`은 ① hashCode로 버킷 찾고 ② equals로 확정. 그래서 **equals를 고치면 hashCode도 반드시 같이** 고쳐야 함(자바 계약). 안 그러면 "넣었는데 contains가 false"인 유령 버그.

DTO는 실무에서 비교(equals)·컬렉션 담기(hashCode)·로깅(toString)을 매일 하므로 세 개가 필수. → 코틀린은 **`data class`**가 이 셋(+`copy`/`componentN`)을 자동 생성해 없애줌.

---

<a id="q6"></a>
## Q6. 코틀린은 왜 enum에 class를 붙였나?
- 📅 2026-07-12 · 🔗 섹션6 강의1

**질문**
> 코틀린은 왜 enum에 class를 붙였대?

**답변**
코틀린에서 **`enum`은 독립 키워드가 아니라 `class` 앞에 붙는 "수식어(modifier)"**이기 때문. 자바는 `enum`을 `class`/`interface`와 동급인 별개의 최상위 키워드로 만들었지만, 코틀린은 특별한 종류의 클래스를 전부 `수식어 + class` 한 틀로 통일했다(`data class`, `sealed class`, `annotation class`, `inner class` … 그리고 `enum class`).

두 가지 이유:
1. **문법 일관성(직교성)**: enum 전용 문법 규칙을 새로 만드는 대신, 클래스에 수식어를 조합하는 방식. 키워드를 안 늘린다.
2. **정직한 이름**: enum은 컴파일되면 실제로 클래스가 되고, 각 상수(`KOREA` 등)는 그 클래스의 **인스턴스(객체)**다. 그래서 enum이 생성자·프로퍼티·메서드·상수별 추상함수 구현을 가질 수 있다. 자바 `enum Baz`는 "사실 클래스"임을 숨기고, 코틀린 `enum class Baz`는 문법에서 드러낸다.

한 줄: **`enum`은 `class`를 대체하는 게 아니라 꾸미는 수식어이고, enum이 원래 클래스라는 사실을 이름에 정직하게 담은 것.**

---

<a id="q7"></a>
## Q7. enum은 주로 어떤 역할로 쓰이나?
- 📅 2026-07-12 · 🔗 섹션6 강의1

**질문**
> enum은 주로 어떤 역할로 쓰이는거야?

**답변**
**미리 정해진, 유한하고, 서로 배타적인 선택지 집합**을 표현할 때 쓴다. "이 값은 이 중 하나여야만 한다"를 컴파일러가 강제하게 만드는 도구. 실무 자리:
1. **상태/종류 표현**: `OrderStatus`(주문상태), `UserRole`(등급), `PayMethod`(결제수단), 요일·방향 등 경우의 수가 딱 정해진 것.
2. **문자열/정수 상수의 안전한 대체**: `String status = "PAID"`는 오타(`"PAdI"`)·잘못된 값이 컴파일 통과 후 런타임 버그. enum은 후보 밖 값을 아예 못 넣게 하고 IDE 자동완성 지원.
3. **상수에 데이터 묶기**: 각 상수에 프로퍼티·메서드 부착(예: `Planet(mass, radius)` + `gravity()`, HTTP코드+메시지).
4. **when과 짝지어 분기**: 상태별 처리를 when으로 짜면 상태 추가 시 빠뜨린 분기를 컴파일러가 전부 잡음(소진 검사).

**경계(안 쓰는 경우)**: 후보가 런타임에 정해지거나 계속 느는 값(사용자 입력 도시명 등)은 enum 아님 → String/DB. 각 경우가 서로 다른 데이터 구조를 가지면 enum 대신 **sealed class**(enum=상수 집합, sealed=타입 집합).

한 줄: **"정해진 몇 개 중 하나"인 값을 문자열/정수 대신 타입으로 못 박아, 잘못된 값·빠뜨린 분기를 컴파일러가 막게 하는 도구.**

---

<a id="q8"></a>
## Q8. val이 뭐였지? (val인데 배열 원소가 왜 바뀌나)
- 📅 2026-07-12 · 🔗 섹션1 강의1 · 섹션7 강의2

**질문**
> 잠깐만 val이 뭐였지?

**답변**
`val` = **재할당 불가**(자바 `final`), `var` = 재할당 가능. 이름도 **val**ue / **var**iable.

```kotlin
val name = "홍길동";  name = "김철수"   // ❌ Val cannot be reassigned
var age  = 20;        age  = 21         // ✅
```

**핵심 오해 풀기 — `val`은 "참조(변수)"를 고정할 뿐, 객체 "내부"를 얼리지 않는다.**
그래서 섹션7에서 `val a = intArrayOf(1,2,3)` 인데도 `a[0]=99`가 된다.
- `a[0]=99` → 객체 **내용** 변경 → val이 안 막음 ✅
- `a = intArrayOf(4)` → 변수에 **다른 객체** 재대입 → val이 막음 ❌

자바 `final int[] a`와 100% 동일(참조만 고정, 원소는 못 얼림).
비유: **"val = 상자를 못 바꾼다, 상자 안 물건 상태는 별개."**

**실무 규칙**: 일단 전부 `val`, 재할당이 꼭 필요할 때만 `var`. (IntelliJ도 var 미재할당 시 val 권장 경고.)

---

<a id="q9"></a>
## Q9. it은 뭐야?
- 📅 2026-07-12 · 🔗 섹션8 강의1·2

**질문**
> it은 뭐야?

**답변**
람다의 **파라미터가 딱 1개**일 때, 이름을 생략하면 코틀린이 그 파라미터에 붙여주는 **자동 이름**.

```kotlin
val square = { x: Int -> x * x }   // 이름 x 명시
val square = { it * it }           // 동일 — 생략하면 it
list.filter { it > 0 }             // it = 지금 처리 중인 원소 하나
```

자바 람다는 파라미터 이름을 항상 직접 써야 함(`n -> n > 0`). 코틀린은 1개면 `it`으로 축약 가능.

**규칙 3가지**
1. 파라미터 2개 이상이면 `it` 불가 → 이름 필수 (`{ k, v -> ... }`).
2. `it`은 축약일 뿐, 안 써도 됨 (`{ person -> person.age }`도 정상). 헷갈리면 이름을 붙여라.
3. **함정**: 람다가 중첩되면 바깥/안쪽 `it`이 충돌(가려짐) → 각 람다에 이름을 줘라.

**실무 감각**: 원소를 한 번 단순하게 쓰면 `it`, 길거나·여러 번·중첩이면 이름. 가독성 우선.

---

<a id="q10"></a>
## Q10. 스코프 함수 5개 각각 언제 쓰나? (구체 사용법)
- 📅 2026-07-12 · 🔗 섹션8 강의3 · 🖼️ `diagrams/s08l03_scope-functions.svg`

**질문**
> 5객체 사용법을 좀더 구체적으로 설명해줘

**답변**
표준 라이브러리 원형이 근거다. `block:(T)->R`이면 `it`, `block:T.()->R`이면 `this`. 반환 `R`=람다결과, 반환 `T`=객체자신.
```kotlin
T.let(block:(T)->R):R       // it, 결과
T.run(block:T.()->R):R      // this, 결과
with(x:T, block:T.()->R):R  // this(인자), 결과
T.apply(block:T.()->Unit):T // this, 객체자신
T.also(block:(T)->Unit):T   // it, 객체자신
```

- **apply** (this, 객체반환) — 객체 **초기화/설정**. `StringBuilder().apply{ append(..) }`, DTO·Builder·Intent 세팅. 자바 "세터 나열" 대체.
- **let** (it, 결과) — **null 안전**(`x?.let{}` = `if(x!=null){}`)·지역 변환. 블록 안 `it`은 non-null 스마트캐스트.
- **also** (it, 객체반환) — 체이닝 중간 **로깅·검증**(값 안 바꿈). `it`이라 바깥 this 안 가림. `.also{ require(it.size==4) }`.
- **run** (this, 결과) — 객체 멤버로 **결과 계산**. `Rect(3,4).run{ w*h }`. apply와 모양 같고 반환만 다름(객체 vs 결과).
- **with** (this, 결과) — run의 문법 변형(`with(obj){}`). **단, `?.` 못 붙임** → nullable이면 with 말고 `obj?.run{}`. 그래서 실무 우선순위는 run < with 낮음.

**3초 판단**: 객체 그대로 받아 쓰고 싶다 → 세팅이면 `apply`, 곁다리 로깅이면 `also`. 결과가 필요하다 → null안전이면 `?.let`, 멤버로 계산이면 `run`.
한 줄: **초기화=apply · null안전=?.let · 로깅/검증=also · 값계산=run.**

---

<a id="q11"></a>
## Q11. 왜 `is Box<Int>`는 안 되고 `is Box<*>`만 되나 (타입 소거)
- 📅 2026-07-12 · 🔗 고급 섹션1 강의1 · 🖼️ `diagrams/s09l01_generics.svg` · 💻 `section09/TypeErasureDemo.kt`

**질문**
> `b is Box<Int>` ❌ / `b is Box<*>` ✅ 이 부분 구체적으로

**답변**
**타입 소거**: 컴파일이 끝나면 `<T>` 정보가 바이트코드에서 지워진다. `Box<Int>`·`Box<String>`은 런타임엔 그냥 같은 `Box` 한 클래스다.
```kotlin
intBox::class == strBox::class   // true (같은 클래스)
```
- **왜**: 자바가 제네릭을 Java 5에 뒤늦게 넣으며 기존 코드와 하위 호환을 위해 "컴파일 때만 쓰고 지운다"로 타협. 코틀린은 JVM 위라 그대로 상속.
- **`is Box<Int>` ❌**: `is`(instanceof)는 런타임 검사인데, 그때 Int 정보가 이미 없음 → 컴파일 에러 `Cannot check for instance of erased type`.
- **`is Box<*>` ✅**: `*`(star projection ≈ 자바 `Box<?>`)는 "T가 뭐든 껍데기가 Box이기만 하면 참" → 런타임에도 검사 가능.

**실무 함정 — `as`는 검사를 안 한다(unchecked)**:
```kotlin
val wrong = strBox as Box<Int>   // 경고만 뜨고 통과! (T 미검사)
// wrong.value + 1                // 💥 여기 와서야 ClassCastException
```
캐스트한 줄이 아니라 값을 쓰는 순간 터져서 디버깅이 고약 → 제네릭 `as` 캐스트는 의심하라.

**우회 = `reified`**(inline 함수 한정): 원소 객체의 실제 타입은 살아있어 검사 가능.
```kotlin
inline fun <reified T> Box<*>.holds(): Boolean = value is T
Box(42).holds<Int>()    // true
```
한 줄: **런타임엔 타입이 지워진다 → `is`는 `Box<*>`만, 정확한 타입 검사는 `reified`로.**

---

<a id="q12"></a>
## Q12. as 캐스트는 뭔데? (as / as? / is 스마트캐스트)
- 📅 2026-07-12 · 🔗 섹션1 강의3 · 고급 섹션1 강의1

**질문**
> as 캐스트는 뭔데?

**답변**
`as` = **강제 타입 변환**(자바 `(Type) obj`). "이 값을 저 타입으로 취급하라"고 컴파일러에 강제.
```kotlin
val obj: Any = "hello"
val s = obj as String     // "hello"
val n = obj as Int        // 💥 ClassCastException (실제 String인데 Int로 강제)
```
`as`는 실패 시 런타임에 죽으므로 위험 → 형제 둘로 피한다.

- **`as?` 안전 캐스트**: 실패하면 예외 대신 `null`.
  ```kotlin
  val n: Int? = obj as? Int        // null (안 터짐)
  val len = (obj as? String)?.length ?: 0   // 실무 패턴
  ```
- **`is` + 스마트캐스트** (1순위 권장): 검사만 하면 블록 안에서 자동 캐스트.
  ```kotlin
  if (obj is String) println(obj.length)   // as 없이 바로 .length
  ```

**선택**: 타입 확인 후 사용 → `is`+스마트캐스트 / 실패 대비 → `as?`+`?:` / 100% 확신 → `as`(신중).

**제네릭 연결(함정)**: 일반 `as`는 런타임 검사하지만, 제네릭 타입 인자는 소거돼 `as Box<Int>`는 **검사조차 안 함(unchecked)** → 캐스트 줄이 아니라 값 쓸 때 뒤늦게 터진다.
한 줄: **`as`=강제(실패=예외), `as?`=안전(실패=null), `is`=검사+자동캐스트(최선).**

---

<a id="q13"></a>
## Q13. `init`/`Regex`와 `check(::repo.isInitialized){}` 한 줄 뜯어보기
- 📅 2026-07-12 · 🔗 고급 섹션2 강의1 · 섹션4 강의1(init)

**질문**
> HeavyRegex 클래스 부분과 check(::repo.isInitialized){...} 이 부분이 이해가 안 돼.

**답변**
**Part 1 — HeavyRegex** (정규식을 감싼 얇은 껍데기)
- `class HeavyRegex(pattern: String)` : 주 생성자로 패턴 문자열 받음.
- `init { println(...) }` : 객체 생성 순간 실행(섹션4). "지금 무거운 작업 한다"는 로그 → lazy가 언제 처음 생성되는지 보여주는 장치.
- `private val regex = Regex(pattern)` : 표준 `Regex(...)`로 패턴을 **컴파일**해 저장(이게 비싼 작업 → 그래서 lazy로 1회만).
- `fun matches(s) = regex.matches(s)` : 실제 검사는 regex에 위임. `regex.matches("hong@corp.io")` → 전체 일치면 true.
- 실행 순서: 생성 시 init(로그) → regex 컴파일, 위→아래.

**Part 2 — `check(::repo.isInitialized) { "..." }`**
1. `check(조건){메시지}` : 표준 사전조건 함수. 조건이 false면 `IllegalStateException(메시지)` 던짐. 자바 `if(!조건) throw new IllegalStateException(...)`. 메시지 람다는 실패 시에만 평가. (형제 `require`=인자 검증=IllegalArgumentException, `check`=상태 검증.)
2. `::repo` : repo 프로퍼티를 값이 아니라 "참조"로 가리킴(`::`=참조 연산자, `::triple`과 같은 계열).
3. `.isInitialized` : lateinit 프로퍼티 참조 전용. "값이 대입됐나?"를 true/false로.

**왜 이렇게?** `lateinit var repo: UserStore`는 **non-null**이라 `repo == null` 비교 자체가 불가 → 초기화 여부 확인의 유일한 방법이 `::repo.isInitialized`.
한 줄: **"repo 아직 주입 안 됐으면 즉시 명확한 예외로 실패시켜라"는 안전장치.**

---

<a id="q14"></a>
## Q14. 생성자 주입만 썼는데 lazy/lateinit이 왜 필요 없었나?
- 📅 2026-07-12 · 🔗 고급 섹션2 강의1 · 💻 `section10/InjectionStyles.kt`

**질문**
> lazy/lateinit이 없으면 어떻게 되나? 스프링에서 private final Repository로 생성자 주입만 받았고 lazy/lateinit을 쓴 적이 없다.

**답변**
**생성자 주입을 하면 lateinit이 필요 없다 — 네 방식이 정석이라 안 썼던 게 정상.**
```kotlin
@Service
class OrderService(private val repository: OrderRepository) { ... }  // val, 만들 때 이미 주입
```
자바 `private final Repository repo + 생성자`와 동일. 객체 생성 순간 완성되니 "나중에 넣을게"(lateinit)가 낄 자리가 없음.

**lateinit이 존재하는 이유 = 코틀린 null 안전성 + 필드 주입**
- 자바 필드 주입: `@Autowired private Repository repo;` — 잠깐 null이어도 자바는 허용.
- 코틀린 `var repo: Repository`는 non-null이라 "잠깐 null"을 금지 → 선언 즉시 값 필요.
- 그래서 "생성자 없이 프레임워크가 나중에 주입"을 표현하려면 `lateinit` 필요.
→ 즉 lateinit은 **비권장 필드 주입**을 위한 우회. 생성자 주입엔 불필요.

**lateinit 없이 필드 주입하면?** ① 컴파일 에러 ② `Repository? = null`로 만들어 `!!`/`?.` 지옥 ③ 생성자 주입으로 전환(정답).

**lateinit이 진짜 필요한 곳**: 테스트 `@BeforeEach lateinit var sut`, 안드로이드 `lateinit var binding`(생성자 못 만듦), 레거시 필드 주입.

**lazy는 주입과 무관**: 비싼 계산을 처음 쓸 때까지 미루는 최적화(로거·무거운 파생값). 안 써도 그만.
한 줄: **생성자 주입=val=완성 → lateinit 불필요. lateinit은 생성자 주입이 불가능할 때의 탈출구.**

---

<a id="q15"></a>
## Q15. inline은 실제로 뭐가 달라지나? (역컴파일로 본 본문 복사·non-local return·crossinline)
- 📅 2026-07-12 · 🔗 고급 섹션3 강의1 · 🖼️ `diagrams/s11l01_inline.svg`

**질문**
> inline 이 부분 좀 더 구체적으로 설명해줘.

**답변**
**일반 고차함수 → 컴파일하면 객체가 생긴다** (자바로 역컴파일):
```java
public static int measure(Function0<Integer> block) { return block.invoke(); }  // ② 가상 호출
public static void main() {
    measure(new Function0<Integer>() { public Integer invoke(){ return 42; } }); // ① 객체 생성
}
```
비용 둘: ① 람다가 Function0 익명클래스로 되고 인스턴스 생성 ② invoke() 가상 호출. 반복문서 누적.

**inline 붙이면 → 함수도 객체도 사라진다**:
```java
public static void main() { int r = 40 + 2; }   // 람다 본문이 그대로 박힘, 객체·호출 0
```
"본문이 복사된다"의 실체. (캡처 없는 람다는 싱글턴이지만, `{x+it}`처럼 캡처하면 호출마다 객체 생성 → inline이 이것도 제거.)

**non-local return** — forEach가 inline이라 본문이 바깥 함수로 펼쳐짐:
```kotlin
fun firstEven(nums: List<Int>): Int? {
    for (it in nums) { if (it%2==0) return it }   // return이 물리적으로 firstEven 안 → firstEven 종료
    return null
}
```
비-inline 람다면 이 return은 "어느 함수?" 모호 → 컴파일 에러.

**crossinline** — 람다를 다른 맥락(Runnable)에 넣을 때: `Runnable { action() }`처럼 action이 run() 안으로 복사되면, 나중/다른 스레드 실행 시 non-local return이 위험 → crossinline이 "inline은 하되 non-local return 금지"로 막음.

한 줄: **inline = Function 객체 생성 + invoke() 가상호출을 본문 복붙으로 제거 → reified·non-local return이 공짜로 따라옴. 대가는 바이트코드 팽창(작은 함수에만).**

---

<a id="q16"></a>
## Q16. `block: () -> Int` — 함수를 파라미터로 받는 문법 뜯어보기
- 📅 2026-07-12 · 🔗 고급 섹션3 강의1 · 섹션8 강의1(함수타입)

**질문**
> measure(block: () -> Int): Int 이 부분 구체적으로

**답변**
`fun measure(block: () -> Int): Int` 세 조각:
- `block` = 파라미터 **이름**
- `() -> Int` = 그 파라미터의 **타입 = 함수**("인자 없이 호출하면 Int 주는 함수"). 값이 아니라 함수를 받는다.
- 끝 `: Int` = measure 자신의 반환 타입

**함수 타입 해부**: `(파라미터 타입들) -> 반환타입`.
- `() -> Int` 없이받고 Int / `(Int) -> Int` / `(String,Int) -> Boolean` / `() -> Unit`(void)

**안에서**: `block()`으로 넘어온 함수를 실행(=`block.invoke()`).
```kotlin
fun measure(block: () -> Int): Int { val r = block(); return r }
```
**밖에서**: 람다를 넘김.
```kotlin
measure({ 40 + 2 })   // { 40 + 2 } 가 () -> Int
measure { 40 + 2 }    // 트레일링 람다
```
**자바 대응**: 함수타입이 없어 함수형 인터페이스 필요 — `() -> Int` ↔ `Supplier<Integer>`(호출 `block.get()`), `(Int)->Int` ↔ `Function`, `(String,Int)->Boolean` ↔ `BiFunction`. 코틀린은 `() -> Int` 하나로 통일.

핵심: measure는 "무슨 계산인지" 모른 채 껍데기만 주고, 실제 계산은 호출부가 람다로 꽂는다 = 고차함수. inline은 이때 그 람다가 객체로 안 만들어지게 함.

---

<a id="q17"></a>
## Q17. 람다가 객체(Function0)로 바뀌는 "마법"의 정체
- 📅 2026-07-13 · 🔗 고급 섹션3 강의1 · 🖼️ `diagrams/s11l01b_lambda-to-object.svg`

**질문**
> measure { 40 + 2 } 가 new Function0(){ invoke(){return 42} } 로 바뀌는 게 이해 안 돼. 흐름을 구체적으로.

**답변**
핵심 열쇠: **JVM엔 "함수" 타입이 없다. 오직 객체(클래스+메서드)만 있다.** 그래서 코틀린이 람다를 객체로 포장한다(마법 X, 기계적 변환).

흐름 5단계:
1. 내 코드: `measure { 40 + 2 }`
2. `{ 40 + 2 }`는 이름 없는 함수인데, JVM은 이걸 저장 못 함 → 객체에 담아야 함.
3. `() -> Int`의 정체 = 인터페이스 `Function0<R> { fun invoke(): R }` (메서드 invoke 하나).
4. 컴파일러가 람다를 그 인터페이스 구현 객체로 포장 — 람다 본문이 invoke() 몸통이 됨:
```java
Function0<Integer> block = new Function0<Integer>() {
    public Integer invoke() { return 40 + 2; }
};
```
5. `measure(block)` 안의 `block()`은 사실 `block.invoke()` → 42.

비용: 매 호출마다 ① 객체 new + ② invoke() 호출.
`inline`이면 ③④⑤ 포장을 건너뛰고 `int r = 40 + 2;`만 남음(객체·호출 없음).

대응표: 람다=Function0 객체 / 람다 본문=invoke() 몸통 / `block()`=`block.invoke()`.
한 줄: **JVM이 함수를 몰라서 코틀린이 "invoke() 하나짜리 객체"로 몰래 포장한 것 — inline은 그 포장을 없앤다.**

---

<a id="q18"></a>
## Q18. `StringBuilder.() -> Unit` 에서 `->` 와 `.()` 를 어떻게 해석하나
- 📅 2026-07-13 · 🔗 고급 섹션4 강의1 · 📄 `notes/reading-function-types.md`

**질문**
> block: StringBuilder.() -> Unit 에서 -> 화살표 의미가 뭐지? 이 부분 어떻게 해석해?

**답변**
`->`의 의미는 **안 바뀐다**: 늘 "왼쪽=받는 것, 오른쪽=주는 것". 새로 붙은 건 `()` 앞의 `StringBuilder.` 부분.

4조각 분해:
```
StringBuilder . ()      -> Unit
  수신객체타입  ↑   파라미터(없음)   반환(Unit=void)
              this로 삼겠다는 표시
```
- `StringBuilder.` = "이 람다 안에서 StringBuilder가 this다"(수신객체)
- `()` = 인자 없음
- `->` = 받아서 준다 (그대로)
- `Unit` = 반환 없음

비교:
- `() -> Unit` : 안 받고 아무것도 안 줌
- `StringBuilder.() -> Unit` : **StringBuilder를 this로 가지며** 안 받고 아무것도 안 줌

전체 번역: "block은 매개변수. 타입은 함수인데 StringBuilder를 this로 갖고, 인자 없고, 반환 없음." → 호출은 `sb.block()`, 블록 안에선 `this.append` → `append`(접두어 생략).

한 줄: **`->`는 그대로, `.()` 앞 타입 = "람다 안에서 this가 될 객체" (이번에 추가된 유일한 조각).**

---

<a id="q19"></a>
## Q19. DSL은 주로 HTML/CSS 작성할 때 쓰나?
- 📅 2026-07-13 · 🔗 고급 섹션4 강의2

**질문**
> dsl은 주로 css,html 코드 작성할 때 많이 사용하는 건가?

**답변**
아니다. **HTML은 중첩이 직관적이라 쓴 대표 "예시"**일 뿐. DSL의 본진은 마크업이 아니라 **설정·빌더**다. 그리고 이미 매일 쓰고 있다 — `build.gradle.kts`.

```kotlin
dependencies {                  // this = DependencyHandler
    implementation("...")       // menu { item(...) } 와 똑같은 구조
}
```

실무 DSL:
- **Gradle** `dependencies { }` `plugins { }` — 빌드 설정
- **Spring Security** `http { authorizeHttpRequests { authorize(...) } }`
- **Spring 라우팅** `router { GET("/x"){} }`
- **Ktor** `routing { get("/"){} }` — 서버 라우팅
- **Kotest** `describe("x"){ it("..."){} }` — 테스트
- **Exposed** `Users.select { age greater 18 }` — SQL
- **코루틴** `launch { }` `coroutineScope { }`
- **kotlinx.html** `html { body { } }` — 이건 실제 마크업(한 경우)

언제 쓰나: **구조적·중첩된 설정/조립을 코드인데 선언적으로 읽기 좋게** 표현할 때. (설정·빌더·테스트 명세·라우팅)
한 줄: **DSL ≠ HTML/CSS 전용. 빌드/프레임워크 설정·빌더 API가 주 무대이고, build.gradle.kts가 그 증거.**
