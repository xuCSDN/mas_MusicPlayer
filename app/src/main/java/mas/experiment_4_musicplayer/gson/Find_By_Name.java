package mas.experiment_4_musicplayer.gson;

import java.util.List;

public class Find_By_Name {

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private ResultDTO result;
    private Integer code;

    public static class ResultDTO {
        private List<SongsDTO> songs;
        private Boolean hasMore;
        private Integer songCount;

        public List<SongsDTO> getSongs() {
            return songs;
        }

        public void setSongs(List<SongsDTO> songs) {
            this.songs = songs;
        }

        public Boolean getHasMore() {
            return hasMore;
        }

        public void setHasMore(Boolean hasMore) {
            this.hasMore = hasMore;
        }

        public Integer getSongCount() {
            return songCount;
        }

        public void setSongCount(Integer songCount) {
            this.songCount = songCount;
        }

        public static class SongsDTO {
            private Integer id;
            private String name;
            private List<ArtistsDTO> artists;
            private AlbumDTO album;
            private Integer duration;
            private Integer copyrightId;
            private Integer status;
            private List<String> alias;
            private Integer rtype;
            private Integer ftype;
            private Integer mvid;
            private Integer fee;
            private Object rUrl;
            private Integer mark;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ArtistsDTO> getArtists() {
                return artists;
            }

            public void setArtists(List<ArtistsDTO> artists) {
                this.artists = artists;
            }

            public AlbumDTO getAlbum() {
                return album;
            }

            public void setAlbum(AlbumDTO album) {
                this.album = album;
            }

            public Integer getDuration() {
                return duration;
            }

            public void setDuration(Integer duration) {
                this.duration = duration;
            }

            public Integer getCopyrightId() {
                return copyrightId;
            }

            public void setCopyrightId(Integer copyrightId) {
                this.copyrightId = copyrightId;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public List<String> getAlias() {
                return alias;
            }

            public void setAlias(List<String> alias) {
                this.alias = alias;
            }

            public Integer getRtype() {
                return rtype;
            }

            public void setRtype(Integer rtype) {
                this.rtype = rtype;
            }

            public Integer getFtype() {
                return ftype;
            }

            public void setFtype(Integer ftype) {
                this.ftype = ftype;
            }

            public Integer getMvid() {
                return mvid;
            }

            public void setMvid(Integer mvid) {
                this.mvid = mvid;
            }

            public Integer getFee() {
                return fee;
            }

            public void setFee(Integer fee) {
                this.fee = fee;
            }

            public Object getrUrl() {
                return rUrl;
            }

            public void setrUrl(Object rUrl) {
                this.rUrl = rUrl;
            }

            public Integer getMark() {
                return mark;
            }

            public void setMark(Integer mark) {
                this.mark = mark;
            }

            public static class AlbumDTO {
                private Integer id;
                private String name;
                private ArtistDTO artist;
                private Long publishTime;
                private Integer size;
                private Integer copyrightId;
                private Integer status;
                private Long picId;
                private Integer mark;

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public ArtistDTO getArtist() {
                    return artist;
                }

                public void setArtist(ArtistDTO artist) {
                    this.artist = artist;
                }

                public Long getPublishTime() {
                    return publishTime;
                }

                public void setPublishTime(Long publishTime) {
                    this.publishTime = publishTime;
                }

                public Integer getSize() {
                    return size;
                }

                public void setSize(Integer size) {
                    this.size = size;
                }

                public Integer getCopyrightId() {
                    return copyrightId;
                }

                public void setCopyrightId(Integer copyrightId) {
                    this.copyrightId = copyrightId;
                }

                public Integer getStatus() {
                    return status;
                }

                public void setStatus(Integer status) {
                    this.status = status;
                }

                public Long getPicId() {
                    return picId;
                }

                public void setPicId(Long picId) {
                    this.picId = picId;
                }

                public Integer getMark() {
                    return mark;
                }

                public void setMark(Integer mark) {
                    this.mark = mark;
                }

                public static class ArtistDTO {
                    private Integer id;
                    private String name;
                    private Object picUrl;
                    private List<?> alias;
                    private Integer albumSize;
                    private Integer picId;
                    private String img1v1Url;
                    private Integer img1v1;
                    private Object trans;

                    public Integer getId() {
                        return id;
                    }

                    public void setId(Integer id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public Object getPicUrl() {
                        return picUrl;
                    }

                    public void setPicUrl(Object picUrl) {
                        this.picUrl = picUrl;
                    }

                    public List<?> getAlias() {
                        return alias;
                    }

                    public void setAlias(List<?> alias) {
                        this.alias = alias;
                    }

                    public Integer getAlbumSize() {
                        return albumSize;
                    }

                    public void setAlbumSize(Integer albumSize) {
                        this.albumSize = albumSize;
                    }

                    public Integer getPicId() {
                        return picId;
                    }

                    public void setPicId(Integer picId) {
                        this.picId = picId;
                    }

                    public String getImg1v1Url() {
                        return img1v1Url;
                    }

                    public void setImg1v1Url(String img1v1Url) {
                        this.img1v1Url = img1v1Url;
                    }

                    public Integer getImg1v1() {
                        return img1v1;
                    }

                    public void setImg1v1(Integer img1v1) {
                        this.img1v1 = img1v1;
                    }

                    public Object getTrans() {
                        return trans;
                    }

                    public void setTrans(Object trans) {
                        this.trans = trans;
                    }
                }
            }

            public static class ArtistsDTO {
                private Integer id;
                private String name;
                private Object picUrl;
                private List<?> alias;
                private Integer albumSize;
                private Integer picId;
                private String img1v1Url;
                private Integer img1v1;
                private Object trans;

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getPicUrl() {
                    return picUrl;
                }

                public void setPicUrl(Object picUrl) {
                    this.picUrl = picUrl;
                }

                public List<?> getAlias() {
                    return alias;
                }

                public void setAlias(List<?> alias) {
                    this.alias = alias;
                }

                public Integer getAlbumSize() {
                    return albumSize;
                }

                public void setAlbumSize(Integer albumSize) {
                    this.albumSize = albumSize;
                }

                public Integer getPicId() {
                    return picId;
                }

                public void setPicId(Integer picId) {
                    this.picId = picId;
                }

                public String getImg1v1Url() {
                    return img1v1Url;
                }

                public void setImg1v1Url(String img1v1Url) {
                    this.img1v1Url = img1v1Url;
                }

                public Integer getImg1v1() {
                    return img1v1;
                }

                public void setImg1v1(Integer img1v1) {
                    this.img1v1 = img1v1;
                }

                public Object getTrans() {
                    return trans;
                }

                public void setTrans(Object trans) {
                    this.trans = trans;
                }
            }
        }
    }
}
