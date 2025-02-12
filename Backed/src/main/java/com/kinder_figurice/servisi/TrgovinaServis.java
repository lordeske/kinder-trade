package com.kinder_figurice.servisi;

import com.kinder_figurice.dto.TradeDto.TrgovinaDtoFigurice;
import com.kinder_figurice.dto.TradeDto.TrgovinaNoStatusDto;
import com.kinder_figurice.dto.TradeDto.TrgovinaPrikaz;

import com.kinder_figurice.modeli.*;
import com.kinder_figurice.repo.FiguricaRepo;
import com.kinder_figurice.repo.KorisnikRepo;
import com.kinder_figurice.repo.TrgovinaFiguriceRepo;
import com.kinder_figurice.repo.TrgovinaRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public Trgovina kreirajTrgovinu(TrgovinaNoStatusDto trgovinaDTO)
    {


        String korisnickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();



        Korisnik posiljalac = korisnikRepo.findByKorisnickoIme(trgovinaDTO.getPosiljalac())
                .orElseThrow(()-> new EntityNotFoundException("Nije pronadjen posiljalac"));


        Korisnik primalac = korisnikRepo.findByKorisnickoIme(trgovinaDTO.getPrimalac())
                .orElseThrow(()-> new EntityNotFoundException("Nije pronadjen primalac"));


        if(!korisnickoImeIzTokena.equals(posiljalac.getKorisnickoIme()))
        {
            throw new SecurityException("Nemas pravo da kreiras offer");
        }



        boolean svePonudjeneValidne = trgovinaDTO.getPonudjeneFigurice().stream()
                .allMatch(figuricaId -> figuricaRepo.existsByIdAndKorisnik(figuricaId, posiljalac));

        if(!svePonudjeneValidne)
        {
            throw new IllegalArgumentException("Neke od figurica ne prripadaju posiljaocu");

        }



        boolean sveZatrazeneValidne = trgovinaDTO.getTrazeneFigurice().stream()
                .allMatch(figuricaId -> figuricaRepo.existsByIdAndKorisnik(figuricaId, primalac));

        if(!sveZatrazeneValidne)
        {
            throw new IllegalArgumentException("Neke od figurica ne prripadaju primaocu");

        }



        Trgovina trgovina = new Trgovina();
        trgovina.setPosiljalac(posiljalac);
        trgovina.setPrimalac(primalac);
        trgovina.setStatus(StatusTrgovine.PENDING);
        trgovina.setFigurice(new ArrayList<>());

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


        for (TrgovinaFigurice trgovinaFigurice : trgovina.getFigurice()) {
            Korisnik trenutniVlasnik = trgovinaFigurice.getFigurica().getKorisnik();

            if ((trgovinaFigurice.getTip() == TipTrgovine.PONUDJENA && !trenutniVlasnik.equals(trgovina.getPosiljalac())) ||
                    (trgovinaFigurice.getTip() == TipTrgovine.TRAZENA && !trenutniVlasnik.equals(trgovina.getPrimalac()))) {
                throw new IllegalArgumentException("Figurica ne pripada očekivanom vlasniku.");
            }


            trgovinaFigurice.getFigurica()
                    .setKorisnik(trgovinaFigurice.getTip() == TipTrgovine.PONUDJENA ? trgovina.getPrimalac() : trgovina.getPosiljalac());
            figuricaRepo.save(trgovinaFigurice.getFigurica());
        }



        trgovina.setStatus(StatusTrgovine.ACCEPTED);

        return trgovinaRepo.save(trgovina);

    }




    public Trgovina odbijTrgovinu(Long trgovinaID)
    {

        String korisnickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        Trgovina trgovina = trgovinaRepo.findById(trgovinaID).orElseThrow(
                ()-> new EntityNotFoundException("Trgovina sa ID " + trgovinaID + " ne postoji" )
        );


        if (!trgovina.getPrimalac().getKorisnickoIme().equals(korisnickoImeIzTokena)) {
            throw new SecurityException("Nisi ovlašćen da povučeš ponudu.");
        }

        trgovina.setStatus(StatusTrgovine.DECLINED);
        return  trgovinaRepo.save(trgovina);


    }
    public Trgovina povuciTrgovinu(Long trgovinaID) {
        String korisnickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        Trgovina trgovina = trgovinaRepo.findById(trgovinaID)
                .orElseThrow(() -> new EntityNotFoundException("Trgovina sa ID " + trgovinaID + " ne postoji"));

        if (!trgovina.getPosiljalac().getKorisnickoIme().equals(korisnickoImeIzTokena)) {
            throw new SecurityException("Nisi ovlašćen da povučeš ponudu.");
        }

        trgovina.setStatus(StatusTrgovine.DECLINED);
        return trgovinaRepo.save(trgovina);
    }



    public Trgovina posaljiCounterPonudu (Long trgovinaID, TrgovinaDtoFigurice noveFigurice )
    {




        Trgovina trgovina = trgovinaRepo.findById(trgovinaID)
                .orElseThrow(()-> new EntityNotFoundException("Trgovina sa ID " + trgovinaID + " ne postoji" ));


        if(!trgovina.getStatus().equals(StatusTrgovine.PENDING))
        {
            throw new RuntimeException("Vec je gotova razmjena");
        }


        /// Da bi kasnije promijenio (jer samo primalac moze da privhati offer)
        Korisnik noviPosiljalac = trgovina.getPrimalac();
        Korisnik noviPrimalac = trgovina.getPosiljalac();


        List<TrgovinaFigurice> ponudjeneTrgovinaFigurice = noveFigurice.getPonudjeneFigurice().stream().map(
                figuricaID -> new TrgovinaFigurice(trgovina, figuricaRepo.findById(figuricaID)
                        .orElseThrow(() -> new RuntimeException("Figurica nije pronađena!")), true))
                .toList();


        List<TrgovinaFigurice> trazeneTrgovinaFigurice = noveFigurice.getTrazeneFigurice().stream().map(
                        figuricaID -> new TrgovinaFigurice(trgovina, figuricaRepo.findById(figuricaID)
                                .orElseThrow(() -> new RuntimeException("Figurica nije pronađena!")), false))
                .toList();


        boolean svePonudjeneValidne = noveFigurice.getPonudjeneFigurice().stream()
                .allMatch(figuricaId -> figuricaRepo.existsByIdAndKorisnik(figuricaId, noviPosiljalac));

        boolean sveTrazeneValidne = noveFigurice.getTrazeneFigurice().stream()
                .allMatch(figuricaId -> figuricaRepo.existsByIdAndKorisnik(figuricaId, noviPrimalac));

        if(!svePonudjeneValidne || !sveTrazeneValidne)
        {
            throw new IllegalArgumentException("Neke od figurica ne prripadaju vlasniku od onih koje si izabrao");
        }


        trgovina.setPrimalac(noviPrimalac);
        trgovina.setPosiljalac(noviPosiljalac);

        trgovinaFiguriceRepo.deleteAllByTrgovina(trgovina);

        trgovina.getFigurice().clear();
        trgovina.getFigurice().addAll(ponudjeneTrgovinaFigurice);
        trgovina.getFigurice().addAll(trazeneTrgovinaFigurice);

        trgovina.setStatus(StatusTrgovine.COUNTER_OFFER);

        return trgovinaRepo.save(trgovina);



    }



    public List<TrgovinaPrikaz> sveMojeTrgovine() {

        String korisnickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        Korisnik korisnik = korisnikRepo.findByKorisnickoIme(korisnickoImeIzTokena)
                .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen!"));

        List <Trgovina> mojeTrgovine =  trgovinaRepo.findByPosiljalacOrPrimalac(korisnik, korisnik);

        return mapirajUTrgovinaPrikaz(mojeTrgovine);

    }

    public List<TrgovinaPrikaz> sveMojePendingTrgovine() {
        String korisnickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        Korisnik korisnik = korisnikRepo.findByKorisnickoIme(korisnickoImeIzTokena)
                .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen!"));

        List <Trgovina> mojeTrgovine = trgovinaRepo.findAllByKorisnikAndStatus(korisnik, StatusTrgovine.PENDING);
        return mapirajUTrgovinaPrikaz(mojeTrgovine);
    }

    public List<TrgovinaPrikaz> sveMojeCounterTrgovine() {
        String korisnickoImeIzTokena = SecurityContextHolder.getContext().getAuthentication().getName();

        Korisnik korisnik = korisnikRepo.findByKorisnickoIme(korisnickoImeIzTokena)
                .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen!"));

        List <Trgovina> mojeTrgovine =  trgovinaRepo.findAllByKorisnikAndStatus(korisnik, StatusTrgovine.COUNTER_OFFER);
        return mapirajUTrgovinaPrikaz(mojeTrgovine);
    }




    public List<TrgovinaPrikaz> mapirajUTrgovinaPrikaz(List<Trgovina> trgovinaObjekat)
    {
        List<TrgovinaPrikaz> novaLista = new ArrayList<>();

        for(Trgovina trgovina : trgovinaObjekat)
        {
            TrgovinaPrikaz trgovinaPrikaz = new TrgovinaPrikaz();
            trgovinaPrikaz.setPosiljalac(trgovina.getPosiljalac().getKorisnickoIme());
            trgovinaPrikaz.setPrimalac(trgovina.getPrimalac().getKorisnickoIme());
            trgovinaPrikaz.setStatus(trgovina.getStatus());
            trgovinaPrikaz.setId(trgovina.getId());

            List<Figurica> ponudjeneFigurice  = trgovina.getFigurice().stream()
                    .filter(figurica -> figurica.getTip() == TipTrgovine.PONUDJENA)
                    .map(TrgovinaFigurice::getFigurica)
                    .toList();


            List<Figurica> trazeneFigurica = trgovina.getFigurice().stream()
                    .filter(figurica -> figurica.getTip() == TipTrgovine.TRAZENA)
                    .map(TrgovinaFigurice::getFigurica)
                    .toList();

            trgovinaPrikaz.setTrazeneFigurice(trazeneFigurica);
            trgovinaPrikaz.setPonudjeneFigurice(ponudjeneFigurice);


            novaLista.add(trgovinaPrikaz);

        }

        return novaLista;


    }



}
