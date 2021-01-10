
package com.it.hybrid.weatherapi.models.owm;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coord {

    private BigDecimal lat;
    
    private BigDecimal lon;
}
