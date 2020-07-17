package com.manage.hotel.Mapper;

import com.manage.hotel.Entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 顾客 Mapper 接口
 * </p>
 *
 * @author 韦庆斌
 * @since 2020-04-20
 */
@Repository
public interface CustomerMapper extends BaseMapper<Customer> {
    @Update({"update customer set name=#{name},phoneNumber=#{phoneNumber},coupon=#{coupon} where id=#{id}"})
    void Update(String name, String phoneNumber, int coupon, int id);

    @Update({"update customer set type=1,checkout=NOW() where id=#{id}"})
    void change(int id);

    @Select({"select * from customer order by checkin"})
    List<Customer> getAll();

    @Select({"select name from customer where id=#{id}"})
    String getNameById(int id);

    @Select({"select * from customer where to_days( now( ) ) - to_days(checkin) =#{day}"})
    List<Customer> getByDays(int day);

    @Select({"select * from customer where period_diff( date_format( now( ) , '%Y%m' ) , date_format( checkin, '%Y%m' ) ) =#{month}"})
    List<Customer> getByMonth(int month);
}
