package me.zohar.runscore.mastercontrol.vo;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import me.zohar.runscore.mastercontrol.domain.PlatformOrderSetting;

@Data
public class PlatformOrderSettingVO {

	private String id;

	/**
	 * 接单有效时长
	 */
	private Integer orderReceiveEffectiveDuration;
	
	/**
	 * 支付有效时长
	 */
	private Integer orderPayEffectiveDuration;

	/**
	 * 接单返水率
	 */
	private Double returnWaterRate;

	/**
	 * 接单返水率启用标识
	 */
	private Boolean returnWaterRateEnabled;

	private Boolean unfixedGatheringCodeReceiveOrder;

	/**
	 * 最近修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date latelyUpdateTime;

	public static PlatformOrderSettingVO convertFor(PlatformOrderSetting platformOrderSetting) {
		if (platformOrderSetting == null) {
			return null;
		}
		PlatformOrderSettingVO vo = new PlatformOrderSettingVO();
		BeanUtils.copyProperties(platformOrderSetting, vo);
		return vo;
	}

}
