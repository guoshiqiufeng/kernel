package com.gitee.fubluesky.kernel.core.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础分页请求参数
 *
 * @author yanghq
 */
@Data
public class BasePageRequest implements Serializable {

	private static final long serialVersionUID = -1618391697513897845L;

	/**
	 * 当前页
	 */
	private int currPage = 1;

	/**
	 * 每页条数
	 */
	private int pageSize = 10;

}
