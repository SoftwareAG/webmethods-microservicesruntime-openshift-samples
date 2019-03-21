package wx.interceptor.pub.utils;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2015-06-10 21:43:05 CEST
// -----( ON-HOST: sagbase.eur.ad.sag

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.data.IData;
// --- <<IS-END-IMPORTS>> ---

public final class testCaseCreator

{
	// ---( internal utility methods )---

	final static testCaseCreator _instance = new testCaseCreator();

	static testCaseCreator _newInstance() { return new testCaseCreator(); }

	static testCaseCreator _cast(Object o) { return (testCaseCreator)o; }

	// ---( server methods )---




	public static final void createTestFromCapturedPipelineData (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(createTestFromCapturedPipelineData)>> ---
		// @sigtype java 3.5
		// [i] field:0:required serviceName
		// [i] field:0:optional packageName
		// [i] field:0:optional removePipelineData {"true","false"}
		// [i] field:0:optional testSuiteNamePrefix
		String serviceName = IDataUtil.getString(pipeline.getCursor(), "serviceName");
		String packageName = IDataUtil.getString(pipeline.getCursor(), "packageName");
		boolean removePipelineData = IDataUtil.getBoolean(pipeline.getCursor(), "removePipelineData", false);
		String testSuiteNamePrefix = IDataUtil.getString(pipeline.getCursor(), "testSuiteNamePrefix");
		
		com.softwareag.wx.is.interceptor.testbuilder.TestCaseBuilder builder = new com.softwareag.wx.is.interceptor.testbuilder.TestCaseBuilder();
		try {
		builder.createTestCasesForService(serviceName, packageName, removePipelineData, testSuiteNamePrefix);
		} catch(Exception e) {
			throw new ServiceException("Could not create test cases: " + e);
		}
		// --- <<IS-END>> ---

                
	}



	public static final void createTestFromCapturedPipelineDataWithXpath (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(createTestFromCapturedPipelineDataWithXpath)>> ---
		// @sigtype java 3.5
		// [i] field:0:required serviceName
		// [i] field:0:optional packageName
		// [i] field:0:required removePipelineData {"true","false"}
		// [i] field:1:required xpathArr
		// [i] field:0:optional testSuiteNamePrefix
		String serviceName = IDataUtil.getString(pipeline.getCursor(), "serviceName");
		String packageName = IDataUtil.getString(pipeline.getCursor(), "packageName");
		String[] xpathArr = IDataUtil.getStringArray(pipeline.getCursor(), "xpathArr");
		boolean removePipelineData = IDataUtil.getBoolean(pipeline.getCursor(), "removePipelineData", false);
		String testSuiteNamePrefix = IDataUtil.getString(pipeline.getCursor(), "testSuiteNamePrefix");
		
		com.softwareag.wx.is.interceptor.TestCaseBuilder builder = new com.softwareag.wx.is.interceptor.TestCaseBuilder();
		try {
			builder.createTestCasesForService(serviceName, packageName, removePipelineData, xpathArr, testSuiteNamePrefix);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException("Could not create test cases: " + e);
		}
		// --- <<IS-END>> ---

                
	}



	public static final void getValueFromPipeline (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getValueFromPipeline)>> ---
		// @sigtype java 3.5
		// [i] field:0:required key
		IDataCursor idc = pipeline.getCursor();
		String key = IDataUtil.getString(idc, "key");
		if( key == null || "".equals(key) ) {
			return;
		}
		// --- <<IS-END>> ---

                
	}
}

