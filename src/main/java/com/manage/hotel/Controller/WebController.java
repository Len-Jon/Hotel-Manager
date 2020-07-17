package com.manage.hotel.Controller;

import com.manage.hotel.Entity.Admin;
import com.manage.hotel.Mapper.AdminMapper;
import com.manage.hotel.Mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 韦庆斌
 * @since 2020/4/20 21:49
 */
@Controller
public class WebController {
    @Autowired
    private AdminMapper adminMapper;

    /**
     * @return 登录页面
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 前端获取的数据：
     *
     * @param name     输入端管理员名
     * @param pass     输入的密码
     * @param request  request
     * @return 成功或错误信息，由前端js处理返回信息
     */
    @ResponseBody
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(String name, String pass, HttpServletRequest request) {
        HttpSession session = request.getSession();
        name = name.replace("<", "&lt;");
        name = name.replace(">", "&gt;");
        //这里为了防止有人用html标签搞破坏 TAT
        Admin admin = adminMapper.selectByName(name);
        String pwd = admin.getPass();
        int id = admin.getId();
        if (pwd.equals(pass)) {//则添加Session并向响应头Cookie添加SessionID字段
            session.setAttribute("id", "" + id);
            session.setAttribute("name", name);
            session.setMaxInactiveInterval(10 * 60);//秒为单位
            return "登陆成功";
        } else
            return "请输入正确的用户名和密码";
    }

    /**
     * 拦截器判断有有效session/cookies 会放行到这里
     *
     * @return 主页面
     */
    @RequestMapping("/")
    public ModelAndView toIndex() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "employeeManage");
        return modelAndView;
    }

    /**
     * 清除Session和Cookie
     *
     * @return 登出
     */
    @RequestMapping("/logout")
    public String logout(HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setMaxInactiveInterval(0);//清除session

        Cookie cookie = new Cookie("SessionID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);//清除cookie的SessionID
        return "login";
    }


    /**
     * 上传图片
     */
    @Value("${${location}.path}")
    private String fileSavePath;

    @RequestMapping(method = RequestMethod.POST, path = "/fileupload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) throws IOException {//因为webUploader上传的文件的“name”为file，要手动绑定
        //设置图片为唯一的uuid
        String[] s = picture.getOriginalFilename().split("\\.");
        String last = s[s.length - 1];
        String pictureName = UUID.randomUUID().toString() + "." + last;
        picture.transferTo(new File(fileSavePath + pictureName));
        return pictureName;
    }

}
