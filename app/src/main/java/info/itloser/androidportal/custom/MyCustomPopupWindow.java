package com.renchehui.vvuser.utils.dialogutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.renchehui.vvuser.R;

import java.util.List;

/**
 * author：zhaoliangwang on 2018/12/25 14:47
 * email：tc7326@126.com
 */
public class MyCustomPopupWindow extends PopupWindow {
    private Context mContext;
    private int resultPositon = -1;
    private String title;
    private List<String> itemStrings;
    private OnMyCustomPopWindowSaveListener listener;

    /*
     * 初始化
     * context  上下文
     * postion  默认下标
     *
     * */
    public MyCustomPopupWindow(Context context, String title, List<String> itemStrings, int positon) {
        super(context);
        this.title = title;
        mContext = context;
        this.itemStrings = itemStrings;
        if (positon > -1) this.resultPositon = positon;
        initVeiw();
    }

    /*
     * 显示PopWindow的方法
     * */
    public void show(View parent) {
        //显示在parent的最下面
        super.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //当前Activity透明度变为0.7
        backgroundAlpha(0.6f);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //消失的时候恢复透明度
        backgroundAlpha(1f);
    }

    /*
     * 初始化视图
     * */
    private void initVeiw() {
        View view = View.inflate(mContext, R.layout.popupwindow_mycustom, null);//加载自定义view
        setOutsideTouchable(false);//不可点击window外
        setTouchable(true);//启用触摸事件
        setFocusable(true);//设置window可以被点击
        setContentView(view);//这里设置视图，也可以在初始化pop的时候在构造里设置。
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//pop的背景色
        setAnimationStyle(R.style.PopupwindowAnimation);//进出动画
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //取消
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //保存
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG-SAVE", "拿到的CODE为：" + resultPositon);
                if (resultPositon > -1) {
                    listener.getItem(resultPositon);
                    dismiss();
                }
            }
        });
        //设置title
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        RecyclerView rvItemList = view.findViewById(R.id.rv_item_list);
        rvItemList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));//设置默认分割线
        rvItemList.setLayoutManager(new LinearLayoutManager(mContext));
        rvItemList.setAdapter(new MyCustomAdapter(itemStrings, resultPositon));

    }

    /*
     * 改变Activity透明度的方法
     * */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    /*
     * ItemAdapter
     * */
    class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.ViewHolder> {

        private List<String> itemStrings;

        private int selectedPosition;

        //ViewHolder
        class ViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tv_item_content);
            }
        }

        public MyCustomAdapter(List<String> itemStrings, int positon) {
            this.itemStrings = itemStrings;
            this.selectedPosition = positon;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_popup_window, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.textView.setText(itemStrings.get(position));
            holder.itemView.setTag(position);
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = holder.getLayoutPosition();
                    notifyDataSetChanged();
                    resultPositon = selectedPosition;

                }
            });

            if (selectedPosition == position) {
                holder.textView.setSelected(true);
            } else {
                holder.textView.setSelected(false);
            }

        }

        @Override
        public int getItemCount() {
            return itemStrings.size();
        }

    }


    public void setOnMyCustomPopWindowSaveListener(OnMyCustomPopWindowSaveListener onMyCustomPopWindowSaveListener) {
        this.listener = onMyCustomPopWindowSaveListener;
    }


    public interface OnMyCustomPopWindowSaveListener {

        void getItem(int i);

    }


}
