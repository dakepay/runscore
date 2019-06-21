package me.zohar.runscore.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import me.zohar.runscore.common.vo.Result;
import me.zohar.runscore.merchant.param.StartOrderParam;
import me.zohar.runscore.merchant.service.MerchantOrderService;
import me.zohar.runscore.merchant.vo.OrderGatheringCodeVO;
import me.zohar.runscore.merchant.vo.PlatformOrderVO;

@Controller
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private MerchantOrderService platformOrderService;

	@PostMapping("/startOrder")
	@ResponseBody
	public Result startOrder(StartOrderParam param) {
		PlatformOrderVO vo = platformOrderService.startOrder(param);
		return Result.success().setData(vo);
	}

	@GetMapping("/getOrderGatheringCode")
	@ResponseBody
	public Result getOrderGatheringCode(String orderNo) {
		OrderGatheringCodeVO vo = platformOrderService.getOrderGatheringCode(orderNo);
		return Result.success().setData(vo);
	}
	
	@GetMapping("/merchantConfirmToPaid")
	@ResponseBody
	public Result merchantConfirmToPaid(String secretKey, String orderId) {
		platformOrderService.merchantConfirmToPaid(secretKey, orderId);
		return Result.success();
	}


}
