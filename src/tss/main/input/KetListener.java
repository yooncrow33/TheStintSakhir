package tss.main.input;

import scope.KeyBindingBase;
import tss.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KetListener extends KeyBindingBase {

    Main main;

    public KetListener(JComponent comp, Main main) {
        super(comp);
        this.main = main;
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_TYPED && main.getConsole().isOpen()) {
                char c = e.getKeyChar();
                if (c != '\n' && c != '\b' && c != 27) {
                    main.getConsole().inputKey(c, 0);
                } return true;
            } return false;
        });
    }

    @Override protected void onKeyEnterPress() {
        if (main.getConsole().isOpen()) {
            main.getConsole().inputKey('\n', 10); // 강제로 코드 10 넣어줌
            return;
        }

        if (main.isScreenState(Main.GameScreenState.SETTINGS)) {
            if (main.getSettingManager().getFocus() == 5) {
                main.getShutter().changScreen(Main.GameScreenState.MENU);
            }
        } else if (main.isScreenState(Main.GameScreenState.MENU)) {
            if (main.getExitPopup().isVisible()) {
                main.getExitPopup().select();
                return;
            } else {
                if (main.menuFocus == 2) {
                    main.getExitPopup().setVisible();
                }
            }
            if (main.menuFocus == 1) {
                main.getShutter().changScreen(Main.GameScreenState.SETTINGS);
            }
        }
    }

    @Override protected void onKeyUpPress() {
        if (main.isScreenState(Main.GameScreenState.SETTINGS)) {
            main.getSettingManager().up();
        }
        if (main.isScreenState(Main.GameScreenState.MENU)) {
            if (main.menuFocus > 0) {
                main.menuFocus--;
            } else main.menuFocus = 2;
        }
    }

    @Override protected void onKeyLeftPress() {
        if (main.isScreenState(Main.GameScreenState.MENU) && main.getExitPopup().isVisible()) {
            main.getExitPopup().move();
        }
        if (main.isScreenState(Main.GameScreenState.SETTINGS)) {
            main.getSettingManager().left();
        }
    }

    @Override protected void onKeyDownPress() {
        if (main.isScreenState(Main.GameScreenState.SETTINGS)) {
            main.getSettingManager().down();
        }
        if (main.isScreenState(Main.GameScreenState.MENU)) {
            if (main.menuFocus < 2) {
                main.menuFocus++;
            } else main.menuFocus = 0;
        }
    }

    @Override protected void onKeyRightPress() {
        if (main.isScreenState(Main.GameScreenState.MENU) && main.getExitPopup().isVisible()) {
            main.getExitPopup().move();
        }
        if (main.isScreenState(Main.GameScreenState.SETTINGS)) {
            main.getSettingManager().right();
        }
    }

    @Override
    protected void onKeyBackspacePress() {
        if (main.getConsole().isOpen()) {
            main.getConsole().inputKey('\b', 8);
        }
    }
    @Override
    protected void onKeyF12Press() {
        main.getConsole().toggle();
    }
}
