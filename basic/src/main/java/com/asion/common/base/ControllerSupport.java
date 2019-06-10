package com.asion.common.base;

import com.asion.business.web.constants.StatusCode;
import com.asion.business.web.model.Result;

/**
 * 控制器辅助接口
 * 
 * @author chenboyang
 *
 */
@Deprecated
public interface ControllerSupport {

	/**
	 * 布尔值返回结果封装
	 * 
	 * @param result
	 *            布尔值
	 * @return 返回结果
	 */
	default Result<Boolean> result(boolean result) {
		if (result) {
			return Result.result(StatusCode.SUCCESS, "操作成功！", result);
		} else {
			return Result.result(StatusCode.FAIL, "操作失败！", result);
		}
	}

	/**
	 * 对象值返回结果封装
	 * 
	 * @param result
	 *            对象
	 * @return 返回结果
	 */
	default <T> Result<T> result(T result) {
		return Result.result(StatusCode.SUCCESS, "操作成功！", result);
	}

}
