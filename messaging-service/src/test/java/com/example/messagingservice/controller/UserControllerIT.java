package com.example.messagingservice.controller;

import com.example.messagingservice.request.BlockUserRequest;
import com.example.messagingservice.request.LoginUserRequest;
import com.example.messagingservice.request.RegisterUserRequest;
import com.example.messagingservice.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerIT extends BaseIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void should_register_user() {
        String URL = "/user/register";

        RegisterUserRequest request = new RegisterUserRequest();
        request.setUserName("username");
        request.setPassword("password");
        request.setEmail("email");

        //when
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Response> responseEntity = testRestTemplate
                .exchange(URL, HttpMethod.POST, new HttpEntity<>(request, headers), Response.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void should_login_user() {
        String URL = "/user/login";

        LoginUserRequest request = new LoginUserRequest();
        request.setUserName("username");
        request.setPassword("password");

        //when
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Response> responseEntity = testRestTemplate
                .exchange(URL, HttpMethod.POST, new HttpEntity<>(request, headers), Response.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void should_block_user() {
        String URL = "/user/block";

        BlockUserRequest request = new BlockUserRequest();
        request.setBlockedUserId(1L);
        request.setUsername("username");

        //when
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Response> responseEntity = testRestTemplate
                .exchange(URL, HttpMethod.POST, new HttpEntity<>(request, headers), Response.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}