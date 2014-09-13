package com.banisaeid.folderdiff;

import com.banisaeid.folderdiff.ui.MainFrame;

import javax.swing.*;
import java.io.IOException;

public class RunUI {
    public static void main(String[] args) {
        // TODO command line options without UI
        new RunUI().start();
    }

    private void start() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    showFrame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showFrame() throws IOException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        JFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
