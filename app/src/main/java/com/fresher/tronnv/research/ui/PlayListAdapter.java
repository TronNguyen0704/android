package com.fresher.tronnv.research.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.model.MusicLyric;

import java.util.List;
public class PlayListAdapter extends BaseAdapter {

    private Context context;
    private List<MusicLyric> musicLyrics;
    public PlayListAdapter(Context context, List<MusicLyric> lyrics){
        this.context = context;
        this.musicLyrics = lyrics;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView author = convertView.findViewById(R.id.txt_author);
        TextView name = convertView.findViewById(R.id.txt_name);
        author.setText(musicLyrics.get(position).getAuthor());
        name.setText(musicLyrics.get(position).getName());
        return convertView;
    }
}
