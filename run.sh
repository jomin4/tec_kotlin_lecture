#!/usr/bin/env bash
# 사용법:  ./run.sh section01/Lesson01.kt
# .kt 파일을 컴파일하고 UTF-8로 실행합니다.
set -e
KT="$1"
if [ -z "$KT" ]; then echo "사용법: ./run.sh <파일.kt>"; exit 1; fi

KOTLINC="C:/Users/kdcho/kotlinc/bin/kotlinc"
DIR="$(dirname "$KT")"
JAR="$DIR/out.jar"

echo "▶ 컴파일 중: $KT"
"$KOTLINC" "$KT" -include-runtime -d "$JAR" 2>&1 | grep -v "^warning:" || true
echo "▶ 실행 결과:"
echo "----------------------------------------"
java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -jar "$JAR"
echo "----------------------------------------"
