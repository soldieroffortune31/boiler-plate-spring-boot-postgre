package com.example.springpostgres;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
// import java.util.Date;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.example.springpostgres.util.JwtUtil;

public class coba {

    // @Autowired
    // private static JwtUtil jwtUtil;

    public static void main(String[] args) {

        validasiToken();

    }

    static void test(){
        System.out.println(HttpStatus.BAD_REQUEST.value());
        LocalDateTime now = LocalDateTime.now();

        // Format sesuai dengan yyyy-MM-dd HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        // Cetak hasilnya
        System.out.println(formattedDateTime);
    }

    static void validasiToken(){

        JwtUtil jwtUtil = new JwtUtil();

        if(jwtUtil.validateToken("eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhbGllZiIsInBlbmdndW5hSWQiOjQsInVzZXJuYW1lIjoiYWxpZWYiLCJpYXQiOjE3Mzg0NjY1MzYsImV4cCI6MTczODQ3MDEzNn0.sJxBqEmKXvpgHXLba39YjrcAIeB7nIpk8MxyLjEFbLtG2w2CMIk8MwsV0ML-s32a")){
            System.out.println("berhasil");
        }else{
            System.out.println("gagal");
        };


    }
}
