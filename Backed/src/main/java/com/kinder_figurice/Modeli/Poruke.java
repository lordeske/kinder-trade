package com.kinder_figurice.modeli;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Table(name = "poruke")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Poruke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "posiljalac_id", nullable = false)
    private Korisnik posiljalac;

    @ManyToOne
    @JoinColumn(name = "primalac_id", nullable = false)
    private Korisnik primalac;

    private String sadrzaj;

    private LocalDateTime datumKreiranja;


}
