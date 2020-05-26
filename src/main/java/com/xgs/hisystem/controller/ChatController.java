package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.entity.UserEntity;
import com.xgs.hisystem.pojo.vo.applyRspVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Handler;

@Controller
public class ChatController {

    static HashMap<String, HashSet<String>> notice = new HashMap<>();

    @RequestMapping("/chatIndex")
    public String chatIndex(){

        return "userIndex";
    }

    @RequestMapping("/doctorDetail/{name}")
    public String doctorDetail(@PathVariable("name") String name){
        return "doctorDetail";
    }


    @RequestMapping("/contact/{name}")
    public String contactTo(@PathVariable("name") String name, Model model){
        Subject subject = SecurityUtils.getSubject();
        UserEntity userEntity = (UserEntity) subject.getPrincipal();
        if(notice.containsKey(name))
        {
            notice.get(name).add(userEntity.getUsername());
        }
        else
        {
            notice.put(name,new HashSet<>());
            notice.get(name).add(userEntity.getUsername());
        }
        model.addAttribute("username",userEntity.getUsername());
        model.addAttribute("toUser",name);
        return "chatRoom";
    }
    @RequestMapping("/receiveChat/{name}")
    public String receiveChat(@PathVariable("name") String name, Model model){
        Subject subject = SecurityUtils.getSubject();
        UserEntity userEntity = (UserEntity) subject.getPrincipal();
        notice.get(userEntity.getUsername()).remove(name);
        model.addAttribute("username",userEntity.getUsername());
        model.addAttribute("toUser",name);
        return "chatRoom";
    }

    @RequestMapping("/getChatNotice")
    public String getNotice(Model model){
        Subject subject = SecurityUtils.getSubject();
        UserEntity userEntity = (UserEntity) subject.getPrincipal();
        HashSet<String> set = notice.get(userEntity.getUsername());
        model.addAttribute("applyRspList", set);
        return "common/common_head::chatNotice";
    }

    @RequestMapping("/download")
    @ResponseBody
    public void downloadLocal(HttpServletResponse response) throws Exception {
        /** 获取静态文件的路径 .*/
        String path = ResourceUtils.getURL("classpath:").getPath() + "static/words.txt";

        /** 获取文件的名称 . */
        String fileName = path.substring(path.lastIndexOf("/") +1);
        File file = new File(path);
        if (!file.exists()){
            return;
        }

        /** 将文件名称进行编码 */
        response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
        response.setContentType("content-type:octet-stream");
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1){ /** 将流中内容写出去 .*/
            outputStream.write(buffer ,0 , len);
        }
        inputStream.close();
        outputStream.close();
    }



}
