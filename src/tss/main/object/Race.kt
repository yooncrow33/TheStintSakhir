package tss.main.`object`

import tss.main.`object`.race.player.PlayerCar
import tss.main.`object`.track.Bahrain
import tss.main.Main;

class Race(main: Main) {
    val bahrain  : Bahrain = Bahrain();
    var playerCar : PlayerCar = PlayerCar(bahrain);
    val main : Main = main

    fun update() {
        playerCar.update();
    }

    fun init() {
        playerCar.init();
    }

    fun enter() {
        if (main.exitPopup.isVisible && main.exitPopup.m == 1) main.exitPopup.select()
    }
}