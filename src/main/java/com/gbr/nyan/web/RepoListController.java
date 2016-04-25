package com.gbr.nyan.web;

import com.gbr.nyan.domain.Account;
import com.gbr.nyan.domain.AccountRepository;
import com.gbr.nyan.domain.User;
import com.gbr.nyan.domain.UserRepository;
import com.gbr.nyan.support.HandlebarsRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.gbr.nyan.domain.Account.Edition.BASIC;
import static com.gbr.nyan.support.Iterables.toList;
import static com.gbr.nyan.web.support.Users.currentUser;
import static com.gbr.nyan.web.support.Users.userIsLoggedIn;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
class RepoListController {
    private final HandlebarsRenderer viewRenderer;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public RepoListController(HandlebarsRenderer viewRenderer, UserRepository userRepository, AccountRepository accountRepository) {
        this.viewRenderer = viewRenderer;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @ResponseBody
    @RequestMapping(value = "/repo-list", method = GET, produces = "text/html")
    public String listRepos(@RequestParam Optional<String> addFakeUser, Optional<String> upgradeMe) throws IOException {
        if (addFakeUser.isPresent()) {
            createNewUser();
        }
        if (upgradeMe.isPresent()) {
            upgradeMe();
        }

        Map<String, Object> viewContext = new HashMap<>();
        viewContext.put("page-title", "A cat named Nyan - Content of the repositories");
        viewContext.put("rendering-repo-list", true);
        viewContext.put("user-is-logged-in", userIsLoggedIn());
        viewContext.put("user-can-be-upgraded", userIsLoggedIn() && (currentUser().getAccount() == null));
        viewContext.put("users", toList(userRepository.findAll()));

        return viewRenderer.render("/templates/repo-list", viewContext);
    }

    private void upgradeMe() {
        User currentUser = currentUser();
        if (currentUser == null || currentUser.getAccount() != null) {
            return;
        }

        currentUser.setAccount(createNewAccount());
        userRepository.save(currentUser);
    }

    private void createNewUser() {
        Account newAccount = createNewAccount();

        User user = new User();
        user.setEmail("some" + newAccount.getId() + "s@email.com");
        user.setAccount(newAccount);

        userRepository.save(user);
    }

    private Account createNewAccount() {
        Account newAccount = new Account();
        newAccount.setEdition(BASIC);
        newAccount = accountRepository.save(newAccount);

        return newAccount;
    }
}
