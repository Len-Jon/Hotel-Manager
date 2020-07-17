package com.manage.hotel.Mapper;

import com.manage.hotel.Entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 管理员 Mapper 接口
 * </p>
 *
 * @author 韦庆斌
 * @since 2020-04-20
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
    @Update({"update admin set name=#{name},pass=#{pass},updatedAt=NOW() where id=#{id}"})
    void Update1(int id, String name, String pass);

    @Update({"update admin set type=#{type},updatedAt=NOW() where id=#{id}"})
    void Update2(int id, int type);

    @Select({"select * from admin"})
    List<Admin> selectAll();

    @Select({"select * from admin where name=#{name}"})
    Admin selectByName(String name);

    @Select({"select name from admin where id=#{id}"})
    String getNameById(int id);
}
