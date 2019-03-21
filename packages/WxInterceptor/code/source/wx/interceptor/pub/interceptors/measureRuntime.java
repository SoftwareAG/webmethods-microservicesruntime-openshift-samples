package wx.interceptor.pub.interceptors;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2015-03-02 12:13:10 CET
// -----( ON-HOST: sagbase.eur.ad.sag

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.Date;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
// --- <<IS-END-IMPORTS>> ---

public final class measureRuntime

{
	// ---( internal utility methods )---

	final static measureRuntime _instance = new measureRuntime();

	static measureRuntime _newInstance() { return new measureRuntime(); }

	static measureRuntime _cast(Object o) { return (measureRuntime)o; }

	// ---( server methods )---




	public static final void measureRuntimePost (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(measureRuntimePost)>> ---
		// @specification wx.interceptor.pub.interceptors:postServiceSpec
		// @sigtype java 3.5
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	__interceptedServiceName = IDataUtil.getString( pipelineCursor, "__interceptedServiceName" );
		String	__serviceInvocationSucceeded = IDataUtil.getString( pipelineCursor, "__serviceInvocationSucceeded" );
		
		// __interceptorStorage
		IData	__interceptorStorage = IDataUtil.getIData( pipelineCursor, "__interceptorStorage" );
		if ( __interceptorStorage != null)
		{
			Date startDate = (Date)IDataUtil.get(__interceptorStorage.getCursor(), "startDate");
			if( startDate != null ) {
				Date endDate = new Date();
				long runtime = endDate.getTime()-startDate.getTime();
				
				// input
				IData input = IDataFactory.create();
				IDataCursor inputCursor = input.getCursor();
				IDataUtil.put( inputCursor, "message", "Runtime for service '" + __interceptedServiceName + "' was " + runtime + " ms." );
				IDataUtil.put( inputCursor, "function", __interceptedServiceName );
				inputCursor.destroy();
		
				// output
				IData 	output = IDataFactory.create();
				try{
					output = Service.doInvoke( "pub.flow", "debugLog", input );
				}catch( Exception e){}
		
		
			}
		
		}
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
			
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	__interceptedServiceName = IDataUtil.getString( pipelineCursor, "__interceptedServiceName" );
		pipelineCursor.destroy();
		
		Date start = new Date();
		IData	__interceptorStorage = IDataFactory.create();
		IDataUtil.put(__interceptorStorage.getCursor(), "startDate", start);
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "__cancelServiceInvocation", "false" );		
		// __interceptorStorage
		IDataUtil.put( pipelineCursor_1, "__interceptorStorage", __interceptorStorage );
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}
}

