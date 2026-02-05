package tss.main.input;

import scope.KeyBindingBase;
import tss.main.Main;

import javax.swing.*;

public class KetListener extends KeyBindingBase {

    Main main;

    public KetListener(JComponent comp, Main main) {
        super(comp);
        this.main = main;
    }

    @Override protected void onKeyEnterPress() {
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
            }
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
            }
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
}
