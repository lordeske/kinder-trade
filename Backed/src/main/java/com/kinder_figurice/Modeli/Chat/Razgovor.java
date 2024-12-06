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
@Table(name = "razgovor")
public class Razgovor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idRazgovora;

    @ManyToOne
    @JoinColumn(name = "primalac_id", nullable = false)
    private Korisnik primalac;

    @ManyToOne
    @JoinColumn(name = "posiljalac_id", nullable = false)
    private Korisnik posiljalac;

    private String tekstPoruke;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime vrijeme;
}
