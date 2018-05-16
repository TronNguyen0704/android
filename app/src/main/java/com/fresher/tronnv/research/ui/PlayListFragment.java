package com.fresher.tronnv.research.ui;


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

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.Utils;
import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.model.MusicLyric;
import com.fresher.tronnv.research.network.RequestLyricInterface;

import java.util.List;

public class PlayListFragment extends Fragment {
    public interface OnItemLyricClickListener {
        void onItemSelected(int position);
    }
    OnItemLyricClickListener mCallback;
    List<MusicLyric> lyrics;
    public PlayListFragment(){
        //Request API and GetData
        lyrics =  Utils.loadJSON();
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

        ListView listView = rootView.findViewById(R.id.list_item);

        PlayListAdapter playListAdapter = new PlayListAdapter(getContext(), lyrics);

        listView.setAdapter(playListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onItemSelected(position);
            }
        });
        return rootView;
    }
}
