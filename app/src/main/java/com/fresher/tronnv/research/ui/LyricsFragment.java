package com.fresher.tronnv.research.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.activities.LyricActivity;
import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.model.MusicLyric;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
public class LyricsFragment extends Fragment {
    private int mIndex;
    private List<MusicLyric> musicLyrics;
    private TextView textViewName ;
    private TextView textViewAuthor ;
    private TextView textViewLyric;
    public LyricsFragment(){
        musicLyrics = new ArrayList<>();
    }

    public void setMusicLyrics(List<MusicLyric> musicLyrics) {
        this.musicLyrics.addAll(musicLyrics);
    }
    public void setmIndex(int index){
        this.mIndex = index;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lyric,container,false);
        textViewName = rootView.findViewById(R.id.txt_name);
        textViewAuthor = rootView.findViewById(R.id.txt_author);
        textViewLyric = rootView.findViewById(R.id.txt_lyric);

        if(musicLyrics!= null && musicLyrics.size() > 0){
            textViewName.setText("Bài hát: "+ musicLyrics.get(mIndex).getName());
            textViewAuthor.setText("Nhạc sĩ: " +musicLyrics.get(mIndex).getAuthor());
            textViewLyric.setText(musicLyrics.get(mIndex).getLyric());
        }
        return rootView;
    }
}
