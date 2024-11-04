package com.kinder_figurice.modeli;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "recenzije")
public class Recenzije {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recenzent_id", nullable = false)
    private Korisnik recenzent;

    @ManyToOne
    @JoinColumn(name = "recenzirani_id", nullable = false)
    private Korisnik recenziraniKorisnik;

    private int ocena;

    private String komentar;

    private LocalDateTime datumKreiranja;


}
