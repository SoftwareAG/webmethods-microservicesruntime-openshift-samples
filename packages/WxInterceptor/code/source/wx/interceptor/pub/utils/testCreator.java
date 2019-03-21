package wx.interceptor.pub.utils;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2017-04-12 07:10:59 IST
// -----( ON-HOST: MCSRPR02.eur.ad.sag

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.data.IData;
// --- <<IS-END-IMPORTS>> ---

public final class testCreator

{
	// ---( internal utility methods )---

	final static testCreator _instance = new testCreator();

	static testCreator _newInstance() { return new testCreator(); }

	static testCreator _cast(Object o) { return (testCreator)o; }

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
		
		com.softwareag.wx.is.interceptor.testbuilder.TestCaseBuilder builder = new com.softwareag.wx.is.interceptor.testbuilder.TestCaseBuilder();
		try {
			builder.createTestCasesForService(serviceName, packageName, removePipelineData, xpathArr, testSuiteNamePrefix);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException("Could not create test cases: " + e);
		}
		// --- <<IS-END>> ---

                
	}
}

