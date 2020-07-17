package com.manage.hotel.Controller;

import com.manage.hotel.Entity.Coupon;
import com.manage.hotel.Mapper.CouponMapper;
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
public class CouponController {
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    private LogMapper logMapper;

    /**
     * @return 优惠券管理页面
     */
    @GetMapping("/couponManage")
    public ModelAndView couponManage() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "couponManage");
        return modelAndView;
    }

    /**
     * @return 添加优惠券页面
     */
    @GetMapping("/addCoupon")
    public ModelAndView addCoupon() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "addCoupon");
        return modelAndView;
    }

    /**
     * @param id 优惠券id
     * @return 更新优惠券页面
     */
    @GetMapping("/updateCoupon")
    public ModelAndView updateCoupon(int id) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("model", "updateCoupon");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    /**
     * 以上返回页面
     * 以下处理请求
     */

    /**
     * @return 优惠券列表
     */
    @ResponseBody
    @GetMapping("/getAllCoupon")
    public List<Coupon> getAllCoupon() {
        return couponMapper.getAll();
    }

    /**
     * @param id 优惠券id
     * @return 根据id获取的优惠券
     */
    @ResponseBody
    @GetMapping("/getCouponById")
    public Coupon getCouponById(int id) {
        return couponMapper.selectById(id);
    }


    /**
     * @param coupon             更新的实体
     * @param httpServletRequest httpServletRequest
     * @return 重定向
     */
    @PostMapping("/doUpdateCoupon")
    public String doUpdateCoupon(Coupon coupon, HttpServletRequest httpServletRequest) {
        coupon.setName(coupon.getName().replace("<", "&lt;"));
        coupon.setName(coupon.getName().replace(">", "&gt;"));
        couponMapper.updateById(coupon);
        logMapper.insert(LogController.log(httpServletRequest) + "更新了优惠券：" + coupon.getName());
        return "redirect:/couponManage";
    }

    /**
     * 添加优惠券
     *
     * @param coupon             添加的优惠券实体
     * @param httpServletRequest httpServletRequest
     * @return 重定向
     */
    @PostMapping("/saveCoupon")
    public String saveCoupon(Coupon coupon, HttpServletRequest httpServletRequest) {
        coupon.setName(coupon.getName().replace("<", "&lt;"));
        coupon.setName(coupon.getName().replace(">", "&gt;"));
        couponMapper.insert(coupon);
        logMapper.insert(LogController.log(httpServletRequest) + "添加了优惠券：" + coupon.getName());
        return "redirect:/couponManage";
    }
}
