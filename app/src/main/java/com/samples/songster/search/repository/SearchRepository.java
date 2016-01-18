package com.samples.songster.search.repository;

import com.samples.songster.search.repository.dto.AuthorizationDto;
import com.samples.songster.search.repository.dto.CheckoutDto;
import com.samples.songster.search.repository.dto.SearchResultDto;
import com.samples.songster.search.repository.dto.SongDto;
import com.samples.songster.search.repository.dto.UserDto;

/**
 * Created by chrisbraunschweiler1 on 02/11/15.
 */
public interface SearchRepository {

    void search(String searchString, SearchListener listener);

    void addSongToMyList(SongDto song, SearchListener listener);

    void checkout(SongDto song, SearchListener listener);

    void authorizePurchase(UserDto loggedInUser, SongDto songDto, SearchListener listener   );

    void login(String username, String password, SongDto songDto, SearchListener listener);

    void purchase(SongDto songDto, SearchListener listener);

    interface SearchListener{
        void onSearchSuccess(SearchResultDto result);

        void onAddSongSuccess(SongDto addedSong);

        void onCheckoutSuccess(CheckoutDto checkoutDto, SongDto songDto);

        void onAuthorizationSuccess(AuthorizationDto authorizationDto, SongDto songDto);

        void onLoginSuccess(UserDto userDto, SongDto songDto);

        void onPurchaseSuccess(SongDto song);
    }
}
