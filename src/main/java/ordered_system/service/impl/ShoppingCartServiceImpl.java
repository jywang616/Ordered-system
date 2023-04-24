package ordered_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ordered_system.entity.ShoppingCart;
import ordered_system.mapper.ShoppingCartMapper;
import ordered_system.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
