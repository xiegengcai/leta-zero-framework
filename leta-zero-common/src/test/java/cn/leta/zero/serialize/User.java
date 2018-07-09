package cn.leta.zero.serialize;

import java.io.Serializable;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2017/10/25.
 */
public class User implements Serializable {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return this == null;
        }
        if (obj instanceof User) {
            User u = (User) obj;
            return this.getAge() == u.getAge() && this.getName().equals(u.getName());
        }
        return false;
    }
}
