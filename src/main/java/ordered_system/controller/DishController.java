package ordered_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ordered_system.common.CustomException;
import ordered_system.common.R;
import ordered_system.dto.DishDto;
import ordered_system.entity.Category;
import ordered_system.entity.Dish;
import ordered_system.entity.DishFlavor;
import ordered_system.entity.Setmeal;
import ordered_system.service.CategoryService;
import ordered_system.service.DishFlavorService;
import ordered_system.service.DishService;
import lombok.extern.slf4j.Slf4j;
import ordered_system.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//菜品管理
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;


    //新增菜品
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

   //菜品信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null,Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    //根据id查询菜品信息和对应的口味信息
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    //修改菜品
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);

        return R.success("新增菜品成功");
    }
    //更新菜品为停售
    @PostMapping("/status/0")
    public R<String> updateStatusStop(Long ids){
        Dish dish=dishService.getById(ids);
        dish.setStatus(0);
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getId, ids);
        dishService.update(dish, lambdaQueryWrapper);
        return R.success("更新成功");
    }
    //更新菜品状态为起售
    @PostMapping("/status/1")
    public R<String> updateStatusStart(Long ids){
        Dish dish=dishService.getById(ids);
        dish.setStatus(1);
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getId, ids);
        dishService.update(dish, lambdaQueryWrapper);
        return R.success("更新成功");
    }

    /*@DeleteMapping
    public R<String> delete(Long ids){
        log.info("删除分类，id为："+ids);
        dishService.remove(ids);
        return R.success("分类信息删除成功");
    }*/
    //根据id删除菜品
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("删除的ids：{}", ids);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids);
        List<Dish> dishes = dishService.list(queryWrapper);
        for (Dish dish : dishes) {
            if (dish.getStatus() == 1) {
                throw new CustomException("删除列表中存在启售状态商品，无法删除");
            }
        }
        dishService.remove(queryWrapper);
        return R.success("删除成功");
    }

   /* @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null ,Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus,1);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        return R.success(list);
    }*/

   @GetMapping("/list")
   public R<List<DishDto>> list(Dish dish){
       //构造查询条件
       LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
       queryWrapper.eq(dish.getCategoryId() != null ,Dish::getCategoryId,dish.getCategoryId());
       //添加条件，查询状态为1（起售状态）的菜品
       queryWrapper.eq(Dish::getStatus,1);
       //添加排序条件
       queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
       List<Dish> list = dishService.list(queryWrapper);
       List<DishDto> dishDtoList = list.stream().map((item) -> {
           DishDto dishDto = new DishDto();
           BeanUtils.copyProperties(item,dishDto);
           Long categoryId = item.getCategoryId();//分类id
           //根据id查询分类对象
           Category category = categoryService.getById(categoryId);
           if(category != null){
               String categoryName = category.getName();
               dishDto.setCategoryName(categoryName);
           }
           //当前菜品的id
           Long dishId = item.getId();
           LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
           lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
           //SQL:select * from dish_flavor where dish_id = ?
           List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
           dishDto.setFlavors(dishFlavorList);
           return dishDto;
       }).collect(Collectors.toList());

       return R.success(dishDtoList);
   }



    @PostMapping("/status/{status}")
    public R<String> updateMulStatus(@PathVariable Integer status, Long[] ids){
        List<Long> list = Arrays.asList(ids);
        //list.forEach(System.out::println);

        //构造条件构造器
        LambdaUpdateWrapper<Dish> updateWrapper = new LambdaUpdateWrapper<>();
        //添加过滤条件
        updateWrapper.set(Dish::getStatus,status).in(Dish::getId,list);
        dishService.update(updateWrapper);

        return R.success("菜品信息修改成功");
    }

}

