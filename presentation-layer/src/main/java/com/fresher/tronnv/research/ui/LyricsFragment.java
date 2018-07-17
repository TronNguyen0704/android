package com.fresher.tronnv.research.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.viewmodel.MusicViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
public class LyricsFragment extends Fragment {
    private int mIndex;
    private List<MusicLyric> mMusicLyrics;
    private TextView mTextViewName;
    private TextView mTextViewAuthor;
    private TextView mTextViewLyric;
    private Context mContext;

    public interface LoadFinish {
        void onFinish(String name, String author);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoadFinish) {
            this.mContext = context;
        }
    }

    public LyricsFragment() {

        mMusicLyrics = new ArrayList<>();
    }

    public List<MusicLyric> getmMusicLyrics() {
        return mMusicLyrics;
    }

    public void setmMusicLyrics(List<MusicLyric> mMusicLyrics) {
        this.mMusicLyrics = mMusicLyrics;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final MusicViewModel viewModel =
                ViewModelProviders.of(this).get(MusicViewModel.class);
        subscribeUI(viewModel);
    }

    private void subscribeUI(MusicViewModel viewModel) {
        viewModel.getMusics().observe(this, musicLyrics -> {
            if (musicLyrics != null) {
                setmMusicLyrics(musicLyrics);
                ((LoadFinish) mContext).onFinish(musicLyrics.get(mIndex).getName(), musicLyrics.get(mIndex).getAuthor());
                mTextViewName.setText("Bài hát: " + musicLyrics.get(mIndex).getName());
                mTextViewAuthor.setText("Nhạc sĩ: " + musicLyrics.get(mIndex).getAuthor());
                mTextViewLyric.setText(musicLyrics.get(mIndex).getLyric());
            } else {
            }
        });
    }

    public void setmIndex(int index) {
        this.mIndex = index;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lyric, container, false);
        mTextViewName = rootView.findViewById(R.id.tv_name);
        mTextViewAuthor = rootView.findViewById(R.id.tv_author);
        mTextViewLyric = rootView.findViewById(R.id.tv_lyric);
        if (mMusicLyrics != null && mMusicLyrics.size() > 0) {
            mTextViewName.setText("Bài hát: " + mMusicLyrics.get(mIndex).getName());
            mTextViewAuthor.setText("Nhạc sĩ: " + mMusicLyrics.get(mIndex).getAuthor());
            mTextViewLyric.setText(mMusicLyrics.get(mIndex).getLyric());
        }
        return rootView;
    }
}
