package tss.main.`object`

import scope.internal.facade.Access.ScopeEngineAccess
import tss.main.Main
import tss.main.`object`.Help;
import java.awt.*
import java.util.*
import kotlin.math.max

class Console(var scopeEngine: ScopeEngineAccess, var main: Main) {
    var isOpen: Boolean = false
        private set
    private val buffer = StringBuilder()
    private val logs = ArrayList<String>()

    // 스크롤 처리를 위한 변수 (로그가 많아지면 위로 밀리게)
    private val MAX_LINES = 10

    val help = Help();

    fun toggle() {
        isOpen = !isOpen
    }

    // ★ 핵심: 키보드 입력을 받아먹는 함수
    fun inputKey(key: Char, code: Int) {
        if (!isOpen) return

        if (code == 10) { // Enter Key Code
            execute()
        } else if (code == 8) { // Backspace
            if (buffer.length > 0) {
                buffer.deleteCharAt(buffer.length - 1)
            }
        } else {
            // 특수문자나 이상한 거 제외하고 출력 가능한 것만 받음
            // (자바 char는 유니코드라 한글도 됨)
            if (key.code >= 32 && key.code <= 126) {
                buffer.append(key)
            }
        }
    }

    private fun execute() {
        val input = buffer.toString().trim()
        if (input.isEmpty()) return

        logs.add("[${String.format("%tT", System.currentTimeMillis())}] root: $input")

        val args = input.split(" ")
        val cmd = args[0].lowercase()

        // if-else if 대신 when을 쓰면 줄 맞춤이 편해져요
        when (cmd) {
            "help" -> help.setVisible()
            "clear" -> logs.clear()
            "close" -> isOpen = false;
            "exit" -> System.exit(0)
            "loaddata" -> main.load()
            "pizza" -> {
                main.pizza = !main.pizza // if-else 대신 반전(!) 하나로 끝
                logs.add("[System] Is very good.")
            }
            "set" -> {
                if (args.size == 4) handleSet(args)
                else logs.add("[Console] Usage: set [type] [name] [value]")
            }
            else -> { logs.add("[Console] Error: Unknown command: $cmd"); logs.add("[Console] Try 'help' for more information") }
        }
        buffer.setLength(0)
    }

    private fun handleSet(args: List<String>) {
        val (_, type, target, valueStr) = args
        val value = valueStr.toIntOrNull() ?: 0

        if (type == "val") {
            if (target.startsWith("setting")) {
                val num = target.replace("setting", "").toIntOrNull() ?: 0
                if (num !in 1..<5) {
                    logs.add("[Console] Error: No found setting")
                    return;
                }
                // 여기서 핵심! 인덱스별로 범위를 다르게 체크
                val isValid = when (num) {
                    2 -> value in 0..4        // setting2는 상남자답게 0~4 허용
                    1, 3, 4, 5 -> value in 0..2 // 나머지는 0~2만
                    else -> false             // setting6 같은 건 바로 컷
                }
                if (isValid) {
                    main.settingManager.setSettingIndex(num - 1, value)
                    logs.add("[System] $target set to $value")
                } else {
                    logs.add("[Console] Error: Value $value is out of range for $target")
                }
            } else {
                logs.add("[Console] Error: No found name")
            }

        } else if (type == "bool") {
            val valueBool = valueStr.toBooleanStrictOrNull()
            if (valueBool == null) { logs.add("[Console] Error: Not boolean value"); return}

            when (target) {
                "screenchangeeffect" -> { main.getShutter().setScreenEffect(valueBool); logs.add("[System] $target set to $valueBool") }
            }
        }
    }

    fun render(g: Graphics?) {
        if (!isOpen) return
        val g2 = g as Graphics2D

        // 1. 배경 (반투명 블랙)
        g2.setColor(Color(10, 10, 10, 240)) // 거의 불투명하게
        g2.fillRect(0, 0, 1920, 340) // 화면 절반 덮기

        // 2. 하단 구분선 (포인트 컬러: Scope Red)
        g2.setColor(Color(255, 50, 30))
        g2.setStroke(BasicStroke(3f))
        g2.drawLine(0, 340, 1920, 340)

        // -----------------------------------------------------
        // [Left Zone] 명령어 로그 창
        // -----------------------------------------------------
        g2.setFont(Font("Consolas", Font.PLAIN, 16)) // Consolas나 Monospaced 권장
        val lineHeight = 25
        val startY = 40

        // 최근 N개의 로그만 보여주기 (스크롤 효과)
        val startIndex = max(0, logs.size - MAX_LINES)
        var lineCount = 0

        for (i in startIndex..<logs.size) {
            val line = logs.get(i)

            // 컬러 코딩 로직 (간단하게)
            if (line.contains("Error")) g2.setColor(Color(255, 80, 80)) // Red
            else if (line.contains("root:")) g2.setColor(Color.WHITE) // Me
            else if (line.contains("[System]")) g2.setColor(Color.GREEN) // System
            else g2.setColor(Color.LIGHT_GRAY) // Others


            g2.drawString(line, 30, startY + (lineCount * lineHeight))
            lineCount++
        }

        // -----------------------------------------------------
        // [Input Line] 입력창 (맨 아래 고정)
        // -----------------------------------------------------
        g2.setColor(Color(255, 50, 30))
        g2.setFont(Font("Consolas", Font.BOLD, 18))
        val cursor = if (System.currentTimeMillis() % 1000 > 300) "_" else ""
        g2.drawString("root@tss:~$ " + buffer.toString() + cursor, 30, 320)

        // -----------------------------------------------------
        // [Right Zone] 시스템 모니터 (간지용 대시보드)
        // -----------------------------------------------------
        // 구분선 (세로)
        g2.setColor(Color.DARK_GRAY)
        g2.drawLine(1500, 20, 1500, 320)

        g2.setFont(Font("Impact", Font.PLAIN, 24))
        g2.setColor(Color.WHITE)
        g2.drawString("TSS GAME STATUS", 1520, 60)

        g2.setFont(Font("Consolas", Font.BOLD, 14))
        g2.setColor(Color.GRAY)


        var y = 100
        drawStat(g2, "FPS", "60 (Fixed)", Color.CYAN, 1520, y)
        y += 30
        drawStat(
            g2,
            "MEMORY",
            scopeEngine.system().getUsedMemory().toString() + "MB / " + scopeEngine.system().getTotalMemory() + "MB",
            Color.YELLOW,
            1520,
            y
        )
        y += 30
        drawStat(g2, "THREADS", Thread.activeCount().toString() + " Active", Color.WHITE, 1520, y)
        y += 30
        drawStat(g2, "CPU", scopeEngine.system().getCpuPercentage().toString() + "%", Color.RED, 1520, y)
        y += 30

        // 밑에 Scope 로고 작게
        g2.setColor(Color(40, 40, 40))
        g2.setFont(Font("Impact", Font.ITALIC, 40))
        g2.drawString("THE STINT : SAKHIR", 1600, 300)
    }

    // 모니터링용 헬퍼 함수
    private fun drawStat(g: Graphics2D, label: String?, value: String?, valColor: Color?, x: Int, y: Int) {
        g.setColor(Color.GRAY)
        g.drawString(label, x, y)
        g.setColor(valColor)
        g.drawString(value, x + 120, y)
    }
}