# 🎨 Spring 분석 시각화 플레이북 (웹조사 기반)

지금까지 시각화가 "설명 텍스트를 박스로 감싼 것"에 그쳤던 문제 개선용. Spring 내부는 정해진 다이어그램 문법으로 그린다.

## 조사 출처 (2026-07-13 수집)
- Spring 공식: [Customizing the Nature of a Bean (bean lifecycle·BeanPostProcessor)](https://docs.spring.io/spring-framework/reference/core/beans/factory-nature.html) · [Application Startup Steps](https://docs.spring.io/spring-framework/reference/core/appendix/application-startup-steps.html)
- 프록시/AOP: [Spring blog — Transactions, Caching and AOP: understanding proxy usage](https://spring.io/blog/2012/05/23/transactions-caching-and-aop-understanding-proxy-usage-in-spring/) · [Marco Behler — @Transactional In-Depth](https://www.marcobehler.com/guides/spring-transaction-management-transactional-in-depth)
- 요청 흐름: [Coding Shuttle — Spring MVC Architecture & Request Flow](https://www.codingshuttle.com/spring-boot-handbook/spring-mvc-architecture/)
- 아키텍처/모듈: [GeeksforGeeks — Spring Framework Architecture](https://www.geeksforgeeks.org/advance-java/spring-framework-architecture/)

## 시각화 유형 (유형이 곧 문법)
| 유형 | 언제 | 다이어그램 문법 (텍스트 나열 금지) |
|---|---|---|
| **생명주기 타임라인** | 빈 라이프사이클·부팅 단계 | 시간축 + 단계 마커 + 확장점 콜아웃 + 객체 상태 변화 |
| **시퀀스** | 메서드 호출 흐름(AOP·요청) | 세로 lifeline(Caller/Proxy/Target) + 가로 화살표(라벨) + 시간 아래로 + activation bar |
| **프록시 래핑도** | `@Transactional`/`@Cacheable` | target을 감싼 shell + 호출이 shell 통과하며 가로채짐(advice in/out) |
| **의존성 그래프** | DI 관계 | 노드(빈) + 방향 엣지(주입) |
| **레이어/모듈 맵** | 아키텍처 개요 | 그룹 박스(Core/Web/AOP) |
| **어노테이션→처리기 매핑** | "누가 처리하나" | 좌(어노)↔우(processor) 연결 + 단계 태그(BFPP/BPP) |

## 표준 빈 라이프사이클 (타임라인용 정본)
인스턴스화 → 의존성 주입 → Aware 콜백 → **BPP.before** → init(@PostConstruct/afterPropertiesSet) → **BPP.after** → 사용 → 소멸
> AOP 프록시는 대개 BPP.after 지점에서 감싼다.

## 우리 Phase ↔ 시각화 유형
- 0-1 부팅/두 훅 → **생명주기 타임라인**
- 3-2 `@Autowired` → **시퀀스**(컨테이너→BPP→InjectionMetadata→`field.set`) + **의존성 그래프**
- 3-3 `@PostConstruct` → 타임라인 위 위치 표시
- 4-2 `@Transactional` → **프록시 래핑도** + **시퀀스**(caller→proxy→advice→target)
- 5-1 `@RequestMapping` → **시퀀스**(DispatcherServlet→HandlerMapping→HandlerMethod)

## 유지할 우리 스타일 (그 위에 얹기)
- 척추: 🪄사용법 → 🔗사용법↔내부처리 대응표
- 모바일 Artifact + SVG 아카이브, 코드 앵커(`file:line`), 최소 텍스트·흐름 집중
- **visible-by-default**(opacity:0 기본 금지), 애니메이션은 `prefers-reduced-motion:no-preference`에서만
- 개선 핵심: **텍스트 카드 나열 → 유형별 공간/시간 관계(시간축·lifeline·래핑·엣지)로 그린다**
