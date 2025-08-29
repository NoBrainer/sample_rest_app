package com.example.sample_rest_app.repository;

import com.example.sample_rest_app.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, QueryByExampleExecutor<Address> {
}
