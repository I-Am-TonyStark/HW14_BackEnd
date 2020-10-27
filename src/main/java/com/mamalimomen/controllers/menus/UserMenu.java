package com.mamalimomen.controllers.menus;

import com.mamalimomen.domains.Account;

public class UserMenu<A extends Account> extends AbstractMenu<A> {
    public UserMenu(A account) {
        super(account.getUser().getFullName() + "'s menu",
                account,
                "Change your password",
                "See|Change your information",
                "See|Change|Delete your Posts",
                "Insert new Post",
                "See|Like|Comment new Posts by Like count",
                "See|Like|Comment new Posts by Following",
                "Search|See|Follow an Account",
                "unFollow an Account",
                "Delete your Account"
        );
    }

    @Override
    public void routerMenu() {
        while (!getAccount().getDeleted()) {
            switch (showMenu()) {
                case 1:
                    Menus.changeYourPassword(getAccount());
                    break;
                case 2:
                    Menus.seeChangeYourInformation(getAccount());
                    break;
                case 3:
                    Menus.seeChangeDeleteYourPosts(getAccount());
                    break;
                case 4:
                    Menus.insertNewPost(getAccount());
                    break;
                case 5:
                    Menus.seeLikeCommentNewPostsByLikeCount(getAccount());
                    break;
                case 6:
                    Menus.seeLikeCommentNewPostsByFollowing(getAccount());
                    break;
                case 7:
                    Menus.searchSeeFollowAnAccount(getAccount());
                    break;
                case 8:
                    Menus.unFollowAnAccount(getAccount());
                    break;
                case 9:
                    Menus.deleteYourAccount(getAccount());
                    break;
                default:
                    return;
            }
        }
    }
}
