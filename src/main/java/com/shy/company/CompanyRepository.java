package com.shy.company;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends CrudRepository<Company, UUID> {
    public List<Company> findAll();
    public Optional<Company> findById(UUID id);
}