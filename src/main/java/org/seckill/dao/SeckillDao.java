package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述 :
 *
 * @author 东野白马
 * @version 2016-10-15 13:34
 */
public interface SeckillDao {
    /**
     * 减库存
     *
     * @param seckillId id
     * @param killTime  时间
     * @return 如果影响行数>1,表示更新的记录行数
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 通过ID查询
     *
     * @param seckillId id
     * @return 返回查询结果
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     *
     * java没有保存形参的记录: queryAll(int offet ,int limit) -> queryAll(arg0,arg1)
     * 当有多个参数的时候要用注解param命名
     *
     * @param offset 偏移量
     * @param limit  个数
     * @return 结果
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 使用存储过程执行秒杀
     *
     * @param paramMap params
     */
    void killByProcedure(Map<String, Object> paramMap);
}

