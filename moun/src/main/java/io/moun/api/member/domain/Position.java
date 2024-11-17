package io.moun.api.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.util.ArrayList;
import java.util.List;

//@Entity
//@Immutable

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Enumerated
    private PositionType positionType;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
//    @JsonIgnore
    private List<MemberPositionRelation> memberPositionRelationList = new ArrayList<>();
}
