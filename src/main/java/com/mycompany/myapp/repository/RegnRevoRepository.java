package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RegnRevo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the RegnRevo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegnRevoRepository extends MongoRepository<RegnRevo, String> {

}
