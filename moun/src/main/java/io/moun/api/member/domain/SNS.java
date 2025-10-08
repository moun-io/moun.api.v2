package io.moun.api.member.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SNS {
    private String instagramUrl;
    private String soundCloudUrl;

    @Builder
    public SNS(String instagramSNS, String soundCloudSNS) {
        this.instagramUrl = instagramSNS;
        this.soundCloudUrl = soundCloudSNS;
    }
}
