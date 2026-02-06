package tss.launch;

import scope.KeyBindingBase;

import javax.swing.*;
import java.awt.*;

public class Key extends KeyBindingBase {
    nLauncher n;
    protected Key(JComponent comp, nLauncher n) {
        super(comp);
        this.n = n;
    }

    @Override
    protected void onKeyUpPress() {
        n.up();
    }
    @Override
    protected void onKeyLeftPress() {
        n.left();
    }
    @Override
    protected void onKeyDownPress() {
        n.down();
    }
    @Override
    protected void onKeyRightPress() {
        n.right();
    }
    @Override
    protected void onKeyEnterPress() {
        n.enter();
    }
}
