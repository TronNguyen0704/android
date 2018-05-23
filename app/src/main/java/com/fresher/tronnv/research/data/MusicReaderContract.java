package com.fresher.tronnv.research.data;

import android.provider.BaseColumns;

public final class MusicReaderContract {
    private MusicReaderContract(){}
    public static class MusicEntry implements BaseColumns {
        public static final String TABLE_NAME = "music";
        public static final String COLUMN_NAME_LYRICID = "lyricid";
        public static final String COLUMN_NAME_TITLE = "name";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_LYRIC = "lyric";
        public static final String COLUMN_NAME_AVATAR = "avatar";



    }
}
