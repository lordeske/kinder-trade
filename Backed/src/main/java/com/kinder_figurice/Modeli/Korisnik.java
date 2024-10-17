package com.kinder_figurice.Modeli;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Entity
@Table(name = "korisnici")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Korisnik {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long idKorisnika;


   @Column(nullable = false)
   private String ime;

   @Column(nullable = false)
   private String prezime;


   @Column(nullable = false , unique = true)
   private String email;


    @Column(nullable = false)
    private String lozinka;

    @Column
    private String slikaKorisnika;

    @Column
    private Boolean aktivanKorisnik = true;

    ////One koje je srcnuo
    @OneToMany
    @JoinTable(name = "wishlist",
    joinColumns = @JoinColumn(name = "idKorisnika"),
            inverseJoinColumns = @JoinColumn(name = "idFigurice"))
    private List<Figurica> wishList;


    /// Vlasnistvo nad figuricama
    @OneToMany(mappedBy = "korisnik" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Figurica> figurice;











}
