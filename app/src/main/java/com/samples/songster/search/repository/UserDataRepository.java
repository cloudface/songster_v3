package com.samples.songster.search.repository;

import com.samples.songster.search.repository.dto.UserDto;

/**
 * Created by chrisbraunschweiler1 on 23/11/15.
 */
public interface UserDataRepository {
    boolean isLoggedIn();

    void login(String username, String password);

    UserDto getLoggedInUser();
}
