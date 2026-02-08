package tss.main.manager;

public class SettingManager {
    int practiceModeIndex = 0;
    String practiceModeString[] = {"Short Practice", "Full Practice", "Quick Practice"};
    int raceLengthIndex = 0;
    String raceLengthString[] = {"10%", "25%", "50%", "75%", "100%"};
    int difficultyIndex = 0;
    String difficultyString[] = {"Normal", "Hard", "Extreme"};
    int weatherIndex = 0;
    String weatherString[] = {"Random", "Always Rainy", "Always Clear"};
    int emptyIndex = 0;
    String emptyString[] = {"1", "2", "3"};

    String settingNames[] = {
            "Practice Mode", "Race Length", "Difficulty", "Weather", "Empty Setting" };

    int focus = 5;

    public String getSettingName(int index) {
        return settingNames[index];
    }

    public int getFocus() {
        return focus;
    }

    public void setSettingIndex(int index,int value){
        switch (index) {
            case 0:
                practiceModeIndex = value;
                break;
            case 1:
                raceLengthIndex = value;
                break;
            case 2:
                difficultyIndex = value;
                break;
            case 3:
                weatherIndex = value;
                break;
            case 4:
                emptyIndex = value;
                break;
        }
    }

    public String getCurrentSettingValue(int index) {
        switch (index) {
            case 0:
                return practiceModeString[practiceModeIndex];
            case 1:
                return raceLengthString[raceLengthIndex];
            case 2:
                return difficultyString[difficultyIndex];
            case 3:
                return weatherString[weatherIndex];
            case 4:
                return emptyString[emptyIndex];
            default:
                return "";
        }
    }

    public void up() {
        focus--;
        if (focus < 0) {
            focus = 5;
        }
    }

    public void down() {
        focus++;
        if (focus > 5) {
            focus = 0;
        }
    }

    public void left() {
        if (focus == 0) {
            practiceModeIndex--;
            if (practiceModeIndex < 0) {
                practiceModeIndex = practiceModeString.length - 1;
            }
        } else if (focus == 1) {
            raceLengthIndex--;
            if (raceLengthIndex < 0) {
                raceLengthIndex = raceLengthString.length - 1;
            }
        } else if (focus == 2) {
            difficultyIndex--;
            if (difficultyIndex < 0) {
                difficultyIndex = difficultyString.length - 1;
            }
        } else if (focus == 3) {
            weatherIndex--;
            if (weatherIndex < 0) {
                weatherIndex = weatherString.length - 1;
            }
        } else if (focus == 4) {
            emptyIndex--;
            if (emptyIndex < 0) {
                emptyIndex = emptyString.length - 1;
            }
        }
    }

    public void right() {
        if (focus == 0) {
            practiceModeIndex++;
            if (practiceModeIndex >= practiceModeString.length) {
                practiceModeIndex = 0;
            }
        } else if (focus == 1) {
            raceLengthIndex++;
            if (raceLengthIndex >= raceLengthString.length) {
                raceLengthIndex = 0;
            }
        } else if (focus == 2) {
            difficultyIndex++;
            if (difficultyIndex >= difficultyString.length) {
                difficultyIndex = 0;
            }
        } else if (focus == 3) {
            weatherIndex++;
            if (weatherIndex >= weatherString.length) {
                weatherIndex = 0;
            }
        } else if (focus == 4) {
            emptyIndex++;
            if (emptyIndex >= emptyString.length) {
                emptyIndex = 0;
            }
        }
    }
}
