package io.moun.api.member.controller.dto;

import io.moun.api.member.domain.SNS;
import io.moun.api.position.Position;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberUpdateRequest {
    @NotNull
    private String displayName;
    private List<Position> positions = new ArrayList<>();


    @Embedded
    private SNS sns;

    @NotNull
    private String description;

    private String profilePictureUrl;
}
