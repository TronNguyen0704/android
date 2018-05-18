package com.fresher.tronnv.research.ui;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.model.MusicLyric;
import com.fresher.tronnv.research.network.RequestLyricInterface;

import java.util.List;
/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */

/**
 * A class PlayListFragment
 */
public class PlayListFragment extends Fragment {
    public interface OnItemLyricClickListener {
        void onItemSelected(int position);
    }
    OnItemLyricClickListener mCallback;
    List<MusicLyric> lyrics;
    public PlayListFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lyrics =  Utils.musicLyrics;
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

        SearchView searchView = rootView.findViewById(R.id.search_view);
        searchView.setActivated(true);
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.clearFocus();

        ListView listView = rootView.findViewById(R.id.list_item);

        final PlayListAdapter playListAdapter = new PlayListAdapter(getContext(), lyrics);
        listView.setDivider(null);
        listView.setAdapter(playListAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                playListAdapter.getFilter(newText);
                playListAdapter.notifyDataSetChanged();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onItemSelected(position);
            }
        });
        return rootView;
    }
}
