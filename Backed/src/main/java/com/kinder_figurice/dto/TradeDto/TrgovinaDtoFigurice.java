package com.kinder_figurice.dto.TradeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TrgovinaDtoFigurice {


    private List<Long> ponudjeneFigurice;
    private List<Long> trazeneFigurice;





}