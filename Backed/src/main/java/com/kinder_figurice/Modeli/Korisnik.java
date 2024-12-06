package com.kinder_figurice.modeli;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "korisnici")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String korisnickoIme;

    @Column(unique = true, nullable = false)
    private String email;

    private String lozinka;

    private String slika;

    private Role uloga;

    private Boolean isVerified;


    private String provider;

    @Column(unique = true)
    private String providerId;

    private LocalDateTime datumKreiranja;

    private LocalDateTime datumAzuriranja;

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Figurica> figurice;





    // Omogucava update svaki put da ne pozivam u funckiji nad servisom
    @PrePersist
    public void onCreate()
    {

        this.datumKreiranja = LocalDateTime.now();

    }


    @PreUpdate
    public void onUpdate()
    {
        this.datumAzuriranja = LocalDateTime.now();
    }
}


