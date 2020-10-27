package com.mamalimomen.controllers.menus;

import com.mamalimomen.base.controllers.guis.DialogProvider;
import com.mamalimomen.base.controllers.utilities.SecurityManager;
import com.mamalimomen.base.controllers.utilities.SingletonScanner;
import com.mamalimomen.controllers.utilities.AppManager;
import com.mamalimomen.controllers.utilities.MenuFactory;
import com.mamalimomen.controllers.utilities.Services;
import com.mamalimomen.domains.Account;
import com.mamalimomen.domains.Post;
import com.mamalimomen.services.AccountService;
import com.mamalimomen.services.PostService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

public final class Menus {
    private Menus() {
    }

    static void login() {
        AccountService accountService = AppManager.getService(Services.ACCOUNT_SERVICE);

        while (true) {
            DialogProvider.createAndShowTerminalMessage("%n====== %s ======%n", "LOGIN");

            Optional<Account> oAccount = accountService.retrieveExistActiveAccount();

            if (oAccount.isEmpty()) {
                DialogProvider.createAndShowTerminalMessage("%s%n%s", "There is not any Account with this username!", "Do you want try again (y/n)? ");
                String answer = SingletonScanner.readLine();
                if (!answer.equalsIgnoreCase("y")) {
                    break;
                }
                continue;
            }
            Account account = oAccount.get();

            DialogProvider.createAndShowTerminalMessage("%s", "Password: ");
            String password = SingletonScanner.readLine();
            if (SecurityManager.checkPasswordHash(password, account.getUser().getPassword())) {
                MenuFactory.getMenu(account).routerMenu();
                break;
            } else {
                DialogProvider.createAndShowTerminalMessage("%s%n", "Wrong Password!");
            }
        }
    }

    static void signUp() {
        AccountService accountService = AppManager.getService(Services.ACCOUNT_SERVICE);

        while (true) {
            DialogProvider.createAndShowTerminalMessage("%n====== %s ======%n", "SIGN UP");

            Optional<Account> oAccount = accountService.createNewAccount();
            if (oAccount.isPresent()) {
                DialogProvider.createAndShowTerminalMessage("%s%n", "Your Account was created successfully!");
                MenuFactory.getMenu(oAccount.get()).routerMenu();
                break;
            } else {
                DialogProvider.createAndShowTerminalMessage("%s%n%s", "Can not create your account!", "Do you want try again (y/n)? ");
                String answer = SingletonScanner.readLine();
                if (!answer.equalsIgnoreCase("y"))
                    break;
            }
        }
    }

    static <A extends Account> void changeYourPassword(A account) {
        DialogProvider.createAndShowTerminalMessage("%n====== %s ======%n", "CHANGE YOUR PASSWORD");
        AccountService accountService = AppManager.getService(Services.ACCOUNT_SERVICE);
        DialogProvider.createAndShowTerminalMessage("%s%n", accountService.updateExistActiveAccountPassword(account));
    }

    static <A extends Account> void seeChangeYourInformation(A account) {
        DialogProvider.createAndShowTerminalMessage("%n====== %s ======%n", "SEE|CHANGE YOUR INFORMATION");
        AccountService accountService = AppManager.getService(Services.ACCOUNT_SERVICE);
        DialogProvider.createAndShowTerminalMessage("%s%n", accountService.updateExistActiveAccountInformation(account));
    }

    static <A extends Account> void seeChangeDeleteYourPosts(A account) {
        PostService postService = AppManager.getService(Services.POST_SERVICE);
        AccountService accountService = AppManager.getService(Services.ACCOUNT_SERVICE);

        while (true) {
            List<Post> posts = account.getPosts();

            if (posts.isEmpty()) {
                DialogProvider.createAndShowTerminalMessage("%s%n", "You have no post yet!");
                return;
            }

            DialogProvider.createAndShowTerminalMessage("%n====== %s ======%n", "SEE|CHANGE|DELETE YOUR POSTS");
            DialogProvider.createAndShowTerminalMessage("%s", "Which action (D or C)? ");
            String choose = SingletonScanner.readLine();
            if (choose.equals("D")) {
                DialogProvider.createAndShowTerminalMessage("%s%n", accountService.removeExistActiveAccountAPost(account));
            } else if (choose.equals("C")) {
                try {
                    for (int i = 1; i <= posts.size(); i++) {
                        DialogProvider.createAndShowTerminalMessage("%d. %s%n", i, posts.get(i - 1));
                    }
                    DialogProvider.createAndShowTerminalMessage("%s", "Enter your choice (or other number for \"exit\"): ");
                    int choice = SingletonScanner.readInteger();
                    DialogProvider.createAndShowTerminalMessage("%s%n", postService.updateExistPost(posts.get(choice - 1)));
                } catch (InputMismatchException e) {
                    DialogProvider.createAndShowTerminalMessage("%s%n", "Wrong format, enter an integer number please!");
                    SingletonScanner.clearBuffer();
                } catch (IndexOutOfBoundsException ignored) {
                }
            } else {
                return;
            }
        }
    }

    static <A extends Account> void insertNewPost(A account) {
        DialogProvider.createAndShowTerminalMessage("%n====== %s ======%n", "INSERT NEW POST");
        AccountService accountService = AppManager.getService(Services.ACCOUNT_SERVICE);
        DialogProvider.createAndShowTerminalMessage("%s%n", accountService.addExistActiveAccountAPost(account));
    }

    static <A extends Account> void seeLikeCommentNewPostsByLikeCount(A account) {
        PostService postService = AppManager.getService(Services.POST_SERVICE);

        while (true) {
            List<Post> posts = postService.retrieveAllExistPostsOrderByLike();

            if (posts.isEmpty()) {
                DialogProvider.createAndShowTerminalMessage("%s%n", "There is not any post yet!");
                return;
            }

            DialogProvider.createAndShowTerminalMessage("%n====== %s ======%n", "SEE|LIKE|COMMENT NEW POSTS BY LIKE COUNT");
            try {
                for (int i = 1; i <= posts.size(); i++) {
                    DialogProvider.createAndShowTerminalMessage("%d. %s%n", i, posts.get(i - 1));
                }
                DialogProvider.createAndShowTerminalMessage("%s", "Enter your choice (or other number for \"exit\"): ");
                int choice = SingletonScanner.readInteger();
                Post post = posts.get(choice - 1);

                DialogProvider.createAndShowTerminalMessage("%s%n", post);
                DialogProvider.createAndShowInformationDialog(post.printLikes(), "Likes");
                DialogProvider.createAndShowInformationDialog(post.printComments(), "Comments");

                DialogProvider.createAndShowTerminalMessage("%s", "Which action (C or L)? ");
                String choose = SingletonScanner.readLine();
                if (choose.equals("C")) {
                    DialogProvider.createAndShowTerminalMessage("%s%n", postService.addExistPostAComment(post, account));
                } else if (choose.equals("L")) {
                    DialogProvider.createAndShowTerminalMessage("%s%n", postService.addExistPostALike(post, account));
                }
            } catch (InputMismatchException e) {
                DialogProvider.createAndShowTerminalMessage("%s%n", "Wrong format, enter an integer number please!");
                SingletonScanner.clearBuffer();
            } catch (IndexOutOfBoundsException ignored) {
                break;
            }
        }
    }

    static <A extends Account> void seeLikeCommentNewPostsByFollowing(A account) {
        PostService postService = AppManager.getService(Services.POST_SERVICE);
        List<Account> accounts = account.getFollowings();

        if (accounts.isEmpty()) {
            DialogProvider.createAndShowTerminalMessage("%s%n", "You do not follow any Account yet!");
            return;
        }

        while (true) {
            DialogProvider.createAndShowTerminalMessage("%n====== %s ======%n", "SEE|LIKE|COMMENT NEW POSTS BY FOLLOWING");
            try {
                for (int i = 1; i <= accounts.size(); i++) {
                    DialogProvider.createAndShowTerminalMessage("%d. %s%n", i, accounts.get(i - 1));
                }
                DialogProvider.createAndShowTerminalMessage("%s", "Enter your choice (or other number for \"exit\"): ");
                int choice = SingletonScanner.readInteger();
                List<Post> posts = accounts.get(choice - 1).getPosts();

                if (posts.isEmpty()) {
                    DialogProvider.createAndShowTerminalMessage("%s%n", "This Account has not any post yet!");
                    continue;
                }

                while (true) {
                    DialogProvider.createAndShowTerminalMessage("%n====== %s ======%n", "SEE|LIKE|COMMENT NEW POSTS BY FOLLOWING");
                    try {
                        for (int i = 1; i <= posts.size(); i++) {
                            DialogProvider.createAndShowTerminalMessage("%d. %s%n", i, posts.get(i - 1));
                        }
                        DialogProvider.createAndShowTerminalMessage("%s", "Enter your choice (or other number for \"exit\"): ");
                        choice = SingletonScanner.readInteger();
                        Post post = posts.get(choice - 1);

                        DialogProvider.createAndShowTerminalMessage("%s%n", post);
                        DialogProvider.createAndShowInformationDialog(post.printLikes(), "Likes");
                        DialogProvider.createAndShowInformationDialog(post.printComments(), "Comments");

                        DialogProvider.createAndShowTerminalMessage("%s", "Which action (C or L)? ");
                        String choose = SingletonScanner.readLine();
                        if (choose.equals("C")) {
                            DialogProvider.createAndShowTerminalMessage("%s%n", postService.addExistPostAComment(post, account));
                        } else if (choose.equals("L")) {
                            DialogProvider.createAndShowTerminalMessage("%s%n", postService.addExistPostALike(post, account));
                        }
                    } catch (InputMismatchException e) {
                        DialogProvider.createAndShowTerminalMessage("%s%n", "Wrong format, enter an integer number please!");
                        SingletonScanner.clearBuffer();
                    } catch (IndexOutOfBoundsException ignored) {
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                DialogProvider.createAndShowTerminalMessage("%s%n", "Wrong format, enter an integer number please!");
                SingletonScanner.clearBuffer();
            } catch (IndexOutOfBoundsException ignored) {
                break;
            }
        }
    }

    static <A extends Account> void searchSeeFollowAnAccount(A account) {
        AccountService accountService = AppManager.getService(Services.ACCOUNT_SERVICE);

        while (true) {
            DialogProvider.createAndShowTerminalMessage("%n====== %s ======%n", "SEARCH|SEE|FOLLOW AN ACCOUNT");
            Optional<Account> oAccount = accountService.retrieveExistActiveAccount();

            if (oAccount.isEmpty()) {
                DialogProvider.createAndShowTerminalMessage("%s%n%s", "There is not any Account with this username!", "Do you want try again (y/n)? ");
                String answer = SingletonScanner.readLine();
                if (!answer.equalsIgnoreCase("y")) {
                    break;
                }
                continue;
            }
            Account searchedAccount = oAccount.get();

            DialogProvider.createAndShowTerminalMessage("%s%n", searchedAccount);

            DialogProvider.createAndShowTerminalMessage("%s", "Do you want follow it? (y/n)? ");
            String choose = SingletonScanner.readLine();
            if (choose.equalsIgnoreCase("y")) {
                if (searchedAccount.equals(account)) {
                    DialogProvider.createAndShowTerminalMessage("%s%n", "You can not follow yourself!");
                } else {
                    DialogProvider.createAndShowTerminalMessage("%s%n", accountService.addExistActiveAccountAFollowing(account, searchedAccount));
                    break;
                }
            }

            DialogProvider.createAndShowTerminalMessage("%s", "Try for another Account (y/n)? ");
            String answer = SingletonScanner.readLine();
            if (!answer.equalsIgnoreCase("y")) {
                break;
            }
        }
    }

    static <A extends Account> void unFollowAnAccount(A account) {
        AccountService accountService = AppManager.getService(Services.ACCOUNT_SERVICE);

        while (true) {
            DialogProvider.createAndShowTerminalMessage("%n====== %s ======%n", "UnFOLLOW AN ACCOUNT");

            DialogProvider.createAndShowTerminalMessage("%s%n", accountService.removeExistActiveAccountAFollowing(account));

            DialogProvider.createAndShowTerminalMessage("%s", "Try for another Account (y/n)? ");
            String answer = SingletonScanner.readLine();
            if (!answer.equalsIgnoreCase("y")) {
                break;
            }
        }
    }

    static <A extends Account> void deleteYourAccount(A account) {
        AccountService accountService = AppManager.getService(Services.ACCOUNT_SERVICE);
        DialogProvider.createAndShowTerminalMessage("%n====== %s ======%n", "DELETE YOUR ACCOUNT");
        DialogProvider.createAndShowTerminalMessage("%s%n", accountService.deleteExistActiveAccount(account));
    }
}
