package com.kinder_figurice.modeli.Chat;


import com.kinder_figurice.modeli.Korisnik;
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
@Table(name = "razgovor_poruka")
public class Razgovor {

    @Id
    private Long id;


    @ManyToOne
    @JoinColumn(name = "razgovor_Soba_Id", nullable = false)
    private String idRazgovora;

    @ManyToOne
    @JoinColumn(name = "primalac_id", nullable = false)
    private Korisnik primalac;

    @ManyToOne
    @JoinColumn(name = "posiljalac_id", nullable = false)
    private Korisnik posiljalac;

    private String tekstPoruke;

    private LocalDateTime vrijeme;



}
