package com.lvddy.snowflake.service.impl;

import com.lvddy.snowflake.service.GeneratorService;

/**
 * Created by kcz-020 on 2017/5/23.
 */
public class GeneratorServiceImpl implements GeneratorService {

	private final static long START_TIME = System.currentTimeMillis(); //开始时间戳


	private final static long WORK_BIT = 10L;

	private final static long SEQUENCE_BIT = 12L;

	private final static long MAX_SEQUENCE_NUM = -1L ^ (-1L << SEQUENCE_BIT);

	private final static long MAX_WORK_NUM = -1L ^ (-1L << WORK_BIT);

	private final static long WORK_LEFT = 12L;

	private final static long TIMESTAMP_LEFT = 22L;

	private long workId;

	private long lastTimeStamp;

	private long sequence;

	public GeneratorServiceImpl(long workId) {
		if (workId > MAX_WORK_NUM) {
			throw new IllegalArgumentException("workId had bigger than MAX_WORK_NUM");
		}
		this.workId = workId;
	}

	@Override
	public synchronized Long generate() {
		long currentTimeStamp = System.currentTimeMillis();
		if(currentTimeStamp < lastTimeStamp){   //发生时钟回拨现象，重试一次
			currentTimeStamp = timeGen();
		}
		else if (currentTimeStamp == lastTimeStamp) {  //同一时刻序列增加
			sequence = (sequence + 1) & MAX_SEQUENCE_NUM;
			if (sequence == 0L) {
				currentTimeStamp = timeGen();
			}
		}
		else {  //不为同一时刻
			sequence = 0L;
		}
		lastTimeStamp = currentTimeStamp;
		return ((currentTimeStamp - START_TIME) << TIMESTAMP_LEFT) | (workId << WORK_LEFT)
				| sequence;
	}

	private long timeGen() {
		long currentTimeStamp = System.currentTimeMillis();
	    if(currentTimeStamp > lastTimeStamp){
            return currentTimeStamp;
        }else{
	        throw new RuntimeException("发生时间回拨现象"); //如果发送该情况, 应该使服务下线
        }
	}

    public static void main(String[] args) {
        GeneratorServiceImpl generatorService = new GeneratorServiceImpl(536);
        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(generatorService.generate());
        }
    }

}
