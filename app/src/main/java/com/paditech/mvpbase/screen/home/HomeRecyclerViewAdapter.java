package com.paditech.mvpbase.screen.home;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.paditech.mvpbase.R;
import com.paditech.mvpbase.common.model.AppModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.paditech.mvpbase.screen.showMoreApp.ShowMoreActicity;

/**
 * Created by hung on 1/3/2018.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<AppModel> mList1, mList2, mList3, mList4;
    Activity act;

    public HomeRecyclerViewAdapter(Activity act){
        this.act = act;
    }
    public void setmList1(List<AppModel> mList1) {
        this.mList1 = mList1;
        notifyDataSetChanged();
    }

    public void setmList2(List<AppModel> mList2) {
        this.mList2 = mList2;
        notifyDataSetChanged();
    }

    public void setmList3(List<AppModel> mList3) {
        this.mList3 = mList3;
        notifyDataSetChanged();
    }

    public void setmList4(List<AppModel> mList4) {
        this.mList4 = mList4;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_app, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerHolder recyclerHolder = (RecyclerHolder) holder;
        recyclerHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_list_app)
        TextView textView_list_app;
        @BindView(R.id.tv_list_end)
        TextView textView_list_end;
        @BindView(R.id.btn_see_more)
        Button button_see_more;
        @BindView(R.id.recycler_view_list_app)
        RecyclerView recyclerView;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        private HomeListAppAdapter mHomeListAppAdapter;

        public RecyclerHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            mHomeListAppAdapter = new HomeListAppAdapter(act);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(mHomeListAppAdapter);
        }

        private void setData(final int pos) {
            switch (pos) {
                case 0:
                    mHomeListAppAdapter.setmList(mList1);
                    progressBar.setVisibility((mList1 != null && !mList1.isEmpty()) ? View.GONE : View.VISIBLE);
                    textView_list_app.setText("Lastest ");
                    textView_list_end.setText("Sale");
                    break;
                case 1:
                    mHomeListAppAdapter.setmList(mList2);
                    progressBar.setVisibility((mList2 != null && !mList2.isEmpty()) ? View.GONE : View.VISIBLE);
                    textView_list_app.setText("Top Download ");
                    textView_list_end.setText("Apps");
                    break;
                case 2:
                    progressBar.setVisibility((mList3 != null && !mList3.isEmpty()) ? View.GONE : View.VISIBLE);
                    mHomeListAppAdapter.setmList(mList3);
                    textView_list_app.setText("Top Grossing Android ");
                    textView_list_end.setText("Apps");
                    break;
                default:
                    progressBar.setVisibility((mList4 != null && !mList4.isEmpty()) ? View.GONE : View.VISIBLE);
                    mHomeListAppAdapter.setmList(mList4);
                    textView_list_app.setText("Apps ");
                    textView_list_end.setText("Gone Free");
                    break;

            }
            button_see_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),ShowMoreActicity.class);
                    intent.putExtra("CHECKCATE","FALSE");
                    switch (pos){
                        case 0:
                            intent.putExtra("CATE_URL","http://appsxyz.com/api/apk/lastes-sale/?page=%s&size=20");
                            intent.putExtra("CATE_NAME","lastest");
                            break;
                        case 1:
                            intent.putExtra("CATE_URL","http://appsxyz.com/api/apk/topdownload/?page=%s&size=20");
                            intent.putExtra("CATE_NAME","topdownload");
                            break;
                        case 2:
                            intent.putExtra("CATE_URL","http://appsxyz.com/api/apk/grossing/?page=%s&size=20&installs=1000");
                            intent.putExtra("CATE_NAME","grossing");
                            break;
                        default:
                            intent.putExtra("CATE_URL","http://appsxyz.com/api/apk/gonefree/?page=%s&size=20");
                            intent.putExtra("CATE_NAME","gonefree");
                            break;
                    }
                    itemView.getContext().startActivity(intent);
                }
            });
        }

    }
}
