package com.example.demo.User.dto;

import com.example.demo.User.entity.Users;
import lombok.Getter;

public class UsersResponse {

   @Getter
    public static class UserResponseDTO{
        String puuid;

       public UserResponseDTO(Users users) {
           this.puuid = users.getPuuid();
       }
   }
}
