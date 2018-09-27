package activity.huafeng.com.myapplication1.bean;

import java.util.List;

/**
 * Created by leovo on 2018-08-27.
 */

//车队接收新的订单消息
public class Manager_FindCarBean {
    private int count;//数据条目
    private int code;
    private String msg;
    private List<OrderInfo> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<OrderInfo> getData() {
        return data;
    }

    public void setData(List<OrderInfo> data) {
        this.data = data;
    }

    public class OrderInfo {
        private String KIdName;//矿点名称
        private String KId;//矿点ID
        private String  Dh;// 计划单号
        private String RwId;//任务ID
        private String Qsd;// 起始地
        private String Zdd;// 目的地
        private String Hwmc;// 货物名称
        private String Dw;// 货物重量单位/吨
        private String Id;//   车队ID
        private String CdMc;// 车队名称
        private String CdDh;// 车队电话
        private String CreateUser;// 创建人id
        private String CreateTime;// 创建时间
        private String UpdateTime;//修改时间
        private String UpdateUser;//修改人


        public String getKIdName() {
            return KIdName;
        }

        public void setKIdName(String KIdName) {
            this.KIdName = KIdName;
        }

        public String getKId() {
            return KId;
        }

        public void setKId(String KId) {
            this.KId = KId;
        }

        public String getDh() {
            return Dh;
        }

        public void setDh(String dh) {
            Dh = dh;
        }

        public String getRwId() {
            return RwId;
        }

        public void setRwId(String rwId) {
            RwId = rwId;
        }

        public String getQsd() {
            return Qsd;
        }

        public void setQsd(String qsd) {
            Qsd = qsd;
        }

        public String getZdd() {
            return Zdd;
        }

        public void setZdd(String zdd) {
            Zdd = zdd;
        }

        public String getHwmc() {
            return Hwmc;
        }

        public void setHwmc(String hwmc) {
            Hwmc = hwmc;
        }

        public String getDw() {
            return Dw;
        }

        public void setDw(String dw) {
            Dw = dw;
        }

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

        public String getCreateUser() {
            return CreateUser;
        }

        public void setCreateUser(String createUser) {
            CreateUser = createUser;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String updateTime) {
            UpdateTime = updateTime;
        }

        public String getUpdateUser() {
            return UpdateUser;
        }

        public void setUpdateUser(String updateUser) {
            UpdateUser = updateUser;
        }
    }
}
