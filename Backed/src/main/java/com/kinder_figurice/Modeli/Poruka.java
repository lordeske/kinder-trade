package com.kinder_figurice.modeli;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Poruka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String posiljalac;
    private String primalac;
    private String sadrzajPoruke;

    @ManyToOne
    @JoinColumn(name = "soba_id", nullable = false)
    private Soba soba;

    private LocalDateTime vremeSlanja = LocalDateTime.now();
}