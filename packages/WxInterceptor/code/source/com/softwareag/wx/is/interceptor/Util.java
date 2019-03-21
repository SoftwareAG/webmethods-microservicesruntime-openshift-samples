package com.softwareag.wx.is.interceptor;

public class Util {

	public static String unifyServiceName(String serviceName ) {
		return serviceName.replaceAll("[:]", "_");
	}
}
