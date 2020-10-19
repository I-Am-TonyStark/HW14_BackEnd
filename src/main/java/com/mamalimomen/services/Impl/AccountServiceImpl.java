package com.mamalimomen.services.Impl;

import com.mamalimomen.base.controllers.guis.DialogProvider;
import com.mamalimomen.base.controllers.utilities.InValidDataException;
import com.mamalimomen.base.controllers.utilities.SecurityManager;
import com.mamalimomen.base.controllers.utilities.SingletonScanner;
import com.mamalimomen.base.services.impl.BaseServiceImpl;
import com.mamalimomen.controllers.utilities.AppManager;
import com.mamalimomen.controllers.utilities.Services;
import com.mamalimomen.domains.Account;
import com.mamalimomen.domains.Post;
import com.mamalimomen.domains.User;

import com.mamalimomen.repositories.AccountRepository;
import com.mamalimomen.repositories.impl.AccountRepositoryImpl;
import com.mamalimomen.services.AccountService;
import com.mamalimomen.services.PostService;

import javax.persistence.EntityManager;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

public class AccountServiceImpl extends BaseServiceImpl<Long, Account, AccountRepository> implements AccountService {
    public AccountServiceImpl(EntityManager em) {
        super(new AccountRepositoryImpl(em));
    }

    @Override
    public Optional<Account> createNewAccount() {
        User user = new User();
        while (true) {
            try {
                DialogProvider.createAndShowTerminalMessage("%s", "First Name: ");
                String firstName = SingletonScanner.readLine();
                if (firstName.equalsIgnoreCase("esc")) {
                    break;
                }
                user.setFirstName(firstName);

                DialogProvider.createAndShowTerminalMessage("%s", "Last Name: ");
                user.setLastName(SingletonScanner.readLine());

                DialogProvider.createAndShowTerminalMessage("%s", "About You: ");
                user.setAboutMe(SingletonScanner.readParagraph());

                DialogProvider.createAndShowTerminalMessage("%s", "Password: ");
                user.setPassword(SingletonScanner.readLine());

                DialogProvider.createAndShowTerminalMessage("%s", "Username: ");
                String username = SingletonScanner.readLine();
                user.setUsername(username);
                if (repository.findOneAccountByUsername(username).isPresent()) {
                    DialogProvider.createAndShowTerminalMessage("%s%n", "This Username has taken already!");
                    continue;
                }

                Account account = new Account();
                account.setUser(user);

                DialogProvider.createAndShowTerminalMessage("%s%n", repository.saveOne(account));
                return Optional.of(account);
            } catch (InValidDataException e) {
                DialogProvider.createAndShowTerminalMessage("%s %s%s%n%n", "Wrong entered data format for", e.getMessage(), "!");
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Account> retrieveExistActiveAccount() {
        DialogProvider.createAndShowTerminalMessage("%s", "Username: ");
        String username = SingletonScanner.readLine();
        return repository.findOneActiveAccountByUsername(username);
    }

    @Override
    public String updateExistActiveAccountPassword(Account account) {
        while (true) {
            try {
                DialogProvider.createAndShowTerminalMessage("%s", "Old Password: ");
                String oldPassword = SingletonScanner.readLine();
                if (oldPassword.equalsIgnoreCase("esc")) {
                    break;
                } else if (!SecurityManager.checkPasswordHash(oldPassword, account.getUser().getPassword())) {
                    DialogProvider.createAndShowTerminalMessage("%s%n%n", "Wrong Password!");
                    continue;
                }
                DialogProvider.createAndShowTerminalMessage("%s", "New Password: ");
                String newPassword = SingletonScanner.readLine();
                if (newPassword.equalsIgnoreCase("esc")) {
                    break;
                }
                account.getUser().setPassword(newPassword);

                if (repository.updateOne(account)) {
                    return "account's password changes successfully!";
                } else {
                    return "can not change account's password!";
                }
            } catch (InValidDataException e) {
                DialogProvider.createAndShowTerminalMessage("%s %s%s%n%n", "Wrong entered data format for", e.getMessage(), "!");
            }
        }
        return "You Cancelled this operation!";
    }

    @Override
    public String updateExistActiveAccountInformation(Account account) {
        User copy = new User();
        User original = account.getUser();

        outer:
        while (true) {
            try {
                DialogProvider.createAndShowTerminalMessage("%s (old = %s): ", "New First Name", original.getFirstName());
                String newFirstName = SingletonScanner.readLine();
                if (newFirstName.equalsIgnoreCase("esc")) {
                    break;
                }
                if (!newFirstName.equalsIgnoreCase("pass")) {
                    copy.setFirstName(newFirstName);
                }

                DialogProvider.createAndShowTerminalMessage("%s (old = %s): ", "New Last Name", original.getLastName());
                String newLastName = SingletonScanner.readLine();
                if (newLastName.equalsIgnoreCase("esc")) {
                    break;
                }
                if (!newLastName.equalsIgnoreCase("pass")) {
                    copy.setLastName(newLastName);
                }

                DialogProvider.createAndShowTerminalMessage("%s (old = %s): ", "New About You", original.getAboutMe());
                String newAboutMe = SingletonScanner.readParagraph();
                if (newAboutMe.equalsIgnoreCase("esc")) {
                    break;
                }
                if (!newAboutMe.equalsIgnoreCase("pass")) {
                    copy.setAboutMe(newAboutMe);
                }

                while (true) {
                    DialogProvider.createAndShowTerminalMessage("%s (old = %s): ", "New Username", original.getUsername());
                    String newUsername = SingletonScanner.readLine();
                    if (newUsername.equalsIgnoreCase("esc")) {
                        break outer;
                    }
                    copy.setUsername(newUsername);
                    if (repository.findOneAccountByUsername(newUsername).isPresent()) {
                        DialogProvider.createAndShowTerminalMessage("%s%n", "This Username has taken already!");
                    } else {
                        break;
                    }
                }

                original.updateUserInformation(copy);

                if (repository.updateOne(account)) {
                    return "account's information changes successfully!";
                } else {
                    return "can not change account's information!";
                }
            } catch (InValidDataException e) {
                DialogProvider.createAndShowTerminalMessage("%s %s%s%n%n", "Wrong entered data format for", e.getMessage(), "!");
            }
        }
        return "You Cancelled this operation!";
    }

    @Override
    public String addExistActiveAccountAPost(Account account) {
        PostService postService = AppManager.getService(Services.POST_SERVICE);
        Optional<Post> oPost = postService.createNewPost();
        if (oPost.isPresent()) {
            account.addPost(oPost.get());
            if (repository.updateOne(account)) {
                return "create new post and update your account successfully!";
            } else {
                return "can not create new post or update your account!";
            }
        } else
            return "You Cancelled this operation!";
    }

    @Override
    public String removeExistActiveAccountAPost(Account account) {
        List<Post> posts = account.getPosts();
        while (true) {
            try {
                for (int i = 1; i <= posts.size(); i++) {
                    DialogProvider.createAndShowTerminalMessage("%d. %s%n", i, posts.get(i - 1));
                }
                DialogProvider.createAndShowTerminalMessage("%s", "Enter your choice (or other number for \"exit\"): ");
                int choice = SingletonScanner.readInteger();
                posts.remove(posts.get(choice - 1));
                account.setPosts(posts);
                if (repository.updateOne(account)) {
                    return "delete selected post and update your account successfully!";
                } else {
                    return "can not delete selected post or update your account!";
                }
            } catch (InputMismatchException e) {
                DialogProvider.createAndShowTerminalMessage("%s%n", "Wrong format, enter an integer number please!");
                SingletonScanner.clearBuffer();
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return "You Cancelled this operation!";
    }

    @Override
    public String addExistActiveAccountAFollowing(Account followerAccount, Account followingAccount) {
        followerAccount.addFollowing(followingAccount);
        if (repository.updateOne(followingAccount)) {
            return "follow selected account and update your account successfully!";
        } else {
            return "can not follow selected account or update your account!";
        }
    }

    @Override
    public String removeExistActiveAccountAFollowing(Account followerAccount) {
        List<Account> followings = followerAccount.getFollowings();
        while (true) {
            try {
                for (int i = 1; i <= followings.size(); i++) {
                    DialogProvider.createAndShowTerminalMessage("%d. %s%n", i, followings.get(i - 1));
                }
                DialogProvider.createAndShowTerminalMessage("%s", "Enter your choice (or other number for \"exit\"): ");
                int choice = SingletonScanner.readInteger();
                followings.remove(followings.get(choice - 1));
                followerAccount.setFollowings(followings);
                if (repository.updateOne(followerAccount)) {
                    return "unFollow selected account and update your account successfully!";
                } else {
                    return "can not unFollow selected account or update your account!";
                }
            } catch (InputMismatchException e) {
                DialogProvider.createAndShowTerminalMessage("%s%n", "Wrong format, enter an integer number please!");
                SingletonScanner.clearBuffer();
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return "You Cancelled this operation!";
    }

    @Override
    public String deleteExistActiveAccount(Account account) {
        DialogProvider.createAndShowTerminalMessage("%s", "Do you want to delete your account? ");
        String answer = SingletonScanner.readLine();

        if (answer.equals("YES")) {
            account.setDeleted(true);
            for (Post post : account.getPosts()) {
                post.setDeleted(true);
            }
            if (repository.updateOne(account)) {
                return "your account was deleted successfully!";
            } else {
                return "can not delete your account!";
            }
        }
        return "You Cancelled this operation!";
    }
}
