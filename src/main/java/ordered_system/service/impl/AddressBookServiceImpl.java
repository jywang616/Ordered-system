package ordered_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ordered_system.entity.AddressBook;
import ordered_system.mapper.AddressBookMapper;
import ordered_system.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
