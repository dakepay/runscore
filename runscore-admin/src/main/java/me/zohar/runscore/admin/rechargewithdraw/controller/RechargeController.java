package me.zohar.runscore.admin.rechargewithdraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import me.zohar.runscore.common.vo.Result;
import me.zohar.runscore.rechargewithdraw.param.RechargeOrderQueryCondParam;
import me.zohar.runscore.rechargewithdraw.service.RechargeService;

/**
 * 
 * @author zohar
 * @date 2019年1月21日
 *
 */
@Controller
@RequestMapping("/recharge")
public class RechargeController {

	@Autowired
	private RechargeService rechargeService;

	@GetMapping("/findRechargeOrderByPage")
	@ResponseBody
	public Result findRechargeOrderByPage(RechargeOrderQueryCondParam param) {
		return Result.success().setData(rechargeService.findRechargeOrderByPage(param));
	}

	@GetMapping("/cancelOrder")
	@ResponseBody
	public Result cancelOrder(String id) {
		rechargeService.cancelOrder(id);
		return Result.success();
	}
	
	@GetMapping("/manualSettlement")
	@ResponseBody
	public Result manualSettlement(String orderNo) {
		rechargeService.manualSettlement(orderNo);
		return Result.success();
	}

}
