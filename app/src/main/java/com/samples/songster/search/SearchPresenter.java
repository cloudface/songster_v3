package com.samples.songster.search;

import android.support.v7.widget.helper.ItemTouchHelper;

import com.samples.songster.search.repository.SearchRepository;
import com.samples.songster.search.repository.UserDataRepository;
import com.samples.songster.search.repository.dto.AuthorizationDto;
import com.samples.songster.search.repository.dto.CheckoutDto;
import com.samples.songster.search.repository.dto.SearchResultDto;
import com.samples.songster.search.repository.dto.SongDto;
import com.samples.songster.search.repository.dto.UserDto;
import com.samples.songster.search.usecase.SearchUseCase;
import com.samples.songster.search.usecase.UseCase;

/**
 * Created by chrisbraunschweiler1 on 02/11/15.
 */
public class SearchPresenter implements SearchRepository.SearchListener, UseCase.UseCaseListener {

    private ViewModel mViewModel;
    private UseCase mUseCase;
    private SearchRepository mSearchRepository;
    private SearchView mSearchView;

    public SearchPresenter(ViewModel viewModel, SearchRepository searchRepository, UserDataRepository userDataRepository, SearchView searchView){
        this.mViewModel = viewModel;
        mSearchRepository = searchRepository;
        mSearchView = searchView;
        mUseCase = new SearchUseCase(searchRepository, userDataRepository, this);
    }

    public void onSearch(String searchString) {
        if(searchString != null && !searchString.isEmpty()){
            mSearchView.showProgressBar();
            mSearchView.hideKeyboard();
            mSearchRepository.search(searchString, this);
        }
    }

    @Override
    public void onSearchSuccess(SearchResultDto result) {
        if(result != null && result.getSongs().size() > 0){
            mViewModel.createDisplayableResults(result);
            mSearchView.showResults();
        } else {
            mSearchView.showNoResultsMessage();
        }
    }

    @Override
    public void onAddSongSuccess(SongDto addedSong) {
        //Nothing to do
    }

    @Override
    public void onCheckoutSuccess(CheckoutDto checkoutDto, SongDto songDto) {
        //Nothing to do
    }

    @Override
    public void onAuthorizationSuccess(AuthorizationDto authorizationDto, SongDto songDto) {

    }

    @Override
    public void onLoginSuccess(UserDto userDto, SongDto songDto) {

    }

    @Override
    public void onPurchaseSuccess(SongDto song) {
    }

    public void present() {
        if(mViewModel != null){
            if(mViewModel.getItems().size() > 0) {
                mSearchView.showResults();
            } else {
                mSearchView.showInfoMessage();
            }
        } else {
            mSearchView.showInfoMessage();
        }
    }

    public void onAddSongToMyList(int position) {
        SongDto selectedSong = mViewModel.selectSong(position);
        mSearchRepository.addSongToMyList(selectedSong, this);
        mSearchView.updateResults();
    }

    public void onSwipedItem(int position, int swipeDir) {
        if(swipeDir == ItemTouchHelper.LEFT){
            //Display some other fragment to demonstrate benefit of viewmodel being parcellable
            mSearchView.displaySongDetails();
        } else {
            SongDto swipedSong = mViewModel.selectSong(position);
            mUseCase.purchaseSong(swipedSong);
        }
    }

    @Override
    public void showLoginView(SongDto songDto) {
        mViewModel.setSongToBePurchased(songDto);
        mSearchView.showLoginView();
    }

    @Override
    public void showPurchaseSuccsessMessage(SongDto song) {
        mSearchView.showPurchaseSuccessMessage(song);
    }

    public void login(String username, String password) {
        mUseCase.login(username, password, mViewModel.getSongToBePurchased());
    }
}
