package ordered_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ordered_system.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}