package wx.interceptor.pub.interceptors;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2015-09-24 19:11:47 CEST
// -----( ON-HOST: sagbase.eur.ad.sag

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.InvokeState;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
import com.wm.app.b2b.server.invoke.InvokeChainProcessor;
import com.softwareag.wx.is.interceptor.WxInterceptor;
import com.softwareag.wx.is.interceptor.measure.MeasurementRuntimeGraphStore;
// --- <<IS-END-IMPORTS>> ---

public final class measureRuntimeGraph

{
	// ---( internal utility methods )---

	final static measureRuntimeGraph _instance = new measureRuntimeGraph();

	static measureRuntimeGraph _newInstance() { return new measureRuntimeGraph(); }

	static measureRuntimeGraph _cast(Object o) { return (measureRuntimeGraph)o; }

	// ---( server methods )---




	public static final void clearMeasurements (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(clearMeasurements)>> ---
		// @specification wx.interceptor.pub.interceptors:postServiceSpec
		// @sigtype java 3.5
		// pipeline
		MeasurementRuntimeGraphStore.getInstance().clearMeasurements();
		// pipeline
			
			
		// --- <<IS-END>> ---

                
	}



	public static final void getMeasureGraphAsJson (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getMeasureGraphAsJson)>> ---
		// @sigtype java 3.5
		// [o] field:0:required json
			
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		String json = ((WxInterceptor)wxInterceptor).getMeasurementGraphAsJson();
		String json2 = ((WxInterceptor)wxInterceptor).getMeasurementGraphAsJson2();
			
		IDataUtil.put(pipelineCursor, "json", json);
		IDataUtil.put(pipelineCursor, "json2", json2);
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void measureRuntimePost (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(measureRuntimePost)>> ---
		// @specification wx.interceptor.pub.interceptors:postServiceSpec
		// @sigtype java 3.5
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	__interceptedServiceName = IDataUtil.getString( pipelineCursor, "__interceptedServiceName" );
		
		// __interceptorStorage
		IData	__interceptorStorage = IDataUtil.getIData( pipelineCursor, "__interceptorStorage" );
		Long runtime = null;
		if ( __interceptorStorage == null) {
			((com.softwareag.wx.is.interceptor.WxInterceptor)wxInterceptor).logError("No __interceptorStorage object in pipeline, cannot measure runtime for service " + __interceptedServiceName);
			pipelineCursor.destroy();
			return;
		}
		Long startTimeNano = (Long)IDataUtil.get(__interceptorStorage.getCursor(), "startTimeNano");
		if( startTimeNano != null ) {
			runtime = System.nanoTime()-startTimeNano;
		} else {
			((com.softwareag.wx.is.interceptor.WxInterceptor)wxInterceptor).logError("No startTimeNano in __interceptorStorage object in pipeline, cannot measure runtime for service " + __interceptedServiceName);
			pipelineCursor.destroy();
			return;
		}
		
		((com.softwareag.wx.is.interceptor.WxInterceptor)wxInterceptor).addMeasurement(__interceptedServiceName, runtime);
		pipelineCursor.destroy();
		
		// pipeline
			
			
		// --- <<IS-END>> ---

                
	}



	public static final void measureRuntimePre (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(measureRuntimePre)>> ---
		// @specification wx.interceptor.pub.interceptors:preServiceSpec
		// @sigtype java 3.5
			
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		String	__interceptedServiceName = IDataUtil.getString( pipelineCursor, "__interceptedServiceName" );
		
		long startTimeNano = System.nanoTime();
		IData	__interceptorStorage = IDataFactory.create();
		IDataUtil.put(__interceptorStorage.getCursor(), "startTimeNano", startTimeNano);
		
		// pipeline
		IDataUtil.put( pipelineCursor, "__cancelServiceInvocation", "false" );		
		// __interceptorStorage
		IDataUtil.put( pipelineCursor, "__interceptorStorage", __interceptorStorage );
		pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void startMeasureRuntimePre (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(startMeasureRuntimePre)>> ---
		// @specification wx.interceptor.pub.interceptors:preServiceSpec
		// @sigtype java 3.5
			
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		String	__interceptedServiceName = IDataUtil.getString( pipelineCursor, "__interceptedServiceName" );
		
		
		String stack = "";
		for( String entry : InvokeState.getCurrentState().getAuditRuntime()
				.getContextStack()) {
			stack += ", " 
			+ entry;
		}
		
		long startTimeNano = System.nanoTime();
		IData	__interceptorStorage = IDataFactory.create();
		IDataUtil.put(__interceptorStorage.getCursor(), "startTimeNano", startTimeNano);
		
		// pipeline
		IDataUtil.put( pipelineCursor, "__cancelServiceInvocation", "false" );		
		// __interceptorStorage
		IDataUtil.put( pipelineCursor, "__interceptorStorage", __interceptorStorage );
		pipelineCursor.destroy();
		
		((WxInterceptor)wxInterceptor).registerRootServiceForMeasurement(__interceptedServiceName);
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	public static InvokeChainProcessor wxInterceptor = com.softwareag.wx.is.interceptor.WxInterceptor.getInstance();
		
	// --- <<IS-END-SHARED>> ---
}

