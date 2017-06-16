package net.keihong.web.imapp.push.bean;

/**
 * Created by ${KeiHong}
 * on 2017/6/16 at 23:41.
 * Description:
 */
public class User {

    private String name;
    private int sex;

    public User(String name, int sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
