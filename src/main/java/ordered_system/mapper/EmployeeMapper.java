package ordered_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ordered_system.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
