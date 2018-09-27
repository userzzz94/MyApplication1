package activity.huafeng.com.myapplication1.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leovo on 2018-08-29.
 */

public class ManagerSure_CheckBoxBean implements Serializable {


    private int isSelect;
    private List<CheckBox> checkBoxList;


    public int getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }

    public List<CheckBox> getCheckBoxList() {
        return checkBoxList;
    }

    public void setCheckBoxList(List<CheckBox> checkBoxList) {
        this.checkBoxList = checkBoxList;
    }
    public class CheckBox {

        private int checkedstate;

        public int getCheckedstate() {
            return checkedstate;
        }

        public void setCheckedstate(int checkedstate) {
            this.checkedstate = checkedstate;
        }
    }
}
