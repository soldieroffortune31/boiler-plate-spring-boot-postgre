package com.example.springpostgres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.springpostgres.entity.Pengguna;
import com.example.springpostgres.model.LoginRequest;
import com.example.springpostgres.model.LoginResponse;
import com.example.springpostgres.repository.PenggunaRepository;
import com.example.springpostgres.util.DateFormatUtil;
// import com.example.springpostgres.util.DateFormatUtil;
import com.example.springpostgres.util.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private ValidationService validationService;
    
    public LoginResponse login(LoginRequest request){

        validationService.validate(request);
        
        Pengguna pengguna = penggunaRepository.findFirstByUsername(request.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username atau password salah"));
        System.out.println("username " + request.getUsername());
        
        if(!BCrypt.checkpw(request.getPassword(), pengguna.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username atau password salah");
        }


        String token = jwtUtil.createToken(pengguna.getPenggunaId(), pengguna.getUsername());
        String expiredAt= DateFormatUtil.formatDateTime(jwtUtil.extractExpiration(token));

        return LoginResponse.builder().token(token).expiredAt(expiredAt).build();

    }

}
