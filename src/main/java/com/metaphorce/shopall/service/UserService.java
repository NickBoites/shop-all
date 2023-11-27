package com.metaphorce.shopall.service;

import com.metaphorce.shopall.dto.UserDTO;
import com.metaphorce.shopall.exception.UserNotFoundException;
import com.metaphorce.shopall.model.User;
import com.metaphorce.shopall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Obtener detalles de un usuario por ID
    public UserDTO getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDTO.class);
        } else {
            throw new UserNotFoundException("Usuario con ID " + userId + " no encontrado");
        }
    }

    // Crear un nuevo usuario
    @Transactional
    public UserDTO createUser(UserDTO userDto) {
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    // Actualizar un usuario existente
    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario con ID " + userId + " no encontrado para actualizar"));

        modelMapper.map(userDto, user);
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    // Eliminar un usuario
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario con ID " + userId + " no encontrado para eliminar"));

        userRepository.delete(user);
    }

    // Aquí puedes agregar más métodos según las necesidades de ShopAll
}


