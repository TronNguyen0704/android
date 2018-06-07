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
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.model.MusicLyric;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
public class PlayListAdapter extends BaseAdapter{

    private Context context;
    private List<MusicLyric> musicLyrics;
    private List<MusicLyric> musicLyricsShow;
    private List<String> names;
    private List<String> authors;
    public PlayListAdapter(Context context, List<MusicLyric> lyrics){
        this.context = context;
        if(lyrics!= null) {
            this.musicLyrics = new ArrayList<>();
            musicLyricsShow = new ArrayList<>();
            names = new ArrayList<>();
            authors = new ArrayList<>();
            for (int i = 0; i < lyrics.size(); i++) {
                names.add(lyrics.get(i).getName());
                authors.add(lyrics.get(i).getAuthor());
                this.musicLyrics.add(lyrics.get(i));
                musicLyricsShow.add(lyrics.get(i));
            }
        }
    }
    @Override
    public int getCount() {
        if(musicLyrics!= null)
            return musicLyrics.size();
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
        names.clear();
        authors.clear();
        musicLyricsShow.clear();
        for(int i = 0; i < musicLyrics.size(); i++){
            MusicLyric musicLyric = musicLyrics.get(i);
            if(musicLyric.getName().toLowerCase().contains(filter.toLowerCase())){
                names.add(musicLyric.getName());
                authors.add(musicLyric.getAuthor());
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
        if(authors!= null && authors.size() != 0 && authors.size() > position) {
            author.setText(authors.get(position ));
            name.setText(names.get(position));
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
