package com.samples.songster.search.usecase;

import com.samples.songster.search.repository.SearchRepository;
import com.samples.songster.search.repository.UserDataRepository;
import com.samples.songster.search.repository.dto.AuthorizationDto;
import com.samples.songster.search.repository.dto.CheckoutDto;
import com.samples.songster.search.repository.dto.SearchResultDto;
import com.samples.songster.search.repository.dto.SongDto;
import com.samples.songster.search.repository.dto.UserDto;

/**
 * Created by chrisbraunschweiler1 on 23/11/15.
 */
public class SearchUseCase implements UseCase, SearchRepository.SearchListener {

    private SearchRepository mSearchRepository;
    private UserDataRepository mUserDataRepository;
    private UseCaseListener mListener;

    public SearchUseCase(SearchRepository searchRepository, UserDataRepository userDataRepository, UseCaseListener listener){
        mSearchRepository = searchRepository;
        mUserDataRepository = userDataRepository;
        mListener = listener;
    }

    @Override
    public void purchaseSong(SongDto song) {
        mSearchRepository.checkout(song, this);
    }

    @Override
    public void login(String username, String password, SongDto songDto) {
        mSearchRepository.login(username, password, songDto, this);
    }

    @Override
    public void onSearchSuccess(SearchResultDto result) {
        //Nothing to do here
    }

    @Override
    public void onAddSongSuccess(SongDto addedSong) {
        //Nothing to do here
    }

    @Override
    public void onCheckoutSuccess(CheckoutDto checkoutDto, SongDto songDto) {
        if(checkoutDto.isLoginRequired()){
            if(mUserDataRepository.isLoggedIn()){
                UserDto loggedInUser = mUserDataRepository.getLoggedInUser();
                mSearchRepository.authorizePurchase(loggedInUser, songDto, this);
            } else {
                mListener.showLoginView(songDto);
            }
        } else {
            mSearchRepository.purchase(songDto, this);
        }
    }

    @Override
    public void onAuthorizationSuccess(AuthorizationDto authorizationDto, SongDto songDto) {
        if(authorizationDto.isPurchaseAuthorized()){
            mSearchRepository.purchase(songDto, this);
        } else {
            //TODO show an error dialog
        }
    }

    @Override
    public void onLoginSuccess(UserDto userDto, SongDto songDto) {
        mSearchRepository.authorizePurchase(userDto, songDto, this);
    }

    @Override
    public void onPurchaseSuccess(SongDto song) {
        mListener.showPurchaseSuccsessMessage(song);
    }
}
