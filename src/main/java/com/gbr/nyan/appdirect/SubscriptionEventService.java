package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.appdirect.entity.SubscriptionResponse;
import com.gbr.nyan.domain.Account;
import com.gbr.nyan.domain.AccountRepository;
import com.gbr.nyan.domain.User;
import com.gbr.nyan.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gbr.nyan.appdirect.entity.Flag.STATELESS;
import static com.gbr.nyan.appdirect.entity.SubscriptionResponse.failure;
import static com.gbr.nyan.appdirect.entity.SubscriptionResponse.success;

@Service
public class SubscriptionEventService {
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionEventService.class);

    private final AccountExtractor accountExtractor;
    private final AccountRepository accountRepository;
    private final EventUserExtractor userExtractor;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionEventService(AccountExtractor accountExtractor, AccountRepository accountRepository, EventUserExtractor userExtractor, UserRepository userRepository) {
        this.accountExtractor = accountExtractor;
        this.accountRepository = accountRepository;
        this.userExtractor = userExtractor;
        this.userRepository = userRepository;
    }

    public SubscriptionResponse create(SubscriptionEvent createEvent) {
        if (createEvent.getFlag() == STATELESS) {
            return failure().withErrorCode("UNKNOWN_ERROR");
        }

        Account newAccount = accountExtractor.fromEvent(createEvent);
        newAccount = accountRepository.save(newAccount);
        logger.info("CREATE ORDER - saved new account #" + newAccount.getId());

        User newUser = userExtractor.fromCreationEvent(createEvent);
        newUser.setAccount(newAccount);
        newUser = userRepository.save(newUser);
        logger.info("CREATE ORDER - saved new user #" + newUser.getEmail());

        return success().withAccountIdentifier(newAccount.getId());
    }

    public SubscriptionResponse change(SubscriptionEvent changeEvent) {
        if (changeEvent.getFlag() == STATELESS) {
            return failure().withErrorCode("UNKNOWN_ERROR");
        }

        Account existingAccountWithNewEdition = accountExtractor.whenAccountExists(changeEvent);
        existingAccountWithNewEdition = accountRepository.save(existingAccountWithNewEdition);
        logger.info("CHANGE ORDER - updated account #" + existingAccountWithNewEdition.getId() + " - new edition:" + existingAccountWithNewEdition.getEdition());

        return success();
    }

    public SubscriptionResponse cancel(SubscriptionEvent cancelEvent) {
        if (cancelEvent.getFlag() == STATELESS) {
            return failure().withErrorCode("UNKNOWN_ERROR");
        }

        Account accountToDelete = accountExtractor.whenAccountExists(cancelEvent);

        Iterable<User> allUsersInThisAccount = userRepository.findAllByAccount(accountToDelete);
        allUsersInThisAccount.forEach(u -> u.setAccount(null));
        userRepository.save(allUsersInThisAccount);

        accountRepository.delete(accountToDelete.getId());
        logger.info("CANCEL ORDER - deleted account #" + accountToDelete.getId());

        return success();
    }
}
