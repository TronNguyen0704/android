package com.fresher.tronnv.research.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.service.NotificationService;
import com.fresher.tronnv.research.viewmodel.MusicViewModel;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
public class LyricsFragment extends Fragment{
    private int mIndex;
    private List<MusicLyric> musicLyrics;
    private TextView textViewName ;
    private TextView textViewAuthor ;
    private TextView textViewLyric;
    private Context context;
    public interface LoadFinish{
        void onFinish(String name, String author);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof LoadFinish){
            this.context = context;
        }
    }

    public LyricsFragment(){
        musicLyrics = new ArrayList<>();
    }

    public List<MusicLyric> getMusicLyrics() {
        return musicLyrics;
    }

    public void setMusicLyrics(List<MusicLyric> musicLyrics) {
        this.musicLyrics = musicLyrics;
    }
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
                    setMusicLyrics(musicLyrics);
                    ((LoadFinish)context).onFinish( musicLyrics.get(mIndex).getName(), musicLyrics.get(mIndex).getAuthor());
                    textViewName.setText("Bài hát: "+ musicLyrics.get(mIndex).getName());
                    textViewAuthor.setText("Nhạc sĩ: " +musicLyrics.get(mIndex).getAuthor());
                    textViewLyric.setText(musicLyrics.get(mIndex).getLyric());
                }
                else{
                }
            }
        });
    }
    public void setmIndex(int index){
        this.mIndex = index;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lyric,container,false);
        textViewName = rootView.findViewById(R.id.tv_name);
        textViewAuthor = rootView.findViewById(R.id.tv_author);
        textViewLyric = rootView.findViewById(R.id.tv_lyric);
        if(musicLyrics!= null && musicLyrics.size() > 0){
            textViewName.setText("Bài hát: "+ musicLyrics.get(mIndex).getName());
            textViewAuthor.setText("Nhạc sĩ: " +musicLyrics.get(mIndex).getAuthor());
            textViewLyric.setText(musicLyrics.get(mIndex).getLyric());
        }
        return rootView;
    }
}
