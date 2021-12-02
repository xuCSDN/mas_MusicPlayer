package mas.experiment_4_musicplayer.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import mas.experiment_4_musicplayer.PlaySong;
import mas.experiment_4_musicplayer.R;

public class DownLoadService extends IntentService {

    private final String TAG="LOGCAT";
    private int fileLength, downloadLength;
    private Handler handler = new Handler();

    public DownLoadService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    protected void onHandleIntent(Intent intent) {
        try {

            Bundle bundle = intent.getExtras();
            String downloadUrl = bundle.getString("downloadurl");
            File dirs = new File(getFilesDir().getAbsolutePath()+"/Sounds","");//在内部存储建目录
            if (!dirs.exists()) {// 检查文件夹是否存在，不存在则创建
                dirs.mkdir();
            }

            File file = new File(dirs, bundle.getString("name")+".mp3");//输出文件名
            Log.d(TAG,"下载启动："+downloadUrl+" --to-- "+ file.getPath());

            downloadFile(downloadUrl, file);// 开始下载

            Intent sendIntent = new Intent("downloadComplete");
            sendIntent.putExtra("downloadFile", file.getPath());
            sendBroadcast(sendIntent);// 广播下载完成事件，通过广播调起对文件的处理。
            Log.d(TAG,"下载结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载
     * @param downloadUrl 下载url
     * @param file  放入的文件
     */
    public void downloadFile(String downloadUrl, File file){
        FileOutputStream outputStream;//文件输出流
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "找不到目录！");
            e.printStackTrace();
            return;
        }
        InputStream inputStream = null;//文件输入流
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection _downLoadCon = (HttpURLConnection) url.openConnection();
            _downLoadCon.setRequestMethod("GET");
            fileLength = Integer.parseInt(_downLoadCon.getHeaderField("Content-Length"));//文件大小
            inputStream = _downLoadCon.getInputStream();

                handler.post(run);//更新下载进度
                byte[] buffer = new byte[1024*8];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                    downloadLength = downloadLength + len;
                    Log.d(TAG, downloadLength + "/" + fileLength );
                }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {//关闭输入输出流
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给主进程发送
     */
    private Runnable run = new Runnable() {
        public void run() {
        }
    };

    /**
     * 销毁下载服务
     */
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        handler.removeCallbacks(run);
        super.onDestroy();
    }

}
