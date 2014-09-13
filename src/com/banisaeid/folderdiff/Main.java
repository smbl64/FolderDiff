package com.banisaeid.folderdiff;

import com.banisaeid.folderdiff.ui.GuiMain;
import com.banisaeid.folderdiff.ui.MainFrame;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0){
            new CommandLineProcessor().process(args);
        } else {
            new GuiMain().start();
        }
    }
}
