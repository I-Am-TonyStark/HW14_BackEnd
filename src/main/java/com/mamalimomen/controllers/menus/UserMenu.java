package com.mamalimomen.controllers.menus;

public class UserMenu<U extends User> extends AbstractMenu<U> {
    public UserMenu(U user) {
        super(user.getFullName() + "'s menu",
                user,
                "Change your password",
                "Change your username",
                "Change your information",
                "See your Posts",
                "Insert new Post",
                "See new Posts",
                "See an Account",
                "Delete your Account"
        );
    }

    @Override
    public void routerMenu() {
        while (true) {
            switch (showMenu()) {
                case 1:
                    Menus.changeYourPassword(getUser());
                    break;
                case 2:
                    Menus.changeYourUsername(getUser());
                    break;
                case 3:
                    Menus.changeYourInformation(getUser());
                    break;
                case 4:
                    Menus.seeYourPosts(getUser());
                    break;
                case 5:
                    Menus.insertNewPost(getUser());
                    break;
                case 6:
                    Menus.seeNewPosts(getUser());
                    break;
                case 7:
                    Menus.seeAnAccount(getUser());
                    break;
                case 8:
                    Menus.deleteYourAccount(getUser());
                    break;
                default:
                    return;
            }
        }
    }

}
