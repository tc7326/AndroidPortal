package info.itloser.androidportal.socket;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/*
 * 基于OkHttp3的WebSocket服务
 * */
public class VVService extends Service {

    private static final String SOCKET_URL = "wss://wsp.vv-che.com/ws/websocket";


    private static final long HEART_BEAT_RATE = 30 * 1000;

    OkHttpClient mClient;
    Request mRequest;
    WebSocket mWebSocket;
    WebSocketListener mWebSocketListener;

    Handler heartHandler;

    @Override
    public void onCreate() {
        Log.i("socket", "Service start");
        //只会在第一次start的时候走这个
        super.onCreate();

        //初始化websocket
        initConfig();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //每次调用start都会走这个
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void initConfig() {
        mRequest = new Request.Builder().url(SOCKET_URL).build();
        mClient = new OkHttpClient.Builder().readTimeout(70, TimeUnit.SECONDS).build();
        mWebSocketListener = new VVWebSocketListener();
        mClient.dispatcher().cancelAll();
        mWebSocket = mClient.newWebSocket(mRequest, mWebSocketListener);
        heartHandler = new Handler();
    }

    class VVWebSocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            String send = "{\"socketId\":\"user_2217\", \"type\":0}";
            webSocket.send(send);
            heartHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);
            super.onOpen(webSocket, response);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Log.i("socket", "get msg - " + text);

            super.onMessage(webSocket, text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            Log.i("socket", "closing - " + code);
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            Log.i("socket", "closed - " + code);
            super.onClosed(webSocket, code, reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Log.i("socket", "err - " + t.getMessage() + "|" + t.getLocalizedMessage());
            super.onFailure(webSocket, t, response);
        }
    }

    public Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mWebSocket.send("\"type\":0}");
                }
            }).start();
            heartHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

}
