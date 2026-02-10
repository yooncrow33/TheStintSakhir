package tss.main.`object`

import tss.main.`object`.race.player.PlayerCar
import tss.main.`object`.track.Bahrain
import tss.main.Main;
import tss.main.`object`.race.display.Display
import java.awt.Graphics

class Race(main: Main) {
    val bahrain  : Bahrain = Bahrain()
    var playerCar : PlayerCar = PlayerCar(bahrain)
    val main : Main = main
    val display : Display = Display(this)

    fun update() {
        playerCar.update();
    }

    fun render(g : Graphics) {
        display.render(g)
    }

    fun init() {
        playerCar.init();
    }

    fun up() {
        display.up()
    }

    fun down() {
        display.down()
    }

    fun left() {
        display.left()
    }

    fun right() {
        display.right()
    }

    fun enter() {
        if (main.exitPopup.isVisible && main.exitPopup.m == 1) main.exitPopup.select()
    }
}