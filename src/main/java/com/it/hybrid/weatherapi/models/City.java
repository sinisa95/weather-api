package com.it.hybrid.weatherapi.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "city")
public class City extends BaseEntity {

    @Column(name = "externalId", nullable = true)
    private Long externalId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "longitude", nullable = false, scale = 3, precision = 7)
    private BigDecimal longitude;

    @Column(name = "latitude", nullable = false, scale = 2, precision = 6)
    private BigDecimal latitude;

    @Column(name = "country_code", nullable = false, length = 3)
    private String countryCode;

    @Column(name = "seconds_timezone_offset", nullable = false)
    private Short secondsTimezoneOffset;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "city")
    private List<Weather> weathers;

    public BigDecimal getAverageTemperature() {
        if (this.weathers == null && weathers.isEmpty()) {
            return null;
        }

        BigDecimal sumTemperature = this.weathers.stream()
            .map(Weather::getTemperature)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal size = BigDecimal.valueOf(this.weathers.size());

        return sumTemperature.divide(size, 2, RoundingMode.HALF_EVEN);
    }

}
