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
public class PlayListAdapter extends BaseAdapter{

    private Context context;
    private List<MusicLyric> musicLyrics;
    private List<MusicLyric> musicLyricsShow;

    public void setMusicLyrics(List<MusicLyric> musicLyrics) {
        this.musicLyrics.addAll(musicLyrics);
        this.musicLyricsShow.addAll(musicLyrics);
    }

    public PlayListAdapter(Context context){
        this.context = context;
        this.musicLyrics = new ArrayList<>();
        this.musicLyricsShow = new ArrayList<>();
    }
    @Override
    public int getCount() {
        if(musicLyricsShow!= null)
            return musicLyricsShow.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(musicLyrics!= null)
            return musicLyrics.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(musicLyrics!= null)
            return musicLyrics.get(position).getId();
        return 0;
    }
    public List<MusicLyric> getFilter(String filter){
        musicLyricsShow.clear();
        for(int i = 0; i < musicLyrics.size(); i++){
            MusicLyric musicLyric = musicLyrics.get(i);
            if(musicLyric.getName().toLowerCase().contains(filter.toLowerCase())){
                musicLyricsShow.add(musicLyric);
            }

        }
        return musicLyricsShow;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);

        TextView author = view.findViewById(R.id.tv_author);
        TextView name = view.findViewById(R.id.tv_name);
        ImageView avatar = view.findViewById(R.id.img_avatar);
        if((musicLyricsShow != null && musicLyricsShow.size() > 0)) {
            author.setText(musicLyricsShow.get(position).getAuthor());
            name.setText(musicLyricsShow.get(position).getName());
            Glide.with(view.getContext())
                    .load(musicLyricsShow.get(position).getAvatar())
                    .apply(RequestOptions.bitmapTransform(
                            new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
                    .into(avatar);
            Glide.get(view.getContext()).clearMemory();
        }
        else{
            view.setVisibility(View.GONE);
        }
        return view;
    }

}
