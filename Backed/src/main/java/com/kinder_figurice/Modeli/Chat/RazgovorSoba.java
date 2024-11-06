package com.kinder_figurice.modeli.Chat;

import com.kinder_figurice.modeli.Korisnik;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "razgovor_soba")
public class RazgovorSoba {

    @Id
    private String idRazgovorSoba;

    @ManyToOne
    @JoinColumn(name = "primalac_id", nullable = false)
    private Korisnik primalac;

    @ManyToOne
    @JoinColumn(name = "posiljalac_id", nullable = false)
    private Korisnik posiljalac;

}
