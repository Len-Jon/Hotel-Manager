package com.manage.hotel.Mapper;

import com.manage.hotel.Entity.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 操作记录 Mapper 接口
 * </p>
 *
 * @author 韦庆斌
 * @since 2020-04-20
 */
@Repository
public interface LogMapper {
    @Select({"select * from log"})
    List<Log> getAll();

    @Insert({"insert into log(name) values(#{name})"})
    void insert(String name);
}
