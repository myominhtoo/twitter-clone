package com.lio.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.lio.api.exception.custom.Index;
import com.lio.api.util.Generator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lio.api.model.dto.AccountFollowDTO;
import com.lio.api.model.entity.Account;
import com.lio.api.repository.AcccountRepository;
import com.lio.api.service.interfaces.AccountService;
import org.springframework.stereotype.Service;

import static com.lio.api.model.constant.Index.*;
import static com.lio.api.model.constant.Messages.INVALID_REQUEST;

@Slf4j
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private AcccountRepository accountRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl( 
        AcccountRepository accountRepository , 
        BCryptPasswordEncoder passwordEncoder
    ){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Account createAccount(Account account) throws Index.DuplicateAccountException {
        account.setId(Generator.generateId("account"));
        if(this.isDuplicateAccount(account)){
            throw new Index.DuplicateAccountException("This user already exits!");
        }
        account.setPassword(this.passwordEncoder.encode(account.getPassword()));
        account.setCreatedDate(LocalDateTime.now());
        account.setVerificationCode(Generator.generateVerificationCode(VERIFICATION_CODE_BOUND));
        account.setHasVerifiedCode(false);

        this.sendVerification(account.getEmail());

        return this.accountRepository.save(account);
    }

    @Override
    public Account editAccount(
            String accountId ,
            Account account
    ) throws Index.InvalidRequestExeption {

        Optional<Account> savedAccOptional = this.accountRepository.findById(accountId);

        if( !accountId.equals(account.getId()) || !savedAccOptional.isPresent() ){
            throw new Index.InvalidRequestExeption( INVALID_REQUEST );
        }

        Account savedAccObject = savedAccOptional.get();
        savedAccObject.setAge(account.getAge());
        savedAccObject.setEmail(account.getEmail());
        savedAccObject.setFirstName(account.getFirstName());
        savedAccObject.setDeviceId(account.getDeviceId());
        savedAccObject.setLastName(account.getLastName());
        savedAccObject.setBios(account.getBios());
        savedAccObject.setUpdatedDate(LocalDateTime.now());
        savedAccObject.setDob(account.getDob());
        savedAccObject.setPhone(account.getPhone());

        return this.accountRepository.save(savedAccObject);

    }

    @Override
    public Boolean followAccount(AccountFollowDTO accountFollowDTO) {
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
       return this.accountRepository.findAll();
    }

    @Override
    public Boolean isDuplicateAccount(Account account) {
        Account foundWithEmail = this.accountRepository.findByEmail(account.getEmail());
        Optional<Account> foundWithId = account.getId() == null
                                        ? Optional.empty()
                                        : this.accountRepository.findById(account.getId());

        return ( foundWithEmail != null  || foundWithId.isPresent()  );
    }

    @Override
    public Boolean verifyAccount(
            String accountId ,
            String email,
            Integer verificationCode
    ) {
       Account savedAccount = this.accountRepository.findByIdAndEmail( accountId , email );
       if(savedAccount == null ) return false;

       /*
        if equal code , will update account to hasVerify
        */
       if( !savedAccount.getHasVerifiedCode() &&  savedAccount.getVerificationCode().equals(verificationCode) ){
           savedAccount.setHasVerifiedCode(true);
           return this.accountRepository.save(savedAccount) == null ? false : true;
       }
       return false;
    }

    @Override
    public Account login(Account account) {
        return null;
    }

    @Override
    public void sendVerification(String email) {
        log.info("Sending email to "+email);
    }

    @Override
    public Boolean unfollowAccount(AccountFollowDTO accountFollowDTO) {
        return null;
    }

}
