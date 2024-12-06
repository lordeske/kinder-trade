package com.kinder_figurice.modeli.Chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PorukaNotif {


    private Long id;
    private Long posiljalacID;
    private Long primalacID;
    private String poruka;






}