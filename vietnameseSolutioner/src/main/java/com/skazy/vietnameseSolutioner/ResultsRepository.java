package com.skazy.vietnameseSolutioner;
// package com.example.accessingdatajpa;
// import crudrepository
import org.springframework.data.repository.CrudRepository;


import java.util.List;


public interface ResultsRepository extends CrudRepository<Results, Long> {

    List<Results> findByCalculation(String calculation);

    // find all results
    List<Results> findAll();

    Results findById(long id);

    void deleteById(long id);

    void deleteAll();


}
