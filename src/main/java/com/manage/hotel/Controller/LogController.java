package com.manage.hotel.Controller;

import com.manage.hotel.Entity.Log;
import com.manage.hotel.Mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 韦庆斌
 * @since 2020/4/20 23:04
 */
@Controller
public class LogController {
    @Autowired
    LogMapper logMapper;


    /**
     * @return 操作记录页面
     */
    @GetMapping("/logs")
    public ModelAndView getLog() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "logs");
        return modelAndView;
    }

    /**
     * 以上返回页面
     * 以下处理请求
     */

    /**
     * @return 操作记录列表
     */
    @ResponseBody
    @GetMapping("/getLogs")
    public List<Log> getAll() {
        return logMapper.getAll();
    }

    /**
     * @param request request
     * @return 管理员id及name的格式化字符串
     */
    public static String log(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String log = "id为 " + session.getAttribute("id") + " , 名字为 " + session.getAttribute("name") + " 的管理员";
        return log.replace("<", "&lt;").replace(">", "&gt;");
    }

}
