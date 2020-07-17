package com.manage.hotel.Controller;

import com.manage.hotel.Entity.Employee;
import com.manage.hotel.Mapper.EmployeeMapper;
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
public class EmployeeController {
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    private LogMapper logMapper;

    /**
     * @return 员工管理页面
     */
    @GetMapping("/employeeManage")
    public ModelAndView emloyeeManage() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "employeeManage");
        return modelAndView;
    }

    /**
     * @return 添加员工页面
     */
    @GetMapping("/addEmployee")
    public ModelAndView updateEmployee() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "addEmployee");
        return modelAndView;
    }

    /**
     * @param id 员工id
     * @return 更新员工页面
     */
    @GetMapping("/updateEmployee")
    public ModelAndView updateEmployee(int id) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "updateEmployee");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    /**
     * 以上返回页面
     * 以下处理请求
     */

    /**
     * @return 员工列表
     */
    @ResponseBody
    @GetMapping("/getAllEmployee")
    public List<Employee> getAll() {
        return employeeMapper.selectAll();
    }

    /**
     * @param id 员工id
     * @return 根据id获取员工
     */
    @ResponseBody
    @GetMapping("/getEmployeeById")
    public Employee getEmployee(int id) {
        return employeeMapper.selectById(id);
    }


    /**
     * @param id                 待删除员工的id
     * @param httpServletRequest httpServletRequest
     * @return 重定向
     */
    @GetMapping("/deleteEmployee")
    public String deleteEmployee(int id, HttpServletRequest httpServletRequest) {
        employeeMapper.deleteById(id);
        logMapper.insert(LogController.log(httpServletRequest) + "删除了员工：" + employeeMapper.getNameById(id));
        return "redirect:/employeeManage";
    }

    /**
     * @param employee           待添加员工实体
     * @param httpServletRequest request
     * @return 重定向
     */
    @PostMapping("/saveEmployee")
    public String addEmployee(Employee employee, HttpServletRequest httpServletRequest) {
        employee.setName(employee.getName().replace("<", "&lt;"));
        employee.setName(employee.getName().replace(">", "&gt;"));
        employeeMapper.insert(employee);
        logMapper.insert(LogController.log(httpServletRequest) + "添加了员工：" + employee.getName());
        return "redirect:/employeeManage";
    }

    /**
     * @param employee           待更新员工实体
     * @param httpServletRequest httpServletRequest
     * @return 重定向
     */
    @PostMapping("/doUpdateEmployee")
    public String doUpdateEmployee(Employee employee, HttpServletRequest httpServletRequest) {
        employee.setName(employee.getName().replace("<", "&lt;"));
        employee.setName(employee.getName().replace(">", "&gt;"));
        employeeMapper.updateById(employee);
        logMapper.insert(LogController.log(httpServletRequest) + "更新了员工：" + employee.getName());
        return "redirect:/employeeManage";
    }
}
