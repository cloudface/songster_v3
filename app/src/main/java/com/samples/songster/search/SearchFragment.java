package com.samples.songster.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.samples.songster.R;
import com.samples.songster.search.repository.SearchMockDataRepository;
import com.samples.songster.search.repository.UserDataInMemoryRepository;
import com.samples.songster.search.repository.dto.SongDto;

/**
 * Created by chrisbraunschweiler1 on 26/10/15.
 */
public class SearchFragment extends Fragment implements SearchView {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private EditText mSearchEditText;
    private ViewModel mViewModel;
    private SearchPresenter mPresenter;
    private SearchAdapter mSearchAdapter;
    private ProgressBar mProgressBar;
    private TextView mInfoText;
    private TextView mNoResultsText;
    private SearchFragmentListener mListener;
    private View mLoginView;
    private TextView mLoginHintText;
    private TextView mUsernameText;
    private TextView mPasswordText;
    private TextView mLoginButton;
    private TextView mPurchaseSuccessMessage;

    public static SearchFragment getInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        //TODO pass arguments into bundle
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle args) {
        super.onCreate(args);
        //TODO retrieve arguments from bundle

        mViewModel = new SearchViewModel();
        mPresenter = new SearchPresenter(mViewModel, new SearchMockDataRepository(), new UserDataInMemoryRepository(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchEditText = (EditText) view.findViewById(R.id.searchEditText);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.searchedSongsRecyclerView);
        mInfoText = (TextView) view.findViewById(R.id.infoText);
        mNoResultsText = (TextView) view.findViewById(R.id.noResultsText);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mLoginView = view.findViewById(R.id.loginView);
        mLoginHintText = (TextView) view.findViewById(R.id.loginHintText);
        mUsernameText = (TextView) view.findViewById(R.id.usernameText);
        mPasswordText = (TextView) view.findViewById(R.id.passwordText);
        mLoginButton = (TextView) view.findViewById(R.id.loginButton);
        mPurchaseSuccessMessage = (TextView) view.findViewById(R.id.purchaseSuccessMessage);
        configureRecyclerView();
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mPresenter.onSearch(mSearchEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameText.getText().toString();
                String password = mPasswordText.getText().toString();
                mPresenter.login(username, password);
            }
        });

        mPresenter.present();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (SearchFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + SearchFragmentListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void configureRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSearchAdapter = new SearchAdapter(mViewModel.getItems(), new SearchAdapter.SearchAdapterListener() {
            @Override
            public void onSelectedItem(int position) {
                mPresenter.onAddSongToMyList(position);
            }
        });
        mRecyclerView.setAdapter(mSearchAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                mPresenter.onSwipedItem(viewHolder.getAdapterPosition(), swipeDir);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void displaySongDetails() {
        mListener.showSongDetails();
    }

    @Override
    public void showLoginView() {
        mLoginHintText.setText(getString(R.string.label_loginhint, mViewModel.getSongToBePurchased().getName()));
        mLoginView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPurchaseSuccessMessage(SongDto song) {
        mLoginView.setVisibility(View.GONE);
        mPurchaseSuccessMessage.setText(getString(R.string.label_purchasesuccessful, song.getName(), song.getArtist()));
        mPurchaseSuccessMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showResults() {
        mSearchAdapter.notifyDataSetChanged();
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mInfoText.setVisibility(View.GONE);
        mNoResultsText.setVisibility(View.GONE);
    }

    @Override
    public void showNoResultsMessage() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mInfoText.setVisibility(View.GONE);
        mNoResultsText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInfoMessage() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mNoResultsText.setVisibility(View.GONE);
        mInfoText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideKeyboard() {
        if (isAdded() && getActivity() != null) {
            // Check if no view has focus:
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void updateResults() {
        mSearchAdapter.notifyDataSetChanged();
    }

    public interface SearchFragmentListener {
        void showSongDetails();
    }
}
