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
 * 管理员
 * </p>
 *
 * @author 韦庆斌
 * @since 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 管理员名
     */
    private String name;

    /**
     * 管理员密码
     */
    private String pass;

    /**
     * 权限类型
     */
    private Integer type;

    /**
     * 创建时间
     */
    @TableField("createdAt")
    private String createdAt;

    /**
     * 更新时间
     */
    @TableField("updatedAt")
    private String updatedAt;


}
