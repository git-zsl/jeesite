package com.thinkgem.jeesite.common.persistence;

public interface ReturnStatus {
	int LOGOUT = 0; // 未登录
	int OPTSUCCESS = 1; // 操作成功
	int OPTFAIL = 2; // 操作失败
	int UNAUTHORIZED = 3; // 未授权
	int SYSEXCEPTION = 4; // 系统异常
	int DOGexception = 5; // 加密狗异常
}
