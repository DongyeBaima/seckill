package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import javax.annotation.Resource;

/**
 * Created by SHENG on 2016/10/15.
 * ����spring��junit����,junit�����Ǽ���springIoc����
 * spring-test ,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
// ����junit spring�����ļ�
@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
public class SeckillDaoTest {

    // ע��daoʵ��������
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {
        long id = 1001;
        Date date = new Date();
        System.out.println(seckillDao.reduceNumber(id, date));
    }

    @Test
    public void queryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {


        int offset = 2;
        int limit = 2;
        System.out.println(seckillDao.queryAll(offset, limit));

    }

}