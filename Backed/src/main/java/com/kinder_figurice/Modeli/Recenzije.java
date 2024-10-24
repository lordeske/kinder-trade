package com.kinder_figurice.modeli;


import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "recenzije")
public class Recenzije {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "recenzent_id", nullable = false)
    private Korisnik recenzent;

    @ManyToOne
    @JoinColumn(name = "figurica_id", nullable = false)
    private Figurica figurica;

    private int ocena;

    private String komentar;

    private LocalDateTime datumKreiranja;


}
