package tss.main;

import scope.Base;
import tss.main.input.KetListener;
import tss.main.manager.SettingManager;
import tss.main.manager.Shutter;
import tss.main.object.ExitPopup;
import tss.main.object.Console;
import tss.main.object.Race;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main extends Base {
    public final int profileId;
    GraphicsManager gm;
    private SettingManager settingManager;
    KetListener ketListener;
    Shutter shutter;
    ExitPopup exitPopup;
    Console console;

    String callSign;
    String firstName;
    String lastName;
    String driverNumber;
    String nationality;

    Race race;

    public boolean pizza = false;

    public int menuFocus = 0;
    public int mode = 0;

    public enum GameScreenState {
        MENU,
        SETTINGS,
        MODESELECT,
        GAME
    }

    GameScreenState gameScreenState = GameScreenState.MENU;

    public Main(String title, int profileId) {
        super(title);
        this.profileId = profileId;
        load();
    }

    public void init() {
        gm = new GraphicsManager(this);
        settingManager = new SettingManager();
        ketListener = new KetListener(this,this);
        shutter = new Shutter(this);
        exitPopup = new ExitPopup(shutter);
        console = new Console(scopeEngine(), this);
        race = new Race(this);
    }

    public void update(double dt) {
        shutter.update();
    }

    public void click() {

        System.out.println("[EVENT] Mouse clicked");
    }

    public boolean isScreenState(GameScreenState gss) {
        return gameScreenState == gss;
    }

    public void setGameScreenState(GameScreenState gss) {
        gameScreenState = gss;
    }

    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (pizza) {
            g2.rotate(Math.PI, 1920 / 2.0, 1080 / 2.0);
        }
        gm.renderBackground(g);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        gm.renderBackground(g);

        switch (gameScreenState) {
            case MENU :
                gm.renderMenu(g);
                break;
            case SETTINGS :
                gm.renderSettings(g,this);
                break;
            case MODESELECT:
                gm.renderModeSelection(g,mode);
                break;
            case GAME :
                gm.renderEngineeringFullView(g);
                break;
        }



        shutter.render(g);
        exitPopup.render(g,"ARE YOU SURE?", "EXIT THE GAME");

        console.render(g);

       // gm.renderModeSelection(g,0);

    }

    public void back() {
        if (!(gameScreenState == GameScreenState.MENU)) {
            if (gameScreenState == GameScreenState.GAME) {
                getExitPopup().setVisible(1);
            } else shutter.changScreen(GameScreenState.MENU);
        }
        else getExitPopup().setVisible(2);
    }

    public void go() {
        shutter.changScreen(GameScreenState.GAME);
        // init gameModel
    }

    public Race getRace() {
        return race;
    }

    public SettingManager getSettingManager() { return settingManager; }

    public Shutter getShutter() { return shutter; }

    public ExitPopup getExitPopup() {return exitPopup;}

    public Console getConsole() {return console;}

    public void moveMode() {if (mode == 1) mode = 0; else mode = 1;}

    public void load() {
        Properties p = new Properties();
        String homeDir = System.getProperty("user.home")+ File.separator + "tss" + File.separator + "save";

        String fullPath1 = homeDir + File.separator + "TheStintSakhirSaveProfile1.properties";
        String fullPath2 = homeDir + File.separator + "TheStintSakhirSaveProfile2.properties";
        String fullPath3 = homeDir + File.separator + "TheStintSakhirSaveProfile3.properties";

        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};

        try (FileInputStream in = new FileInputStream(paths[profileId])) {
            p.load(in);

            firstName = p.getProperty("firstName");
            lastName = p.getProperty("lastName");
            driverNumber = p.getProperty("driverNumber");
            callSign = p.getProperty("callSign");
            nationality = p.getProperty("nationality");


        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "저장파일 인식 실패");
        }
    }

    public static void main(String[] args) {
        new Main("The Stint : Sakhir", 1);
    }
}
