package com.boot.board_240718.service;

import com.boot.board_240718.model.Role;
import com.boot.board_240718.model.User;
import com.boot.board_240718.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // 암호화를 위해서
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user){

        // 비밀번호를 get으로 가져와서 암호화 시켜서 다시 set으로 집어넣음
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        // 활성화된 사용자
        user.setEnabled(true);

        Role role = new Role();
        // roll repository 안만들고, 하드코딩으로 1저장(자동 증가)
        role.setId(1L);
        // user_role 테이블에 저장
        user.getRoles().add(role);

        return userRepository.save(user);
    }
}
