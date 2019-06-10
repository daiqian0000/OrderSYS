package com.asion.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 分页信息类
 * 
 * @author chenboyang
 * 
 * @param <T>
 *            业务类型
 */
@Deprecated
@SuppressWarnings("unchecked")
public class Page<T> extends HashMap<String, Object> {

	/**
	 * 序列化版本编号
	 */
	private static final long serialVersionUID = -4568995458589417503L;

	/**
	 * 当前页标识
	 */
	private static final String PAGE_INDEX = "pageIndex";

	/**
	 * 每页总行数标识
	 */
	private static final String PAGE_SIZE = "pageSize";

	/**
	 * 总记录数标识
	 */
	private static final String TOTAL_RECORD = "totalRecord";

	/**
	 * 总页数标识
	 */
	private static final String TOTAL_PAGE = "totalPage";

	/**
	 * 数据集标识
	 */
	private static final String RESULT = "result";

	/**
	 * 当前页
	 */
	private int pageIndex = 1;

	/**
	 * 每页总行数
	 */
	private int pageSize = 10;

	/**
	 * 总记录数
	 */
	private int totalRecord;

	/**
	 * 总页数
	 */
	private int totalPage;

	/**
	 * 数据集
	 */
	private List<T> result = new ArrayList<T>();

	public Page() {
		put(PAGE_INDEX, pageIndex);
		put(PAGE_SIZE, pageSize);
		put(TOTAL_RECORD, totalRecord);
		put(TOTAL_PAGE, totalPage);
		put(RESULT, result);
	}

	@Override
	public Object put(String key, Object value) {
		Object result = super.put(key, value);
		if (key != null) {
			String keyName = key.toString();
			switch (keyName) {
			case PAGE_INDEX:
				setPageIndex(BaseUtil.convert(value, int.class));
				break;
			case PAGE_SIZE:
				setPageSize(BaseUtil.convert(value, int.class));
				break;
			case TOTAL_RECORD:
				setTotalRecord(BaseUtil.convert(value, int.class));
				break;
			case TOTAL_PAGE:
				setTotalPage(BaseUtil.convert(value, int.class));
				break;
			case RESULT:
				setResult(BaseUtil.convert(value, List.class));
				break;
			}
		}
		return result;
	}

	/**
	 * 返回分页对象的JSON字符串
	 * 
	 * @return JSON字符串
	 */
	public String toJson() {
		return JsonUtil.toJson(this);
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex > 0) {
			this.pageIndex = pageIndex;
			if (containsKey(PAGE_INDEX)) {
				replace(PAGE_INDEX, pageIndex);
			} else {
				put(PAGE_INDEX, pageIndex);
			}
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize > 0) {
			this.pageSize = pageSize;
			if (containsKey(PAGE_SIZE)) {
				replace(PAGE_SIZE, pageSize);
			} else {
				put(PAGE_SIZE, pageSize);
			}
		}
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
		setTotalPage(totalPage);
		if (containsKey(TOTAL_RECORD)) {
			replace(TOTAL_RECORD, totalRecord);
		} else {
			put(TOTAL_RECORD, totalRecord);
		}
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
		if (totalPage > 0 && totalPage < pageIndex) {
			setPageIndex(totalPage);
		}
		if (containsKey(TOTAL_PAGE)) {
			replace(TOTAL_PAGE, totalPage);
		} else {
			put(TOTAL_PAGE, totalPage);
		}
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
		if (containsKey(RESULT)) {
			replace(RESULT, result);
		} else {
			put(RESULT, result);
		}
	}

}
