package com.manage.hotel.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工
 * </p>
 *
 * @author 韦庆斌
 * @since 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名字
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 职位id
     */
    private Integer job;

    /**
     * 工号
     */
    private Integer jobNumber;

    /**
     * 图片路径
     */
    private String path;

    /**
     * 地址
     */
    private String address;

    /**
     * 评价
     */
    private String evaluation;

    /**
     * 描述
     */
    private String description;


}
