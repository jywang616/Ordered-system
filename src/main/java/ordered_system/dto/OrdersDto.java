package ordered_system.dto;

import lombok.Data;
import ordered_system.entity.OrderDetail;
import ordered_system.entity.Orders;

import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;

}