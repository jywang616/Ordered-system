package ordered_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ordered_system.entity.DishFlavor;
import ordered_system.mapper.DishFlavorMapper;
import ordered_system.service.DishFlavorService;

@Service
public class DishFlavorImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
