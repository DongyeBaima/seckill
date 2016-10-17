package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.exception.SeckillException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by SHENG on 2016/10/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        System.out.println(seckillService.getSeckillList());
    }

    @Test
    public void getById() throws Exception {
        long id = 1000;
        System.out.println(seckillService.getById(id));
    }

    // -- 集成测试代码完整逻辑,注意可重复执行
    @Test
    public void exportSeckillUrl() throws Exception {
        long seckillId = 1001;
        long userPhone = 13211111112L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        System.out.println("    --  exposer:" + exposer);
        if (exposer.isExposed()) {
            // 暴露成功
            String md5 = exposer.getMd5();
            SeckillExecution execution = null;
            try {
                execution = seckillService.executeSeckill(seckillId, userPhone, md5);
            } catch (SeckillException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("    --  execution:" + execution);
        }
    }

    @Test
    public void executeSeckillProcedure() throws Exception {
        long seckillId = 1003;
        long userPhone = 13211111112L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        System.out.println("    --  exposer:" + exposer);
        if (exposer.isExposed()) {
            // 暴露成功
            String md5 = exposer.getMd5();
            SeckillExecution execution = null;
            try {
                execution = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
            } catch (SeckillException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("    --  execution:" + execution);
        }
    }
}