package com.mamalimomen.controllers.menus;

import com.mamalimomen.base.controllers.guis.DialogProvider;
import com.mamalimomen.base.controllers.menus.Menu;
import com.mamalimomen.base.controllers.utilities.SingletonScanner;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public abstract class AbstractMenu<U extends User> implements Menu {
    private final U user;
    private final String title;
    private final List<String> options;

    public AbstractMenu(String title, U user, String... options) {
        this.options = new ArrayList<>(List.of(options));
        this.title = title;
        this.user = user;
    }

    public U getUser() {
        return this.user;
    }

    @Override
    public int showMenu() {
        while (true) {
            DialogProvider.createAndShowTerminalMessage("%n====== %s ======%n", title.toUpperCase());
            for (int i = 1; i <= options.size(); i++) {
                DialogProvider.createAndShowTerminalMessage("%d. %s%n", i, options.get(i - 1));
            }
            DialogProvider.createAndShowTerminalMessage("%s", "Enter your choice (or other number for \"exit\"): ");
            try {
                return SingletonScanner.readInteger();
            } catch (InputMismatchException e) {
                DialogProvider.createAndShowTerminalMessage("%s%n", "Wrong format, enter an integer number please!");
                SingletonScanner.clearBuffer();
            }
        }
    }
}