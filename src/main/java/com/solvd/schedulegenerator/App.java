package com.solvd.schedulegenerator;

import com.solvd.schedulegenerator.menu.IMenu;
import com.solvd.schedulegenerator.menu.impl.ConsoleMenu;

public class App {
    public static void main(String[] args) {
        IMenu menu = new ConsoleMenu();
        int choice = 0;
        while (true) {
            menu.displayMenu();
            choice = menu.getUserChoice();
            menu.performUserChoice(choice);
        }
    }
}
