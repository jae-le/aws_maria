package com.boot.board_240718.repository;

import com.boot.board_240718.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    // like %parameter% 라서 여러개 올수가 있기 때문에 리턴타입을 List로 받았다.
//    @Query("select u from User u where u.username like %?1%")
//    @Query("select username from User u where u.username like %?1%")
//    @Query(value= "select username from User u where u.username like %?1%")
//    @Query(value= "select username from User u where u.username like %?1%", nativeQuery = true)
    @Query(value= "select * from User u where u.username like %?1%", nativeQuery = true)
    List<User> findByUsernameQuery(String username);
}
