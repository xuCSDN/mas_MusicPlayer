package mas.experiment_4_musicplayer.Song;

public class Song {
    private  String id;//歌曲id
    private  String song;//歌名
    private  String Singer;//歌手
    private  String album;//专辑
    private  String time;//播放时间
    private  String path;//存放路径

    public  Song(){

    }
    public Song(String id, String song, String singer, String album, String time, String path) {
        this.id = id;
        this.song = song;
        Singer = singer;
        this.album = album;
        this.time = time;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return Singer;
    }

    public void setSinger(String singer) {
        Singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
