package com.kinder_figurice.dto.TradeDto;

import com.kinder_figurice.modeli.StatusTrgovine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TrgovinaNoStatusDto {

    private String posiljalac;
    private String primalac;
    private List<Long> ponudjeneFigurice;
    private List<Long> trazeneFigurice;





}
