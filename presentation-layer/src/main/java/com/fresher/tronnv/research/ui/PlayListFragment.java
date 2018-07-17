package com.fresher.tronnv.research.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.ui.adapter.PlayListAdapter;
import com.fresher.tronnv.research.viewmodel.MusicViewModel;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */

/**
 * A class PlayListFragment
 */
public class PlayListFragment extends Fragment {
    //Interface to transfer data between two fragment
    public interface OnItemLyricClickListener {
        void onItemSelected(int position, int idSong, String filter);
    }
    OnItemLyricClickListener mCallback;
    List<MusicLyric> mLyrics;
    List<MusicLyric> mMusicLyricsShow;
    private SearchView mSearchView;
    private PlayListAdapter mPlayListAdapter;
    public void setmSearchView(SearchView mSearchView){
        this.mSearchView = mSearchView;
    }
    public PlayListFragment(){
        mLyrics = new ArrayList<>();
        mMusicLyricsShow = new ArrayList<>();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final MusicViewModel viewModel =
                ViewModelProviders.of(this).get(MusicViewModel.class);
        subscribeUI(viewModel);
    }
    private void subscribeUI(MusicViewModel viewModel){
        viewModel.getMusics().observe(this, new Observer<List<MusicLyric>>() {
            @Override
            public void onChanged(@Nullable List<MusicLyric> musicLyrics) {
                if (musicLyrics!= null){
                    mPlayListAdapter.setMusicLyrics(musicLyrics);
                    mLyrics.addAll(musicLyrics);
                    mMusicLyricsShow.addAll(musicLyrics);
                    mPlayListAdapter.notifyDataSetChanged();
                }
                else{
                }
            }
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(mMusicLyricsShow.size() > 0) {
//            mLyrics.addAll(mMusicLyricsShow);
//        }
//        else {
//            mLyrics.addAll(applicationPresenter.requestMusic());
//            mMusicLyricsShow.addAll(applicationPresenter.requestMusic());
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnItemLyricClickListener) context;
        }
        catch (ClassCastException  e){
            throw new ClassCastException(context.toString() + "must implement OnItemClickListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_play_list,container,false);

        //final SearchView mSearchView = rootView.findViewById(R.id.search_view);
        mSearchView.setActivated(true);
        mSearchView.onActionViewExpanded();
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        final ListView listView = rootView.findViewById(R.id.recycler_view);

         mPlayListAdapter = new PlayListAdapter(getContext());
        //listView.setDivider(null);
        listView.setAdapter(mPlayListAdapter);
        final String[] filter = {""};
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter[0] = newText;
                mMusicLyricsShow.clear();
                mMusicLyricsShow.addAll(mPlayListAdapter.getFilter(newText));
                mPlayListAdapter.notifyDataSetChanged();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onItemSelected(position, mMusicLyricsShow.get(position).getId(),filter[0]);

            }
        });
        return rootView;
    }
}
