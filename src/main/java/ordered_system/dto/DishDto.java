package ordered_system.dto;
import lombok.Data;
import ordered_system.entity.Dish;
import ordered_system.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;


@Data
public class DishDto extends Dish {
    //封装页面提交的json 结构有点乱
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
