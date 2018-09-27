package activity.huafeng.com.myapplication1.bean;

/**
 * Created by xian on 2018/8/16.
 */

public class LoginBean_Security {
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
        private String tel;
        private String password;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id=id;
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


    }
}
