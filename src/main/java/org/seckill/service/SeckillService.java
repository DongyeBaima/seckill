package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * 描述 : 秒杀业务类
 *
 *
 *
 * 业务接口:站在使用这角度设计接口
 * 三个方面:方法定义粒度,参数,返回类型(return类型,异常)
 *
 * @author 东野白马
 * @version 2016-10-15 16:45
 */
public interface SeckillService {
    /**
     * 查询所有的秒杀记录
     *
     * @return 秒杀记录
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId id
     * @return 秒杀记录
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀是否开启:
     * 1.开启时:输出秒杀接口地址
     * 2.未开启:输出系统时间和秒杀时间
     *
     * @param seckillId id
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 秒杀执行接口
     *
     * @param seckillId id
     * @param userPhone 手机号
     * @param md5       暴露给客户的md5校验码
     * @return 秒杀是否成功信息
     * @throws RepeatKillException   重复秒杀异常
     * @throws SeckillCloseException 秒杀关闭异常
     * @throws SeckillException      秒杀业务其他异常
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws RepeatKillException, SeckillCloseException, SeckillException;


}
