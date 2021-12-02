package mas.experiment_4_musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mas.experiment_4_musicplayer.gson.Find_By_Name;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText input; //输入框内容
    private Button find;
    private Button d_down;
    private ListView listView;
    private String songname;
    private List<String> list=new ArrayList<>();  //歌曲名称列表
    private List<Find_By_Name.ResultDTO.SongsDTO> list_name=new ArrayList<>();//歌曲信息类列表

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        input=findViewById(R.id.input);
        find=findViewById(R.id.b_find);
        d_down=findViewById(R.id.d_down);
        listView=findViewById(R.id.showlist);

        find.setOnClickListener(new View.OnClickListener() {     //设置搜索按钮监听
            @Override
            public void onClick(View view) {
                songname=input.getText().toString();      //获得输入的歌名
                if(!songname.isEmpty()){
                    String address="http://api.we-chat.cn/search?keywords="+songname;
                    findbyname(address);             //根据歌名查询歌曲列表
                }
                else{
                    Toast.makeText(MainActivity.this,"歌曲名为空！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        d_down.setOnClickListener(new View.OnClickListener() {       //设置查看已下载歌曲按钮监听
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,DownLoade_List.class);
                startActivity(intent);
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {         //设置listview项监听器
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str="http://api.we-chat.cn/song/url?id="+list_name.get(i).getId();
                Intent intent=new Intent(MainActivity.this,PlaySong.class);
                intent.putExtra("url",str);
                intent.putExtra("name",list.get(i));
                intent.putExtra("id",list_name.get(i).getId());
                intent.putExtra("pos","m");
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 根据歌名查找同名歌曲
     * @param address  查找地址
     */
    public void findbyname(String address){
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建客户端对象
                Request request = new Request.Builder().url(address).build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                System.out.println("-----------8---------"+responseData);
                Find_By_Name data = JSON.parseObject(responseData, Find_By_Name.class);
                list_name = data.getResult().getSongs();
                refresh();              //在该函数中执行runonUIthread
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

            if(list_name.get(0).getId().equals(",")){       //如果返回的结果为空
                Toast.makeText(MainActivity.this,"服务器提供的歌曲id不正确！换首歌！",Toast.LENGTH_SHORT).show();
            }
            else{
                list.clear();
                for (Find_By_Name.ResultDTO.SongsDTO province : list_name) {
                    list.add(province.getName());           //获取查找到的每首歌的歌名
                }
                adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,list);
                listView.setAdapter(adapter);
            }
        });
    }
}