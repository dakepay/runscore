package me.zohar.runscore.common.utils;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ThreadPoolUtils {

	/**
	 * 充值结算线程池
	 */
	private static ScheduledThreadPoolExecutor rechargeSettlementPool = new ScheduledThreadPoolExecutor(5);

	/**
	 * 登录日志线程池
	 */
	private static ScheduledThreadPoolExecutor loginLogPool = new ScheduledThreadPoolExecutor(2);

	public static ScheduledThreadPoolExecutor getRechargeSettlementPool() {
		return rechargeSettlementPool;
	}

	public static ScheduledThreadPoolExecutor getLoginLogPool() {
		return loginLogPool;
	}
}
