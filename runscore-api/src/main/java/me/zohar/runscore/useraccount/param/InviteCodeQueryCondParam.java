package me.zohar.runscore.useraccount.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zohar.runscore.common.param.PageParam;

@Data
@EqualsAndHashCode(callSuper=false)
public class InviteCodeQueryCondParam extends PageParam {
	
	private String inviter;

}
