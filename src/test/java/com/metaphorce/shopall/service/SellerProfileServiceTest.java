package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.SellerProfileDTO;
import com.metaphorce.shopall.dto.SellerProfileRegistrationDTO;
import com.metaphorce.shopall.model.SellerProfile;
import com.metaphorce.shopall.model.User;
import com.metaphorce.shopall.repository.CartRepository;
import com.metaphorce.shopall.repository.ProductRepository;
import com.metaphorce.shopall.repository.SellerProfileRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SellerProfileServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SellerProfileRepository sellerProfileRepository;


    @Mock
    private SellerProfileService sellerProfileService;


    @Mock
    private ModelMapper modelMapper;


    @Test
    void createSellerProfile_ShouldCreateSellerProfile() {
        SellerProfileRegistrationDTO registrationDTO = new SellerProfileRegistrationDTO();
        SellerProfile sellerProfile = new SellerProfile();
        User user = new User();
        sellerProfile.setUser(user);
        when(modelMapper.map(any(SellerProfileRegistrationDTO.class), eq(SellerProfile.class))).thenReturn(sellerProfile);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(sellerProfileRepository.save(any(SellerProfile.class))).thenReturn(sellerProfile);
        when(modelMapper.map(any(SellerProfile.class), eq(SellerProfileDTO.class))).thenReturn(new SellerProfileDTO());

        SellerProfileDTO result = sellerProfileService.createSellerProfile(registrationDTO);

        assertNotNull(result);
        verify(sellerProfileRepository).save(any(SellerProfile.class));
    }

    @Test
    void getSellerById_WhenSellerExists_ShouldReturnSeller() {
        Long sellerId = 1L;
        SellerProfile sellerProfile = new SellerProfile();
        when(sellerProfileRepository.findById(sellerId)).thenReturn(Optional.of(sellerProfile));
        when(modelMapper.map(any(SellerProfile.class), eq(SellerProfileDTO.class))).thenReturn(new SellerProfileDTO());

        SellerProfileDTO result = sellerProfileService.getSellerById(sellerId);

        assertNotNull(result);
        verify(sellerProfileRepository).findById(sellerId);
    }

    @Test
    void updateSellerProfile_WhenSellerExists_ShouldUpdateSeller() {
        Long sellerId = 1L;
        SellerProfileDTO sellerProfileDto = new SellerProfileDTO();
        SellerProfile existingProfile = new SellerProfile();
        User user = new User();
        existingProfile.setUser(user);
        when(sellerProfileRepository.findById(sellerId)).thenReturn(Optional.of(existingProfile));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(sellerProfileRepository.save(any(SellerProfile.class))).thenReturn(existingProfile);
        when(modelMapper.map(any(SellerProfile.class), eq(SellerProfileDTO.class))).thenReturn(sellerProfileDto);

        SellerProfileDTO result = sellerProfileService.updateSellerProfile(sellerId, sellerProfileDto);

        assertNotNull(result);
        verify(sellerProfileRepository).save(any(SellerProfile.class));
    }



}
