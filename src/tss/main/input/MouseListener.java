package tss.main.input;

import tss.main.Main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {

    final Main main;

    public MouseListener(Main main) {
        this.main = main;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        main.click();
    }
}