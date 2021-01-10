
package com.it.hybrid.weatherapi.models.owm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {

    private Long id;

    private String name;

    private Coord coord;

    private String country;
    
    private Short timezone;
}
