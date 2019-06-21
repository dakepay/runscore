package me.zohar.runscore.mastercontrol.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import me.zohar.runscore.mastercontrol.domain.PlatformOrderSetting;

public interface PlatformOrderSettingRepo
		extends JpaRepository<PlatformOrderSetting, String>, JpaSpecificationExecutor<PlatformOrderSetting> {

	PlatformOrderSetting findTopByOrderByLatelyUpdateTime();

}
