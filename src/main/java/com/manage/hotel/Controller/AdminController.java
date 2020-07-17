package com.manage.hotel.Controller;

import com.manage.hotel.Entity.Admin;
import com.manage.hotel.Mapper.AdminMapper;
import com.manage.hotel.Mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 韦庆斌
 * @since 2020/4/20 23:14
 */
@Controller
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private LogMapper logMapper;

    /**
     * @return 返回管理员界面
     */
    @GetMapping("/adminManage")
    public ModelAndView adminManage() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "adminManage");
        return modelAndView;
    }

    /**
     * @return 添加管理员页面
     */
    @GetMapping("/addAdmin")
    public ModelAndView addAdmin() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "addAdmin");
        return modelAndView;
    }

    /**
     * @param id 管理员id
     * @return 更新管理员界面
     */
    @GetMapping("/updateAdmin")
    public ModelAndView updateAdmin(int id) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "updateAdmin");
        modelAndView.addObject("id", id);
        return modelAndView;
    }


    /**
     * 以上返回页面
     * 以下处理请求
     */


    /**
     * @return 管理员列表
     */
    @ResponseBody
    @GetMapping("/test")    //原来只是测试用的，发现用得上就懒得改了
    public List<Admin> test() {
        return adminMapper.selectAll();
    }

    /**
     * @param id 管理员id
     * @return 根据id获取的管理员
     */
    @ResponseBody
    @GetMapping("/selectById")
    public Admin selectById(int id) {
        return adminMapper.selectById(id);
    }

    /**
     * @param request request
     * @return 当前管理员权限类型
     */
    @ResponseBody
    @GetMapping("/check")
    public String checkType(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String name = (String) session.getAttribute("name");
        return adminMapper.selectByName(name).getType() + "";
    }

    /**
     * @param id                 待提升权限的管理员id
     * @param httpServletRequest httpServletRequest
     * @return 重定向
     */
    @GetMapping("/updateAdminLimit")
    public String updateAdminLimit(int id, HttpServletRequest httpServletRequest) {
        adminMapper.Update2(id, 1);
        logMapper.insert(LogController.log(httpServletRequest) + "更新了管理员：" + adminMapper.getNameById(id));
        return "redirect:/adminManage";
    }

    /**
     * @param id                 删除的管理员id
     * @param httpServletRequest httpServletRequest
     * @return 重定向
     */
    @GetMapping("/deleteAdmin")
    public String deleteAdmin(int id, HttpServletRequest httpServletRequest) {
        adminMapper.deleteById(id);
        logMapper.insert(LogController.log(httpServletRequest) + "删除了管理员：" + adminMapper.getNameById(id));
        return "redirect:/adminManage";
    }

    /**
     * @param admin              添加管理员实体
     * @param httpServletRequest httpServletRequest
     * @return 重定向
     */
    @PostMapping("/saveAdmin")
    public String saveAdmin(Admin admin, HttpServletRequest httpServletRequest) {
        String all = adminMapper.selectAll().toString();
        admin.setName(admin.getName().replace("<", "&lt;"));
        admin.setName(admin.getName().replace(">", "&gt;"));

        if (!all.contains("name=" + admin.getName())) {//如果name冲突则不添加
            adminMapper.insert(admin);//SQL语句自动处理时间
            logMapper.insert(LogController.log(httpServletRequest) + "添加了管理员：" + admin.getName());
        }
        return "redirect:/adminManage";
    }

    /**
     * @param id                 更新的管理员id
     * @param name               更新的管理员名字
     * @param pass               更新的管理员密码
     * @param httpServletRequest httpServletRequest
     * @return 重定向
     */
    @PostMapping("/doUpdateAdmin")
    public String doUpdateAdmin(int id, String name, String pass, HttpServletRequest httpServletRequest) {
        name = name.replace("<", "&lt;");
        name = name.replace(">", "&gt;");

        adminMapper.Update1(id, name, pass);
        logMapper.insert(LogController.log(httpServletRequest) + "更新了管理员：" + name);
        return "redirect:/adminManage";
    }

}
