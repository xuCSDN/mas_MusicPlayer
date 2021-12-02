package mas.experiment_4_musicplayer;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import mas.experiment_4_musicplayer.Song.Song;
import mas.experiment_4_musicplayer.gson.Find_By_ID;
import mas.experiment_4_musicplayer.service.DownLoadService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlaySong extends AppCompatActivity {

    private TextView name;
    private Button b_last;
    private Button b_next;
    private Button b_play;
    private Intent intent;
    private Button back;
    private String url;
    private SeekBar progressBar;
    private List<Find_By_ID.DataDTO> list_id_url=new ArrayList<>();   //歌曲信息类列表
    private int find_code;//搜索歌曲返回码
    private DownLoadService downLoadService=new DownLoadService();

    private MediaPlayer mediaPlayer;
    private int flag=0;//用来判断是从暂停到播放还是直接播放
    private int currentlyPause;//记录暂停后的播放位置

    private List<Song> songs=new ArrayList<>();//存放本地音乐的信息
    private int list_current;
    private List<String> down_list_name=new ArrayList<>();

    private Timer timer;
    private int duration;//总时长
    private int currentPos;//当前进度

    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playmusic);

        b_last=findViewById(R.id.b_last);
        b_next=findViewById(R.id.b_next);
        b_play=findViewById(R.id.b_play);
        name=findViewById(R.id.name);
        intent=getIntent();
        url=intent.getStringExtra("url");
        mediaPlayer=new MediaPlayer();
        progressBar=findViewById(R.id.info_seekBar);
        back=findViewById(R.id.back);

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);//设置通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {   //设置通知暗号
            String channelId = "chat";
            String channelName = "下载消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);
        }

        name.setText(intent.getStringExtra("name"));//获取歌名
        initDataMusic();//获取本地音乐信息

        if(intent.getStringExtra("pos").contains("m")){//说明是从搜索页面跳转过来
            list_current=songs.size();
        }
        else{
            list_current=Integer.parseInt(intent.getStringExtra("pos"));//获得从已下载页面跳转过来的要播放音乐的位置
        }

        for(int i=0;i<songs.size();i++){
            down_list_name.add(i,songs.get(i).getSong());
        }
        String str2=intent.getStringExtra("name")+".mp3";
        if(down_list_name.contains(str2)){//音乐已下载
            stopMusic();
            start();
        }
        else {//音乐未下载，调用服务下载
            findbyid(url);
        }

        back.setOnClickListener(new View.OnClickListener() {//返回按钮
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PlaySong.this,MainActivity.class);
                startActivity(intent);
                stopMusic();//返回后停止音乐
                finish();
            }
        });

        b_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0){//下载完成后播放
                    start();
                }
                else if(flag==1) {//暂停音乐
                    pauseMusic();
                }
                else {//继续播放音乐
                    playMusic();
                }
            }
        });

        b_last.setOnClickListener(new View.OnClickListener() {//上一首按钮
            @Override
            public void onClick(View view) {
                list_current--;   //音乐位置减1
                if(list_current<0){//
                    Toast.makeText(PlaySong.this,"这是第一首歌，到顶啦！",Toast.LENGTH_SHORT).show();
                    list_current++;      //已经是第一首歌，所以再加回来
                }
                else {                   //根据歌曲id搜索后重新加载此页面
                    String str2=String.valueOf(list_current);
                    String str="http://api.we-chat.cn/song/url?id="+songs.get(list_current).getId();
                    Intent intent=new Intent(PlaySong.this,PlaySong.class);
                    intent.putExtra("url",str);
                    intent.putExtra("name",songs.get(list_current).getSong().substring(0,songs.get(list_current).getSong().length()-4));
                    intent.putExtra("pos",str2.toString());
                    stopMusic();
                    startActivity(intent);
                    finish();
                }
            }
        });

        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list_current++;//音乐位置加1
                if(list_current>=songs.size()){
                    Toast.makeText(PlaySong.this,"这是最后一首歌，到底啦！",Toast.LENGTH_SHORT).show();
                    list_current--;//已经是最后一首歌，所以再减回来
                }
                else {//根据歌曲id搜索后重新加载此页面
                    String str2=String.valueOf(list_current);
                    String str="http://api.we-chat.cn/song/url?id="+songs.get(list_current).getId();
                    Intent intent=new Intent(PlaySong.this,PlaySong.class);
                    intent.putExtra("url",str);
                    intent.putExtra("name",songs.get(list_current).getSong().substring(0,songs.get(list_current).getSong().length()-4));
                    intent.putExtra("pos",str2.toString());
                    stopMusic();
                    startActivity(intent);
                    finish();
                }
            }
        });

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {//设置进度条点击监听
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {//拖动进度条
                if(b){
                    pauseMusic();//先暂停音乐
                    flag=2;
                    currentlyPause=i;//记录拖动条的位置
                    playMusic();//从拖动后位置进行播放
                }
                if(i==mediaPlayer.getDuration()){//拖动条拖到最后就暂停音乐
                    pauseMusic();
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    /**
     * 音乐未下载，先获取id然后启动下载服务
     * @param url2
     */
    public void findbyid(String url2){
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建客户端对象
                Request request = new Request.Builder().url(url2).build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                Find_By_ID data = JSON.parseObject(responseData, Find_By_ID.class);
                list_id_url = data.getData();
                find_code=data.getCode();
                refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 更新UI界面，显示listview
     */
    private void refresh() {
        runOnUiThread(()->{
            if(find_code==200){//返回值正确
                Intent intent2=new Intent(PlaySong.this,DownLoadService.class);
                System.out.println("--------------------"+list_id_url.get(0).getUrl());
                intent2.putExtra("downloadurl",list_id_url.get(0).getUrl());
                intent2.putExtra("name",intent.getStringExtra("name"));
                startService(intent2);                  //启动下载服务

                Notification notification=new NotificationCompat.Builder(this,"chat")//下载完成，设置通知信息
                        .setAutoCancel(true)
                        .setContentTitle("下载音乐通知")
                        .setContentText("下载完成")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setColor(Color.parseColor("#F00606"))
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .build();
                manager.notify(1, notification);
            }
            else{
                Toast.makeText(PlaySong.this,"服务器提供的歌曲id不正确！换首歌！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 开始播放音乐
     */
    public void start(){
        if(mediaPlayer.isPlaying()){//停止正在播放的音乐
            stopMusic();
        }
        else {
            mediaPlayer.reset();//重置播放器
            try {
                String str=getFilesDir().getAbsolutePath()+"/Sounds/"+intent.getStringExtra("name")+".mp3";
                mediaPlayer.setDataSource(str);//设置音乐文件位置
                playMusic();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 播放音乐的函数
     */
    private void playMusic() {

            if(flag == 0){//下载完成后第一次播放
                try {
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    addTimer();//设置计数器和定时任务

                    b_play.setBackgroundResource(R.drawable.pause_song);//改变按钮状态
                    flag=1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(flag==2){//从暂停到播放
                try{
                    mediaPlayer.seekTo(currentlyPause);//从指定位置开始播放
                    mediaPlayer.start();
                    b_play.setBackgroundResource(R.drawable.pause_song);//改变按钮状态
                    flag=1;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
    }

    /**
     * 暂停音乐
     */
    private void pauseMusic() {
        try{
            currentlyPause=mediaPlayer.getCurrentPosition();//记录暂停后音乐播放的位置
            mediaPlayer.pause();
            flag=2;
            b_play.setBackgroundResource(R.drawable.play_song);//改变按钮状态
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 停止音乐的函数
     */
    private void stopMusic() {
        try{
            flag = 0;
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            mediaPlayer.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取本地音乐存储到列表中
     */
    @SuppressLint("Range")
    private void initDataMusic() {
        String sid = null;
        ContentResolver resolver = getContentResolver();//获取ContentResolver对象
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;//获取本地音乐存储的uri地址
        Cursor cursor = resolver.query(uri, null, null, null, null);//设置查询开始游标
        int id=0;
        while (cursor.moveToNext()){
            String song = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            id++;
            sid = String.valueOf(id);
            String path=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            long time=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
            String time1 = simpleDateFormat.format(new Date(time));
            Song bean = new Song(sid, song, singer, album, time1, path);
            songs.add(bean);
        }
        Vector<File> mp3 = getAllFiles(getFilesDir().getAbsolutePath()+"/Sounds", "mp3");
        System.out.println("---------9------------"+mp3);
        for(int i=0;i<mp3.size();i++){
            String song = mp3.get(i).getName();
            String path = mp3.get(i).getPath();
            Song bean = new Song(sid,song,"","","",path);
            songs.add(bean);
        }
    }

    /**
     * 获取指定目录内所有文件路径
     * @param dirPath 文件所在目录
     * @param fileType  类型
     */
    public Vector<File> getAllFiles(String dirPath, String fileType) {
        Vector<File> fileVector = new Vector<>();
        File f = new File(dirPath);
        if (!f.exists()) {//判断路径是否存在
            return fileVector;
        }
        File[] files = f.listFiles();
        if (files == null) {//判断权限
            return fileVector;
        }
        Vector<File> vecFile = new Vector<File>();
        for (File _file : files) {//遍历目录
            if (_file.isFile() && _file.getName().endsWith(fileType)) {
                vecFile.add(_file);
            }
            else if (_file.isDirectory()) {//查询子目录
                getAllFiles(_file.getAbsolutePath(), fileType);
            }
        }
        return vecFile;
    }

    /**
     * 设置计数器和定时任务
     */
    public void addTimer(){
        if(timer==null){
            timer=new Timer();
            TimerTask task=new TimerTask() {
                @Override
                public void run() {
                    duration=mediaPlayer.getDuration();
                    currentPos=mediaPlayer.getCurrentPosition();
                    progressBar.setMax(duration);
                    progressBar.setProgress(currentPos);
                }
            };
            timer.schedule(task,5,500);
        }
    }

    /**
     * 设置通知
     * @param channelId 通知id
     * @param channelName  通知名
     * @param importance   通知重要程度
     */
    @SuppressLint("NewApi")
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
}
