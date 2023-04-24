package ordered_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ordered_system.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
