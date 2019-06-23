package me.zohar.runscore.task.merchant;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MerchantOrderTimeoutDealTask {
	
//	@Autowired
//	private RechargeService rechargeService;
	
//	@Scheduled(fixedRate = 20000)
	public void execute() {
		try {
			log.info("商户订单超时处理定时任务start");
//			rechargeService.orderTimeoutDeal();
			log.info("商户订单超时处理定时任务end");
		} catch (Exception e) {
			log.error("商户订单超时处理定时任务", e);
		}
	}

}
