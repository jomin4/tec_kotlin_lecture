# 🌱 심화 트랙: Spring 소스로 배우는 어노테이션 & 리플렉션

우리가 만든 미니 검증기(`section13/Lesson02.kt`)가 실제 Spring에서 어떻게 커지는지, **진짜 소스를 난이도 순으로** 해부하는 트랙.

## 분석 대상 (포크 클론)
- 레포: **`jomin4/opensource_spring`** (spring-framework 포크)
- 세션 클론 경로: `/workspace/opensource_spring`
- 버전: **Spring Framework 7.1.0-SNAPSHOT** · Kotlin 2.3.20 · 커밋 `99b991b`
- 핵심 모듈: `spring-core`(리플렉션·어노테이션), `spring-beans`(DI), `spring-context`(스캔·설정), `spring-web`/`spring-webmvc`(웹)

## 진행 방식 (기존 강의 흐름 유지)
각 강의: ① 개념(우리 코드와 비교) → ② 도표(모바일 Artifact+SVG, 흐름 집중) → ③ **실제 소스 해부**(아래 경로의 핵심 메서드를 줄 단위로) → ④ 미니 재현 `.kt` → ⑤ Q&A+요약.
> 학습 기록은 `tec_kotlin_lecture`에 쌓고, `opensource_spring`은 읽기용 분석 대상.

---

## 난이도 순 커리큘럼

### Phase 0 — 준비 (★)
- [ ] **0-1** Spring 모듈 구조 & 소스 탐색법 (core/beans/context/web가 각각 뭘 하나)
- [ ] **0-2** 순수 리플렉션 복습 + 우리 `validate` ↔ Spring 대응 지도

### Phase 1 — Spring의 리플렉션 도구층 (★★) — 자기완결적, 읽기 쉬움
- [ ] **1-1** `ReflectionUtils` — `spring-core/.../util/ReflectionUtils.java`
      (필드/메서드 접근·`doWithFields`·`makeAccessible` = 우리 `field.isAccessible`의 정석)
- [ ] **1-2** `AnnotationUtils` / `AnnotatedElementUtils` — `spring-core/.../core/annotation/`
      (`findAnnotation` = 우리 `getAnnotation`의 강화판: 상속·메타 어노테이션까지 탐색)
- [ ] **1-3** `ClassUtils` / `BeanUtils` — 타입 유틸·인스턴스 생성

### Phase 2 — 어노테이션 모델 (★★★) — Spring 어노테이션 설계의 핵심
- [ ] **2-1** 메타·합성 어노테이션 (`@RestController`=`@Controller`+`@ResponseBody` 분해)
- [ ] **2-2** `@AliasFor` — `spring-core/.../core/annotation/AliasFor.java` (속성 별칭)
- [ ] **2-3** `MergedAnnotations` — `spring-core/.../core/annotation/MergedAnnotations.java` (병합·탐색 엔진)

### Phase 3 — 빈 등록 & DI (★★★★) — 우리 프레임워크가 진짜로 커진 버전
- [ ] **3-1** 컴포넌트 스캔 — `spring-context/.../context/annotation/ClassPathScanningCandidateComponentProvider.java`
      (`@Component` 어떻게 찾나)
- [ ] **3-2** `@Autowired` 주입 — `spring-beans/.../beans/factory/annotation/AutowiredAnnotationBeanPostProcessor.java`
      + `InjectionMetadata.java` (리플렉션으로 필드/생성자 주입)
- [ ] **3-3** `@PostConstruct`/`@PreDestroy` — `spring-context/.../context/annotation/CommonAnnotationBeanPostProcessor.java`

### Phase 4 — 설정 & 프록시 (★★★★★)
- [ ] **4-1** `@Configuration`/`@Bean` — `spring-context/.../context/annotation/ConfigurationClassPostProcessor.java` (CGLIB 프록시)
- [ ] **4-2** `@Transactional` — 어노테이션 기반 AOP 프록시 (`spring-tx`, `AnnotationTransactionAttributeSource`)

### Phase 5 — 웹 & 검증 (★★★★★) — 우리가 만든 @Valid와 직접 연결
- [ ] **5-1** `@RequestMapping` 매핑 — `spring-webmvc/.../web/servlet/mvc/method/annotation/RequestMappingHandlerMapping.java`
      + `spring-web/.../web/method/HandlerMethod.java`
- [ ] **5-2** 인자 해석 (`@RequestBody`/`@RequestParam`) — `HandlerMethodArgumentResolver` 구현들
- [ ] **5-3** `@Valid` 실제 파이프라인 — 우리 미니 검증기의 진짜 버전 (`MethodValidationInterceptor`, `LocalValidatorFactoryBean`)

---

## 진행 상황
- ✅ 세팅: 포크(`opensource_spring`) 클론 + 세션 등록 완료, 핵심 클래스 경로 확인
- ⏭ 다음: **Phase 0-1** (Spring 모듈 구조 & 소스 탐색법)
