package org.seckill.exception;

/**
 * ���� : �ظ���ɱ�쳣(�������쳣)
 *
 * @author ��Ұ����
 * @version 2016-10-15 16:57
 */
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }


    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
