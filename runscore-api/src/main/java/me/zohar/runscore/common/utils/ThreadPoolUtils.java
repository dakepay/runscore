package me.zohar.runscore.common.utils;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ThreadPoolUtils {

	
	/**
	 * 充值结算线程池
	 */
	private static ScheduledThreadPoolExecutor rechargeSettlementPool = new ScheduledThreadPoolExecutor(5);
	
	public static ScheduledThreadPoolExecutor getRechargeSettlementPool() {
		return rechargeSettlementPool;
	}
	
}
