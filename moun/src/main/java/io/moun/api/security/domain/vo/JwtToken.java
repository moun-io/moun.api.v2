package io.moun.api.security.domain.vo;

import lombok.*;
import org.springframework.security.core.token.Token;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public final class JwtToken {
    private String value;
}
