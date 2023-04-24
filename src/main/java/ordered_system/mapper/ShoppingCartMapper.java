package ordered_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ordered_system.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}
