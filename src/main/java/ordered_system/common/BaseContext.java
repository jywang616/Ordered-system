package ordered_system.common;

//基于ThreadLocal封装工具类，用于保存获取当前登录用户id
//作用范围是一个线程之内

public class BaseContext {
    private static ThreadLocal<Long> threadLocal= new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
