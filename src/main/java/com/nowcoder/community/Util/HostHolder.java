package com.nowcoder.community.Util;

import com.nowcoder.community.entity.User;
import org.springframework.stereotype.Component;

/** 持有用户信息，代替session
 * @author sun
 * @create 2022-04-10 11:18
 */
@Component
public class HostHolder {
    //以线程为key存取值
    private ThreadLocal<User> users = new ThreadLocal<>();


    public void setUsers(User user){
        users.set(user);
    }

    public User getUsers(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
