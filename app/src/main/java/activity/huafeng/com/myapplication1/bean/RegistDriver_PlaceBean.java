package activity.huafeng.com.myapplication1.bean;

import java.util.List;

/**
 * Created by leovo on 2018-08-29.
 */

public class RegistDriver_PlaceBean {

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
        private String CdMc;//车队名称
        private String CdDh;//车队电话
        private String Zcmc;//注册名
        private String Ma;// 密码
        private String KId;//所属矿点


        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getCdMc() {
            return CdMc;
        }

        public void setCdMc(String cdMc) {
            CdMc = cdMc;
        }

        public String getCdDh() {
            return CdDh;
        }

        public void setCdDh(String cdDh) {
            CdDh = cdDh;
        }

        public String getZcmc() {
            return Zcmc;
        }

        public void setZcmc(String zcmc) {
            Zcmc = zcmc;
        }

        public String getMa() {
            return Ma;
        }

        public void setMa(String ma) {
            Ma = ma;
        }

        public String getKId() {
            return KId;
        }

        public void setKId(String KId) {
            this.KId = KId;
        }
    }
}
