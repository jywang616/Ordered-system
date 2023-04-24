package ordered_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ordered_system.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

}