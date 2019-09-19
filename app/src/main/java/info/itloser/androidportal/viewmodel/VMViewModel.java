package info.itloser.androidportal.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * author：zhaoliangwang on 2019/9/17 14:21
 * email：tc7326@126.com
 */
class VMViewModel extends ViewModel {

    private MutableLiveData<VMBean> liveData;

    MutableLiveData<VMBean> getVmBeanMutableLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        liveData.setValue(loadVMBean());//给默认值
        return liveData;
    }

    //获取一波数据
    private VMBean loadVMBean() {
        return new VMBean("fuck viewModel+liveData", 0xFFFFFFFF, 100);
    }

    //更改数据的接口
    public void changeData(VMBean vmBean) {
        liveData.setValue(vmBean);
    }


}