package com.lio.api.controller.account;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.entity.Account;
import com.lio.api.service.interfaces.AccountService;
import com.lio.api.model.dto.ApiResponse;
import com.lio.api.util.CustomResponse;
import static com.lio.api.model.constant.Messages.*;

import com.lio.api.controller.ResourceConfig;
import static com.lio.api.model.constant.Messages.INVALID_REQUEST;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
            Map<String,String> errorsMap = new HashMap<>();

            bindingResult.getFieldErrors()
                    .stream()
                    .forEach( error -> {
                        errorsMap.put( error.getField() , error.getDefaultMessage() );
                    });

            return CustomResponse.getErrorResponse( null , INVALID_REQUEST , errorsMap  );
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

}
