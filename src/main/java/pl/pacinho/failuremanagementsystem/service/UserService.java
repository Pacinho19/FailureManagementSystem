/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pacinho.failuremanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.pacinho.failuremanagementsystem.config.CryptoConfig;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.model.FmsUserDetails;
import pl.pacinho.failuremanagementsystem.model.dto.FmsUserDto;
import pl.pacinho.failuremanagementsystem.model.dto.mapper.UserDtoMapper;
import pl.pacinho.failuremanagementsystem.repository.UserRepository;

import java.util.List;
import java.util.Optional;


/**
 * @author pojdana
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CryptoConfig cryptoConfig;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        return user.map(FmsUserDetails::new).get();
    }

    public User getByLogin(String login) {
        return userRepository.findByUsername(login)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + login));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User save(FmsUserDto fmsUserDto) {
        User user = UserDtoMapper.parseToEntity(fmsUserDto);
        user.setPassword(cryptoConfig.encoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getById(long id) {
        return userRepository.findById(id);
    }

    public long getUsersCount() {
        return userRepository.count();
    }
}