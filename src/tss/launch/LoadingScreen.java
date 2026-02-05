package tss.launch;

import tss.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoadingScreen {
    public static void showSplashThenLaunchGame(int profileId) {
        JWindow splash = new JWindow();
        splash.getContentPane().setBackground(Color.BLACK);
        splash.getContentPane().setLayout(new BoxLayout(splash.getContentPane(), BoxLayout.Y_AXIS));

        // 1. 로고 및 텍스트 설정
        JLabel logo = new JLabel("The Stint : Sakhir", SwingConstants.CENTER);
        logo.setFont(new Font("맑은 고딕", Font.BOLD, 72));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel powered = new JLabel("Powered by Scope", SwingConstants.CENTER);
        powered.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        powered.setForeground(Color.WHITE);

        // 2. JProgressBar 설정 (오늘의 주인공)
// 2. JProgressBar 설정
        JProgressBar progressBar = new JProgressBar(0, 100);

// [중요 1] 시스템 테마 무시하고 기본 UI로 강제 설정 (색상 반영을 위해)
        progressBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI() {
            protected Color getSelectionBackground() { return Color.WHITE; } // 미진행 글자색
            protected Color getSelectionForeground() { return Color.BLACK; } // 진행된 글자색
        });

        progressBar.setForeground(new Color(255, 200, 30)); // 로딩바 (빨강)
        progressBar.setBackground(new Color(255, 255, 255)); // 배경 (어두운 회색)
        progressBar.setBorderPainted(false);

// [중요 2] 크기 수정 (가로를 길게, 세로를 얇게!)
        progressBar.setPreferredSize(new Dimension(600, 15));
        progressBar.setMaximumSize(new Dimension(600, 15));
        progressBar.setMinimumSize(new Dimension(600, 15));

        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Consolas", Font.BOLD, 12));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);


        // 3. 레이아웃 조립
        splash.getContentPane().add(Box.createVerticalGlue());
        splash.getContentPane().add(logo);
        splash.getContentPane().add(Box.createRigidArea(new Dimension(0, 40))); // 간격
        splash.getContentPane().add(progressBar);
        splash.getContentPane().add(Box.createVerticalGlue());

        splash.setBounds(0, 0, 720, 300);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);

        // 4. 진행도 업데이트 및 페이드 아웃 타이머
        Timer timer = new Timer(3, null); // 30ms 마다 실행
        timer.addActionListener(new ActionListener() {
            double progress = 0;
            double target = 100.0;
            double k = 0.01; // 이 숫자가 작을수록 더 부드럽고 느리게 멈춤

            @Override
            public void actionPerformed(ActionEvent e) {
                if (target - progress < 0.1) { // 일정 수치 이하로 좁혀지면 강제 종료
                    progress = 100;
                    timer.stop();
                    // 메인 실행
                }
                progress += (target - progress) * k;
                progressBar.setValue((int)progress);

                if (progress >= 100) {
                    timer.stop();
                    splash.dispose();
                    // 드디어 본선 경기(Main) 진출!
                    new Main("The Stint : Sakhir", profileId);
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        showSplashThenLaunchGame(1);
    }
}