package com.batch211.flashcart.serviceimpl;

import com.batch211.flashcart.entities.Address;
import com.batch211.flashcart.entities.User;
import com.batch211.flashcart.repo.AddressRepository;
import com.batch211.flashcart.services.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> getUserAddresses(User user) {
        return addressRepository.findByUser(user);
    }

    @Override
    public Address createAddress(User user, Address address) {
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(User user, Long addressId, Address address) {
        Address existing = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized address access");
        }
        existing.setStreet(address.getStreet());
        existing.setCity(address.getCity());
        existing.setState(address.getState());
        existing.setCountry(address.getCountry());
        existing.setPincode(address.getPincode());
        existing.setType(address.getType());
        existing.setDefault(address.isDefault());
        return addressRepository.save(existing);
    }

    @Override
    public void deleteAddress(User user, Long addressId) {
        Address existing = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized address access");
        }
        addressRepository.delete(existing);
    }
}