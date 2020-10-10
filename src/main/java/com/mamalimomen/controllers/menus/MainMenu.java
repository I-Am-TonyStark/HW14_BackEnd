package com.mamalimomen.controllers.menus;

import com.mamalimomen.domains.User;

public class MainMenu<U extends User> extends AbstractMenu<U> {
    public MainMenu() {
        super("main menu",
                null,
                "Login",
                "SignUp"
        );
    }

    @Override
    public void routerMenu() {
        while (true) {
            switch (showMenu()) {
                case 1:
                    Menus.login();
                    break;
                case 2:
                    Menus.signUp();
                    break;
                default:
                    return;
            }
        }
    }
}
