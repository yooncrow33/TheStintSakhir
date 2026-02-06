package tss.main.`object`

import java.awt.FlowLayout
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.SwingUtilities

class Help {
    var frame: JFrame = JFrame("Help")
    var helpText: String = ("Help Information:\n"
            + "1. setter.\n"
            + "  - set type name value\n"
            + "\n"
            + " 1.1 val \n"
            + "  - setting1,3,4,5 = 0~2\n"
            + "  - setting2 = 0~4\n"
            + "  - setting -> gameSetting(Those in Settings Tap)\n"
            + "\n"
            + " 1.2 bool\n"
            + "  - screenchangeeffect -> y or n for screen change shutter.\n"
            + "\n"
            + "2. others\n"
            + "  - clear -> clear your console logs.\n"
            + "  - close -> close console.\n"
            + "  - loaddata -> quick load profile data.\n"
            + "  - exit -> exit the game.\n"
            + "\n")
    var area: JTextArea = JTextArea(helpText, 25, 60)

    init {
        frame.setSize(800, 600)
        // 게임 도중 도움말 창을 닫는 용도라면 DISPOSE_ON_CLOSE가 낫습니다.
        // EXIT_ON_CLOSE는 도움말 닫으면 게임 프로세스도 종료됩니다.
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE)
        frame.setLayout(FlowLayout(FlowLayout.CENTER, 20, 40))
        frame.setResizable(false)

        // 스크롤이 필요할 수도 있으니 JScrollPane으로 감싸는 것을 추천합니다.
        val scrollPane = JScrollPane(area)
        frame.add(scrollPane)

        // 핵심: 위치 설정과 가시성 설정은 항상 마지막에!
        frame.setLocationRelativeTo(null)
        frame.setVisible(false)
    }


    fun setVisible() {
        frame.setVisible(true)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // Swing GUI는 Event Dispatch Thread에서 돌리는 게 안전합니다.
            SwingUtilities.invokeLater(Runnable { Help() })
        }
    }
}
