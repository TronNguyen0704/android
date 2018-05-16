package com.fresher.tronnv.research.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.model.MusicLyric;

import java.util.List;

public class LyricsFragment extends Fragment{
    private int mIndex;
    private List<MusicLyric> musicLyrics;
    public LyricsFragment(){

    }

    public void setMusicLyrics(List<MusicLyric> musicLyrics) {
        this.musicLyrics = musicLyrics;
    }
    public void setmIndex(int index){
        this.mIndex = index;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lyric,container,false);
        final TextView textViewName = rootView.findViewById(R.id.txt_name);
        final TextView textViewAuthor = rootView.findViewById(R.id.txt_author);
        final TextView textViewLyric = rootView.findViewById(R.id.txt_lyric);
        if(DataManager.musicLyrics!= null){
            textViewName.setText(musicLyrics.get(mIndex).getName());
            textViewAuthor.setText(musicLyrics.get(mIndex).getAuthor());
            textViewLyric.setText(musicLyrics.get(mIndex).getLyric());
        }
        return rootView;
    }
}
