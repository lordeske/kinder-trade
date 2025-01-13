package com.kinder_figurice.dto.RecenzijeDTO;


import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecenzijaDTO {

    private Long idRecenzije;
    private String imeRecenzenta;
    private Integer ocena;
    private String komentar;
    private LocalDateTime datumKreiranja;


}
