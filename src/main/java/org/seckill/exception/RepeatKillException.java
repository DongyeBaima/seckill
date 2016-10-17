package org.seckill.exception;

/**
 * 描述 : 重复秒杀异常(运行期异常)
 *
 * @author 东野白马
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
