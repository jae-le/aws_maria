package com.boot.board_240718.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
// Entity가 다른 Entity와 연관관계가 있는 상태에서 둘 다 @Data 어노테이션이 붙어있을경우
// Lombok에서 생성하는 equals, hasCode
// 혹은 toString 매서드가 서로를 순환 참조하는 문제가 발생
// @Data
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
