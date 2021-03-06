package com.dinosaur.plat.group.web.controller;

import com.dinosaur.core.util.JsonResultUtil;
import com.dinosaur.core.util.entity.JsonObject;
import com.dinosaur.module.group.entity.Group;
import com.dinosaur.module.system.construction.Construction;
import com.dinosaur.module.user.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户组管理控制器
 * @Author Alcott Hawk
 * @Date 12/2/2016
 */
@Controller
@RequestMapping(value = "/group/manager")
public class GroupManagerController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GroupService groupService;

    /**
     * 加入添加组页面
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(){
        return "view/group/create";
    }

    /**
     * 保存添加一个用户组
     * @param group
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(Group group,Model model){
        String id = null;
        try {
            id = groupService.add(group).getId();
        } catch (Exception e){
            logger.error("创建用户组错误："+e.getMessage());
            model.addAttribute("message","组创建失败");
            return "redirect:/group/manager/creat";
        }
        return "redirect:/group/manager/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(value = "pageSize",defaultValue = Construction.PAGE_SIZE_STR) int pageSize,
                            @RequestParam(value = "paheNo", defaultValue = Construction.PAGE_NO_STR) int pageNo,
                            Model model){
        model.addAttribute("groups",groupService.getGroupByPage(pageSize,pageNo).getContent());
        return "view/group/list";
    }

    @RequestMapping(value = "/list-json", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject list(){
        List<Group> groups = groupService.getAll();
        return JsonResultUtil.getObjectJson(groups);
    }

}
