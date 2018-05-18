package com.fresher.tronnv.research.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.model.MusicLyric;

import java.util.ArrayList;
import java.util.List;

import static com.fresher.tronnv.research.Utils.musicLyricsShow;

/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
public class PlayListAdapter extends BaseAdapter{

    private Context context;
    private List<MusicLyric> musicLyrics;
    private List<String> names;
    private List<String> authors;
    public PlayListAdapter(Context context, List<MusicLyric> lyrics){
        this.context = context;
        if(lyrics!= null) {
            this.musicLyrics = new ArrayList<>();
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
    public void getFilter(String filter){
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
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);

        TextView author = view.findViewById(R.id.txt_author);
        TextView name = view.findViewById(R.id.txt_name);
        if(authors!= null && authors.size() != 0 && authors.size() > position) {
            author.setText(authors.get(position ));
            name.setText(names.get(position));
        }
        else{
            view.setVisibility(View.GONE);
        }
        return view;
    }
}
