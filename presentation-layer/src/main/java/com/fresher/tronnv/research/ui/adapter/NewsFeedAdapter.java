package com.fresher.tronnv.research.ui.adapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.ListPreloader.PreloadModelProvider;
import com.bumptech.glide.RequestBuilder;
import com.fresher.tronnv.research.ui.viewholders.FirstBodyItem;
import com.fresher.tronnv.research.ui.viewholders.FooterItem;
import com.fresher.tronnv.research.ui.viewholders.HeaderItem;
import com.fresher.tronnv.research.ui.viewholders.PlaylistBodyItem;
import com.fresher.tronnv.research.ui.viewholders.SecondBodyItem;
import com.fresher.tronnv.research.ui.viewholders.ThirdBodyItem;

import java.util.List;


public class NewsFeedAdapter extends RecyclerAdapter {
    public NewsFeedAdapter(){super();}
    @Override
    protected Class<? extends RecyclerHolder> getHolderClassForViewType(int viewType) {
        switch(viewType) {
            case HeaderItem.HEADER_ITEM_RES_ID:
                return HeaderItem.Holder.class;
            case FirstBodyItem.FIRSTBODY_ITEM_RES_ID:
                return FirstBodyItem.Holder.class;
            case SecondBodyItem.SECONDBODY_ITEM_RES_ID:
                return SecondBodyItem.Holder.class;
            case ThirdBodyItem.THRIDBODY_ITEM_RES_ID:
                return ThirdBodyItem.Holder.class;
            case FooterItem.FOOTER_ITEM_RES_ID:
                return FooterItem.Holder.class;
            case PlaylistBodyItem.PLAYLIST_ITEM_RES_ID:
                return PlaylistBodyItem.Holder.class;
            default:
                return null;
        }
    }
}
