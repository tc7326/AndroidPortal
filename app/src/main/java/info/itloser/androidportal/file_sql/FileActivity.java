package info.itloser.androidportal.file_sql;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

import butterknife.ButterKnife;
import info.itloser.androidportal.R;

public class FileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        ButterKnife.bind(this);

        try {
//            randomWrite(getDiskCacheDir(this, "dd.txt"));
//            randomRead(getDiskCacheDir(this, "dd.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * 字节流文件操作（读写）
     * */
    public static void readFileByByte(String filePath) {

        File file = new File(filePath);//1.创建文件对象
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {

            inputStream = new FileInputStream(file);//2.装载文件
            outputStream = new FileOutputStream("/path");

            int temp;
            while ((temp = inputStream.read()) != -1) {
                outputStream.write(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }

        }


    }

    /*
     * 字符流文件操作（读写）
     * */
    public static void readFileByCharcater(String filePath) {

        File file = new File(filePath);

        FileReader reader = null;
        FileWriter writer = null;

        try {
            reader = new FileReader(file);
            writer = new FileWriter("/path");

            int temp;

            while ((temp = reader.read()) != -1) {
                writer.write((char) temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

    }

    /*
     * 按行（读写）
     * */
    public static void readFileByLine(String filePath) {
        File file = new File(filePath);

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            bufferedWriter = new BufferedWriter(new FileWriter("/path"));

            String temp = null;
            while ((temp = bufferedReader.readLine()) != null) {
                bufferedWriter.write(temp + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }


    }

    /*
     * 字节流转字符流
     * */
    public static String getOuterIp() throws IOException {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL("https://www.fuck.com/veryday.asp");
            URLConnection urlConnection = url.openConnection();
            inputStream = urlConnection.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GB2312");
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer webContent = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                webContent.append(str);
            }
            int ipStart = webContent.indexOf("[") + 1;
            int ipEnd = webContent.indexOf("]");
            return webContent.substring(ipStart, ipEnd);
        } finally {
            //关闭流
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 随机流 读
     * */
    private static void randomRead(File file) throws IOException {
        //以只读的方法读取数据
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        byte b = randomAccessFile.readByte();
        int i = randomAccessFile.readInt();
        String string = randomAccessFile.readUTF();

        Log.i("randomRead", b + "");
        Log.i("randomRead", i + "");
        Log.i("randomRead", string + "");

    }

    /*
     * 随机流 写
     * */
    private static void randomWrite(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        randomAccessFile.writeInt(10086);
        randomAccessFile.writeByte(233);
        randomAccessFile.writeUTF("WDNMD");
        randomAccessFile.close();
    }

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
