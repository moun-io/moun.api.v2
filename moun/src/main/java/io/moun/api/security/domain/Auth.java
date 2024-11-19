package io.moun.api.security.domain;

import io.moun.api.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class Auth {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Member member;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Role> roles = new ArrayList<Role>();


}
