package activity.huafeng.com.myapplication1.bean;

/**
 * Created by xian on 2018/8/16.
 */

public class LoginBean_Manager {
    private String type;
    private String message;
    private DataManager data;
    private String token;

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

    public DataManager getData() {
        return data;
    }

    public void setData(DataManager data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String Token) {
        this.token = Token;
    }

    public class DataManager {
        private String Id;
        private String UserName;//用户名
        private String tel;
        private String TrueName;//车队名
        private String address; //所属矿点
        private String password;
        private String registName;//创建人
        private String registTime;//创建时间
        private String reviseName;//修改人
        private String reviseTime;//修改时间
        private String longitude;
        private String latitude;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }


//        public int getId() {
//            return Id;
//        }
//
//        public void setId(int id) {
//            Id=id;
//        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTel() {
            return tel;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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
    }
}
