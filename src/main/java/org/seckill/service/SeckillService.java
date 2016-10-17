package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * ���� : ��ɱҵ����
 *
 *
 *
 * ҵ��ӿ�:վ��ʹ����Ƕ���ƽӿ�
 * ��������:������������,����,��������(return����,�쳣)
 *
 * @author ��Ұ����
 * @version 2016-10-15 16:45
 */
public interface SeckillService {
    /**
     * ��ѯ���е���ɱ��¼
     *
     * @return ��ɱ��¼
     */
    List<Seckill> getSeckillList();

    /**
     * ��ѯ������ɱ��¼
     *
     * @param seckillId id
     * @return ��ɱ��¼
     */
    Seckill getById(long seckillId);

    /**
     * ��ɱ�Ƿ���:
     * 1.����ʱ:�����ɱ�ӿڵ�ַ
     * 2.δ����:���ϵͳʱ�����ɱʱ��
     *
     * @param seckillId id
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * ��ɱִ�нӿ�
     *
     * @param seckillId id
     * @param userPhone �ֻ���
     * @param md5       ��¶���ͻ���md5У����
     * @return ��ɱ�Ƿ�ɹ���Ϣ
     * @throws RepeatKillException   �ظ���ɱ�쳣
     * @throws SeckillCloseException ��ɱ�ر��쳣
     * @throws SeckillException      ��ɱҵ�������쳣
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws RepeatKillException, SeckillCloseException, SeckillException;


}
