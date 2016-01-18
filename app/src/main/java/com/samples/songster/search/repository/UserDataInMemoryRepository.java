package com.samples.songster.search.repository;

import com.samples.songster.search.repository.dto.UserDto;

/**
 * Created by chrisbraunschweiler1 on 23/11/15.
 */
public class UserDataInMemoryRepository implements UserDataRepository {
    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public void login(String username, String password) {
    }

    @Override
    public UserDto getLoggedInUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("BertBernankee");
        userDto.setPassword("bertisgreat123");
        return userDto;
    }
}
