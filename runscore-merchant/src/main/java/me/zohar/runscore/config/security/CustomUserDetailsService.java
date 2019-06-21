package me.zohar.runscore.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import me.zohar.runscore.constants.Constant;
import me.zohar.runscore.merchant.service.MerchantService;
import me.zohar.runscore.merchant.vo.MerchantVO;
import me.zohar.runscore.useraccount.service.UserAccountService;
import me.zohar.runscore.useraccount.vo.LoginAccountInfoVO;

/**
 * 通过实现UserDetailsService接口提供复杂认证
 * 
 * @author 黄振华
 * @date 2018年8月28日
 *
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private MerchantService merchantService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginAccountInfoVO loginAccountInfo = userAccountService.getLoginAccountInfo(username);
		if (loginAccountInfo == null) {
			log.warn("账号不存在:{}", username);
			throw new AuthenticationServiceException("用户名或密码不正确");
		}
		if (Constant.账号状态_禁用.equals(loginAccountInfo.getState())) {
			log.warn("账号已被禁用:{}", username);
			throw new AuthenticationServiceException("账号已被禁用");
		}
		if (!Constant.账号类型_商户.equals(loginAccountInfo.getAccountType())) {
			log.warn("该账号不是商户,无法登陆到商户端:{}", username);
			throw new AuthenticationServiceException("该账号不是商户,无法登陆到商户端");
		}
		MerchantVO merchant = merchantService.findMerchantByRelevanceAccountId(loginAccountInfo.getId());
		if (merchant == null) {
			log.warn("该账号未开通商户资格:{}", username);
			throw new AuthenticationServiceException("该账号未开通商户资格,无法登陆到商户端");
		}

		return new MerchantAccountDetails(loginAccountInfo, merchant);
	}

}
