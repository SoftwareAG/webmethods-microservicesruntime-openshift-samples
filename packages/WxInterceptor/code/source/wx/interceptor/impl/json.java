package wx.interceptor.impl;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2015-03-02 12:12:22 CET
// -----( ON-HOST: sagbase.eur.ad.sag

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.ServerAPI;
// --- <<IS-END-IMPORTS>> ---

public final class json

{
	// ---( internal utility methods )---

	final static json _instance = new json();

	static json _newInstance() { return new json(); }

	static json _cast(Object o) { return (json)o; }

	// ---( server methods )---




	public static final void toJson (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(toJson)>> ---
		// @sigtype java 3.5
		// [i] object:0:required object
		// [o] field:0:required json
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		Object	object = IDataUtil.get( pipelineCursor, "object" );
		
		if( useWxJson == null ) {
			useWxJson = useWxJson();
		}
		
		String	json = null;		
		if( useWxJson ) {
			json = invokeWxJson(object);
		} else {
			json = invokeNativeIsJson(object);
		}
		
		IDataUtil.put( pipelineCursor, "json", json );
		pipelineCursor.destroy();
		
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	static Boolean useWxJson = null;
	
	private static Boolean useWxJson() {
		String pcks[] = ServerAPI.getEnabledPackages();
		for( String pck : pcks) {
			if( pck.equalsIgnoreCase("WxJSON") ) {
				return true;
			}
		}
		return false;
		
	}
	
	private static String invokeWxJson(Object object) throws ServiceException {
	
		// input
		IData input = IDataFactory.create();
		IDataCursor inputCursor = input.getCursor();
		IDataUtil.put( inputCursor, "object", object );
		inputCursor.destroy();
		// output
		IData 	output = IDataFactory.create();
		try{
			output = Service.doInvoke( "wx.json.services", "toJson", input );
		} catch( Exception e) { 
			throw new ServiceException("Error invoking WxJSON service wx.json.services: " + e); 
		}
		IDataCursor outputCursor = output.getCursor();
		String json = IDataUtil.getString( outputCursor, "json" );
		outputCursor.destroy();
		return json;
	}
	
	private static String invokeNativeIsJson(Object o) throws ServiceException {
	
		// input
		IData input = IDataFactory.create();
		IDataCursor inputCursor = input.getCursor();
		// document
		IData	document = IDataFactory.create();
		IDataUtil.put( inputCursor, "document", o );
		inputCursor.destroy();
		// output
		IData 	output = IDataFactory.create();
		try{
			output = Service.doInvoke( "pub.json", "documentToJSONString", input );
		} catch( Exception e) { 
			throw new ServiceException("Error invoking WmPublic service pub.json:documentToJSONString: " + e); 
		}
		IDataCursor outputCursor = output.getCursor();
		String	jsonString = IDataUtil.getString( outputCursor, "jsonString" );
		outputCursor.destroy();
		return jsonString;
	}
		
	// --- <<IS-END-SHARED>> ---
}

