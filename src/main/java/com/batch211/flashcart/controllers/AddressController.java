package com.batch211.flashcart.controllers;

import com.batch211.flashcart.entities.Address;
import com.batch211.flashcart.entities.User;
import com.batch211.flashcart.services.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin(origins = "http://localhost:5173")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAddresses(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(addressService.getUserAddresses(user));
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(
            @AuthenticationPrincipal User user,
            @RequestBody Address address
    ) {
        return ResponseEntity.ok(addressService.createAddress(user, address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody Address address
    ) {
        return ResponseEntity.ok(addressService.updateAddress(user, id, address));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        addressService.deleteAddress(user, id);
        return ResponseEntity.noContent().build();
    }
}