package ordered_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ordered_system.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
