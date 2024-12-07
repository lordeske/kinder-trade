package com.kinder_figurice.modeli;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "poruke")
public class Poruka {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPoruke;

    @Column(nullable = false)
    private String posiljalac;

    @Column(nullable = false)
    private String primalac;


    @Column(nullable = false)
    private String sadrzajPoruke;


    private LocalDateTime datumSlanjaPoruke;

    @ManyToOne
    @JoinColumn(name = "idSobe")
    @JsonIgnore
    private Soba soba;


    @PrePersist
    public void prePresis()
    {
        this.datumSlanjaPoruke = LocalDateTime.now();
    }







}
