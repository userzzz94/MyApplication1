package activity.huafeng.com.myapplication1.bean;

/**
 * Created by xian on 2018/9/13.
 */

public class UpdateBean {
    private int code;
    private int count;
    private int type;
    private String message;
    private String msg;
    private Data data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type=type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code=code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count=count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg=msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message=message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String Id;
        private String vername;// 软件名
        private String Version;// 版本号
        private String Describe;// 更新内容
        private String Url;// app地址
        private String Time;// 时间
        private int isforce;// 是否强制更新 0-否 1-是

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id=id;
        }

        public String getVername() {
            return vername;
        }

        public void setVername(String vername) {
            this.vername=vername;
        }

        public String getVersion() {
            return Version;
        }

        public void setVersion(String version) {
            Version=version;
        }

        public String getDescribe() {
            return Describe;
        }

        public void setDescribe(String describe) {
            Describe=describe;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String url) {
            Url=url;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time=time;
        }

        public int getIsforce() {
            return isforce;
        }

        public void setIsforce(int isforce) {
            this.isforce = isforce;
        }
    }
}
