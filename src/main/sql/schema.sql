-- ���ݿ��ʼ���ű�

-- �������ݿ�

USE seckill;

CREATE TABLE seckill (
  `seckill_id`  BIGINT       NOT NULL  AUTO_INCREMENT
  COMMENT '��Ʒ���id',
  `name`        VARCHAR(120) NOT NULL
  COMMENT '��Ʒ����',
  `number`      INT          NOT NULL
  COMMENT '�������',
  `start_time`  TIMESTAMP    NOT NULL
  COMMENT '��ɱ����ʱ��',
  `end_time`    TIMESTAMP    NOT NULL
  COMMENT '��ɱ����ʱ��',
  `create_time` TIMESTAMP    NOT NULL
  COMMENT '����ʱ��',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)
  ENGINE = innoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8
  COMMENT = '��ɱ����';

-- ��ʼ������
INSERT INTO
  seckill (name, number, start_time, end_time, create_time)
VALUES
  ('1000Ԫ��ɱiphone6', 100, '2016-10-14 00:00:00', '2016-10-15 00:00:00', now()),
  ('500Ԫ��ɱipad2', 200, '2016-10-15 00:00:00', '2016-10-16 00:00:00', now()),
  ('300Ԫ��ɱС��4', 300, '2016-10-16 00:00:00', '2016-10-17 00:00:00', now()),
  ('200Ԫ��ɱ����note', 400, '2016-10-17 00:00:00', '2016-10-18 00:00:00', now());

-- ��ɱ�ɹ���ϸ��
-- �û���¼��֤��ص���Ϣ
CREATE TABLE success_killed (
  `seckill_id`  BIGINT    NOT NULL  AUTO_INCREMENT
  COMMENT '��Ʒ���id',
  `user_phone`  BIGINT    NOT NULL
  COMMENT '�û��ֻ���',
  `stat`        TINYINT   NOT NULL  DEFAULT -1
  COMMENT '״̬��־: -1:��Ч  0:�ɹ�  1:�Ѹ���',
  `create_time` TIMESTAMP NOT NULL
  COMMENT '����ʱ��',
  PRIMARY KEY (seckill_id, user_phone),
  KEY idx_create_time(create_time)
)
  ENGINE = innoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8
  COMMENT = '��ɱ�ɹ���ϸ��';
