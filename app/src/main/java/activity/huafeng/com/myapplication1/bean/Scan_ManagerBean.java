package activity.huafeng.com.myapplication1.bean;

/**
 * Created by leovo on 2018-09-05.
 */

public class Scan_ManagerBean {
    private int code;
    private String msg;
    private int count;
    private OrderInfo data;


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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public OrderInfo getData() {
        return data;
    }

    public void setData(OrderInfo data) {
        this.data = data;
    }

    public class OrderInfo {

        private String Rwbh;//任务编号
        private String RId;//任务Id
        private String Id;//明细ID
        private String Rwmbh;//二维码编号
        private String CIdName;//车队名称
        private String CdIdName;//矿点名称
        private String SjId;//司机Id
        private String Qsd;//起始地
        private String Zdd;// 终到地
        private String Hwmc;//货物名称
        private int Zt;//状态（1、在线、2、离线、3、工作中）
        private String Mz;// 毛重
        private String Pz;//皮重
        private String Jz;//净重
        private String Sjxm;//司机姓名
        private String Cph ;// 车牌号
        private String Kd ;//矿点
        private String Meiz;//煤种
        private String Lrr ;//录入人
        private String Lrsj ;//录入时间
        private String CdId;//车队Id
        private String Dw;// 吨位
        private String Sjdh;//司机电话
        private String Sfz;//身份证
        private String CId;//车队ID
        private String Jd;//经度
        private String Wd;// 纬度
        private String Cx;//车型
        private String CreateTime;//创建时间
        private String CreateUser;// 创建人
        private String UpdateTime;//修改时间
        private String UpdateUser;//修改人
        private String Sf;//是否（1.空闲2忙碌）

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getRwmbh() {
            return Rwmbh;
        }

        public void setRwmbh(String rwmbh) {
            Rwmbh = rwmbh;
        }

        public String getRwbh() {
            return Rwbh;
        }

        public void setRwbh(String rwbh) {
            Rwbh = rwbh;
        }

        public String getRId() {
            return RId;
        }

        public void setRId(String RId) {
            this.RId = RId;
        }

        public String getSjId() {
            return SjId;
        }

        public void setSjId(String sjId) {
            SjId = sjId;
        }

        public String getMz() {
            return Mz;
        }

        public void setMz(String mz) {
            Mz = mz;
        }

        public String getPz() {
            return Pz;
        }

        public void setPz(String pz) {
            Pz = pz;
        }

        public String getJz() {
            return Jz;
        }

        public void setJz(String jz) {
            Jz = jz;
        }

        public String getCph() {
            return Cph;
        }

        public void setCph(String cph) {
            Cph = cph;
        }

        public String getSjxm() {
            return Sjxm;
        }

        public void setSjxm(String sjxm) {
            Sjxm = sjxm;
        }

        public String getKd() {
            return Kd;
        }

        public void setKd(String kd) {
            Kd = kd;
        }

        public String getMeiz() {
            return Meiz;
        }

        public void setMeiz(String meiz) {
            Meiz = meiz;
        }

        public String getLrr() {
            return Lrr;
        }

        public void setLrr(String lrr) {
            Lrr = lrr;
        }

        public String getLrsj() {
            return Lrsj;
        }

        public void setLrsj(String lrsj) {
            Lrsj = lrsj;
        }

        public String getCdId() {
            return CdId;
        }

        public void setCdId(String cdId) {
            CdId = cdId;
        }

        public String getDw() {
            return Dw;
        }

        public void setDw(String dw) {
            Dw = dw;
        }

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

        public int getZt() {
            return Zt;
        }

        public void setZt(int zt) {
            Zt = zt;
        }

        public String getSjdh() {
            return Sjdh;
        }

        public void setSjdh(String sjdh) {
            Sjdh = sjdh;
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

        public String getCx() {
            return Cx;
        }

        public void setCx(String cx) {
            Cx = cx;
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

        public String getSf() {
            return Sf;
        }

        public void setSf(String sf) {
            Sf = sf;
        }
    }
}
