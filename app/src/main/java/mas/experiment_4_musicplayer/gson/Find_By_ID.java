package mas.experiment_4_musicplayer.gson;

import java.util.List;

public class Find_By_ID {

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private List<DataDTO> data;
    private Integer code;

    public static class DataDTO {
        private Integer id;
        private String url;
        private Integer br;
        private Integer size;
        private String md5;
        private Integer code;
        private Integer expi;
        private String type;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getBr() {
            return br;
        }

        public void setBr(Integer br) {
            this.br = br;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public Integer getExpi() {
            return expi;
        }

        public void setExpi(Integer expi) {
            this.expi = expi;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getGain() {
            return gain;
        }

        public void setGain(Integer gain) {
            this.gain = gain;
        }

        public Integer getFee() {
            return fee;
        }

        public void setFee(Integer fee) {
            this.fee = fee;
        }

        public Object getUf() {
            return uf;
        }

        public void setUf(Object uf) {
            this.uf = uf;
        }

        public Integer getPayed() {
            return payed;
        }

        public void setPayed(Integer payed) {
            this.payed = payed;
        }

        public Integer getFlag() {
            return flag;
        }

        public void setFlag(Integer flag) {
            this.flag = flag;
        }

        public Boolean getCanExtend() {
            return canExtend;
        }

        public void setCanExtend(Boolean canExtend) {
            this.canExtend = canExtend;
        }

        public Object getFreeTrialInfo() {
            return freeTrialInfo;
        }

        public void setFreeTrialInfo(Object freeTrialInfo) {
            this.freeTrialInfo = freeTrialInfo;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getEncodeType() {
            return encodeType;
        }

        public void setEncodeType(String encodeType) {
            this.encodeType = encodeType;
        }

        public FreeTrialPrivilegeDTO getFreeTrialPrivilege() {
            return freeTrialPrivilege;
        }

        public void setFreeTrialPrivilege(FreeTrialPrivilegeDTO freeTrialPrivilege) {
            this.freeTrialPrivilege = freeTrialPrivilege;
        }

        public FreeTimeTrialPrivilegeDTO getFreeTimeTrialPrivilege() {
            return freeTimeTrialPrivilege;
        }

        public void setFreeTimeTrialPrivilege(FreeTimeTrialPrivilegeDTO freeTimeTrialPrivilege) {
            this.freeTimeTrialPrivilege = freeTimeTrialPrivilege;
        }

        public Integer getUrlSource() {
            return urlSource;
        }

        public void setUrlSource(Integer urlSource) {
            this.urlSource = urlSource;
        }

        private Integer gain;
        private Integer fee;
        private Object uf;
        private Integer payed;
        private Integer flag;
        private Boolean canExtend;
        private Object freeTrialInfo;
        private String level;
        private String encodeType;
        private FreeTrialPrivilegeDTO freeTrialPrivilege;
        private FreeTimeTrialPrivilegeDTO freeTimeTrialPrivilege;
        private Integer urlSource;

        public static class FreeTrialPrivilegeDTO {
            private Boolean resConsumable;
            private Boolean userConsumable;
        }

        public static class FreeTimeTrialPrivilegeDTO {
            private Boolean resConsumable;
            private Boolean userConsumable;
            private Integer type;
            private Integer remainTime;
        }
    }
}
