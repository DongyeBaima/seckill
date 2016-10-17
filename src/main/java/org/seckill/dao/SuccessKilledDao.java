package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * ���� :
 *
 * @author ��Ұ����
 * @version 2016-10-15 13:39
 */
public interface SuccessKilledDao {

    /**
     * ���빺����ϸ,�ɹ����ظ�
     *
     * @param seckillId id
     * @param userPhone �ֻ���
     * @return ���������
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * ����Id��ѯ�زĲ���sKilled��Я����ɱ��ƽ����ʵ��
     *
     * @param seckillId id
     * @return ���
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
