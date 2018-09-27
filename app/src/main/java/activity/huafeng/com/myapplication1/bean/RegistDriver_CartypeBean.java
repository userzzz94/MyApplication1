package activity.huafeng.com.myapplication1.bean;

import java.util.List;

/**
 * Created by leovo on 2018-09-19.
 */

public class RegistDriver_CartypeBean {

    
    private String message;
    private String type;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {

        private String Id;
        private String Name;//车型



        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }
}
