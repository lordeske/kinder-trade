package com.kinder_figurice.Servisi;


import com.kinder_figurice.DataTransferModeli.KorisnikDTO;
import com.kinder_figurice.DataTransferModeli.KorisnikRegistracijaDTO;
import com.kinder_figurice.DataTransferModeli.LoginDTO;
import com.kinder_figurice.ModelMapper.Mapper;
import com.kinder_figurice.Modeli.Figurica;
import com.kinder_figurice.Modeli.Korisnik;
import com.kinder_figurice.Repo.FiguricaRepo;
import com.kinder_figurice.Repo.KorisnikRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class KorisnikService {


    private final KorisnikRepo korisnikRepo;
    private final PasswordEncoder passwordEncoder;
    private final FiguricaRepo figuricaRepo;


    @Autowired
    public KorisnikService(KorisnikRepo korisnikRepo, PasswordEncoder passwordEncoder, FiguricaRepo figuricaRepo) {
        this.korisnikRepo = korisnikRepo;
        this.passwordEncoder = passwordEncoder;
        this.figuricaRepo = figuricaRepo;
    }


    public Korisnik kreirajKorisnika(KorisnikRegistracijaDTO korisnikRegistracijaDTO)
    {
        /// Autentifikacije ce kasnije biti ubacena, naknadno

        Korisnik korisnik = Mapper.toEntity(korisnikRegistracijaDTO);
        korisnik.setLozinka(passwordEncoder.encode(korisnikRegistracijaDTO.getLozinka()));
        return korisnikRepo.save(korisnik);
    }


    public Boolean loginKorisnika(LoginDTO loginDTO)
    {
        Korisnik korisnik = korisnikRepo.findByEmail(loginDTO.getEmail());

        if (korisnik == null)
        {
            return  false;
        }

        return passwordEncoder.matches(loginDTO.getLozinka(), korisnik.getLozinka());


    }


    public KorisnikDTO prikaziProfilKorisnika(Long idKorisnika) {
        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);

        return korisnik.map(Mapper::toDTO).orElse(null);


    }


    public KorisnikDTO azurirajProfil(Long idKorisnika, KorisnikDTO korisnikDTO)
    {
        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);

        if(korisnik.isPresent())
        {
            Korisnik korisnik1 = korisnik.get();

            korisnik1.setIme(korisnikDTO.getIme());
            korisnik1.setPrezime(korisnikDTO.getPrezime());
            korisnik1.setEmail(korisnikDTO.getEmail());

            Korisnik sacuvaniKorisnik = korisnikRepo.save(korisnik1);
            return Mapper.toDTO(sacuvaniKorisnik);
        }
        else
        {
            return null;
        }

    }


    public  KorisnikDTO azurirajLozinku(Long idKorisnika, String novaLozinka)
    {

        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);

        if(korisnik.isPresent())
        {
            Korisnik izmenjeniKorisnik = korisnik.get();

            izmenjeniKorisnik.setLozinka(passwordEncoder.encode(novaLozinka));
            return  Mapper.toDTO(korisnikRepo.save(izmenjeniKorisnik));
        }
        else
        {
            return null;
            /// posle implementacija ovoga!
        }


    }


    public void promijeniSliku(Long idKorisnika, String novaSlika)
    {
        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);

        if(korisnik.isPresent()) {

            Korisnik dobijeniKorisnik = korisnik.get();
            dobijeniKorisnik.setSlikaKorisnika(novaSlika);
            korisnikRepo.save(dobijeniKorisnik);

        }

    }


    public void  promijeniStatusKorisnika(Long idKorisnika)
    {
        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);

        if(korisnik.isPresent())
        {
            Korisnik dobijeniKorisnik = korisnik.get();
            dobijeniKorisnik.setAktivanKorisnik(!dobijeniKorisnik.getAktivanKorisnik());
            korisnikRepo.save(dobijeniKorisnik);

        }
    }


    public Korisnik dodajFiguricuUWishList(Long idKorisnika, Long idFigurice)
    {

        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
        Optional<Figurica> figurica = figuricaRepo.findById(idFigurice);

        if(!korisnik.isPresent())
        {
            throw new EntityNotFoundException("Korisnik nije pronađen.");
        }

        if (!figurica.isPresent()) {
            throw new EntityNotFoundException("Figurica nije pronađena.");
        }

        Korisnik korisnik1 = korisnik.get();
        Figurica figurica1 = figurica.get();


            if(!korisnik1.getWishList().contains(figurica1))
            {
                korisnik1.getWishList().add(figurica1);
                return korisnikRepo.save(korisnik1);
            }
            else
            {
                throw new IllegalArgumentException("Figurica je već u wishlisti");
            }






    }



    public Korisnik izbrisiFiguricuIzWishList(Long idKorisnika, Long idFigurice)
    {

        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);
        Optional<Figurica> figurica = figuricaRepo.findById(idFigurice);


        if(!korisnik.isPresent())
        {
            throw new EntityNotFoundException("Korisnik nije pronađen.");
        }

        if (!figurica.isPresent()) {
            throw new EntityNotFoundException("Figurica nije pronađena.");
        }


            Korisnik korisnik1 = korisnik.get();
            Figurica figurica1 = figurica.get();

            if(korisnik1.getWishList().contains(figurica1))
            {
                korisnik1.getWishList().remove(figurica1);
                return korisnikRepo.save(korisnik1);
            }
            else
            {
                throw new IllegalArgumentException("Figurica nije pronađena u wishlisti korisnika");
            }








    }







}
