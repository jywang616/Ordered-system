package ordered_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ordered_system.dto.DishDto;
import ordered_system.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，插入菜品对应的口味数据
    //要操作两张表
    public void saveWithFlavor(DishDto dishDto);
    //根据id查询口味信息
    public DishDto getByIdWithFlavor(Long id);
    //更新菜品和口味信息
    public void updateWithFlavor(DishDto dishDto);
    //删除菜品信息
    public void remove(Long id);
}
