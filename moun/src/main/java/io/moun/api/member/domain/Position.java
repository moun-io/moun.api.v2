package io.moun.api.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

//@Entity
//@Immutable

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @Enumerated(value = EnumType.STRING)
    private PositionType positionType;


    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnore
    private Member member;

    public Position(PositionType positionType , Member member) {
        this.positionType = positionType;
        this.member = member;
    }
}

