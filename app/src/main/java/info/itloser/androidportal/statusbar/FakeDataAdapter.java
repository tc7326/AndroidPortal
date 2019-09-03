package info.itloser.androidportal.statusbar;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import info.itloser.androidportal.R;

/**
 * author：zhaoliangwang on 2019/8/23 16:45
 * email：tc7326@126.com
 */
public class FakeDataAdapter extends BaseQuickAdapter<FakeDataBean, BaseViewHolder> {

    public FakeDataAdapter(int layoutResId, @Nullable List<FakeDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FakeDataBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_content, item.getContent());
    }
}
