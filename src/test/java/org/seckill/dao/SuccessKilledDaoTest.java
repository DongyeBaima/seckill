package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by SHENG on 2016/10/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        long id = 1001L;
        long userPhone = 13211111111L;
        System.out.println(successKilledDao.insertSuccessKilled(id, userPhone));

    }

    @Test
    public void queryBytIdWithSeckill() throws Exception {
        long id = 1000;
        long userPhone = 13211111111L;
        System.out.println(successKilledDao.queryByIdWithSeckill(id, userPhone));
    }

}