package info.itloser.androidportal.retrofits;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.itloser.androidportal.R;
import info.itloser.androidportal.webview.WebViewActivity;

/**
 * author：zhaoliangwang on 2018/9/6 09:28
 * email：tc7326@126.com
 */
public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Activity mContext;
    private List<WanFriendBean.DataBean> orderList;

    public HomeListAdapter(Activity context, List<WanFriendBean.DataBean> orderList) {
        super();
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.orderList = orderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = mInflater.inflate(R.layout.item_wan_android, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final WanFriendBean.DataBean order = orderList.get(position);

        holder.tv_name.setText(order.getName());
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, order.getLink(), Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, WebViewActivity.class).putExtra("URL", order.getLink()));
            }

        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //给司机打电话
        @BindView(R.id.tv_name)
        TextView tv_name;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}













