package wx.interceptor.impl;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2017-04-18 14:23:45 IST
// -----( ON-HOST: MCSRPR02.eur.ad.sag

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.Stack;
import com.wm.app.b2b.server.InvokeState;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
// --- <<IS-END-IMPORTS>> ---

public final class util

{
	// ---( internal utility methods )---

	final static util _instance = new util();

	static util _newInstance() { return new util(); }

	static util _cast(Object o) { return (util)o; }

	// ---( server methods )---




	public static final void addAsNamedChild (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(addAsNamedChild)>> ---
		// @sigtype java 3.5
		// [i] record:0:required parent
		// [i] - record:0:required child
		// [i] - record:1:required childList
		// [i] field:0:required name
		// [i] field:0:required nameList
		// [o] record:0:required parent
		// [o] - record:0:required namedChild
		// [o] - record:1:required namedChildList
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		// parent
		IData	parent = IDataUtil.getIData( pipelineCursor, "parent" );
		if( parent == null ) {
			parent = IDataFactory.create();
		}
		IDataCursor parentCursor = parent.getCursor();
		IData child = IDataUtil.getIData( parentCursor, "child" );
		IData[]	childList = IDataUtil.getIDataArray(parentCursor, "childList" );
		if( child != null ) {
		
			String	name = IDataUtil.getString( pipelineCursor, "name" );
			if( name == null || "".equals(name) ) {
				throw new ServiceException("Provide a name for the child object");
			}
			
			IDataUtil.remove(parentCursor, "child");
			IDataUtil.put(parentCursor, name, child);
		} 
		if( childList != null ) {
		
			String	nameList = IDataUtil.getString( pipelineCursor, "nameList" );
			if( nameList == null || "".equals(nameList) ) {
				throw new ServiceException("Provide a name for the child list object");
			}
			IDataUtil.remove(parentCursor, "childList");
			IDataUtil.put(parentCursor, nameList, childList);
		}
		parentCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void docListToStringList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(docListToStringList)>> ---
		// @sigtype java 3.5
		// [i] record:1:required docList
		// [i] field:0:required fieldName
		// [o] field:1:required stringList
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	fieldName = IDataUtil.getString( pipelineCursor, "fieldName" );
		IData[]	docList = IDataUtil.getIDataArray( pipelineCursor, "docList" );
		java.util.List<String> tmpList = new java.util.ArrayList<String>();
		if ( docList != null) {
			for ( int i = 0; i < docList.length; i++ ) {
				String field = IDataUtil.getString(docList[i].getCursor(), fieldName);
				if( field != null && !("".equals(field)) ) {
					tmpList.add(field);
				}
			}
		}
		if( tmpList.size() == 0 ) {
			return;
		}
		IDataUtil.put( pipelineCursor, "stringList", tmpList.toArray(new String[0]));
		pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void getCallingService (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getCallingService)>> ---
		// @sigtype java 3.5
		// [o] field:0:required callingService
		String callingServiceName = "undefined";
		
		// get callingServiceName
		IDataCursor pipelineCursor = pipeline.getCursor();
		try {
			Stack stack = InvokeState.getCurrentState().getCallStack();
			callingServiceName = stack.get(stack.size()-(3)).toString();
		} catch(Exception e) {
			if( callingServiceName.equals("undefined") ) {
					com.wm.app.b2b.server.InvokeState invokeState = com.wm.app.b2b.server.InvokeState.getCurrentState();
					if (invokeState != null) {
						com.wm.lang.flow.FlowState flowState = invokeState.getFlowState();
						if (flowState != null) {
							com.wm.lang.flow.FlowElement flowElement = flowState.current();
							if (flowElement != null) {
								com.wm.lang.flow.FlowElement flowRoot = flowElement.getFlowRoot();
								String flowName = flowRoot.getNSName();
								com.wm.app.b2b.server.BaseService bs = com.wm.app.b2b.server.ns.Namespace.getService(com.wm.lang.ns.NSName.create(flowName));
								callingServiceName = bs.getNSName().getFullName();
							}
						}   
					}
			}	
		}	
		IDataUtil.put( pipelineCursor, "callingService", callingServiceName );
		pipelineCursor.destroy();
			
			
		// --- <<IS-END>> ---

                
	}



	public static final void getRandomNumber (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getRandomNumber)>> ---
		// @sigtype java 3.5
		// [i] field:0:required length
		// [i] field:0:optional prefix
		// [o] field:0:required random
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			int	length = IDataUtil.getInt( pipelineCursor, "length", 10 );
			String	prefix = IDataUtil.getString( pipelineCursor, "prefix" );
		pipelineCursor.destroy();
		
		prefix = prefix == null ? "" : prefix;
		char[] chars = "0123456789".toCharArray();
		StringBuilder sb = new StringBuilder(prefix);
		//java.util.Random random = new java.util.Random(new java.util.Date().getTime());
		java.util.Random random = new java.util.Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "random", sb.toString() );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}
}

