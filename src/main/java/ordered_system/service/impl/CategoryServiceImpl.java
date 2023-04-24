package ordered_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ordered_system.common.CustomException;
import ordered_system.entity.Category;
import ordered_system.entity.Dish;
import ordered_system.entity.Setmeal;
import ordered_system.mapper.CategoryMapper;
import ordered_system.service.CategoryService;
import ordered_system.service.DishService;
import ordered_system.service.SetmealService;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    //根据id删除分类，删除之前要进行判断，看是否关联了菜品和套餐
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1=dishService.count(dishLambdaQueryWrapper);
        if(count1>0){
            //已经关联菜品，抛出异常
            throw new CustomException("当前分类下关联菜品，不能删除");

        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2=setmealService.count(setmealLambdaQueryWrapper);
        if(count2>0){
            //已经关联套餐，抛出异常
            throw new CustomException("当前分类下关联套餐，不能删除");
        }
        super.removeById(id);
    }
}
