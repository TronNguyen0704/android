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
import com.fresher.tronnv.research.presenters.ApplicationPresenter;
import com.fresher.tronnv.research.presenters.ApplicationPresenterImpl;
import com.fresher.tronnv.research.ui.adapter.PlayListAdapter;
import com.fresher.tronnv.research.viewmodel.MusicViewModel;
import com.fresher.tronnv.research.viewmodel.RecordChartViewModel;
import com.fresher.tronnv.research.viewmodel.TrackViewModel;

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
    List<MusicLyric> lyrics ;
    List<MusicLyric> musicLyricsShow ;
    private boolean isLoading = true;
    private SearchView searchView;
    private PlayListAdapter playListAdapter;
    public void setSearchView(SearchView searchView){
        this.searchView = searchView;
    }
    public PlayListFragment(){
        lyrics = new ArrayList<>();
        musicLyricsShow = new ArrayList<>();
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
                    playListAdapter.setMusicLyrics(musicLyrics);
                    lyrics.addAll(musicLyrics);
                    musicLyricsShow.addAll(musicLyrics);
                    playListAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
                else{
                    isLoading = true;
                }
            }
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(musicLyricsShow.size() > 0) {
//            lyrics.addAll(musicLyricsShow);
//        }
//        else {
//            lyrics.addAll(applicationPresenter.requestMusic());
//            musicLyricsShow.addAll(applicationPresenter.requestMusic());
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

        //final SearchView searchView = rootView.findViewById(R.id.search_view);
        searchView.setActivated(true);
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.setFocusable(false);
        searchView.clearFocus();

        final ListView listView = rootView.findViewById(R.id.recycler_view);

         playListAdapter = new PlayListAdapter(getContext());
        //listView.setDivider(null);
        listView.setAdapter(playListAdapter);
        final String[] filter = {""};
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter[0] = newText;
                musicLyricsShow.clear();
                musicLyricsShow.addAll(playListAdapter.getFilter(newText));
                playListAdapter.notifyDataSetChanged();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onItemSelected(position,musicLyricsShow.get(position).getId(),filter[0]);

            }
        });
        return rootView;
    }
}
