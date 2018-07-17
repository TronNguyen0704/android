package com.fresher.tronnv.research.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fresher.tronnv.android_models.MusicLyric;
import com.fresher.tronnv.research.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
public class PlayListAdapter extends BaseAdapter {

    private Context mContext;
    private List<MusicLyric> mMusicLyrics;
    private List<MusicLyric> mMusicLyricsShow;

    public void setMusicLyrics(List<MusicLyric> musicLyrics) {
        this.mMusicLyrics.addAll(musicLyrics);
        this.mMusicLyricsShow.addAll(musicLyrics);
    }

    public PlayListAdapter(Context context) {
        this.mContext = context;
        this.mMusicLyrics = new ArrayList<>();
        this.mMusicLyricsShow = new ArrayList<>();
    }

    @Override
    public int getCount() {
        if (mMusicLyricsShow != null)
            return mMusicLyricsShow.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mMusicLyrics != null)
            return mMusicLyrics.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mMusicLyrics != null)
            return mMusicLyrics.get(position).getId();
        return 0;
    }

    public List<MusicLyric> getFilter(String filter) {
        mMusicLyricsShow.clear();
        for (int i = 0; i < mMusicLyrics.size(); i++) {
            MusicLyric musicLyric = mMusicLyrics.get(i);
            if (musicLyric.getName().toLowerCase().contains(filter.toLowerCase())) {
                mMusicLyricsShow.add(musicLyric);
            }

        }
        return mMusicLyricsShow;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);

        TextView author = view.findViewById(R.id.tv_author);
        TextView name = view.findViewById(R.id.tv_name);
        ImageView avatar = view.findViewById(R.id.img_avatar);
        if ((mMusicLyricsShow != null && mMusicLyricsShow.size() > 0)) {
            author.setText(mMusicLyricsShow.get(position).getAuthor());
            name.setText(mMusicLyricsShow.get(position).getName());
            Glide.with(view.getContext())
                    .load(mMusicLyricsShow.get(position).getAvatar())
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
                    .into(avatar);
            Glide.get(view.getContext()).clearMemory();
        } else {
            view.setVisibility(View.GONE);
        }
        return view;
    }

}
