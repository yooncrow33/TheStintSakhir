package tss.main.input

import scope.KeyBindingBase
import tss.main.Main
import java.awt.KeyEventDispatcher
import java.awt.KeyboardFocusManager
import java.awt.event.KeyEvent
import javax.swing.JComponent

class KetListener(comp: JComponent, var main: Main) : KeyBindingBase(comp) {
    init {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(KeyEventDispatcher { e: KeyEvent? ->
            if (e!!.getID() == KeyEvent.KEY_TYPED && main.getConsole().isOpen) {
                val c = e.getKeyChar()
                if (c != '\n' && c != '\b' && c.code != 27) {
                    main.getConsole().inputKey(c, 0)
                }
            }
            false
        })
    }

    override fun onKeyEnterPress() {
        if (main.getConsole().isOpen) {
            main.getConsole().inputKey('\n', 10) // 강제로 코드 10 넣어줌
            return
        }

        if (main.isScreenState(Main.GameScreenState.SETTINGS)) {
            if (main.getSettingManager().getFocus() == 5) {
                main.getShutter().changScreen(Main.GameScreenState.MENU)
            }
        } else if (main.isScreenState(Main.GameScreenState.MENU)) {
            if (main.getExitPopup().isVisible()) {
                main.getExitPopup().select()
                return
            } else {
                if (main.menuFocus == 2) {
                    main.getExitPopup().setVisible()
                }
            }
            if (main.menuFocus == 1) {
                main.getShutter().changScreen(Main.GameScreenState.SETTINGS)
            }
        }
    }

    override fun onKeyUpPress() {
        if (main.isScreenState(Main.GameScreenState.SETTINGS)) {
            main.getSettingManager().up()
        }
        if (main.isScreenState(Main.GameScreenState.MENU)) {
            if (main.menuFocus > 0) {
                main.menuFocus--
            } else main.menuFocus = 2
        }
    }

    override fun onKeyLeftPress() {
        if (main.isScreenState(Main.GameScreenState.MENU) && main.getExitPopup().isVisible()) {
            main.getExitPopup().move()
        }
        if (main.isScreenState(Main.GameScreenState.SETTINGS)) {
            main.getSettingManager().left()
        }
    }

    override fun onKeyDownPress() {
        if (main.isScreenState(Main.GameScreenState.SETTINGS)) {
            main.getSettingManager().down()
        }
        if (main.isScreenState(Main.GameScreenState.MENU)) {
            if (main.menuFocus < 2) {
                main.menuFocus++
            } else main.menuFocus = 0
        }
    }

    override fun onKeyRightPress() {
        if (main.isScreenState(Main.GameScreenState.MENU) && main.getExitPopup().isVisible()) {
            main.getExitPopup().move()
        }
        if (main.isScreenState(Main.GameScreenState.SETTINGS)) {
            main.getSettingManager().right()
        }
    }

    override fun onKeyBackspacePress() {
        if (main.getConsole().isOpen) {
            main.getConsole().inputKey('\b', 8)
        }
    }

    override fun onKeyF12Press() {
        main.getConsole().toggle()
    }
}
