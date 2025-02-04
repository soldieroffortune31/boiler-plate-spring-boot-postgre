package com.example.springpostgres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.springpostgres.entity.Pengguna;
import com.example.springpostgres.model.CreatePenggunaRequest;
import com.example.springpostgres.repository.PenggunaRepository;

@Service
public class PenggunaService {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void resigter(CreatePenggunaRequest request){

        validationService.validate(request);

        if(penggunaRepository.existsByUsernameOrEmail(request.getUsername(), request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username atau Email sudah digunakan");
        }

        Pengguna pengguna = new Pengguna();
        pengguna.setEmail(request.getEmail());
        pengguna.setUsername(request.getUsername());
        pengguna.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        penggunaRepository.save(pengguna);

    }

}
