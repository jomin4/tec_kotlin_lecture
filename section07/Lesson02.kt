// 섹션 7 - 강의 2: 배열 (Array / IntArray, 크기 고정, 생성·순회·변환)
// 실행:  .\run.ps1 section07\Lesson02.kt

fun main() {
    // ============================================================
    // ① 만드는 법 3가지
    //    자바: int[] a = {1,2,3};  /  new int[5]  /  {i -> ...} 없음
    // ============================================================
    val a = intArrayOf(1, 2, 3)              // IntArray  = 자바 int[] (박싱 없음)
    val b = arrayOf("코틀린", "자바")          // Array<String> = 자바 String[]
    val zeros = IntArray(5)                   // [0, 0, 0, 0, 0]  기본값 0으로 5칸
    val squares = IntArray(5) { i -> i * i }  // 람다로 초기화 → [0, 1, 4, 9, 16]

    println("a = ${a.joinToString()}")        // 배열은 그냥 println하면 주소가 나옴!
    println("squares = ${squares.joinToString()}")
    //  ⚠️ println(a) 하면  [I@1b6d3586  같은 게 나온다 → joinToString()/contentToString() 필수

    // ============================================================
    // ② 접근·크기·val인데 원소 변경 (자바 final int[] 와 동일)
    // ============================================================
    println("a[0] = ${a[0]}, 크기 = ${a.size}, 마지막 = ${a.last()}")
    a[0] = 99                                 // ✅ val이어도 원소는 바뀐다
    println("변경 후 a = ${a.joinToString()}") // 99, 2, 3
    //  a.add(4)  → ❌ 배열은 크기 고정. add/remove 없음 (List를 써야 함)

    // ============================================================
    // ③ 순회 — 인덱스가 필요하냐 아니냐로 골라 쓴다
    // ============================================================
    for (x in b) print("$x ")                 // 값만
    println()
    for (i in b.indices) print("[$i]=${b[i]} ") // 0..size-1 인덱스
    println()
    for ((i, x) in b.withIndex()) print("$i→$x ") // 인덱스+값 (구조분해)
    println()

    // ============================================================
    // ④ IntArray vs Array<Int> — 박싱 차이 (성능)
    // ============================================================
    val prim: IntArray = intArrayOf(1, 2, 3)   // int[]     : 박싱 없음 (빠름)
    val boxed: Array<Int> = arrayOf(1, 2, 3)   // Integer[] : 원소마다 박싱
    println("합계 IntArray = ${prim.sum()}, Array<Int> = ${boxed.sum()}") // 둘 다 6

    // ============================================================
    // ⑤ vararg 와 스프레드(*) — 배열을 가변인자로 펼쳐 넘김
    // ============================================================
    println("max = ${maxOf3(*a)}")             // *a : 배열을 1,2,3... 낱개로 펼침

    // ============================================================
    // ⑥ 배열 ↔ List 변환 (실무에선 대개 List로 갈아탄다)
    // ============================================================
    val list = a.toList()                      // IntArray → List<Int> (읽기전용)
    val back = listOf(4, 5, 6).toIntArray()    // List<Int> → IntArray
    println("toList = $list, toIntArray = ${back.joinToString()}")
}

// vararg: 자바의 int... 와 동일. 내부에서 xs는 IntArray로 받는다.
fun maxOf3(vararg xs: Int): Int = xs.max()
