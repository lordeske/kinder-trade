package com.kinder_figurice.modeli;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trgovina")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Trgovina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "posiljalac_id", nullable = false)
    private Korisnik posiljalac;

    @ManyToOne
    @JoinColumn(name = "primalac_id", nullable = false)
    private Korisnik primalac;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTrgovine status;

    @OneToMany(mappedBy = "trgovina", cascade = CascadeType.ALL)
    private List<TrgovinaFigurice> figurice;

    @Column(name = "datum_kreiranja", updatable = false)
    private LocalDateTime datumKreiranja = LocalDateTime.now();


}

