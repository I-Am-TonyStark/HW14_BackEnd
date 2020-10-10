package com.mamalimomen.controllers.utilities;

import com.mamalimomen.base.controllers.menus.Menu;
import com.mamalimomen.controllers.menus.MainMenu;
import com.mamalimomen.controllers.menus.UserMenu;

public final class MenuFactory {
    private MenuFactory() {
    }

    public static synchronized <U extends User> Menu getMenu(U user) {
        if (user instanceof User){
            return new UserMenu<>(user);}
        else return new MainMenu<>();
    }
}