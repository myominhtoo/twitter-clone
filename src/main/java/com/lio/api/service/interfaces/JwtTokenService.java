package com.lio.api.service.interfaces;

import java.util.Map;

public interface JwtTokenService {

    String generateToken( String accountId , String email );

    Boolean isValid( String token );

    Map<String,String> getClaimsFromToken( String token );

}
