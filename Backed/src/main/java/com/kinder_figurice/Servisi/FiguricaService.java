package com.kinder_figurice.Servisi;

import com.kinder_figurice.DataTransferModeli.FiguricaDTO;
import com.kinder_figurice.ModelMapper.Mapper;
import com.kinder_figurice.Modeli.Figurica;
import com.kinder_figurice.Modeli.Korisnik;
import com.kinder_figurice.Repo.FiguricaRepo;
import com.kinder_figurice.Repo.KorisnikRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FiguricaService {


    @Autowired
    private  FiguricaRepo figuricaRepo;
    @Autowired
    private  KorisnikRepo korisnikRepo;


    public Figurica kreirajFiguricu(FiguricaDTO figuricaDTO, Long idKorisnika)
    {

        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);

        return korisnik.map(value -> figuricaRepo.save(Mapper.toEntity(figuricaDTO, value))).orElse(null);


    }


    public Optional<FiguricaDTO> pronadjiFiguricu(Long idFigurice)
    {
        Optional<Figurica> figurica =  figuricaRepo.findById(idFigurice);
        return figurica.map(value -> Optional.of(Mapper.toDTO(value))).orElse(null);

    }

    public void obrisiFiguricu(Long idFigurice)
    {
        figuricaRepo.deleteById(idFigurice);
    }

    public List<FiguricaDTO> vratiFiguriceZaKorisnika(Long idKorisnika) {

        Optional<Korisnik> korisnik = korisnikRepo.findById(idKorisnika);

        if (korisnik.isPresent())
        {
            List<Figurica> figurice = figuricaRepo.findByKorisnik(korisnik.get());

            return figurice.stream()
                    .map(Mapper::toDTO)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    public FiguricaDTO azurirajFiguricu(Long idFigurice, FiguricaDTO figuricaDTO) throws Exception {
            Optional<Figurica> figurica = figuricaRepo.findById(idFigurice);

            if(figurica.isPresent())
            {
                Figurica dobijenaFigurica = figurica.get();
                dobijenaFigurica.setNazivFigurice(figuricaDTO.getNazivFigurice());
                dobijenaFigurica.setSerijaFigurice(figuricaDTO.getSerijaFigurice());
                dobijenaFigurica.setGodinaIzdanja(figuricaDTO.getGodinaIzdanja());
                dobijenaFigurica.setOpisFigurice(figuricaDTO.getOpisFigurice());
                dobijenaFigurica.setSlikaFigurice(figuricaDTO.getSlikaFigurice());
                figuricaRepo.save(dobijenaFigurica);

                return Mapper.toDTO(dobijenaFigurica);

            }
            else
            {
                throw  new Exception("Nije pronadjena figurcia");
            }



    }















}
