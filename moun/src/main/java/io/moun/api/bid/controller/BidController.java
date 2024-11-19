package io.moun.api.bid.controller;

import io.moun.api.security.service.IJwtTokenHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bids")
@RequiredArgsConstructor
public class BidController {
    private final IJwtTokenHelper jwtTokenHelper;

}
