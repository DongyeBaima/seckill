package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ���� :
 *
 * @author ��Ұ����
 * @version 2016-10-15 13:34
 */
public interface SeckillDao {
    /**
     * �����
     *
     * @param seckillId id
     * @param killTime  ʱ��
     * @return ���Ӱ������>1,��ʾ���µļ�¼����
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * ͨ��ID��ѯ
     *
     * @param seckillId id
     * @return ���ز�ѯ���
     */
    Seckill queryById(long seckillId);

    /**
     * ����ƫ������ѯ��ɱ��Ʒ�б�
     *
     * javaû�б����βεļ�¼: queryAll(int offet ,int limit) -> queryAll(arg0,arg1)
     * ���ж��������ʱ��Ҫ��ע��param����
     *
     * @param offset ƫ����
     * @param limit  ����
     * @return ���
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * ʹ�ô洢����ִ����ɱ
     *
     * @param paramMap params
     */
    void killByProcedure(Map<String, Object> paramMap);
}

