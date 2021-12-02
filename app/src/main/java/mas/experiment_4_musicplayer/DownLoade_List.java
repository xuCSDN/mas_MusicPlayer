package mas.experiment_4_musicplayer;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import mas.experiment_4_musicplayer.Song.Song;

public class DownLoade_List extends AppCompatActivity {

    private Button button;
    private ListView listView;
    private int position=0;//记录位置
    private List<Song> songs=new ArrayList<>();
    private List<String> down_list_name=new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloade);

        button=findViewById(R.id.back3);
        listView=findViewById(R.id.downloadelist);

        initDataMusic();//获取本地音乐信息
        show_list();

        button.setOnClickListener(new View.OnClickListener() {//返回按钮
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DownLoade_List.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(DownLoade_List.this,PlaySong.class);
                String str=getFilesDir().getAbsolutePath()+"/Sounds/"+songs.get(i).getSong();
                intent.putExtra("url",str);
                String str2=songs.get(i).getSong().substring(0,songs.get(i).getSong().length()-4);
                intent.putExtra("name",str2);
                position=i;
                String str3=String.valueOf(position);//这里直接传整型，接收方会接收到null，所以需要用tostring
                intent.putExtra("pos",str3.toString());
                startActivity(intent);
                finish();
            }
        });
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
        for(int i=0;i<mp3.size();i++){
            String song = mp3.get(i).getName();
            String path = mp3.get(i).getPath();
            Song bean = new Song(sid,song,"","","",path);
            songs.add(bean);
        }
    }

    /**
     * 获取目录下的所有文件
     * @param dirPath 文件所在目录
     * @param fileType   类型
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
     * 显示在listvi上
     */
    public void show_list(){
        for(int i=0;i<songs.size();i++){
            down_list_name.add(i,songs.get(i).getSong());
        }
        adapter = new ArrayAdapter<String>(DownLoade_List.this, android.R.layout.simple_list_item_1,down_list_name);
        listView.setAdapter(adapter);
    }
}
