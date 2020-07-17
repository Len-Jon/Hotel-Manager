package com.manage.hotel.Controller;

import com.manage.hotel.Entity.Customer;
import com.manage.hotel.Mapper.CustomerMapper;
import com.manage.hotel.Mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 韦庆斌
 * @since 2020/4/20 23:15
 */
@Controller
public class CustomerController {
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    private LogMapper logMapper;

    /**
     * @return 管理用户界面
     */
    @GetMapping("/customerManage") //处理请求后重定向到这里
    public ModelAndView customerManage() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "customerManage");
        return modelAndView;
    }

    /**
     * @return 添加用户界面
     */
    @GetMapping("/addCustomer")
    public ModelAndView addCustomer() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "addCustomer");
        return modelAndView;
    }


    /**
     * @param id 顾客id
     * @return 更新顾客页面
     */
    @GetMapping("/updateCustomer")
    public ModelAndView updateCustomer(int id) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "updateCustomer");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    /**
     * @return 统计图表页面
     */
    @GetMapping("/statistics")
    public ModelAndView statistics() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "statistics");
        for (int i = 0; i < 7; i++) {
            modelAndView.addObject("day" + i, customerMapper.getByDays(i).size());
        }
        for (int i = 0; i < 12; i++) {
            modelAndView.addObject("month" + i, customerMapper.getByMonth(i).size());
        }
        return modelAndView;
    }

    /**
     * 以上返回页面
     * 以下处理请求
     */


    /**
     * 获取顾客列表
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/getAllCustomer")
    public List<Customer> getAllCustomer() {
        return customerMapper.getAll();
    }

    /**
     * @param id 顾客id
     * @return 根据id获取到的顾客实体
     */
    @ResponseBody
    @GetMapping("/getCustomerById")
    public Customer getCustomerById(int id) {
        return customerMapper.selectById(id);
    }

    /**
     * @param id 退房的顾客id
     * @return 重定向
     */
    @GetMapping("/outCheckIn")
    public String outCheckIn(int id) {
        customerMapper.change(id);

        return "redirect:/customerManage";
    }

    /**
     * @param id                 待删除顾客的id
     * @param httpServletRequest httpServletRequest
     * @return 重定向
     */
    @GetMapping("/deleteCustomer")
    public String deleteCustomer(int id, HttpServletRequest httpServletRequest) {
        customerMapper.deleteById(id);
        logMapper.insert(LogController.log(httpServletRequest) + "删除了顾客：" + customerMapper.getNameById(id));
        return "redirect:/customerManage";
    }

    /**
     * @param customer           添加顾客实体
     * @param httpServletRequest httpServletRequest
     * @return 重定向
     */
    @PostMapping("/saveCustomer")
    public String saveCustomer(Customer customer, HttpServletRequest httpServletRequest) {
        customer.setName(customer.getName().replace("<", "&lt;"));
        customer.setName(customer.getName().replace(">", "&gt;"));
        customerMapper.insert(customer);
        logMapper.insert(LogController.log(httpServletRequest) + "添加了顾客：" + customer.getName());

        return "redirect:/customerManage";
    }

    /**
     * @param name               更新顾客名字
     * @param phoneNumber        更新顾客手机号
     * @param coupon             更新顾客优惠券类型
     * @param id                 更新顾客id
     * @param httpServletRequest httpServletRequest
     * @return 重定向
     */
    @PostMapping("/doUpdateCustomer")
    public String doUpdateCustomer(String name, String phoneNumber, int coupon, int id, HttpServletRequest httpServletRequest) {
        name = name.replace("<", "&lt;");
        name = name.replace("<", "&gt;");

        customerMapper.Update(name, phoneNumber, coupon, id);
        logMapper.insert(LogController.log(httpServletRequest) + "更新了顾客：" + name);

        return "redirect:/customerManage";
    }


}
