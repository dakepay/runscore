package me.zohar.runscore.mastercontrol.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.extern.slf4j.Slf4j;
import me.zohar.runscore.common.valid.ParamValid;
import me.zohar.runscore.mastercontrol.domain.CustomerQrcodeSetting;
import me.zohar.runscore.mastercontrol.domain.InviteRegisterSetting;
import me.zohar.runscore.mastercontrol.domain.PlatformOrderSetting;
import me.zohar.runscore.mastercontrol.domain.RechargeSetting;
import me.zohar.runscore.mastercontrol.param.UpdatePlatformOrderSettingParam;
import me.zohar.runscore.mastercontrol.repo.CustomerQrcodeSettingRepo;
import me.zohar.runscore.mastercontrol.repo.InviteRegisterSettingRepo;
import me.zohar.runscore.mastercontrol.repo.PlatformOrderSettingRepo;
import me.zohar.runscore.mastercontrol.repo.RechargeSettingRepo;
import me.zohar.runscore.mastercontrol.vo.CustomerQrcodeSettingVO;
import me.zohar.runscore.mastercontrol.vo.InviteRegisterSettingVO;
import me.zohar.runscore.mastercontrol.vo.PlatformOrderSettingVO;
import me.zohar.runscore.mastercontrol.vo.RechargeSettingVO;

@Validated
@Service
@Slf4j
public class MasterControlService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private PlatformOrderSettingRepo platformOrderSettingRepo;

	@Autowired
	private InviteRegisterSettingRepo inviteRegisterSettingRepo;

	@Autowired
	private RechargeSettingRepo rechargeSettingRepo;

	@Autowired
	private CustomerQrcodeSettingRepo customerQrcodeSettingRepo;

	@Transactional(readOnly = true)
	public InviteRegisterSettingVO getInviteRegisterSetting() {
		InviteRegisterSetting setting = inviteRegisterSettingRepo.findTopByOrderByEnabled();
		return InviteRegisterSettingVO.convertFor(setting);
	}

	@Transactional
	public void updateInviteRegisterSetting(
			@NotNull @DecimalMin(value = "0", inclusive = true) Integer effectiveDuration, @NotNull Boolean enabled) {
		InviteRegisterSetting setting = inviteRegisterSettingRepo.findTopByOrderByEnabled();
		if (setting == null) {
			setting = InviteRegisterSetting.build();
		}
		setting.update(effectiveDuration, enabled);
		inviteRegisterSettingRepo.save(setting);
	}

	@Transactional(readOnly = true)
	public PlatformOrderSettingVO getPlatformOrderSetting() {
		PlatformOrderSetting setting = platformOrderSettingRepo.findTopByOrderByLatelyUpdateTime();
		return PlatformOrderSettingVO.convertFor(setting);
	}

	@ParamValid
	@Transactional
	public void updatePlatformOrderSetting(UpdatePlatformOrderSettingParam param) {
		PlatformOrderSetting setting = platformOrderSettingRepo.findTopByOrderByLatelyUpdateTime();
		if (setting == null) {
			setting = PlatformOrderSetting.build();
		}
		BeanUtils.copyProperties(param, setting);
		setting.setLatelyUpdateTime(new Date());
		platformOrderSettingRepo.save(setting);
	}

	@Transactional(readOnly = true)
	public RechargeSettingVO getRechargeSetting() {
		RechargeSetting setting = rechargeSettingRepo.findTopByOrderByLatelyUpdateTime();
		return RechargeSettingVO.convertFor(setting);
	}

	@Transactional
	public void updateRechargeSetting(
			@NotNull @DecimalMin(value = "0", inclusive = true) Integer orderEffectiveDuration,
			@NotNull @DecimalMin(value = "0", inclusive = true) Double returnWaterRate,
			@NotNull Boolean returnWaterRateEnabled) {
		RechargeSetting setting = rechargeSettingRepo.findTopByOrderByLatelyUpdateTime();
		if (setting == null) {
			setting = RechargeSetting.build();
		}
		setting.update(orderEffectiveDuration, returnWaterRate, returnWaterRateEnabled);
		rechargeSettingRepo.save(setting);
	}
	
	@Transactional(readOnly = true)
	public CustomerQrcodeSettingVO getCustomerQrcodeSetting() {
		CustomerQrcodeSetting setting = customerQrcodeSettingRepo.findTopByOrderByLatelyUpdateTime();
		return CustomerQrcodeSettingVO.convertFor(setting);
	}

	@Transactional
	public void updateCustomerQrcodeSetting(@NotBlank String qrcodeStorageIds) {
		CustomerQrcodeSetting setting = customerQrcodeSettingRepo.findTopByOrderByLatelyUpdateTime();
		if (setting == null) {
			setting = CustomerQrcodeSetting.build();
		}
		setting.update(qrcodeStorageIds);
		customerQrcodeSettingRepo.save(setting);
	}

	/**
	 * 刷新缓存
	 * 
	 * @param cacheItems
	 */
	public void refreshCache(@NotEmpty List<String> cacheItems) {
		List<String> deleteSuccessKeys = new ArrayList<>();
		List<String> deleteFailKeys = new ArrayList<>();
		for (String cacheItem : cacheItems) {
			Set<String> keys = redisTemplate.keys(cacheItem);
			for (String key : keys) {
				Boolean flag = redisTemplate.delete(key);
				if (flag) {
					deleteSuccessKeys.add(key);
				} else {
					deleteFailKeys.add(key);
				}
			}
		}
		if (!deleteFailKeys.isEmpty()) {
			log.warn("以下的缓存删除失败:", deleteFailKeys);
		}
	}

}
