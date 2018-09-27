package activity.huafeng.com.myapplication1.bean;

import java.util.List;

/**
 * Created by xian on 2018/8/2.
 */

public class RegistManager_PlaceBean {

    private int count;
    private String msg;
    private String type;
    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {
        private String Id;
        private String Kdmc; // 矿点名称
        private String Dz;// 矿点地址

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getKdmc() {
            return Kdmc;
        }

        public void setKdmc(String kdmc) {
            Kdmc = kdmc;
        }

        public String getDz() {
            return Dz;
        }

        public void setDz(String dz) {
            Dz = dz;
        }
    }
}
