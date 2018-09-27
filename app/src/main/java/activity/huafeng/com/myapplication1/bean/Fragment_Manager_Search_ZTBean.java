package activity.huafeng.com.myapplication1.bean;

import java.util.List;

/**
 * Created by leovo on 2018-08-21.
 */

public class Fragment_Manager_Search_ZTBean {

    private String code;
    private String message;
    private List<CargoInfo> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CargoInfo> getData() {
        return data;
    }

    public void setData(List<CargoInfo> data) {
        this.data = data;
    }

    public class CargoInfo {
        private int id;// 货源id
        private String startaddress;// 起始地
        private String endaddress;// 目的地
        private String goodstype;// 货物类型
        private double goodsweight;// 货物重量单位/吨
        private double goodsvolume;// 货物体积单位/方
        private double fare;// 运费金额
        private String paytypes;// 付款方式
        private String loadtime;// 装车时间
        private String loadtypes;// 承运类型
        private int masterid;// 货主id
        private String master;// 货主姓名
        private String mastertel;//货主电话
        private String insdatetime;// 创建时间
        private String goodsstates;//货源状态名称
        private String gaptime;// 时间间隔
        private int orderid;// 承运id
        private String carlongs;// 车长
        private String cartypes;// 车型

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStartaddress() {
            return startaddress;
        }

        public void setStartaddress(String startaddress) {
            this.startaddress = startaddress;
        }

        public String getEndaddress() {
            return endaddress;
        }

        public void setEndaddress(String endaddress) {
            this.endaddress = endaddress;
        }


        public String getGoodstype() {
            return goodstype;
        }

        public void setGoodstype(String goodstype) {
            this.goodstype = goodstype;
        }

        public double getGoodsweight() {
            return goodsweight;
        }

        public void setGoodsweight(double goodsweight) {
            this.goodsweight = goodsweight;
        }

        public double getGoodsvolume() {
            return goodsvolume;
        }

        public void setGoodsvolume(double goodsvolume) {
            this.goodsvolume = goodsvolume;
        }

        public double getFare() {
            return fare;
        }

        public void setFare(double fare) {
            this.fare = fare;
        }

        public String getPaytypes() {
            return paytypes;
        }

        public void setPaytypes(String paytypes) {
            this.paytypes = paytypes;
        }

        public String getLoadtime() {
            return loadtime;
        }

        public void setLoadtime(String loadtime) {
            this.loadtime = loadtime;
        }

        public String getLoadtypes() {
            return loadtypes;
        }

        public void setLoadtypes(String loadtypes) {
            this.loadtypes = loadtypes;
        }

        public int getMasterid() {
            return masterid;
        }

        public void setMasterid(int masterid) {
            this.masterid = masterid;
        }

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }

        public String getMastertel() {
            return mastertel;
        }

        public void setMastertel(String mastertel) {
            this.mastertel = mastertel;
        }

        public String getInsdatetime() {
            return insdatetime;
        }

        public void setInsdatetime(String insdatetime) {
            this.insdatetime = insdatetime;
        }

        public String getGoodsstates() {
            return goodsstates;
        }

        public void setGoodsstates(String goodsstates) {
            this.goodsstates = goodsstates;
        }

        public String getGaptime() {
            return gaptime;
        }

        public void setGaptime(String gaptime) {
            this.gaptime = gaptime;
        }

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }

        public String getCarlongs() {
            return carlongs;
        }

        public void setCarlongs(String carlongs) {
            this.carlongs = carlongs;
        }

        public String getCartypes() {
            return cartypes;
        }

        public void setCartypes(String cartypes) {
            this.cartypes = cartypes;
        }
    }
}
