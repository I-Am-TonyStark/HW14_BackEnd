package com.mamalimomen.controllers.utilities;

import com.mamalimomen.base.controllers.menus.Menu;
import com.mamalimomen.controllers.menus.MainMenu;
import com.mamalimomen.controllers.menus.UserMenu;
import com.mamalimomen.domains.Account;

public final class MenuFactory {
    private MenuFactory() {
    }

    public static synchronized <A extends Account> Menu getMenu(A account) {
        if (account instanceof Account) {
            return new UserMenu<>(account);
        } else return new MainMenu<>();
    }
}