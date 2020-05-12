package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RegnCanc;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the RegnCanc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegnCancRepository extends MongoRepository<RegnCanc, String> {

}
