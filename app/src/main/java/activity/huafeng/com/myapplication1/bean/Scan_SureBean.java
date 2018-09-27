package activity.huafeng.com.myapplication1.bean;


import java.util.List;

/**
 * Created by leovo on 2018-09-10.
 */

public class Scan_SureBean {

    private String type;// 登录代码
    private String message;// 响应描述
    private String value;
    private List<Data> data;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List <Data> getData() {
        return data;
    }

    public void setData(List <Data> data) {
        this.data = data;
    }

    public class Data{

    }
}
