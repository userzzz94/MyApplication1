package activity.huafeng.com.myapplication1.bean;

import java.util.List;

/**
 * Created by leovo on 2018-09-13.
 */

public class Manager_OrderStateBean {

        private int code;
        private int count;
        private String msg;
        private List<OrderInfo> data;

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

        public List<OrderInfo> getData() {
            return data;
        }

        public void setData(List<OrderInfo> data) {
            this.data = data;
        }

        public class OrderInfo {
            private String CIdName;//车队名称
            private String CdIdName;//矿点名称
            private String Id;//明细ID
            private String Rwbh;//计划单号
            private String Rwmbh;//二维码编号
            private String RId;// 任务Id
            private String Qsd;//起始地
            private String Zdd;//终到地
            private String Hwmc;//货物名称
            private String SjId;//司机ID
            private String Cph;  //车牌号
            private String Sfz;  //身份证
            private String CId; //车队ID
            private String Cx; //车型
            private int Zt;  //状态 （1、初始、2.装车完成 3、开始运输、4、到达、5、完成）
            private String Mz;// 毛重
            private String Pz;//皮重
            private String Jz;//净重
            private String Sjxm;//司机姓名
            private String Kd ;//矿点
            private String Meiz;//煤种
            private String Lrr ;//录入人
            private String Lrsj ;//录入时间
            private String CdId;//车队Id
            private String Dw;// 吨位
            private String Sjdh;//司机电话
            private int state;
            private String Jd;// 经度
            private String Wd; // 纬度
            private String address;//详细地址

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

            public String getRwbh() {
                return Rwbh;
            }

            public void setRwbh(String rwbh) {
                Rwbh = rwbh;
            }

            public String getRwId() {
                return RId;
            }

            public void setRwId(String rwId) {
                RId = rwId;
            }

            public String getRwmbh() {
                return Rwmbh;
            }

            public void setRwmbh(String rwmbh) {
                Rwmbh = rwmbh;
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

            public String getSjId() {
                return SjId;
            }

            public void setSjId(String sjId) {
                SjId = sjId;
            }

            public String getSjxm() {
                return Sjxm;
            }

            public void setSjxm(String sjxm) {
                Sjxm = sjxm;
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

            public String getRId() {
                return RId;
            }

            public void setRId(String RId) {
                this.RId=RId;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address=address;
            }
        }


}
