package me.zohar.runscore.mastercontrol.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import me.zohar.runscore.mastercontrol.domain.InviteRegisterSetting;

public interface InviteRegisterSettingRepo
		extends JpaRepository<InviteRegisterSetting, String>, JpaSpecificationExecutor<InviteRegisterSetting> {

	InviteRegisterSetting findTopByOrderByEnabled();
	
}
