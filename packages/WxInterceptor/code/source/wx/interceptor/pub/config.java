package wx.interceptor.pub;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2015-09-24 17:56:28 CEST
// -----( ON-HOST: sagbase.eur.ad.sag

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.wm.app.b2b.server.invoke.InvokeChainProcessor;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.app.b2b.server.invoke.InvokeManager;
import com.softwareag.wx.is.interceptor.*;
// --- <<IS-END-IMPORTS>> ---

public final class config

{
	// ---( internal utility methods )---

	final static config _instance = new config();

	static config _newInstance() { return new config(); }

	static config _cast(Object o) { return (config)o; }

	// ---( server methods )---




	public static final void addInterceptor (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(addInterceptor)>> ---
		// @sigtype java 3.5
		// [i] field:0:required id
		// [i] field:0:required serviceName
		// [i] field:1:required preServiceList
		// [i] field:1:required postServiceList
		// [o] field:0:required result
		// pipeline
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		int id = IDataUtil.getInt(pipelineCursor, "id", -1 );
		String	serviceName = IDataUtil.getString( pipelineCursor, "serviceName" );
		String[]	preServiceList = IDataUtil.getStringArray( pipelineCursor, "preServiceList" );
		String[]	postServiceList = IDataUtil.getStringArray( pipelineCursor, "postServiceList" );
		
		if( serviceName == null || "".equals(serviceName)  ) {
			throw new ServiceException("You must provide the fully qualified service name for the intercepted service.");
		}
		
		List<String> preServiceListArr = null;
		List<String> postServiceListArr = null;
		if(preServiceList != null && preServiceList.length > 0){
			preServiceListArr = new ArrayList<String>(Arrays.asList(preServiceList));
		}
		else{
			preServiceListArr = new ArrayList<String>();
		}
		if(postServiceList != null && postServiceList.length > 0){
			postServiceListArr = new ArrayList<String>(Arrays.asList(postServiceList));
		}
		else{
			postServiceListArr = new ArrayList<String>();
		}
		try {
			com.softwareag.wx.is.interceptor.InterceptorService is = com.softwareag.wx.is.interceptor.WxInterceptorHelper.createInterceptService(id, serviceName, preServiceListArr, postServiceListArr);
			((com.softwareag.wx.is.interceptor.WxInterceptor)wxInterceptor).addInterceptor(is);
		} catch(com.softwareag.wx.is.interceptor.InvalidConfigurationException ice ) {
			throw new ServiceException("Invalid configuration provided: " + ice);
		}
		
		String result = "Added interceptor for service " + serviceName + " with id " + id + ".";
		
		IDataUtil.put(pipelineCursor, "result", result);
		pipelineCursor.destroy();
		
			
			
		// --- <<IS-END>> ---

                
	}



	public static final void getInterceptors (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getInterceptors)>> ---
		// @sigtype java 3.5
		// [o] recref:1:required interceptorList wx.interceptor.pub.documents:Interceptor
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		List<com.softwareag.wx.is.interceptor.InterceptorService> list = 
				((com.softwareag.wx.is.interceptor.WxInterceptor)wxInterceptor).listInterceptServices();
		
		IData[]	interceptorList = new IData[list.size()];
		int interceptorListIdx = 0;
		for( com.softwareag.wx.is.interceptor.InterceptorService elem : list ) {
			interceptorList[interceptorListIdx] = IDataFactory.create();
			IDataCursor interceptorListCursor = interceptorList[interceptorListIdx].getCursor();
			IDataUtil.put( interceptorListCursor, "id", elem.getId().toString() );
			IDataUtil.put( interceptorListCursor, "serviceFqn", elem.getServiceName() );
			IDataUtil.put( interceptorListCursor, "active", elem.isActive() + "" );
			
			IData[] preList = prePostListToIData(elem.getPreServiceList());
			IData[] postList = prePostListToIData(elem.getPostServiceList());
			IDataUtil.put( interceptorListCursor, "preList", preList );
			IDataUtil.put( interceptorListCursor, "postList", postList );
			interceptorListCursor.destroy();
			
			interceptorListIdx++;
		}
		IDataUtil.put( pipelineCursor, "interceptorList", interceptorList );
		
		pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void printInterceptors (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(printInterceptors)>> ---
		// @sigtype java 3.5
		// [o] field:0:required interceptorsAsString
		List<com.softwareag.wx.is.interceptor.InterceptorService> list = 
			((com.softwareag.wx.is.interceptor.WxInterceptor)wxInterceptor).listInterceptServices();
		String interceptorsAsString = "";
		for( com.softwareag.wx.is.interceptor.InterceptorService elem : list ) {
			interceptorsAsString += elem.toString() + System.getProperty("line.separator");
		}
		
		IDataUtil.put(pipeline.getCursor(), "interceptorsAsString", interceptorsAsString);
			
		// --- <<IS-END>> ---

                
	}



	public static final void registerInvokeChainProcessor (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(registerInvokeChainProcessor)>> ---
		// @sigtype java 3.5
		// [o] field:0:required result
		try {
			InvokeManager im = InvokeManager.getDefault();
			
			im.registerProcessor(wxInterceptor);
		}
		catch(Throwable t)
		{
		   throw new ServiceException("could not register processor WxInterceptor with InvokeManager: " + t);
		}
		String result = "Successfully registered WxInterceptor with the InvokeChain";
		IDataUtil.put(pipeline.getCursor(), "result", result);
			
			
		// --- <<IS-END>> ---

                
	}



	public static final void reloadConfig (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(reloadConfig)>> ---
		// @sigtype java 3.5
		// [o] field:0:required result
		((com.softwareag.wx.is.interceptor.WxInterceptor)wxInterceptor).reloadConfig();
		String result = "Successfully reloaded WxInterceptor config. For details see server and wxinterceptor log.";
		IDataUtil.put(pipeline.getCursor(), "result", result);
			
		// --- <<IS-END>> ---

                
	}



	public static final void removeInterceptor (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeInterceptor)>> ---
		// @sigtype java 3.5
		// [i] field:0:required id
		// [o] field:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		int id = IDataUtil.getInt( pipelineCursor, "id", -1 );
		
		com.softwareag.wx.is.interceptor.InterceptorService interceptor = 
			((com.softwareag.wx.is.interceptor.WxInterceptor)wxInterceptor).removeInterceptor(id);
		String result = null;
		if( interceptor != null ) {
			result = "Successfully removed interceptor: " + interceptor.toString();
		} else {
			result = "Interceptor with Id " + id + " does not exist";
		}
		// pipeline
		IDataUtil.put( pipelineCursor, "result", result );
		pipelineCursor.destroy();
			
			
		// --- <<IS-END>> ---

                
	}



	public static final void toggleInterceptorState (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(toggleInterceptorState)>> ---
		// @sigtype java 3.5
		// [i] field:0:required id
		// [o] field:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		int id = IDataUtil.getInt( pipelineCursor, "id", -1 );
		
		if( id == -1 ) {
			throw new ServiceException("A interceptor id must be provided");
		}
		
		((com.softwareag.wx.is.interceptor.WxInterceptor)wxInterceptor).toogleInterceptorState(id);
		
		pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void unregisterInvokeChainProcessor (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(unregisterInvokeChainProcessor)>> ---
		// @sigtype java 3.5
		// [o] field:0:required result
		try {
		    if( wxInterceptor != null ) {
		    	InvokeManager im = InvokeManager.getDefault();
				im.unregisterProcessor(wxInterceptor);
		    }		    	
		}
		catch(Throwable t)
		{
		   throw new ServiceException("could not register processor WxInterceptor with InvokeManager: " + t);
		}
		String result = "Successfully removed WxInterceptor from InvokeChain";
		IDataUtil.put(pipeline.getCursor(), "result", result);
		
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	public static InvokeChainProcessor wxInterceptor = com.softwareag.wx.is.interceptor.WxInterceptor.getInstance();
		
	private static IData[] prePostListToIData(List<InvokeService> list) {
	
		// interceptorList.postList
		IData[]	iList = new IData[list.size()];
		int idx = 0;
		for( InvokeService is : list ) {
			iList[idx] = IDataFactory.create();
			IDataCursor iListCursor = iList[idx].getCursor();
			IDataUtil.put( iListCursor, "fqn", is.getServiceToInvoke() );
			iListCursor.destroy();
			idx++;
		}
		return iList;
	}
		
	// --- <<IS-END-SHARED>> ---
}

