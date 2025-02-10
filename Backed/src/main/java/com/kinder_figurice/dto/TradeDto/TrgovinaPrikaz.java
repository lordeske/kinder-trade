package com.kinder_figurice.dto.TradeDto;

import com.kinder_figurice.modeli.Figurica;
import com.kinder_figurice.modeli.StatusTrgovine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TrgovinaPrikaz {

    private Long id;
    private String posiljalac;
    private String primalac;
    private List<Figurica> ponudjeneFigurice;
    private List<Figurica> trazeneFigurice;
    private StatusTrgovine status;




}
