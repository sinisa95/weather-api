package com.it.hybrid.weatherapi.repositories.custom;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.it.hybrid.weatherapi.models.City;
import com.it.hybrid.weatherapi.models.persistance.projection.CityAverageTemperature;

import org.springframework.stereotype.Repository;

/**
 * Implementation of custom repository for city entity.
 */
@Repository
public class CityCustomRepositoryImpl implements CityCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CityAverageTemperature> findCitiesWithAvgTemp(
        Optional<List<Long>> optionalCityIds,
        Optional<Boolean> optionalSortByAvgTemp
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CityAverageTemperature> query = criteriaBuilder.createQuery(CityAverageTemperature.class);
        Root<City> cityRoot = query.from(City.class);

        Path<Long> idPath = cityRoot.get("id");
        Path<String> namePath = cityRoot.get("name");
        Path<String> countryCodePath = cityRoot.get("countryCode");
        Path<BigDecimal> temperaturePath = cityRoot.join("weathers").get("temperature");
        
        Expression<BigDecimal> avgTemperature = criteriaBuilder.avg(temperaturePath).as(BigDecimal.class);
        
        query.multiselect(idPath, namePath, countryCodePath, avgTemperature.alias("averageTemperature"))
            .groupBy(idPath);
            
        optionalCityIds.ifPresent(cityIds -> 
            query.where(idPath.in(cityIds))
        );

        optionalSortByAvgTemp.ifPresent(sortByAvgTemp ->
            query.orderBy(criteriaBuilder.asc(avgTemperature))
        );
            
        return entityManager.createQuery(query).getResultList();
    }
}