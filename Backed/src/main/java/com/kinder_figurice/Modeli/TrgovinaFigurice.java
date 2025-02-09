package com.kinder_figurice.modeli;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trgovina_figurice")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TrgovinaFigurice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trgovina_id", nullable = false)
    @JsonIgnore
    private Trgovina trgovina;

    @ManyToOne
    @JoinColumn(name = "figurica_id", nullable = false)
    private Figurica figurica;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipTrgovine tip; // PONUDJENA ili TRAŽENA


    public TrgovinaFigurice(Trgovina trgovina, Figurica figurica, boolean jePonudjena) {
        this.trgovina = trgovina;
        this.figurica = figurica;
        this.tip = jePonudjena ? TipTrgovine.PONUDJENA : TipTrgovine.TRAZENA;
    }

}
