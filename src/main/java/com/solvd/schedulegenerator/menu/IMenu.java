package com.solvd.schedulegenerator.menu;

public interface IMenu {
    void displayMenu();

    int getUserChoice();

    void performUserChoice(int choice);
}