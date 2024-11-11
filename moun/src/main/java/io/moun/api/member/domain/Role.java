package io.moun.api.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String username;
    private String roleType;


    public Role(String username, String roleType) {
        this.username = username;
        this.roleType = roleType;
    }
}
