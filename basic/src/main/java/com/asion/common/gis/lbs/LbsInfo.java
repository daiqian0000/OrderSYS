package com.asion.common.gis.lbs;

/**
 * LBS基站定位信息
 * 
 * @author chenboyang
 *
 */
public class LbsInfo {

	/**
	 * 国家代码(460=中国)
	 */
	private int mcc = 460;

	/**
	 * 网络类型：0=移动，1=联通(11=电信4G)
	 */
	private int mnc;

	/**
	 * 位置区码
	 */
	private int lac;

	/**
	 * 小区标识
	 */
	private int ci;

	/**
	 * 信号强度
	 */
	private int rssi;

	/**
	 * 经度
	 */
	private double x;

	/**
	 * 纬度
	 */
	private double y;

	/**
	 * 地址
	 */
	private String address;

	public int getMcc() {
		return mcc;
	}

	public void setMcc(int mcc) {
		this.mcc = mcc;
	}

	public int getMnc() {
		return mnc;
	}

	public void setMnc(int mnc) {
		this.mnc = mnc;
	}

	public int getLac() {
		return lac;
	}

	public void setLac(int lac) {
		this.lac = lac;
	}

	public int getCi() {
		return ci;
	}

	public void setCi(int ci) {
		this.ci = ci;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
