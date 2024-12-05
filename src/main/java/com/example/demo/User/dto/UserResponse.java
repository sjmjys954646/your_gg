package com.example.demo.User.dto;

import com.example.demo.User.entity.User;
import lombok.Getter;

public class UserResponse {

   @Getter
    public static class UserResponseDTO{
        String puuid;

       public UserResponseDTO(User user) {
           this.puuid = user.getPuuid();
       }
   }
}
