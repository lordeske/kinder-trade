package com.kinder_figurice.Konfiguracija;

import com.kinder_figurice.modeli.Korisnik;
import com.kinder_figurice.modeli.Role;
import com.kinder_figurice.repo.KorisnikRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomUserDetailSerice implements UserDetailsService {



    private final KorisnikRepo korisnikRepo;
    private final PasswordEncoder passwordEncoder;

    // Koristi konstruktorsku injekciju
    public CustomUserDetailSerice(KorisnikRepo korisnikRepo, PasswordEncoder passwordEncoder) {
        this.korisnikRepo = korisnikRepo;
        this.passwordEncoder = passwordEncoder;

    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Korisnik korisnik = korisnikRepo.findByKorisnickoIme(username).orElseThrow(()->
                new UsernameNotFoundException("Nije pronadjen korisnik"));



        Collection<GrantedAuthority> prava = List.of(new SimpleGrantedAuthority("ROLE_"+korisnik.getUloga().name()));


        return  new User(korisnik.getKorisnickoIme(), korisnik.getLozinka(), prava);

    }




}
