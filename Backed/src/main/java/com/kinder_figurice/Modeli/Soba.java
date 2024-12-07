package com.kinder_figurice.modeli;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "soba")
public class Soba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSobe;

    @Column(unique = true, nullable = false)
    private String nazivSobe;


    @OneToMany(mappedBy = "soba", cascade = CascadeType.ALL)
    private List<Poruka> poruke = new ArrayList<>();


}
