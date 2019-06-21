package me.zohar.runscore.mastercontrol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import me.zohar.runscore.common.vo.Result;
import me.zohar.runscore.mastercontrol.service.MasterControlService;

/**
 * 总控
 * 
 * @author zohar
 * @date 2019年3月9日
 *
 */
@Controller
@RequestMapping("/masterControl")
public class MasterControlController {

	@Autowired
	private MasterControlService service;

	@GetMapping("/getInviteRegisterSetting")
	@ResponseBody
	public Result getInviteRegisterSetting() {
		return Result.success().setData(service.getInviteRegisterSetting());
	}

}
