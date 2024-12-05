package com.example.demo.User;

import com.example.demo.User.dto.UsersResponse;
import com.example.demo.User.entity.Users;
import com.example.demo.User.repository.UsersRepository;
import com.example.demo.User.service.UsersService;
import com.example.demo.Utils.Exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UsersService usersService;
    @Autowired
    UsersRepository usersRepository;

    @Test
    @DisplayName("유저의 puuid를 받아옵니다.(RIOT API요청)")
    void getUserPUUIDSuccessTest(){
        //given
        String username = "더유자소전";
        String tag = "KR1";

        //when
        UsersResponse.UserResponseDTO user = usersService.getUserPUUID(username, tag);

        assertThat(user.getPuuid()).isEqualTo("WM-w8SKG3RATuOSi41mOugd7lMWhjMHuGfGjvwrTzDD76xIs5uvsrFbepjrzgM2AMlSXDG921wCNIg");
    }

    @Test
    @DisplayName("존재하지 않는 유저의 puuid를 받아옵니다.(RIOT API요청)")
    void getUserPUUIDFailTest1(){
        //given
        String username = "더유자소전";
        String tag = "KR2";

        assertThatThrownBy(() -> usersService.getUserPUUID(username, tag))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("RIOT API 아이디가 없음");
    }

    @Test
    @DisplayName("유저의 이미 존재하는 puuid를 받아옵니다.")
    void getUserPUUIDExistSuccessTest(){
        //given
        String username = "더유자소전";
        String tag = "KR1";
        String riotpuuid = "WM-w8SKG3RATuOSi41mOugd7lMWhjMHuGfGjvwrTzDD76xIs5uvsrFbepjrzgM2AMlSXDG921wCNIg";

        Users newUsers = Users.toEntity(username, tag, riotpuuid);
        usersRepository.save(newUsers);

        //when
        UsersResponse.UserResponseDTO user = usersService.getUserPUUID(username, tag);

        assertThat(user.getPuuid()).isEqualTo("WM-w8SKG3RATuOSi41mOugd7lMWhjMHuGfGjvwrTzDD76xIs5uvsrFbepjrzgM2AMlSXDG921wCNIg");
    }
}
