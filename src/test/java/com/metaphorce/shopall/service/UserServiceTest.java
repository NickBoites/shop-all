package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.UserDTO;
import com.metaphorce.shopall.dto.UserRegistrationDTO;
import com.metaphorce.shopall.model.User;
import com.metaphorce.shopall.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;


    @Mock
    private UserService userService;


    @Mock
    private ModelMapper modelMapper;

    @Test
    void createUser_ShouldCreateUser() {
        UserRegistrationDTO userDto = new UserRegistrationDTO();
        userDto.setPassword("password");
        User user = new User();
        when(modelMapper.map(any(UserDTO.class), eq(User.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDto);

        UserDTO result = userService.createUser(userDto);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void updateUser_WhenUserExists_ShouldUpdateUser() {
        Long userId = 1L;
        UserRegistrationDTO userDto = new UserRegistrationDTO();
        userDto.setPassword("newPassword");
        User existingUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDto);

        UserDTO result = userService.updateUser(userId, userDto);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
        assertEquals("encodedNewPassword", existingUser.getPassword());
    }

    @Test
    void deleteUser_WhenUserExists_ShouldDeleteUser() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));

        userService.deleteUser(userId);

        verify(userRepository).delete(any(User.class));
    }


}
