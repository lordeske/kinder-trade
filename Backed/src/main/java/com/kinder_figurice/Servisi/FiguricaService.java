package com.kinder_figurice.Servisi;

import com.kinder_figurice.DataTransferModeli.FiguricaDTO;
import com.kinder_figurice.ModelMapper.Mapper;
import com.kinder_figurice.Modeli.Figurica;
import com.kinder_figurice.Modeli.Korisnik;
import com.kinder_figurice.Repo.FiguricaRepo;
import com.kinder_figurice.Repo.KorisnikRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FiguricaService {


    @Autowired
    private static FiguricaRepo figuricaRepo;
    @Autowired
    private static KorisnikRepo korisnikRepo;


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

    public List<FiguricaDTO> vratiFiguriceZaKorisnika(Long idKorisnika)
    {
        List<Figurica> dobijeneFigurice = figuricaRepo.findByIdKorisnika(idKorisnika);

        List<FiguricaDTO> figuriceDTO = new ArrayList<>();


        for(Figurica figurica : dobijeneFigurice )
        {
            figuriceDTO.add(Mapper.toDTO(figurica));
        }

        return  figuriceDTO;



    }












}
