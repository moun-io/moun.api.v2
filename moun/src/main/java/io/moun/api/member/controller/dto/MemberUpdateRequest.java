package io.moun.api.member.controller.dto;

import io.moun.api.member.domain.SNS;
import io.moun.api.member.domain.Position;
import jakarta.persistence.Embedded;
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
