package com.kinder_figurice.servisi;

import com.kinder_figurice.dto.TradeDto.TrgovinaDto;
import com.kinder_figurice.modeli.*;
import com.kinder_figurice.repo.FiguricaRepo;
import com.kinder_figurice.repo.KorisnikRepo;
import com.kinder_figurice.repo.TrgovinaFiguriceRepo;
import com.kinder_figurice.repo.TrgovinaRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrgovinaServis {



    @Autowired
    private TrgovinaRepo trgovinaRepo;

    @Autowired
    private KorisnikRepo korisnikRepo;

    @Autowired
    private FiguricaRepo figuricaRepo;

    @Autowired
    private TrgovinaFiguriceRepo trgovinaFiguriceRepo;


    public Trgovina kreirajTrgovinu(TrgovinaDto trgovinaDTO)
    {


        Korisnik posiljalac = korisnikRepo.findByKorisnickoIme(trgovinaDTO.getPosiljalac())
                .orElseThrow(()-> new EntityNotFoundException("Nije pronadjen posiljalac"));


        Korisnik primalac = korisnikRepo.findByKorisnickoIme(trgovinaDTO.getPrimalac())
                .orElseThrow(()-> new EntityNotFoundException("Nije pronadjen primalac"));




        boolean svePonudjeneValidne = trgovinaDTO.getPonudjeneFigurice().stream()
                .allMatch(figuricaId -> figuricaRepo.existByIdAndKorisnik(figuricaId, posiljalac));

        if(!svePonudjeneValidne)
        {
            throw new IllegalArgumentException("Neke od figurica ne prripadaju posiljaocu");

        }



        boolean sveZatrazeneValidne = trgovinaDTO.getTrazeneFigurice().stream()
                .allMatch(figuricaId -> figuricaRepo.existByIdAndKorisnik(figuricaId, primalac));

        if(!sveZatrazeneValidne)
        {
            throw new IllegalArgumentException("Neke od figurica ne prripadaju primaocu");

        }



        Trgovina trgovina = new Trgovina();
        trgovina.setPosiljalac(posiljalac);
        trgovina.setPrimalac(primalac);
        trgovina.setStatus(StatusTrgovine.PENDING);

        trgovinaRepo.save(trgovina);



        List<TrgovinaFigurice> ponudjene = trgovinaDTO.getPonudjeneFigurice().stream()
                .map(figuricaID -> new TrgovinaFigurice(trgovina, figuricaRepo.findById(figuricaID)
                        .orElseThrow(() -> new RuntimeException("Figurica nije pronađena!")), true))
                .toList();


        List<TrgovinaFigurice> trazene = trgovinaDTO.getTrazeneFigurice().stream()
                .map(figuraId -> new TrgovinaFigurice(trgovina, figuricaRepo.findById(figuraId)
                        .orElseThrow(() -> new RuntimeException("Figurica nije pronađena!")), false))
                .toList();


        trgovina.getFigurice().addAll(ponudjene);
        trgovina.getFigurice().addAll(trazene);

        return trgovinaRepo.save(trgovina);
    }

    @Transactional
    public Trgovina promeniStatusTrgovine(Long trgovinaId, StatusTrgovine noviStatus) {
        Trgovina trgovina = trgovinaRepo.findById(trgovinaId)
                .orElseThrow(() -> new RuntimeException("Trgovina nije pronađena!"));

        trgovina.setStatus(noviStatus);
        return trgovinaRepo.save(trgovina);
    }





    public Trgovina prihvatiTrgovinu(Long trgovinaID)
    {

        String korisnickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        Trgovina trgovina = trgovinaRepo.findById(trgovinaID).orElseThrow(()->
                new EntityNotFoundException("Trgovina nije pronadjena"));


        if(!trgovina.getPrimalac().getKorisnickoIme().equals(korisnickoImeIzTokena))
        {
            throw new SecurityException("Nisi ovlascen da prihvatis trgovinu");
        }


        for(TrgovinaFigurice trgovinaFigurice : trgovina.getFigurice())
        {

            if(trgovinaFigurice.getTip() == TipTrgovine.PONUDJENA)
            {

                if(!trgovinaFigurice.getFigurica().getKorisnik().equals(trgovina.getPosiljalac()))
                {
                    throw new IllegalArgumentException("Posiljalac nije vlasnik figurice");
                }

            }
            else /// ovde ce da bude kontra TipTrgovine.TRAZENA
            {


                if(!trgovinaFigurice.getFigurica().getKorisnik().equals(trgovina.getPrimalac()))
                {
                    throw new IllegalArgumentException("Primalac nije vlasnik figurice");
                }
            }



        }


        for(TrgovinaFigurice trgovinaFigurice : trgovina.getFigurice())
        {

            if(trgovinaFigurice.getTip() == TipTrgovine.PONUDJENA)
            {

                 trgovinaFigurice.getFigurica().setKorisnik(trgovina.getPrimalac());


            }
            else
            {

                trgovinaFigurice.getFigurica().setKorisnik(trgovina.getPosiljalac());
            }


            figuricaRepo.save(trgovinaFigurice.getFigurica());



        }


        trgovina.setStatus(StatusTrgovine.ACCEPTED);

        return trgovinaRepo.save(trgovina);

    }




    public Trgovina odbijTrgovinu(Long trgovinaID)
    {

        Trgovina trgovina = trgovinaRepo.findById(trgovinaID).orElseThrow(
                ()-> new EntityNotFoundException("Trgovina sa ID " + trgovinaID + " ne postoji" )
        );


        trgovina.setStatus(StatusTrgovine.DECLINED);
        return  trgovinaRepo.save(trgovina);


    }


    public  Trgovina


    public List<Trgovina> mojeTrgovine() {

        String korisnickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        Korisnik korisnik = korisnikRepo.findByKorisnickoIme(korisnickoImeIzTokena)
                .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen!"));

        return trgovinaRepo.findByPosiljalacOrPrimalac(korisnik);
    }

}
