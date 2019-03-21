package wx.interceptor.impl;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2015-03-02 12:12:31 CET
// -----( ON-HOST: sagbase.eur.ad.sag

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.File;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import com.wm.app.b2b.server.ServerAPI;
import com.wm.data.IData;
// --- <<IS-END-IMPORTS>> ---

public final class log

{
	// ---( internal utility methods )---

	final static log _instance = new log();

	static log _newInstance() { return new log(); }

	static log _cast(Object o) { return (log)o; }

	// ---( server methods )---




	public static final void logMessage (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(logMessage)>> ---
		// @sigtype java 3.5
		// [i] field:0:required logMessage
		// [i] field:0:required severity {"TRACE","DEBUG","INFO","WARN","ERROR","FATAL"}
		// [i] field:0:optional logger
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	logMessage = IDataUtil.getString( pipelineCursor, "logMessage" );
		String	severity = IDataUtil.getString( pipelineCursor, "severity" );
		pipelineCursor.destroy();
		
		if( severity.equalsIgnoreCase("warn") ) {
			log.warn(logMessage);
		} else if( severity.equalsIgnoreCase("fatal") ) {
			log.fatal(logMessage);
		} else if( severity.equalsIgnoreCase("info") ) {
			log.info(logMessage);
		} else if( severity.equalsIgnoreCase("debug") ) {
			log.debug(logMessage);
		} else {
			log.trace(logMessage);
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void setupLogger (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(setupLogger)>> ---
		// @sigtype java 3.5
		File configFile = new File(ServerAPI.getPackageConfigDir("WxInterceptor"),
				"log4j.xml");
		if(configFile.exists() && configFile.canRead()) {
		    DOMConfigurator.configureAndWatch(configFile.getAbsolutePath());
		} else {
			throw new ServiceException("Cannot load log4j.xml config from WxInterceptor config dir");
		}
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	static Logger log = Logger.getLogger("com.softwareag.wx.is.interceptor");
	// --- <<IS-END-SHARED>> ---
}

