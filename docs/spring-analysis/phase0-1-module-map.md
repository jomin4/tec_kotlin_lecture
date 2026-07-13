# Phase 0-1 — Spring 모듈 구조 & 어노테이션 처리의 두 훅

> 🎯 분석 질문: 내 Spring 어노테이션은 **어디서·언제** 처리되나?
> 📂 대상: 모듈 지도 + `spring-beans/.../beans/factory/config/BeanPostProcessor.java:67`
> 🖼️ 도표(타임라인·최종): `diagrams/sp0-1_bean-lifecycle-hooks.svg` · [📱Artifact](https://claude.ai/code/artifact/74e83b31-35c1-4b20-bf02-dab886dfe088)
> (구버전 박스형 `sp0-1_boot-hooks.svg`는 템플릿 도입 전 — 타임라인 버전으로 대체)
> 분석 대상 소스: `/workspace/opensource_spring` (spring-framework 7.1.0-SNAPSHOT)

## 🪄 사용자 관점
`@Service`/`@Autowired`/`@Transactional`/`@GetMapping` — 붙이면 동작. "어디서, 부팅의 어느 순간에" 처리되는지가 질문.

## 모듈 지도 (클론 실측)
| 모듈 | 규모(.java) | 역할 |
|---|---|---|
| spring-core | 733 | 리플렉션·어노테이션 **엔진**(도구층) |
| spring-beans | 336 | 빈·DI (BeanFactory, @Autowired) |
| spring-context | 625 | 컨테이너·스캔·설정 (@Component/@Configuration) |
| spring-aop / tx | 226 / 173 | 프록시 (@Transactional) |
| spring-web / webmvc | 789 / 363 | 웹 (@RequestMapping) |

## 어노테이션·리플렉션 코드가 사는 3곳
- `spring-core/.../core/annotation/` — 어노테이션 모델: `AnnotationUtils`·`AnnotatedElementUtils`·`MergedAnnotations`·`AliasFor`
- `spring-core/.../util/` — 리플렉션 유틸: `ReflectionUtils`·`ClassUtils`
- 각 기능 모듈의 `*PostProcessor` — 실제 처리기 (spring-beans/context에만 19개)
→ **읽는 도구는 core, 처리하는 놈은 각 *PostProcessor.**

## 🔧 뼈대 — 두 확장점 훅
- **BeanFactoryPostProcessor** (`spring-beans/.../config/BeanFactoryPostProcessor.java`): 빈 **정의(설계도)** 가공 — 인스턴스화 **전**. 스캔·설정.
- **BeanPostProcessor** (`.../config/BeanPostProcessor.java:67`): 빈 **인스턴스** 가공 — 생성 **중**. 두 메서드:
  - `postProcessBeforeInitialization(bean, name)` (:82)
  - `postProcessAfterInitialization(bean, name)` (:107)
  주입·프록시가 여기서. 어노테이션은 이 훅에서 `spring-core` 리플렉션 유틸로 읽힘.

**부팅 = ① 빈 정의 등록(BFPP) → ② 빈 하나씩 생성하며 후처리(BPP).**
앞으로 모든 어노테이션의 질문은 하나로 수렴: **"어느 PostProcessor가, ①정의냐 ②인스턴스냐?"**

## 🔗 Phase 배치
Phase1(core 도구) → 2(core 어노테이션 모델) → 3(context ① + beans ②) → 4(aop/tx 프록시 ②) → 5(webmvc, 부팅 후 요청).

## 💭 탐색 과제
`AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor` → @Autowired 주입은 ①정의냐 ②인스턴스냐?
답: **②인스턴스 단계** (이름의 BeanPostProcessor = 빈 실물 후처리, 주입은 실제 객체에 값을 꽂는 것).

## 요약
1. 읽는 도구=spring-core, 처리기=각 *PostProcessor.
2. 모든 어노테이션 처리는 BeanFactoryPostProcessor(정의) / BeanPostProcessor(인스턴스) 두 훅 중 하나.
3. 부팅=정의 등록→인스턴스 후처리. 질문은 늘 "어느 PostProcessor가 어느 단계에서?".
