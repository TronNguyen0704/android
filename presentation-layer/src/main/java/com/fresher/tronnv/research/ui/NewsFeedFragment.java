package com.fresher.tronnv.research.ui;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fresher.tronnv.android_models.FirstBody;
import com.fresher.tronnv.android_models.Footer;
import com.fresher.tronnv.android_models.Header;
import com.fresher.tronnv.android_models.PlayListBody;
import com.fresher.tronnv.android_models.SecondBody;
import com.fresher.tronnv.android_models.ThirdBody;
import com.fresher.tronnv.research.R;
import com.fresher.tronnv.research.ui.adapter.NewsFeedAdapter;
import com.fresher.tronnv.research.ui.viewholders.FirstBodyItem;
import com.fresher.tronnv.research.ui.viewholders.FooterItem;
import com.fresher.tronnv.research.ui.viewholders.HeaderItem;
import com.fresher.tronnv.research.ui.viewholders.PlaylistBodyItem;
import com.fresher.tronnv.research.ui.viewholders.SecondBodyItem;
import com.fresher.tronnv.research.ui.viewholders.ThirdBodyItem;

import java.util.Random;

public class NewsFeedFragment extends Fragment {
    private Context mContext;
    private final int mImageWidthPixels = 1024;
    private final int mImageHeightPixels = 768;
    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public NewsFeedFragment(){
    }
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = 1000;//generator.nextInt(1000);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_newsfeed, container, false);


        final RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_news_feed);
        SwipeRefreshLayout swipeRefreshLayout =rootView.findViewById(R.id.swipeRefreshLayout);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);

        NewsFeedAdapter newsFeedAdapter = new NewsFeedAdapter();
        recyclerView.setAdapter(newsFeedAdapter);
        for(int i=1; i<10; ++i) {
//            newsFeedAdapter.addItem(new FirstBodyItem(new FirstBody(random())));
            newsFeedAdapter.addItem(new HeaderItem(new Header("MP3Dev","MV","https://zmp3-photo.zadn.vn/default.jpg")));
            newsFeedAdapter.addItem(new FirstBodyItem(new FirstBody("Thả mình vào không gian âm nhạc của những DJ, Producer nổi tiếng cùng EDM.TOP 100 là danh sách 100 ca khúc hot nhất hiện tại của từng thể loại nhạc, được hệ thống tự động đề xuất dựa trên thông tin số liệu lượt nghe và lượt chia sẻ của từng bài hát trên cả desktop và mobile. Dữ liệu sẽ được lấy trong 30 ngày gần nhất và được cập nhật liên tục.")));
            newsFeedAdapter.addItem(new SecondBodyItem(new SecondBody("EDM","https://zmp3-photo.zadn.vn/banner/1/2/9/b/129b73cbb3a724e44d556461d642d8f7.jpg")));
            newsFeedAdapter.addItem(new ThirdBodyItem(new ThirdBody("EDM","Zing Mp3")));
            newsFeedAdapter.addItem(new FooterItem(new Footer(500)));
        }
        for(int i=1; i<20; ++i) {
            newsFeedAdapter.addItem(new HeaderItem(new Header("Zing Hot","playlist","https://zmp3-photo.zadn.vn/thumb/240_240/covers/5/d/5dc9d55184835d31fcc6eda47ff21f34_1362062713.jpg")));
            newsFeedAdapter.addItem(new FirstBodyItem(new FirstBody("Tận hưởng không khí buổi sáng trong lành và thử mở ngay list nhạ USUK ngẫu nhiên mà #ZingMP3 dành riêng cho bạn. Lên dây cót tinh thần để xử lí nốt công việc chuẩn bị cho hai ngày nghỉ cuối tuần thôi nào!")));
            newsFeedAdapter.addItem(new PlaylistBodyItem(new PlayListBody("Nhạc US-UK Hôm Nay Nghe Gì?","https://zmp3-photo.zadn.vn/thumb/240_240/cover/a/5/3/c/a53c6e0581dc772ea0c6d32f3cb16be9.jpg",new String[]{"1. STARGAZING","2. The Way I Am","3. Loud"})));
            newsFeedAdapter.addItem(new FooterItem(new Footer(164)));
        }
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
        });
        return rootView;
    }
}
