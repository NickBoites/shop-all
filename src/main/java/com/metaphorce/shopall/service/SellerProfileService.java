package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.SellerProfileDTO;
import com.metaphorce.shopall.dto.SellerProfileRegistrationDTO;
import com.metaphorce.shopall.exception.SellerProfileNotFoundException;
import com.metaphorce.shopall.model.SellerProfile;
import com.metaphorce.shopall.model.User;
import com.metaphorce.shopall.repository.SellerProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SellerProfileService {

    @Autowired
    private SellerProfileRepository sellerProfileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SellerProfileDTO createSellerProfile(SellerProfileRegistrationDTO sellerProfileRegistrationDTO) {
        SellerProfile sellerProfile = modelMapper.map(sellerProfileRegistrationDTO, SellerProfile.class);

        User user = sellerProfile.getUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        sellerProfile.setUser(user);

        SellerProfile savedProfile = sellerProfileRepository.save(sellerProfile);
        return modelMapper.map(savedProfile, SellerProfileDTO.class);
    }

    // Obtener el perfil de un vendedor por ID
    public SellerProfileDTO getSellerById(Long sellerId) {
        SellerProfile sellerProfile = sellerProfileRepository.findById(sellerId)
                .orElseThrow(() -> new SellerProfileNotFoundException("Perfil de vendedor no encontrado: " + sellerId));

        return modelMapper.map(sellerProfile, SellerProfileDTO.class);
    }

    // Actualizar el perfil de un vendedor
    public SellerProfileDTO updateSellerProfile(Long sellerId, SellerProfileDTO sellerProfileDto) {
        SellerProfile existingProfile = sellerProfileRepository.findById(sellerId)
                .orElseThrow(() -> new SellerProfileNotFoundException("Perfil de vendedor no encontrado: " + sellerId));

        modelMapper.map(sellerProfileDto, existingProfile);

        User user = existingProfile.getUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        existingProfile.setUser(user);

        SellerProfile updatedProfile = sellerProfileRepository.save(existingProfile);
        return modelMapper.map(updatedProfile, SellerProfileDTO.class);
    }
}


