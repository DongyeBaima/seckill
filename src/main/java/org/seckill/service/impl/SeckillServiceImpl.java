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
 * ���� : ��ɱҵ��ʵ����
 *
 * @author ��Ұ����
 * @version 2016-10-15 17:05
 */
// @Component @Service @Dao @Conroller
@Service
public class SeckillServiceImpl implements SeckillService {

    // md5��ֵ,���ڻ���
    private final String slat = "salijr32ujjeopujOPIUWPOIUU#WO#(*#(*IKQKLJDFL:KSJDGH";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // ע��service����
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    /**
     * ��ѯ���е���ɱ��¼
     *
     * @return ��ɱ��¼
     */
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    /**
     * ��ѯ������ɱ��¼
     *
     * @param seckillId id
     * @return ��ɱ��¼
     */
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /**
     * ��ɱ�Ƿ���:
     * 1.����ʱ:�����ɱ�ӿڵ�ַ
     * 2.δ����:���ϵͳʱ�����ɱʱ��
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
     * ��ɱִ�нӿ�
     *
     * ʹ��ע��������񷽷����ŵ�
     * 1: �����ŶӴ��һ��Լ��,��ȷ��ע���񷽷��ı�̷��
     * 2: ��֤���񷽷���ִ��ʱ�価���ܶ�,��Ҫ���������������RPC/HTTP�������뵽���񷽷��ⲿ
     * 3: �������еķ�������Ҫ����,��:ֻ��һ���޸Ĳ���,ֻ������;
     *
     * @param seckillId id
     * @param userPhone �ֻ���
     * @param md5       ��¶���ͻ���md5У����
     * @return ��ɱ�Ƿ�ɹ���Ϣ
     * @throws RepeatKillException   �ظ���ɱ�쳣
     * @throws SeckillCloseException ��ɱ�ر��쳣
     * @throws SeckillException      ��ɱҵ�������쳣
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws RepeatKillException, SeckillCloseException, SeckillException {
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        //ִ����ɱ�߼� : ����� + ��¼������Ϊ
        Date now = new Date();
        try {
            // �����
            int updateCount = seckillDao.reduceNumber(seckillId, now);
            if (updateCount <= 0) {
                // û�и��µ���¼,��ɱ����
                throw new SeckillCloseException("seckill is closed");
            }
            // ��¼��ɱ������Ϊ
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);

            if (insertCount <= 0) {
                // �ظ���ɱ
                throw new RepeatKillException("seckill repeated");
            }
            // ��ɱ�ɹ�
            SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
            return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
        } catch (SeckillException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // ���б������쳣,ת�����������쳣
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }

    private String getMd5(long seckillId) {
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
