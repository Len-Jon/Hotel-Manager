package com.manage.hotel.Controller;

import com.manage.hotel.Entity.Jobs;
import com.manage.hotel.Mapper.JobsMapper;
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
 * @since 2020/4/20 23:16
 */
@Controller
public class JobController {
    @Autowired
    JobsMapper jobsMapper;
    @Autowired
    private LogMapper logMapper;


    /**
     * @return 岗位管理页面
     */
    @GetMapping("/jobManage")
    public ModelAndView jobManage() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "jobManage");
        return modelAndView;
    }

    /**
     * @return 岗位添加页面
     */
    @GetMapping("/addJob")
    public ModelAndView addJob() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "addJob");
        return modelAndView;
    }

    /**
     * @param id 岗位id
     * @return 岗位更新页面
     */
    @GetMapping("/updateJob")
    public ModelAndView updateJob(int id) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "updateJob");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    /**
     * 以上返回页面
     * 以下处理请求
     */

    /**
     * @return 岗位列表
     */
    @ResponseBody
    @GetMapping("/getAllJobs")
    public List<Jobs> getAll() {
        return jobsMapper.selectAll();
    }

    /**
     * @param id 岗位id
     * @return 根据id获取到的岗位
     */
    @ResponseBody
    @GetMapping("/getJobById")
    public Jobs getById(int id) {
        return jobsMapper.selectById(id);
    }

    /**
     * @param jobs               添加岗位的实体
     * @param httpServletRequest httpServletRequest
     * @return 重定向
     */
    @PostMapping("/saveJob")
    public String saveJob(Jobs jobs, HttpServletRequest httpServletRequest) {
        jobs.setName(jobs.getName().replace("<", "&lt;"));
        jobs.setName(jobs.getName().replace(">", "&gt;"));
        jobsMapper.insert(jobs);
        logMapper.insert(LogController.log(httpServletRequest) + "添加了岗位：" + jobs.getName());
        return "redirect:/jobManage";
    }

    /**
     * @param jobs               更新岗位的实体
     * @param httpServletRequest httpServletRequest
     * @return 重定向
     */
    @PostMapping("/doUpdateJob")
    public String doUpdateJob(Jobs jobs, HttpServletRequest httpServletRequest) {
        jobs.setName(jobs.getName().replace("<", "&lt;"));
        jobs.setName(jobs.getName().replace(">", "&gt;"));
        jobsMapper.updateById(jobs);
        logMapper.insert(LogController.log(httpServletRequest) + "更新了岗位：" + jobs.getName());
        return "redirect:/jobManage";
    }


}
