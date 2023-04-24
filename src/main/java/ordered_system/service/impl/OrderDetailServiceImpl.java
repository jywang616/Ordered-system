package ordered_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ordered_system.entity.OrderDetail;
import ordered_system.mapper.OrderDetailMapper;
import ordered_system.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}