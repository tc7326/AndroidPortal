package info.itloser.androidportal.file_sql;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import info.itloser.androidportal.R;

public class SerialActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial);

        mContext = this;

//        SerialThis();

//        DeSerialThis();

//        ExtSerialThis();

        ExtDeSerialThis();

    }

    /*
     * 实现Serializable序列化接口对象的操作
     * */

    //序列化对象到文件
    public void SerialThis() {
        MsgBean msg0 = new MsgBean(0, "测试", "消息测试");
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(getDiskCacheDir(mContext, "SerialDemo")));
            objectOutputStream.writeObject(msg0);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //反序列化
    public void DeSerialThis() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(getDiskCacheDir(mContext, "SerialDemo")));
            MsgBean msgBean1 = (MsgBean) objectInputStream.readObject();
            Log.i("ser", msgBean1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 实现Externalizable外部化接口对象的操作
     * */

    //序列化对象到文件
    public void ExtSerialThis() {
        ExtMsgBean msg0 = new ExtMsgBean(0, "测试", "消息测试");
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(getDiskCacheDir(mContext, "ExtSerialDemo")));
            objectOutputStream.writeObject(msg0);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //反序列化
    public void ExtDeSerialThis() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(getDiskCacheDir(mContext, "ExtSerialDemo")));
            ExtMsgBean msgBean1 = (ExtMsgBean) objectInputStream.readObject();
            Log.i("ser", msgBean1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //可以提个工具类了
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
