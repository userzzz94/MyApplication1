package activity.huafeng.com.myapplication1.bean;

import java.util.List;

/**
 * Created by leovo on 2018-08-31.
 */

// 司机在途订单信息
public class Driver_SendCarBean {

    private String code;
    private String msg;
    private int count;
    private List<OrderInfo> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OrderInfo> getData() {
        return data;
    }

    public void setData(List<OrderInfo> data) {
        this.data = data;
    }

    public class OrderInfo {
        private String CIdName;//车队名称
        private String CdIdName;//矿点名称
        private String Jhdh;//计划单号
        private String RwId;// 任务Id
        private String Qsd;//起始地
        private String Zdd;//终到地
        private String Hwmc;//货物名称
        private String Dw;//吨位
        private String id;//司机ID
        private String Sjmc; //司机姓名
        private String Sjdh; //司机电话
        private String Cph;  //车牌号
        private String Sfz;  //身份证
        private String CId; //车队ID
        private String Cx; //车型
        private int Zt;  //车辆状态
        private String CreateTime;//创建时间
        private String CreateUser;//创建人
        private String UpdateUser;//修改人
        private String UpdateTime;//修改时间
        private int state;
        private String Jd;// 经度
        private String Wd; // 纬度

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
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public String getCreateUser() {
            return CreateUser;
        }

        public void setCreateUser(String createUser) {
            CreateUser = createUser;
        }

        public String getUpdateUser() {
            return UpdateUser;
        }

        public void setUpdateUser(String updateUser) {
            UpdateUser = updateUser;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String updateTime) {
            UpdateTime = updateTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
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
    }


}
