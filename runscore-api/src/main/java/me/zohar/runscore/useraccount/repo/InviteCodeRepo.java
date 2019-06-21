package me.zohar.runscore.useraccount.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import me.zohar.runscore.useraccount.domain.InviteCode;

public interface InviteCodeRepo extends JpaRepository<InviteCode, String>, JpaSpecificationExecutor<InviteCode> {
	
	InviteCode findTopByInviteeIdIsNullAndCodeAndPeriodOfValidityGreaterThanEqual(String code, Date periodOfValidity);

}
