package info.itloser.androidportal.viewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.itloser.androidportal.R;

public class VMActivity extends AppCompatActivity {

    VMViewModel vmViewModel;

    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.fl_one)
    FrameLayout flOne;
    @BindView(R.id.fl_two)
    FrameLayout flTwo;
    @BindView(R.id.btn_change)
    Button btnChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm);
        ButterKnife.bind(this);

        //将fragment添加到布局中
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_one, VMOneFragment.newInstance(0xFFF5C71E))
                .add(R.id.fl_two, VMOneFragment.newInstance(0xFFFF4081))
                .commit();

        vmViewModel = ViewModelProviders.of(this).get(VMViewModel.class);
        vmViewModel.getVmBeanMutableLiveData().observe(this, new Observer<VMBean>() {
            @Override
            public void onChanged(@Nullable VMBean vmBean) {
                if (vmBean != null) {

                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        vmViewModel.changeData(new VMBean("瞎jb搞", 0xFF78456, 500));
    }

    @OnClick(R.id.btn_change)
    public void onViewClicked() {
        vmViewModel.changeData(new VMBean("瞎jb搞", 0xFF78456, 500));
    }
}
