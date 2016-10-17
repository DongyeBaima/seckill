package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * 描述 : 秒杀业务实现类
 *
 * @author 东野白马
 * @version 2016-10-15 17:05
 */
// @Component @Service @Dao @Conroller
@Service
public class SeckillServiceImpl implements SeckillService {

    // md5盐值,用于混淆
    private final String slat = "salijr32ujjeopujOPIUWPOIUU#WO#(*#(*IKQKLJDFL:KSJDGH";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // 注入service依赖
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    /**
     * 查询所有的秒杀记录
     *
     * @return 秒杀记录
     */
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId id
     * @return 秒杀记录
     */
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /**
     * 秒杀是否开启:
     * 1.开启时:输出秒杀接口地址
     * 2.未开启:输出系统时间和秒杀时间
     *
     * @param seckillId id
     */
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = getById(seckillId);

        if (seckill == null) {
            return new Exposer(false, seckillId);
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date now = new Date();

        if (now.getTime() > endTime.getTime()
                || now.getTime() < startTime.getTime()) {
            return new Exposer(false, seckillId, now.getTime(), startTime.getTime(), endTime.getTime());
        }

        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    /**
     * 秒杀执行接口
     *
     * 使用注解控制事务方法的优点
     * 1: 开发团队达成一致约定,明确标注事务方法的编程峰哥
     * 2: 保证事务方法的执行时间尽可能短,不要穿插其他网络操作RPC/HTTP请求或剥离到事务方法外部
     * 3: 不是所有的方法都需要事务,如:只有一条修改操作,只读操作;
     *
     * @param seckillId id
     * @param userPhone 手机号
     * @param md5       暴露给客户的md5校验码
     * @return 秒杀是否成功信息
     * @throws RepeatKillException   重复秒杀异常
     * @throws SeckillCloseException 秒杀关闭异常
     * @throws SeckillException      秒杀业务其他异常
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws RepeatKillException, SeckillCloseException, SeckillException {
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        //执行秒杀逻辑 : 减库存 + 记录购买行为
        Date now = new Date();
        try {
            // 减库存
            int updateCount = seckillDao.reduceNumber(seckillId, now);
            if (updateCount <= 0) {
                // 没有更新到记录,秒杀结束
                throw new SeckillCloseException("seckill is closed");
            }
            // 记录秒杀购买行为
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);

            if (insertCount <= 0) {
                // 重复秒杀
                throw new RepeatKillException("seckill repeated");
            }
            // 秒杀成功
            SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
            return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
        } catch (SeckillException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 所有编译器异常,转化成运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }

    private String getMd5(long seckillId) {
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
