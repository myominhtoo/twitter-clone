package com.lio.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.lio.api.configuration.CustomUserDetails;
import com.lio.api.exception.custom.Index;
import com.lio.api.model.entity.AccountFollowAccount;
import com.lio.api.repository.AccountFollowAccountRepository;
import com.lio.api.service.interfaces.JwtTokenService;
import com.lio.api.util.Generator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lio.api.model.dto.AccountFollowDTO;
import com.lio.api.model.entity.Account;
import com.lio.api.repository.AccountRepository;
import com.lio.api.service.interfaces.AccountService;
import org.springframework.stereotype.Service;

import static com.lio.api.model.constant.Index.*;
import static com.lio.api.model.constant.Messages.INVALID_REQUEST;

@Slf4j
@Service("accountService")
public class AccountServiceImpl implements AccountService , UserDetailsService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final AccountFollowAccountRepository accFollowAccRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    @Autowired
    public AccountServiceImpl( 
        AccountRepository accountRepository ,
        BCryptPasswordEncoder passwordEncoder ,
        AccountFollowAccountRepository accFollowAccRepository ,
        AuthenticationManager authenticationManager ,
        JwtTokenService jwtTokenService
    ){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.accFollowAccRepository = accFollowAccRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public UserDetails loadUserByUsername( String username )
            throws UsernameNotFoundException {
        Account savedAccount = this.accountRepository.findByEmail( username );
        if( savedAccount == null ){
            throw new UsernameNotFoundException("Invalid email or password!");
        }
        return new CustomUserDetails(savedAccount);
    }

    @Override
    public Account createAccount(Account account)
            throws Index.DuplicateAccountException {
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
    ) throws Index.InvalidRequestException {

        Optional<Account> savedAccOptional = this.accountRepository.findById(accountId);

        if( !accountId.equals(account.getId()) || !savedAccOptional.isPresent() ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
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
    public Account login(Account account)
            throws Index.InvalidRequestException {

        if( account.getEmail() == null || account.getPassword() == null ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }

        Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        account.getEmail(),
                        account.getPassword()
                )
        );
        return (Account) auth.getPrincipal();
    }

    @Override
    public void sendVerification(String email) {
        log.info("Sending email to "+email);
    }

    @Override
    public Boolean followAccount( AccountFollowDTO accountFollowDTO )
            throws Index.InvalidRequestException
    {
        if( accountFollowDTO.getFrom().getId() == null ||
                accountFollowDTO.getTo().getId() == null ||
                accountFollowDTO.getFrom().getId().equals(accountFollowDTO.getTo().getId())
        ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }

        Optional<Account> fromAccOptional = this.accountRepository
                .findById(accountFollowDTO.getFrom().getId());
        Optional<Account> toAccOptional = this.accountRepository
                .findById(accountFollowDTO.getTo().getId());

        if( !fromAccOptional.isPresent() ||
                !toAccOptional.isPresent() ||
                this.accFollowAccRepository.findByFromAccountIdAndToAccountId(
                        accountFollowDTO.getFrom().getId() ,
                        accountFollowDTO.getTo().getId()
                ) != null
        ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }

        AccountFollowAccount accFollowAcc = AccountFollowAccount.builder()
                .fromAccount(fromAccOptional.get())
                .toAccount(toAccOptional.get())
                .createdDate(LocalDateTime.now())
                .build();

        return this.accFollowAccRepository.save(accFollowAcc) != null;
    }

    @Override
    public Boolean unfollowAccount(AccountFollowDTO accountFollowDTO)
            throws Exception
    {

        if( accountFollowDTO.getFrom().getId() == null ||
                accountFollowDTO.getTo().getId() == null ||
            accountFollowDTO.getFrom().getId().equals(accountFollowDTO.getTo().getId())
        ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }

        AccountFollowAccount savedData = this.accFollowAccRepository
                .findByFromAccountIdAndToAccountId(
                        accountFollowDTO.getFrom().getId() ,
                        accountFollowDTO.getTo().getId()
                );

        if( savedData != null ){
            try{
                this.accFollowAccRepository.deleteById(savedData.getId());
                return true;
            }catch( Exception e ){
                throw new Exception("Error");
            }
        }
        return false;
    }

    @Override
    public HttpHeaders getAuthTokenHeader(Account account) {
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.add(
                HttpHeaders.AUTHORIZATION ,
                this.jwtTokenService.generateToken( account.getId() , account.getEmail() )
        );
        return httpHeader;
    }

    @Override
    public Account getAccount(String accountId) throws Index.InvalidRequestException {
        Optional<Account> accountOptional = this.accountRepository.findById( accountId );
        if( !accountOptional.isPresent() ){
            throw  new Index.InvalidRequestException( INVALID_REQUEST );
        }
        return accountOptional.get();
    }

}
