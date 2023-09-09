package com.auth.client.controller;

import com.auth.library.exception.UnAuthenticatedException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/utility")
public class UtilityController {

    @GetMapping("/get-routes")
    public ResponseEntity<?> getAppRoutes(Principal principal)
            throws UnAuthenticatedException {

        if(principal == null || principal.getName() == null) {
            throw new UnAuthenticatedException("UserDetails not found");
        }

        String userId = principal.getName();

        Map<String, Object> user = new HashMap<>();
        String userBase = "/api/user";
        user.put("base", userBase);
        user.put("getUser", userBase + "/me");
        user.put("profileImage", userBase + "/" + userId + "/profile-image");

        Map<String, Object> client = new HashMap<>();
        String clientBase = userBase + "/" + userId + "/client";
        client.put("base", clientBase);

        Routes routes = new Routes(user, client);
        return ResponseEntity.ok(routes);
    }

    record Routes(Map<String, Object> user, Map<String, Object> client) {}
}
