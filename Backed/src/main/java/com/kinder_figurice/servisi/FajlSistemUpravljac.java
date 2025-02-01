package com.kinder_figurice.servisi;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FajlSistemUpravljac {

    private static final String PUTANJA_DO_SLIKE_NA_SERVERU = "D:\\kinder_figurice\\kinder-front\\public\\publicslike";



    public String sacuvajSliku(MultipartFile file)
    {

        try
        {
            Path uploadPath = Paths.get(PUTANJA_DO_SLIKE_NA_SERVERU);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String imeFajla = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path putanjaFajla = uploadPath.resolve(imeFajla);
            Files.copy(file.getInputStream(), putanjaFajla, StandardCopyOption.REPLACE_EXISTING);
            return imeFajla;



        }
        catch (Exception e)
        {

            throw new RuntimeException("Greska pri cuvanju fajla", e);

        }



    }


    public byte[] ocitajFajl(String imeFajla)
    {

        try {

            Path putanjaFajla = Paths.get(PUTANJA_DO_SLIKE_NA_SERVERU).resolve(imeFajla);

            return Files.readAllBytes(putanjaFajla);



        }
        catch (Exception e)
        {
            throw new RuntimeException("Greska pri cuvanju fajla", e);
        }



    }



}
