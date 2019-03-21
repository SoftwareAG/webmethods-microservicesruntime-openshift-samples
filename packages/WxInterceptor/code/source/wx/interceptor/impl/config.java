package wx.interceptor.impl;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2015-04-02 21:30:43 CEST
// -----( ON-HOST: sagbase.eur.ad.sag

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class config

{
	// ---( internal utility methods )---

	final static config _instance = new config();

	static config _newInstance() { return new config(); }

	static config _cast(Object o) { return (config)o; }

	// ---( server methods )---




	public static final void isWxInterceptorRegisteredInterceptor (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(isWxInterceptorRegisteredInterceptor)>> ---
		// @specification wx.interceptor.pub.interceptors:preServiceSpec
		// @sigtype java 3.5
		/**
		 * This is a dummy interceptor! It is used only to determine if WxInterceptor
		 * is already registered as an InvokeChainProcessor, since there are no other
		 * means to determin if it has been registered already or not.
		 * Therefore we add this service as a pre-Service to the service
		 * "wx.interceptor.impl.config:isRegisteredAlready" and then we call 
		 * the service "wx.interceptor.pub.config:isRegisteredAlready". 
		 * If WxInterceptor has been registered already, this dummy service will 
		 * put a value in the pipeline which proofes that it is running. Else, if
		 * WxInterceptor is not running, no value will appear in the pipeline.
		 */
		IDataCursor pipelineCursor = pipeline.getCursor();
		// __pipeline
		IData	__pipeline = IDataFactory.create();
		IDataUtil.put(__pipeline.getCursor(), "isRegistered", "true");
		IDataUtil.put(pipeline.getCursor(), "isRegistered", "true");
		IDataUtil.put( pipelineCursor, "__pipeline", __pipeline );
		pipelineCursor.destroy();
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void printInterceptorsJson (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(printInterceptorsJson)>> ---
		// @sigtype java 3.5
		// [o] field:0:required json
		
		String json = com.softwareag.wx.is.interceptor.WxInterceptor.listInterceptServicesAsJson();
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		IDataUtil.put( pipelineCursor, "json", json );
		pipelineCursor.destroy();
		
			
		// --- <<IS-END>> ---

                
	}
}

