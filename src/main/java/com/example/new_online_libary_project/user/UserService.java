package com.example.new_online_libary_project.user;
import com.example.new_online_libary_project.user.dto.RegisterDto;
import com.example.new_online_libary_project.user.dto.UpdateDto;
import com.example.new_online_libary_project.user.entity.Role;
import com.example.new_online_libary_project.user.entity.Status;
import com.example.new_online_libary_project.user.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public List<User> getAll(){
        return userRepository.findAll();
    }
    @Transactional
    public Optional<User> getById(@NonNull UUID id){
        return userRepository.findById(id);
    }
    @Transactional
    public void create(RegisterDto registerDto){

        User user = new User( null,
                registerDto.getFirstName(),
                registerDto.getLastName(),
                registerDto.getEmail(),
                registerDto.getUsername(),
                registerDto.getPhoneNumber(),
                registerDto.getPassword(),
                Role.USER,
                Status.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
                );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    @Transactional
    public void delete(@NonNull UUID uuid){
        userRepository.deleteById(uuid);
    }
    @Transactional
    public void update(@ModelAttribute UpdateDto updateDto, @NonNull UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setFirstName(updateDto.getFirstName());
        user.setLastName(updateDto.getLastName());
        user.setEmail(updateDto.getEmail());
        user.setUsername(updateDto.getUsername());
        user.setPhoneNumber(updateDto.getPhoneNumber());
        //user.setPassword(updateDto.getPassword());
        user.setRole(updateDto.getRole());
        user.setStatus(updateDto.getStatus());
        user.setUpdateAd(LocalDateTime.now());

        userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->
                new BadCredentialsException("Username or password is not correct"));

    }



}
