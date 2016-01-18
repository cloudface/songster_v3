package com.samples.songster.search.usecase;

import com.samples.songster.search.repository.dto.SongDto;

/**
 * Created by chrisbraunschweiler1 on 23/11/15.
 */
public interface UseCase {
    void purchaseSong(SongDto song);

    void login(String username, String password, SongDto songDto);

    interface UseCaseListener {

        void showLoginView(SongDto songDto);

        void showPurchaseSuccsessMessage(SongDto song);
    }
}
