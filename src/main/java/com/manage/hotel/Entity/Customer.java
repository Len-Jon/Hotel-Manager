package com.manage.hotel.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 顾客
 * </p>
 *
 * @author 韦庆斌
 * @since 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名字
     */
    private String name;

    /**
     * 电话号码
     */
    @TableField("phoneNumber")
    private String phoneNumber;

    /**
     * 优惠券id
     */
    private Integer coupon;

    /**
     * 入住状态
     */
    private Integer type;

    /**
     * 入住时间
     */
    private String checkin;

    /**
     * 退房时间
     */
    private String checkout;


}
