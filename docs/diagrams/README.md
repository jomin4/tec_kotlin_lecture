# 🖼️ 시각 자료 모음

학습하며 그린 그림(SVG)들을 모아두는 폴더.
`.svg`는 VS Code에서 파일 우클릭 → **Open Preview**, 또는 브라우저로 바로 열림.

## 파일 명명 규칙
- 강의용: `s{섹션}l{강의}_주제.svg`  (예: `s01l02_null-safety.svg`)
- 질문용: `q{번호}_주제.svg`  (예: `q04_npe-safecall.svg` → QnA.md의 Q4와 연결)

## 목록

| 파일 | 내용 | 관련 |
|------|------|------|
| [s01l01_val-var.svg](s01l01_val-var.svg) | val/var, 타입 추론 (Java 비교) | 섹션1 강의1 |
| [s01l02_null-safety.svg](s01l02_null-safety.svg) | String vs String?, `?.` `?:` `!!` | 섹션1 강의2 |
| [s01l03_type-casting.svg](s01l03_type-casting.svg) | 명시적 형변환, `is`/`as`/스마트캐스트, Any/Unit/Nothing | 섹션1 강의3 |
| [s01l04_operators.svg](s01l04_operators.svg) | `==`vs`===`, 비교연산자, 연산자 오버로딩, 중위함수 | 섹션1 강의4 |
| [s02l01_if-expression.svg](s02l01_if-expression.svg) | if는 표현식(값 반환), 삼항연산자 부재 | 섹션2 강의1 |
| [s02l02_when.svg](s02l02_when.svg) | when = switch 강화판, 범위/타입/조건 분기 | 섹션2 강의2 |
| [s02l03_loops.svg](s02l03_loops.svg) | for-in 범위, until/downTo/step, for-each, while | 섹션2 강의3 |
| [s03l01_functions.svg](s03l01_functions.svg) | fun 선언, 표현식 함수(=), default·named 파라미터 | 섹션3 강의1 |
| [s03l02_extension-functions.svg](s03l02_extension-functions.svg) | 확장 함수, this(수신객체), static 컴파일 원리 | 섹션3 강의2 |
| [s04l01_class-constructor.svg](s04l01_class-constructor.svg) | 주 생성자, 프로퍼티(getter/setter 자동), init, 커스텀 getter | 섹션4 강의1 |
| [s04l02_dataclass-visibility.svg](s04l02_dataclass-visibility.svg) | data class 자동생성(equals/hashCode/toString/copy), 접근지정자 | 섹션4 강의2 |
| [s05l01_inheritance.svg](s05l01_inheritance.svg) | 기본 final, open/override 필수, 추상클래스 | 섹션5 강의1 |
| [s05l02_interface.svg](s05l02_interface.svg) | interface(Java비교), default메서드, 추상프로퍼티, 다중구현 | 섹션5 강의2 |
| [s06l01_enum.svg](s06l01_enum.svg) | enum class(Java비교), 프로퍼티/entries, when 소진검사 | 섹션6 강의1 |
| [s06l02_sealed-class.svg](s06l02_sealed-class.svg) | sealed class(vs enum), 서로 다른 데이터 타입집합, when+is 소진검사·스마트캐스트 | 섹션6 강의2 |
| [s07l01_collections.svg](s07l01_collections.svg) | 컬렉션: 읽기전용 vs 가변(listOf/mutableListOf), List/Set/Map, 널 위치 | 섹션7 강의1 |
| [s07l02_arrays.svg](s07l02_arrays.svg) | 배열: IntArray(int[]) vs Array&lt;Int&gt;(Integer[]) 박싱, 크기고정·val원소변경, 배열 vs List | 섹션7 강의2 |
| [s08l01_lambda.svg](s08l01_lambda.svg) | 람다: 함수를 값으로, `(Int)->Int` 함수타입, `it`, 트레일링 람다, 고차함수·`::` | 섹션8 강의1 |
| [s08l02_collection-functions.svg](s08l02_collection-functions.svg) | 컬렉션 함수형: filter/map/forEach 파이프라인, Java Stream 비교, any/all/groupBy, asSequence(lazy) | 섹션8 강의2 |
| [s08l03_scope-functions.svg](s08l03_scope-functions.svg) | 스코프 함수: let/run/with/apply/also, it vs this·반환값 2축 | 섹션8 강의3 |
| [q03_kt-jar-pipeline.svg](q03_kt-jar-pipeline.svg) | .kt → .class → .jar → 실행 흐름 | QnA Q3 |
| [q04_npe-safecall.svg](q04_npe-safecall.svg) | NPE: Java 죽음 vs Kotlin 안전호출 | QnA Q4 |
| [q05_why-equals-hashcode-tostring.svg](q05_why-equals-hashcode-tostring.svg) | equals/hashCode/toString을 DTO에서 재정의하는 이유 | QnA Q5 |
