package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.SellerProfileDTO;
import com.metaphorce.shopall.dto.SellerProfileRegistrationDTO;
import com.metaphorce.shopall.exception.SellerProfileNotFoundException;
import com.metaphorce.shopall.model.SellerProfile;
import com.metaphorce.shopall.repository.SellerProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerProfileService {

    @Autowired
    private SellerProfileRepository sellerProfileRepository;

    @Autowired
    private ModelMapper modelMapper;

    public SellerProfileDTO createSellerProfile(SellerProfileRegistrationDTO sellerProfileRegistrationDTO) {
        SellerProfile sellerProfile = modelMapper.map(sellerProfileRegistrationDTO, SellerProfile.class);
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
        SellerProfile updatedProfile = sellerProfileRepository.save(existingProfile);
        return modelMapper.map(updatedProfile, SellerProfileDTO.class);
    }

    // Aquí puedes agregar más métodos según las necesidades de ShopAll
}


