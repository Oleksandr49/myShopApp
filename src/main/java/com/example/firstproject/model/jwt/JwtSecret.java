package com.example.firstproject.model.jwt;

import javax.xml.bind.DatatypeConverter;

public class JwtSecret {

    private static final String secret = "Some_very_long_message_for_better_security";

    public static byte[] getEncryptedSecret(){
        String encryptedSecret = DatatypeConverter.printBase64Binary(secret.getBytes());
        return DatatypeConverter.parseBase64Binary(encryptedSecret);
    }
}
