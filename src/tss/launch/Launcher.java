package tss.launch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.*;
import java.util.Properties;

public class Launcher {
    JFrame frame = new JFrame("The Stint : Sakhir Launcher");
    JTextField main = new JTextField("Enter your password here");
    JButton Start = new JButton("Start >>");
    JButton fux = new JButton("Create password to start");
    JLabel lable = new JLabel("The Stint : Sakhir");
    JLabel lable2 = new JLabel("Race Engineering Simulator");
    JButton recentNews = new JButton("Recent News");
    JButton patchNotes = new JButton("Patch Notes");
    JButton manual = new JButton("Manual");
    JTextArea Area = new JTextArea();
    JScrollPane p = new JScrollPane(Area);
    JTextArea TitleArea = new JTextArea("Manual");
    //JLabel lable2 = new JLabel("Since it is in the development stage, only the latest version will be executed.");
    String password;
    String login;
    boolean setPass;
    String SAVE_FILE = new File(System.getProperty("user.home") + "/tss", "the_stint_sakhir_password.txt").getAbsolutePath();
    final File file = new File(SAVE_FILE);

    boolean profile1_set = false;
    boolean profile2_set = false;
    boolean profile3_set = false;

    String recent_news = "\n" +
            /*
            "이것을 쓴 시점은 런처만 만든 시점임.\n" +
            "       2025/10/22 -yooncrow33-\n" +
            "\n" +
            "alpha 1.3!\n" +
            "   - 내가 똥같이 싼 코드 치우는중...\n" +
            "       2025/10/28 -yooncrow33-\n" +
            "\n" +
            "alpha 1.8!\n" +
            "   - 정신 나갈거 같음.\n" +
            "       2025/11/02 -yooncrow33-\n" +
            "\n" +
            "alpha 1.10!\n" +
            "   - 정신 나갈거 같음...\n" +
            "       2025/11/10 -yooncrow33-\n" +
            "\n" +
            "alpha 1.13!\n" +
            "   - 정신 나갈거 같음...\n" +
            "       2026/1/9 -yooncrow33-\n" +
            "\n" +
            "alpha 1.13.3!\n" +
            "   - 끝이 보인다...!\n" +
            "       2026/1/19 -yooncrow33-\n" +
            "\n" +
            "alpha 1.13.5!\n" +
            "   - 뭐였지?\n" +
            "       2026/1/21 -yooncrow33-\n" +

             */
            "\n";


    String patch_notes = "\n" +
            "dev-01 2/3\n" +
            "   - 프로젝트를 만듦.\n" +
            "\n" +
            "dev-01 2/3\n" +
            "   - 메뉴 그래픽을 만듦.\n" +
            "\n" +
            "dev-03 2/3\n" +
            "   - 옵션 그래픽을 만듦.\n" +
            "\n" +
            "dev-04 2/3\n" +
            "   - 이름을 바꿈. The Grid -> The Weekend\n" +
            "\n" +
            "dev-05 2/4\n" +
            "   - 세팅,메뉴를 왔다갔다 할수 있게 구현.\n" +
            "   - 이름을 바꿈. The Weekend -> The Stint\n" +
            "\n" +
            "dev-06 2/4\n" +
            "   - 새로운 런처 시스템 구현.\n" +
            "\n" +
            "dev-07 2/4\n" +
            "   - 세팅/메뉴/나가기 가능.\n" +
            "\n" +
            "dev-08 2/5\n" +
            "   - 콘솔 추가.\n" +
            "\n" +

            "\n";

    String manual_text = "\n" +
            "Hello! this is THe Grid : Sakhir Launcher\n" +
            "\n" +
            " 게임 스타트\n" +
            "   - 처음 실행 시,원하는 패스워드를 입력하고 'Create password' 버튼을 클릭하여 패스워드를 생성합니다.\n" +
            "   - Profile에서 세개의 프로필중 프로필을 선택합니다.\n" +
            "   - 패스워드를 입력하고 Start 버튼을 클릭하여 게임을 실행합니다\n" +
            "   - 그 프로필이 처음실행 된다면 프로필 런처에서 프로필을 설정합니다.\n" +
            "\n" +
            " 최근 소식 및 패치 노트\n" +
            "   - 'Recent News'를 클릭하여 최신 소식을 확인할 수 있습니다.\n" +
            "   - 'Patch Notes'를 클릭하여 패치노트를 확인할 수 있습니다.\n";
    /*

    패치노트의 기본 양식

                "alpha 0.0\n" +
            "   [버그 수정]\n" +
            "\n" +
            "   [사용자 편의]\n" +
            "\n" +
            "   [게임내용]\n" +
            "\n" +
            "   [최적화]\n" +
            "\n" +
            "   [신기능]\n" +
            "\n" +
     */

    public Launcher() {
        load();
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                password = reader.readLine();
                reader.close();
                setPass = true;
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null, "Password error!");
                exception.printStackTrace();
            }
        }

        Area.setText(manual_text);


        String[] profile = {"profile1", "profile2", "profile3"};
        JComboBox<String> versionBox = new JComboBox<>(profile);
        frame.add(versionBox);
        versionBox.setBounds(10, 430, 390, 40);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(main);
        frame.add(Start);
        frame.add(fux);
        frame.add(lable);
        frame.add(lable2);
        frame.add(p);
        frame.add(recentNews);
        frame.add(patchNotes);
        frame.add(manual);
        frame.add(TitleArea);

        main.setBounds(10, 330,390,70);
        main.setHorizontalAlignment(SwingConstants.CENTER);
        main.setFont(new Font("Arial", Font.BOLD, 20));
        Start.setFont(new Font("Arial", Font.PLAIN, 30));
        Start.setBounds(10, 470, 390, 70);
        fux.setBounds(10, 410, 390, 20);
        lable.setBounds(0, 40, 410,90);
        lable.setFont(new Font("Impact", Font.BOLD, 46));
        lable.setHorizontalAlignment(SwingConstants.CENTER);
        lable2.setBounds(0, 110, 410,70);
        lable2.setFont(new Font("Impact", Font.PLAIN, 26));//원래 36
        lable2.setHorizontalAlignment(SwingConstants.CENTER);
        TitleArea.setBounds(410,10,380,60);
        TitleArea.setFont(new Font("Impact", Font.BOLD, 48));
        TitleArea.setEnabled(false);
        p.setBounds(410,70,380,470);
        Area.setEditable(false);
        Area.setFont(new Font("Arial", Font.PLAIN, 14));
        Area.setLineWrap(true);
        recentNews.setBounds(410,540,126,10);
        patchNotes.setBounds(536,540,127,10);
        manual.setBounds(663,540,126,10);


        fux.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!setPass) {
                    password = main.getText().trim();
                    if (password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter the password you want to create!");
                    } else {
                        // 패스워드 생성 및 파일 저장
                        try {
                            setPass = true; // 상태 업데이트
                            fux.setEnabled(false); // 버튼 비활성화 (핵심 개선)
                            fux.setText("password has already been created."); // 문구 변경 (선택 사항)

                            PrintWriter writer = new PrintWriter(SAVE_FILE);
                            writer.println(password);
                            writer.close();

                            JOptionPane.showMessageDialog(null, "Your password has been created and the button is now disabled.");

                        } catch (FileNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "Error saving password file!");
                            throw new RuntimeException(ex);
                        }
                    }
                } else {
                    // 이 블록은 setPass=true일 때 실행되는데, 버튼이 비활성화되므로 실행될 일이 거의 없습니다.
                    // 하지만 혹시 모를 경우를 대비해 메시지는 유지합니다.
                    JOptionPane.showMessageDialog(null, "The password has already been created.");
                }
            }
        });

        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!setPass) {
                    return;
                }
                login = main.getText().trim();
                if (login.equals(password)) {

                    /*JOptionPane.showMessageDialog(null, "Run the Speed Click sc.25.8.4.mon");
                    frame.dispose();
                    sc.base.SplashScreen.showSplashThenLaunchGame();*/
                    //new IceDash();

                    // 1. 콤보 박스에서 선택된 String 값을 가져옵니다.
                    String selectedProfileName = (String) versionBox.getSelectedItem();

                    // 2. String을 int 값으로 변환하는 함수 또는 로직을 사용합니다.
                    int profileId = getProfileIdFromName(selectedProfileName);

                    //JOptionPane.showMessageDialog(null, "Run the IceDrop Lite Edition profile : " + selectedVersion  /*"을(를) 실행합니다."*/);
                    // 3. a02.sc.base.Main 클래스 생성자에 캡슐화된 int 값을 전달합니다.
                    frame.dispose();

                    if (!getCurrentProfileDataIsSet(profileId)) {
                        new ProfileStart(profileId,profile1_set,profile2_set,profile3_set);
                    } else {
                        LoadingScreen.showSplashThenLaunchGame(profileId);
                    }
                    //StartSplashScreen.showSplashThenLaunchGame(profileId,language);



                } else {
                    JOptionPane.showMessageDialog(null, "The password is wrong.");
                }
            }
        });

        recentNews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Area.setText(recent_news);
                TitleArea.setText("Recent News");
            }
        });

        patchNotes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Area.setText(patch_notes);
                TitleArea.setText("Patch Notes");
            }
        });

        manual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Area.setText(manual_text);
                TitleArea.setText("Manual");
            }
        });

        if (setPass) {
            fux.setEnabled(false);
            fux.setText("password has already been created."); // 문구 변경 (선택 사항)
        }

    }

    public boolean getCurrentProfileDataIsSet(int profileId) {
        return switch (profileId) {
            case 1 -> profile1_set;
            case 2 -> profile2_set;
            case 3 -> profile3_set;
            default -> false;
        };

    }

    public void load() {
        String saveDirPath = System.getProperty("user.home") + "/tss";
        File saveDir = new File(saveDirPath);
        if (!saveDir.exists()) {
            saveDir.mkdirs(); // tgs 폴더가 없으면 생성
        }
        String saveDirPath1 = System.getProperty("user.home") + "/tss/lang";
        File saveDir1 = new File(saveDirPath1);
        if (!saveDir1.exists()) {
            saveDir1.mkdirs();
            //save default lang files
        }
        String saveDirPath2 = System.getProperty("user.home") + "/tss/save";
        File saveDir2 = new File(saveDirPath2);
        if (!saveDir2.exists()) {
            saveDir2.mkdirs();
        }

        Properties p = new Properties();
        String homeDir = System.getProperty("user.home")+ File.separator + "tss" + File.separator + "save";
        File file = new File(homeDir, "TheStintSakhirProfileIsSet.properties");

        // [핵심] 파일이 없으면 기본값을 싹 다 false로 박은 파일을 일단 생성!
        if (!file.exists()) {
            putProfileDataIsSetFile();
        }

        String fullPath1 = homeDir + File.separator + "TheStintSakhirProfileIsSet.properties";

        String paths[] = {"empty", fullPath1};

        try (FileInputStream in = new FileInputStream(paths[1])) {
            p.load(in);
            profile1_set = Boolean.parseBoolean(p.getProperty("profile1_set", "false"));
            profile2_set = Boolean.parseBoolean(p.getProperty("profile2_set", "false"));
            profile3_set = Boolean.parseBoolean(p.getProperty("profile3_set", "false"));

        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Load failed");
        }
    }

    private int getProfileIdFromName(String name) {
        // 콤보박스 이름이 "profile1"이면 1, "profile2"이면 2를 반환
        return switch (name) { // Java 12+ switch expression 사용 시 간결
            case "profile1" -> 1;
            case "profile2" -> 2;
            case "profile3" -> 3;
            default -> -1; // 잘못된 값 처리
        };
    }

    public void putProfileDataIsSetFile() {
        Properties p = new Properties();

        p.setProperty("profile1_set", Boolean.toString(false));
        p.setProperty("profile2_set", Boolean.toString(false));
        p.setProperty("profile3_set", Boolean.toString(false));

        String homeDir = System.getProperty("user.home")+ File.separator + "tss" + File.separator + "save";

        String fullPath1 = homeDir + File.separator + "TheStintSakhirProfileIsSet.properties";
        String paths[] = {"empty", fullPath1};

        try (FileOutputStream out = new FileOutputStream(paths[1])) {
            p.store(out, "User Save Data - ID is not included");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "저장 실패: " + e.getMessage() + "\n경로: " + paths[1]);
        }
    }

    public static void main(String[] args) {
        new Launcher();
    }
}