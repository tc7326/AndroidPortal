package info.itloser.androidportal.viewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.itloser.androidportal.R;

public class VMOneFragment extends Fragment {

    VMViewModel vmViewModel;

    private static final String BG_COLOR = "bgColor";
    private int bgColor;

    @BindView(R.id.tv_test)
    TextView tvTest;

    @BindView(R.id.fl_main)
    ConstraintLayout flMain;

    Unbinder unbinder;


    public VMOneFragment() {
        // Required empty public constructor
    }

    public static VMOneFragment newInstance(int param1) {
        VMOneFragment fragment = new VMOneFragment();
        Bundle args = new Bundle();
        args.putInt(BG_COLOR, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bgColor = getArguments().getInt(BG_COLOR);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vmone, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flMain.setBackgroundColor(bgColor);

        //viewModel
        vmViewModel = ViewModelProviders.of(this).get(VMViewModel.class);
        vmViewModel.getVmBeanMutableLiveData().observe(this, new Observer<VMBean>() {
            @Override
            public void onChanged(@Nullable VMBean vmBean) {
                if (vmBean != null) {
                    tvTest.setText(vmBean.getTitle());
                    tvTest.setBackgroundColor(vmBean.getColor());
                    tvTest.setHeight(vmBean.getWidth());
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
