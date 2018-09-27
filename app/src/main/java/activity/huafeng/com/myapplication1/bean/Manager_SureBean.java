package activity.huafeng.com.myapplication1.bean;

import java.util.List;

/**
 * Created by leovo on 2018-08-28.
 */

//选择司机
public class Manager_SureBean {
    private int code;// 登录代码
    private int count;//数据条数
    private String msg;// 响应描述
    private List <Manager_SureBean.DriverInfo> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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

    public List <Manager_SureBean.DriverInfo> getData() {
        return data;
    }

    public void setData(List <Manager_SureBean.DriverInfo> data) {
        this.data = data;
    }

    public class DriverInfo  {
        private String  CIdName; //车队名称
        private String CdIdName; //矿点名称
        private String Jhdh; //计划单号
        private String RwId;//计划任务Id
        private String Id;   //司机ID
        private String Sjmc; //司机姓名
        private String Sjdh; //司机电话
        private String Cph;  //车牌号
        private String Sfz;  //身份证
        private String CId; //车队ID
        private String Cx; //车型
        private String Jd;//经度
        private String Wd;//纬度
        private int Zt;  //状态（1、在线、2、离线、）
        private int Sf;// 是否（1空闲2忙碌)
        private int checkedstate;

        public String getCIdName() {
            return CIdName;
        }

        public void setCIdName(String CIdName) {
            this.CIdName = CIdName;
        }

        public String getCdIdName() {
            return CdIdName;
        }

        public void setCdIdName(String cdIdName) {
            CdIdName = cdIdName;
        }

        public String getJhdh() {
            return Jhdh;
        }

        public void setJhdh(String jhdh) {
            Jhdh = jhdh;
        }

        public String getRwId() {
            return RwId;
        }

        public void setRwId(String rwId) {
            RwId = rwId;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getSjmc() {
            return Sjmc;
        }

        public void setSjmc(String sjmc) {
            Sjmc = sjmc;
        }

        public String getSjdh() {
            return Sjdh;
        }

        public void setSjdh(String sjdh) {
            Sjdh = sjdh;
        }

        public String getCph() {
            return Cph;
        }

        public void setCph(String cph) {
            Cph = cph;
        }

        public String getSfz() {
            return Sfz;
        }

        public void setSfz(String sfz) {
            Sfz = sfz;
        }

        public String getCId() {
            return CId;
        }

        public void setCId(String CId) {
            this.CId = CId;
        }

        public String getCx() {
            return Cx;
        }

        public void setCx(String cx) {
            Cx = cx;
        }

        public int getZt() {
            return Zt;
        }

        public void setZt(int zt) {
            Zt = zt;
        }

        public String getJd() {
            return Jd;
        }

        public void setJd(String jd) {
            Jd = jd;
        }

        public String getWd() {
            return Wd;
        }

        public void setWd(String wd) {
            Wd = wd;
        }

        public int getSf() {
            return Sf;
        }

        public void setSf(int sf) {
            Sf = sf;
        }

        public int getCheckedstate() {
            return checkedstate;
        }

        public void setCheckedstate(int checkedstate) {
            this.checkedstate = checkedstate;
        }
    }
}
