package tss.launch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ProfileStart {
    final JFrame frame;

    TextField firstNameField = new TextField("");
    TextField lastNameField = new TextField("");
    TextField driverNameField = new TextField("");
    TextField nationalityField = new TextField("");

    String callSign;
    String firstName;
    String lastName;
    String driverNumber;
    String nationality;

    JLabel firstNameLabel = new JLabel("First Name:");
    JLabel lastNameLabel = new JLabel("Last Name:");
    JLabel driverNameLabel = new JLabel("Driver Number:");
    JLabel nationalityLabel = new JLabel("Nationality:");

    JButton saveButton = new JButton("Go!");

    final int profileId;
    final boolean profile1_set, profile2_set, profile3_set;

    public ProfileStart(int ProfileId, boolean profile1_set, boolean profile2_set, boolean profile3_set) {
        profileId = ProfileId;
        this.profile1_set = profile1_set;
        this.profile2_set = profile2_set;
        this.profile3_set = profile3_set;

        frame = new JFrame("Set Profile " + ProfileId + " Launch");
        frame.setSize(400, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(firstNameField);
        frame.add(lastNameField);
        frame.add(driverNameField);
        frame.add(nationalityField);
        frame.add(firstNameLabel);
        frame.add(lastNameLabel);
        frame.add(driverNameLabel);
        frame.add(nationalityLabel);
        frame.add(saveButton);

        firstNameLabel.setBounds(10,0,200,10);
        firstNameField.setBounds(10,10,200,20);
        lastNameLabel.setBounds(10,30,200,10);
        lastNameField.setBounds(10,40,200,20);
        driverNameLabel.setBounds(10,60,200,10);
        driverNameField.setBounds(10,70,200,20);
        nationalityLabel.setBounds(10,90,200,10);
        nationalityField.setBounds(10,100,200,20);
        saveButton.setBounds(220,5,170,115);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProfileData();
                //boolean p1 = (profileId == 1) ? true : profile1_set;
                //boolean p2 = (profileId == 2) ? true : profile2_set;
                //boolean p3 = (profileId == 3) ? true : profile3_set;
                String profileSet[];
                switch (profileId) {
                    case 1:
                        profileSet = new String[]{ "empty", Boolean.toString(true), Boolean.toString(profile2_set), Boolean.toString(profile3_set) };
                        break;
                    case 2:
                        profileSet = new String[]{ "empty", Boolean.toString(profile1_set), Boolean.toString(true), Boolean.toString(profile3_set) };
                        break;
                    case 3:
                        profileSet = new String[]{ "empty", Boolean.toString(profile1_set), Boolean.toString(profile2_set), Boolean.toString(true) };
                        break;
                    default:
                        profileSet = new String[]{ "empty", Boolean.toString(profile1_set), Boolean.toString(profile2_set), Boolean.toString(profile3_set) };
                }
                saveProfileDataIsSet(Boolean.parseBoolean(profileSet[1]), Boolean.parseBoolean(profileSet[2]), Boolean.parseBoolean(profileSet[3]));
            }
        });
    }

    public void saveProfileDataIsSet(boolean profile1_set, boolean profile2_set, boolean profile3_set) {
        Properties p = new Properties();

        p.setProperty("profile1_set", Boolean.toString(profile1_set));
        p.setProperty("profile2_set", Boolean.toString(profile2_set));
        p.setProperty("profile3_set", Boolean.toString(profile3_set));

        String homeDir = System.getProperty("user.home")+ File.separator + "tss" + File.separator + "save";

        String fullPath1 = homeDir + File.separator + "TheStintSakhirProfileIsSet.properties";
        String paths[] = {"empty", fullPath1};

        try (FileOutputStream out = new FileOutputStream(paths[1])) {
            p.store(out, "User Save Data - ID is not included");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "저장 실패: " + e.getMessage() + "\n경로: " + paths[1]);
        }
    }

    public void saveProfileData() {
        if (isEmpty(firstNameField) || isEmpty(lastNameField) || isEmpty(driverNameField) || isEmpty(nationalityField)) {
            JOptionPane.showMessageDialog(null, "모든 데이터를 채워주세요.");
            return;
        }
        firstName = firstNameField.getText().trim();
        lastName = lastNameField.getText().trim();
        driverNumber = driverNameField.getText().trim();
        nationality = nationalityField.getText().trim();
        callSign = lastName.substring(0, 3).toUpperCase();

        Properties p = new Properties();

        p.setProperty("firstName", String.valueOf(firstName));
        p.setProperty("lastName", String.valueOf(lastName));
        p.setProperty("driverNumber", String.valueOf(driverNumber));
        p.setProperty("nationality", String.valueOf(nationality));
        p.setProperty("callSign", String.valueOf(callSign));

        String homeDir = System.getProperty("user.home")+ File.separator + "tss" + File.separator + "save";

        String fullPath1 = homeDir + File.separator + "TheStintSakhirSaveProfile1.properties";
        String fullPath2 = homeDir + File.separator + "TheStintSakhirSaveProfile2.properties";
        String fullPath3 = homeDir + File.separator + "TheStintSakhirSaveProfile3.properties";
        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};

        try (FileOutputStream out = new FileOutputStream(paths[profileId])) {

            p.store(out, "User Save Data - ID is not included");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "저장 실패: " + e.getMessage() + "\n경로: " + paths[profileId]);
        }

        frame.dispose();
        LoadingScreen.showSplashThenLaunchGame(profileId);
    }

    public boolean isEmpty(TextField t) {
        return t.getText().trim().isEmpty();
    }

    public static void main(String[] args) {
        new ProfileStart(1, false, false, false);
    }
}
