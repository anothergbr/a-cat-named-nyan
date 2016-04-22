package com.gbr.nyan.appdirect;

import com.gbr.nyan.appdirect.entity.SubscriptionEvent;
import com.gbr.nyan.appdirect.entity.SubscriptionResponse;
import com.gbr.nyan.domain.Account;
import com.gbr.nyan.domain.AccountRepository;
import com.gbr.nyan.domain.User;
import com.gbr.nyan.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gbr.nyan.appdirect.entity.SubscriptionEvent.Flag.STATELESS;
import static com.gbr.nyan.appdirect.entity.SubscriptionResponse.failure;
import static com.gbr.nyan.appdirect.entity.SubscriptionResponse.success;

@Service
public class SubscriptionEventService {
    private final AccountExtractor accountExtractor;
    private final AccountRepository accountRepository;
    private final UserExtractor userExtractor;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionEventService(AccountExtractor accountExtractor, AccountRepository accountRepository, UserExtractor userExtractor, UserRepository userRepository) {
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
        accountRepository.save(newAccount);

        User newUser = userExtractor.fromEvent(createEvent);
        newUser.setAccount(newAccount);
        userRepository.save(newUser);

        return success().withAccountIdentifier(newAccount.getId());
    }
}
