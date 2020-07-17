package com.manage.hotel.Mapper;

import com.manage.hotel.Entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 员工 Mapper 接口
 * </p>
 *
 * @author 韦庆斌
 * @since 2020-04-20
 */
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {
    @Select({"select * from employee"})
    List<Employee> selectAll();

    @Select({"select name from employee where id=#{id}"})
    String getNameById(int id);
}
