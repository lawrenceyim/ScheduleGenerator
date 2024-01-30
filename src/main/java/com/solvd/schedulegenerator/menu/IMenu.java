package com.solvd.schedulegenerator.menu;

public interface IMenu <T> {
    void displayMenu();

    T getUserChoice();

    void performUserChoice(T choice);
}