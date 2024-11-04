package com.kinder_figurice.dto.RecenzijeDTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecenzijaRequest {


    private Long recenzentID;
    private Long recenziraniID;
    private Integer ocena;
    private String komentar;
}
