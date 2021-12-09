package com.capstone.danjinae.user.service;

import com.capstone.danjinae.user.repository.ApartRepository;
import com.capstone.danjinae.user.entity.Apartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ApartService {
    @Autowired
    ApartRepository apartRepository;

    public Page<Apartment> searchAddress(String address, Pageable pageable) {
        return apartRepository.findByAddressContaining(address, pageable);
    }

    public Apartment getApartment(Integer id)
    {
        return apartRepository.getById(id);
    }
}
