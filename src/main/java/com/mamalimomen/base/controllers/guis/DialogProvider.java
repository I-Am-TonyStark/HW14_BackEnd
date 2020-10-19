package com.mamalimomen.base.controllers.guis;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public final class DialogProvider {
    private DialogProvider() {
    }

    public static synchronized void createAndShowTerminalMessage(String format, Object... messages) {
        System.out.format(format, messages);
    }

    public static synchronized void createAndShowTerminalError(String format, Object... messages) {
        System.err.format(format, messages);
    }

    public static synchronized <T> void createAndShowErrorDialog(T message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static synchronized <T> void createAndShowInformationDialog(T message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static synchronized <T> void createAndShowWarningDialog(T message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public static synchronized <T> String createAndShowInputDialog(T message, String title) {
        return JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
    }

    public static synchronized void showImage(String imagePath) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
