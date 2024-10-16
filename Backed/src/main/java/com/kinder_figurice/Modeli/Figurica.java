package com.kinder_figurice.Modeli;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "figurice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Figurica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idFigurice;


    @Column(nullable = false)
    private String nazivFigurice;


    @Column
    private String serijaFigurice;

    @Column(length = 4 , nullable = false)
    private String godinaIzdanja;


    @Column(length = 255)
    private String opisFigurice;

    @Column(length = 255)
    private String slikaFigurice;

    @ManyToOne
    @JoinColumn(name = "idKorisnika")
    private Korisnik korisnik;









}
