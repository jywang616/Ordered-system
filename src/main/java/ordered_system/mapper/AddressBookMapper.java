package ordered_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ordered_system.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}
