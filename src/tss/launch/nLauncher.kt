package tss.launch

import scope.Base
import tss.launch.nLauncher.MenuState.*
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.util.*

class nLauncher : Base("The Stint Series Launcher") {
    val patchNoteIndex = 1;
    val patchNoteBar = 1920-200/(patchNoteIndex+1)

    val MAX_LINE = 30
    var currentLine = 0 // 현재 스크롤 위치 (몇 번째 줄부터 보여줄지)

    enum class MenuState {
        GAME,
        STORE,
        SUPPORT,
        PATCHNOTES
    }

    enum class Game {
        TSS,
        TSJ,
        TSM
    }
    var currentMenuState = STORE;
    var currentGame = Game.TSS;

    var dev1 : String = "\n" +
            "dev-01 2/3\n" +
            "   - 프로젝트를 만듦.\n" +
            "\n" +
            "dev-01 2/3\n" +
            "   - 메뉴 그래픽을 만듦.\n" +
            "\n" +
            "dev-03 2/3\n" +
            "   - 옵션 그래픽을 만듦.\n" +
            "\n" +
            "dev-04 2/3\n" +
            "   - 이름을 바꿈. The Grid -> The Weekend\n" +
            "\n" +
            "dev-05 2/4\n" +
            "   - 세팅,메뉴를 왔다갔다 할수 있게 구현.\n" +
            "   - 이름을 바꿈. The Weekend -> The Stint\n" +
            "\n" +
            "dev-06 2/4\n" +
            "   - 새로운 런처 시스템 구현.\n" +
            "\n" +
            "dev-07 2/4\n" +
            "   - 세팅/메뉴/나가기 가능.\n" +
            "\n" +
            "dev-08 2/5\n" +
            "   - 콘솔 추가.\n" +
            "\n" +
            "dev-08 2/5\n" +
            "   - 콘솔 추가.\n" +
            "\n"

    var tssEx : String = "\n" +
            //"당신은 레이스 엔지니어가 됩니다. ㅎㅎㅎㅎㅎㅎㅎ\n" +
            "당신은 레이스 엔진니어가 됩니다.\n" +
            "아마 드라이버는 유능한 엔지니어를 원할것이고 당\n" +
            "신은 그자리를 해낼수 있을 것입니다!\n" +
            "24년을 시작하는 Sakhir로 가서 경기를 승리로 \n" +
            "만드세요!\n" +
            "모든것은 당신에게 달렸습니다\n" +
            "\n"
    public override fun init() {

    }

    public override fun update(dt: Double) {

    }

    public override fun render(g: Graphics) {
        g.color = Color(30,30,30);
        g.fillRect(-20000,-20000,40000,40000);
        g.color = Color(50,50,50)
        g.fillRect(0,0,200,1080)
        g.color = Color(40,40,40)
        g.fillRect(200,0,1920-200,100)
        renderMainScreen(g, 200,100)
    }

    fun renderMainScreen(g: Graphics, x : Int  ,y : Int) {
        when(currentMenuState) {
            GAME -> {
                when(currentGame) {
                    Game.TSS -> {
                        g.color = Color(240,240,240)
                        g.font = Font("Impact", Font.BOLD, 90)
                        g.drawString("GAME / THE STINT : SAKHIR", x + 10, y - 15)
                    }
                    Game.TSJ -> {
                        g.color = Color(240,240,240)
                        g.font = Font("Impact", Font.BOLD, 90)
                        g.drawString("GAME / THE STINT: JEDDAH", x + 10, y - 15)
                    }
                    Game.TSM -> {
                        g.color = Color(240,240,240)
                        g.font = Font("Impact", Font.BOLD, 90)
                        g.drawString("GAME / THE STINT: MEXICO", x + 10, y - 15)
                    }
                }
            }
            STORE -> {
                g.color = Color(240,240,240)
                g.font = Font("Impact", Font.BOLD, 90)
                g.drawString("STORE", x + 10, y - 15)
                renderShop(g, x, y, 0)
            }
            SUPPORT -> {
                g.color = Color(240,240,240)
                g.font = Font("Impact", Font.BOLD, 90)
                g.drawString("SUPPORT", x + 10, y - 15)
            }
            PATCHNOTES -> {
                g.color = Color(240,240,240)
                g.font = Font("Impact", Font.BOLD, 90)
                g.drawString("PATCH NOTE", x + 10, y - 15)
                // 2. 단어 쪼개기
                val args = dev1.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val endLine = if (currentLine + MAX_LINE > args.size) args.size else currentLine + MAX_LINE

                // 2. i는 0부터 MAX_LINE까지만 돌면서 화면상의 위치를 잡음
                for (i in 0 until (endLine - currentLine)) {
                    val word = args[currentLine + i] // 실제 데이터는 currentLine을 더해서 가져옴
                    g.drawString(word, x + 50, y + 100 + (i * 20))
                }


        }
    }
    }

    fun scrollDown(argsSize: Int) {
        currentLine++
        // 맨 밑에 도달하면 다시 0으로 (순환)
        if (currentLine + MAX_LINE > argsSize) {
            currentLine = 0
        }
    }

    fun scrollUp(argsSize: Int) {
        currentLine--
        // 맨 위에서 위로 더 가면 맨 밑으로 (순환)
        if (currentLine < 0) {
            currentLine = argsSize - MAX_LINE
            if (currentLine < 0) currentLine = 0 // 데이터가 적을 때 방어 코드
        }
    }

    fun renderShop(g : Graphics, x : Int, y : Int, scrollY : Int) {
        g.color = Color(255,50,30)
        g.fillRect(x + 20, y + 20 + scrollY, 1200, 700)
        g.color = Color(70,70,70)
        g.fillRect(x + 1220, y + 20 + scrollY, 500, 700)
        g.color = Color(240,240,240)
        g.font = Font("Arial", Font.BOLD, 24)
        val args = tssEx.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        // 2. i는 0부터 MAX_LINE까지만 돌면서 화면상의 위치를 잡음
        for (i in args.indices) {
            val word = args[currentLine + i] // 실제 데이터는 currentLine을 더해서 가져옴
            g.drawString(word, x + 50, y + 100 + (i * 20))
        }
    }

    fun up() {

    }

    fun down() {

    }

    fun left() {

    }

    fun right() {

    }

    fun enter() {

    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun main(args: Array<String>) {
            nLauncher()
        }
    }
}
