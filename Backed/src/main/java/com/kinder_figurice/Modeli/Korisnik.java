package com.kinder_figurice.modeli;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "korisnici")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String korisnickoIme;

    @Column(unique = true, nullable = false)
    private String email;

    private String lozinka;

    private String uloga;

    private Boolean isVerified = true;

    private String provider;

    private String providerId;

    private LocalDateTime datumKreiranja;

    private LocalDateTime datumAzuriranja;

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
    private List<Figurica> figurice;

}
