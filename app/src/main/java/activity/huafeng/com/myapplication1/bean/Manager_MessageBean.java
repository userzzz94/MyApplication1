package activity.huafeng.com.myapplication1.bean;

import java.util.List;

/**
 * Created by leovo on 2018-08-31.
 */

public class Manager_MessageBean {

    private int count;
    private String code;// 登录代码
    private String msg;// 响应描述
    private List<Message> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }

    public class Message  {

        private String Rwbh;//任务编号
        private String Id;//任务明细Id
        private String RId;//任务Id
        private String Rwmbh ;//二维码编号
        private String SjId;//司机Id
        private String Zt;//状态（1、初始、2、装车完成、3、开始运输、4、到达、5、完成）
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

        public String getRwbh() {
            return Rwbh;
        }

        public void setRwbh(String rwbh) {
            Rwbh = rwbh;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getRId() {
            return RId;
        }

        public void setRId(String RId) {
            this.RId = RId;
        }

        public String getRwmbh() {
            return Rwmbh;
        }

        public void setRwmbh(String rwmbh) {
            Rwmbh = rwmbh;
        }

        public String getSjId() {
            return SjId;
        }

        public void setSjId(String sjId) {
            SjId = sjId;
        }

        public String getZt() {
            return Zt;
        }

        public void setZt(String zt) {
            Zt = zt;
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

        public String getSjxm() {
            return Sjxm;
        }

        public void setSjxm(String sjxm) {
            Sjxm = sjxm;
        }

        public String getCph() {
            return Cph;
        }

        public void setCph(String cph) {
            Cph = cph;
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

        public String getSjdh() {
            return Sjdh;
        }

        public void setSjdh(String sjdh) {
            Sjdh = sjdh;
        }
    }
}




