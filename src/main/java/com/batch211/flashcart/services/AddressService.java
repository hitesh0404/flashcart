package com.batch211.flashcart.services;

import com.batch211.flashcart.entities.Address;
import com.batch211.flashcart.entities.User;

import java.util.List;

public interface AddressService {
    List<Address> getUserAddresses(User user);
    Address createAddress(User user, Address address);
    Address updateAddress(User user, Long addressId, Address address);
    void deleteAddress(User user, Long addressId);
}