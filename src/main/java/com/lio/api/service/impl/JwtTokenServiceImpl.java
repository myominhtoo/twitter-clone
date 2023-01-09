package com.lio.api.service.impl;

import com.lio.api.service.interfaces.JwtTokenService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("jwtTokenService")
public class JwtTokenServiceImpl implements JwtTokenService {

    @Override
    public String generateToken(String accountId, String email) {
        return null;
    }

    @Override
    public Boolean isValid(String token) {
        return null;
    }

    @Override
    public Map<String, String> getClaimsFromToken(String token) {
        return null;
    }
}
