# 사용법:  .\run.ps1 section01\Lesson01.kt
# .kt 파일을 컴파일하고 UTF-8로 실행합니다.
param([Parameter(Mandatory=$true)][string]$File)

# 터미널을 UTF-8로 맞춤 (한글 깨짐 방지 핵심)
chcp 65001 > $null
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8

$kotlinc = "C:\Users\kdcho\kotlinc\bin\kotlinc.bat"
$dir = Split-Path $File -Parent
$jar = Join-Path $dir "out.jar"

Write-Host "▶ 컴파일 중: $File" -ForegroundColor Cyan
& $kotlinc $File -include-runtime -d $jar 2>$null

Write-Host "▶ 실행 결과:" -ForegroundColor Green
Write-Host "----------------------------------------"
java "-Dfile.encoding=UTF-8" "-Dstdout.encoding=UTF-8" -jar $jar
Write-Host "----------------------------------------"
