# 🔄 새 세션 재시작 가이드 (Spring 분석 트랙)

컨텍스트 초기화 후 새 세션에서 이 트랙을 이어갈 때, 아래 순서로 복구하고 진행한다.

## 1. 분석 대상 소스 다시 붙이기 (필수)
컨테이너가 재생성되면 `/workspace/opensource_spring` 클론이 사라진다. 다시:
1. `add_repo(owner=jomin4, repo=opensource_spring)` — 세션 스코프에 추가
2. 클론 (한 번, 인라인, 넉넉한 타임아웃 ~10분):
   `git clone --depth 1 https://github.com/jomin4/opensource_spring /workspace/opensource_spring`
3. `register_repo_root(owner=jomin4, repo=opensource_spring, directory=/workspace/opensource_spring)`
> 포크 원본: spring-framework 7.1.0-SNAPSHOT (Kotlin 2.3.20).

## 2. 진행 상태
- ✅ 세팅 완료 · Phase 0-1 완료(모듈 지도 & 두 훅)
- ✅ 학습 형식·시각화 템플릿 확정
- ⏭ 다음: (선택) Phase 0-1 도표 김영한 스타일 재적용 → Phase 0-2 또는 Phase 1-1(`ReflectionUtils`)

## 3. 확정된 학습 방식 (요약 — 상세는 아래 문서)
- 계획·커리큘럼: `docs/spring-reflection-study-plan.md` (난이도순 Phase 0~5)
- 강의 형식: **소스 분석 전용** — 🪄사용법으로 열고 → 소스 골든패스 줄단위 해부(`file:line`) → 🔗사용법↔내부처리 대응표로 닫음. 한 강의=한 질문=한 골든패스. 전달=채팅(내가 파일 보여줌).
- 시각화: **김영한 스프링 입문 슬라이드 스타일** 템플릿 킷 `docs/spring-analysis/viz-template.html`의 `<style>` 복사해 컴포넌트 조립(`.jh-bean` 초록/`.jh-proxy` 주황/`.jh-container`/`.jh-arrow`/`.jh-step`/`.jh-x` …). 텍스트 최소·모바일 Artifact+SVG·visible-by-default.
- 시각화 조사·플레이북: `docs/spring-analysis/viz-playbook.md`
- 학습 기록은 이 레포(`tec_kotlin_lecture`)에, 원본은 읽기용.

## 4. 브랜치
작업 브랜치: `claude/project-analysis-5zn3lg` (모든 기록·도표·노트 커밋됨).
