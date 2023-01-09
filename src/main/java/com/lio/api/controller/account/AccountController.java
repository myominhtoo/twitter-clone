package com.lio.api.controller.account;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.dto.AccountFollowDTO;
import com.lio.api.model.entity.Account;
import com.lio.api.service.interfaces.AccountService;
import com.lio.api.model.dto.ApiResponse;
import com.lio.api.util.AppUtil;
import com.lio.api.util.CustomResponse;
import static com.lio.api.model.constant.Messages.*;

import com.lio.api.controller.ResourceConfig;
import static com.lio.api.model.constant.Messages.INVALID_REQUEST;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping( value = "${api.current.version}" )
public class AccountController extends ResourceConfig {

    private AccountService accountService;

    @Autowired
    public AccountController( AccountService accountService ){
        this.accountService = accountService;
    }

    /*
      for creating account
      route => /api/v1/accounts (POST)->body(account)
     */
    @PostMapping( value = "${registerAccount}" )
    public ResponseEntity<ApiResponse<Object>> postRegisterNewAccount(
            @Valid @RequestBody Account account ,
            BindingResult bindingResult
    ) throws Index.DuplicateAccountException {
        if( bindingResult.hasErrors() ){
            return CustomResponse.getErrorResponse(
                    null ,
                    INVALID_REQUEST ,
                    AppUtil.getErrorsMapFromBindingResults(bindingResult)
            );
        }
        Account createdAccount = this.accountService.createAccount(account);
        return CustomResponse.getResponse( createdAccount , SUCCESS_CREATED_ACCOUNT );
    }

    /*
     for verifying account with email
     after click verified link, will request this route to set as verified account
     route => /api/v1/verify-email (PUT)->queryParams(
          email => account's email
          id => account's id
          code => verification code
         )
     */
    @GetMapping( value = "${verifyEmail}" )
    public ResponseEntity<ApiResponse<Object>> getVerifyAccountEmail(
            @RequestParam( value = "accountId" , required = true ) String accountId,
            @RequestParam( value = "email" , required = true ) String email,
            @RequestParam( value = "verificationCode" , required = true ) Integer verificationCode
    ){
        Boolean verifyStatus = this.accountService.verifyAccount(
                accountId , email , verificationCode
        );
        if( !verifyStatus ){
            Map<String,String> errorMap = new HashMap<>();
            errorMap.put( "message" , INVALID_REQUEST );
            return CustomResponse.getErrorResponse( null , INVALID_REQUEST , errorMap );
        }
        return CustomResponse.getResponse( null ,null );
    }

    /*
     for getting all accounts
     later, this will be updated with search algos
     route => /api/v1/accounts (GET)
     */
    @GetMapping( value = "${getAccounts}" )
    public ResponseEntity<ApiResponse<Object>> getAllAccounts(){
        return CustomResponse.getResponse(
                this.accountService.getAllAccounts() ,
                REQUEST_SUCCESS
        );
    }

    /*
     for updating profile
     will need confirmPassword to complete action
     route => /api/v1/accounts/{accountId} (PUT)->body(account)
     must same accountId with account.id
     */
    @PutMapping( value = "${editAccount}" )
    public ResponseEntity<ApiResponse<Object>> putEditAccountProfile(
            @PathVariable("accountId") String accountId ,
            @Valid @RequestBody Account editedAccount ,
            BindingResult bindingResult
    ) throws Index.InvalidRequestException {
        if( bindingResult.hasErrors() ){
           return CustomResponse.getErrorResponse(
                   null ,
                   INVALID_REQUEST ,
                   AppUtil.getErrorsMapFromBindingResults(bindingResult)
           );
        }

        Account updatedAcc = this.accountService.editAccount(
                accountId ,
                editedAccount
        );

        return CustomResponse.getResponse( updatedAcc , SUCCESS_DONE );
    }

    /*
      for login acount
      will get jwt token if valid
      route => /api/v1/login (POST)->body(account)
     */
    @PostMapping( value = "${loginAccount}" )
    public ResponseEntity<ApiResponse<Object>> postLoginAccount( @RequestBody Account account )
            throws Index.InvalidRequestException {
        Account validAccount = this.accountService.login( account );

        /*
         to generate jwt and add to headers
         */
        HttpHeaders httpHeaders = new HttpHeaders();

        return CustomResponse.getResponse(
                validAccount ,
                httpHeaders ,
                REQUEST_SUCCESS
        );
    }

    /*
     for following account
     won't be able to follow for your account self
     route => /api/v1/follow-account (POST)->body(accountFollowDTO)
     */
    @PostMapping( value = "${followAccount}" )
    public ResponseEntity<ApiResponse<Object>> postFollowAccount( @RequestBody AccountFollowDTO accountFollowDTO )
            throws Index.InvalidRequestException {
        Boolean followStatus = this.accountService.followAccount( accountFollowDTO );

        return followStatus
                ? CustomResponse.getResponse( null , SUCCESS_DONE )
                : CustomResponse.getErrorResponse( null , REQUEST_FAILED , null );
    }

    /*
     for un-following account
     won't be able to unfollow account without following to that account
     route => /api/v1/unfollow-account (POST)->body(accountFollowDTO)
     */
    @PostMapping( value = "${unfollowAccount}" )
    public ResponseEntity<ApiResponse<Object>> postUnfollowAccount( @RequestBody AccountFollowDTO accountFollowDTO ) throws Exception {
         Boolean unfollowStatus = this.accountService.unfollowAccount( accountFollowDTO );

         return unfollowStatus
                 ? CustomResponse.getResponse( null , SUCCESS_DONE )
                 : CustomResponse.getErrorResponse( null , REQUEST_FAILED , null );
    }

}
