package com.manage.hotel.Mapper;

import com.manage.hotel.Entity.Jobs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 职位 Mapper 接口
 * </p>
 *
 * @author 韦庆斌
 * @since 2020-04-20
 */
@Repository
public interface JobsMapper extends BaseMapper<Jobs> {
    @Select({"select * from jobs"})
    List<Jobs> selectAll();
}
