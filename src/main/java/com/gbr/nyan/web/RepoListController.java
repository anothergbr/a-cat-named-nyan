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
    public String listRepos(@RequestParam Optional<String> addFakeUser) throws IOException {
        if (addFakeUser.isPresent()) {
            createNewUser();
        }

        Map<String, Object> viewContext = new HashMap<>();
        viewContext.put("page-title", "A cat named Nyan - Content of the repositories");
        viewContext.put("rendering-repo-list", true);
        viewContext.put("users", toList(userRepository.findAll()));

        return viewRenderer.render("/templates/repo-list", viewContext);
    }

    private void createNewUser() {
        Account newAccount = new Account();
        newAccount.setEdition(BASIC);
        accountRepository.save(newAccount);

        User user = new User("some" + newAccount.getId() + "s@email.com");
        user.setAccount(newAccount);
        userRepository.save(user);
    }
}
