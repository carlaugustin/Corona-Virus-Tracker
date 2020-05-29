package com.carl_the_creator.coronavirustracker.repo;

import com.carl_the_creator.coronavirustracker.models.LocationUsStats;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Max;
import java.util.List;


public interface UsRepository extends JpaRepository<LocationUsStats,String> {

    List<LocationUsStats> findByName(String state);

}
