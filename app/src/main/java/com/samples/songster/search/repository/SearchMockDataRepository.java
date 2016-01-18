package com.samples.songster.search.repository;

import com.samples.songster.search.repository.dto.AuthorizationDto;
import com.samples.songster.search.repository.dto.CheckoutDto;
import com.samples.songster.search.repository.dto.SearchResultDto;
import com.samples.songster.search.repository.dto.SongDto;
import com.samples.songster.search.repository.dto.UserDto;

/**
 * Created by chrisbraunschweiler1 on 30/11/15.
 */
public class SearchMockDataRepository implements SearchRepository {
    @Override
    public void search(String searchString, SearchListener listener) {
        SearchResultDto resultDto = new SearchResultDto();
        SongDto song1 = new SongDto();
        song1.setAlbum("Album 1");
        song1.setArtist("Artist 1");
        song1.setName("Song 1");
        resultDto.getSongs().add(song1);

        SongDto song2 = new SongDto();
        song2.setAlbum("Album 2");
        song2.setArtist("Artist 2");
        song2.setName("Song 2");
        resultDto.getSongs().add(song2);

        SongDto song3 = new SongDto();
        song3.setAlbum("Album 3");
        song3.setArtist("Artist 3");
        song3.setName("Song 3");
        resultDto.getSongs().add(song3);

        SongDto song4 = new SongDto();
        song4.setAlbum("Album 4");
        song4.setArtist("Artist 4");
        song4.setName("Song 4");
        resultDto.getSongs().add(song4);

        SongDto song5 = new SongDto();
        song5.setAlbum("Album 5");
        song5.setArtist("Artist 5");
        song5.setName("Song 5");
        resultDto.getSongs().add(song5);

        listener.onSearchSuccess(resultDto);
    }

    @Override
    public void addSongToMyList(SongDto song, SearchListener listener) {
    }

    @Override
    public void checkout(SongDto song, SearchListener listener) {
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setLoginRequired(true);
        listener.onCheckoutSuccess(checkoutDto, song);
    }

    @Override
    public void authorizePurchase(UserDto loggedInUser, SongDto songDto, SearchListener listener) {
        AuthorizationDto authorizationDto = new AuthorizationDto();
        authorizationDto.setPurchaseAuthorized(true);
        listener.onAuthorizationSuccess(authorizationDto, songDto);
    }

    @Override
    public void login(String username, String password, SongDto songDto, SearchListener listener) {
        UserDto user = new UserDto();
        user.setUsername(username);
        user.setPassword(password);
        listener.onLoginSuccess(user, songDto);
    }

    @Override
    public void purchase(SongDto songDto, SearchListener listener) {
        listener.onPurchaseSuccess(songDto);
    }
}
