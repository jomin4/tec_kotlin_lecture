# 자바 개발자를 위한 코틀린 입문 — 학습 레포

인프런 **"자바 개발자를 위한 코틀린 입문(Java to Kotlin Starter Guide)"** 커리큘럼을 따라가며
코틀린을 학습한 기록. 강의 1개당 **개념(Java 비교) → 도표 → 실습 코드 → 실행 → 연습문제** 순으로 정리한다.

## 실행 방법 (Windows)

```powershell
# PowerShell
.\run.ps1 section06\Lesson02.kt
```
```bash
# Git Bash
./run.sh section06/Lesson02.kt
```
`.kt`를 컴파일해 UTF-8로 실행한다. (컴파일러: `kotlinc`, JDK 25 · 한글 깨짐 방지 자동 적용)

## 진행 현황

| 섹션 | 주제 | 강의 |
|------|------|------|
| 1 | 변수 · null · 타입 · 연산자 | 4강 ✅ |
| 2 | 제어문 (if · when · 반복문) | 3강 ✅ |
| 3 | 함수 · 확장함수 | 2강 ✅ |
| 4 | 클래스 · data class · 접근지정자 | 2강 ✅ |
| 5 | 상속 · 인터페이스 | 2강 ✅ |
| 6 | enum · sealed class | 2강 ✅ |
| 7 | 배열 / 컬렉션 | 진행중 🚧 |
| 8 | 함수형 (람다 · 스코프 함수) | 예정 |

## 구조

```
section0N/LessonNN.kt   # 강의별 실습 코드 (한글 주석)
docs/QnA.md             # 학습 중 질문·답변 모음
docs/diagrams/          # 개념 도표 (SVG)
run.ps1 / run.sh        # 컴파일 + 실행 스크립트
```
