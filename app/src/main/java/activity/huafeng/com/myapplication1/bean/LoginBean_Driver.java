package activity.huafeng.com.myapplication1.bean;

/**
 * Created by xian on 2018/8/16.
 */

public class LoginBean_Driver {
    private String type;// 登录代码
    private String message;// 响应描述
    private String token;
    private Data data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String Id;
        private String UserName;//用户名
        private String tel;
        private String TrueName;//司机名
        private String password;
        private String pid;
        private String address; //所属矿点
        private String managerName;//所属车队
        private String carnum; //车牌号
        private String cartypes; //车型
        private String registName;//创建人
        private String registTime;//创建时间
        private String reviseName;//修改人
        private String reviseTime;//修改时间
        private int state;
        private String longitude;
        private String latitude;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getTrueName() {
            return TrueName;
        }

        public void setTrueName(String trueName) {
            TrueName = trueName;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }


        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getManagerName() {
            return managerName;
        }

        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }

        public String getCarnum() {
            return carnum;
        }

        public void setCarnum(String carnum) {
            this.carnum = carnum;
        }


        public String getRegistName() {
            return registName;
        }

        public void setRegistName(String registName) {
            this.registName = registName;
        }

        public String getRegistTime() {
            return registTime;
        }
        public void setRegistTime(String registTime) {
            this.registTime = registTime;
        }

        public String getReviseName() {
            return reviseName;
        }

        public void setReviseName(String reviseName) {
            this.reviseName = reviseName;
        }


        public String getReviseTime() {
            return reviseTime;
        }

        public void setReviseTime(String reviseTime) {
            this.reviseTime = reviseTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }


        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {

            this.latitude = latitude;
        }

        public String getCartypes() {
            return cartypes;
        }

        public void setCartypes(String cartypes) {
            this.cartypes = cartypes;
        }

    }
}
