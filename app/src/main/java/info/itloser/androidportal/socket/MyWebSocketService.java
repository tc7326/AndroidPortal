package info.itloser.androidportal.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.net.URI;

public class MyWebSocketService extends Service {

    private String address = "ws://wsp.vv-che.com/ws/websocket";
    private URI uri;
    private static final String TAG = "JavaWebSocket";
//    WebSocketClient mWebSocketClient;


    public MyWebSocketService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        initSockect();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


//    public void initSockect() {
//        try {
//            uri = new URI(address);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        if (null == mWebSocketClient) {
//            mWebSocketClient = new WebSocketClient(uri) {
//                @Override
//                public void onOpen(ServerHandshake serverHandshake) {
//                    Log.i(TAG, "onOpen: ");
//                    //连接成功，发送点对点服务
//                }
//
//                @Override
//                public void onMessage(String s) {
//                    Log.i(TAG, "onMessage: " + s);
//                }
//
//                @Override
//                public void onClose(int i, String s, boolean b) {
//                    Log.i(TAG, "onClose: ");
//                }
//
//                @Override
//                public void onError(Exception e) {
//                    Log.i(TAG, "onError: ");
//                }
//            };
//            mWebSocketClient.connect();
//        }
//    }


}
