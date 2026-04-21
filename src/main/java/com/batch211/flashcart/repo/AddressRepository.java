package com.batch211.flashcart.repo;

import com.batch211.flashcart.entities.Address;
import com.batch211.flashcart.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
}