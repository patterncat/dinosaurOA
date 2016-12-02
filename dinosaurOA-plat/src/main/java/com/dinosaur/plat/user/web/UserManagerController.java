package com.dinosaur.plat.user.web;

import com.dinosaur.module.user.UserService;
import com.dinosaur.module.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户管理控制器
 * @Author Alcott Hawk
 * @Date 11/28/2016
 */
@Controller
@RequestMapping(value = "/user/manager")
public class UserManagerController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    /**
     * 加入添加用户页面
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(){
        return  "user/create";
    }

    /**
     * 保存添加一个用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(User user){
        String id = null;
        try {
            id = userService.addUser(user).getId();
        }catch (Exception e){
            logger.error("用户创建失败："+e.getMessage());
            return "error";
        }
        return id;
    }

}
