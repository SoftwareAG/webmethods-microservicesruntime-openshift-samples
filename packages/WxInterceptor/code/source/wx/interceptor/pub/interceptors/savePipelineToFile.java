package wx.interceptor.pub.interceptors;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2017-04-10 12:16:09 IST
// -----( ON-HOST: MCSRPR02.eur.ad.sag

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.File;
import java.io.FileNotFoundException;
import com.wm.app.b2b.server.Server;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
import com.wm.util.JournalLogger;
import com.wm.util.coder.IDataXMLCoder;
// --- <<IS-END-IMPORTS>> ---

public final class savePipelineToFile

{
	// ---( internal utility methods )---

	final static savePipelineToFile _instance = new savePipelineToFile();

	static savePipelineToFile _newInstance() { return new savePipelineToFile(); }

	static savePipelineToFile _cast(Object o) { return (savePipelineToFile)o; }

	// ---( server methods )---




	public static final void savePipelineToFilePost (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(savePipelineToFilePost)>> ---
		// @specification wx.interceptor.pub.interceptors:postServiceSpec
		// @sigtype java 3.5
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	__interceptedServiceName = IDataUtil.getString( pipelineCursor, "__interceptedServiceName" );
		IData	__pipeline = IDataUtil.getIData( pipelineCursor, "__pipeline" );
		IData	__interceptorStorage = IDataUtil.getIData( pipelineCursor, "__interceptorStorage" );
		if ( __pipeline == null) {
			__pipeline = IDataFactory.create();
		}
		// get the random Id from the __interceptorStorage if a pre-service put it in
		Integer randomId = random.nextInt();
		if(randomId < 0 ) {
			randomId = randomId * -1;
		}
		if( __interceptorStorage != null ) {
			randomId = IDataUtil.getInt(__interceptorStorage.getCursor(), "randomId", randomId);
		} 
		
		String fileName = __interceptedServiceName.replaceFirst(":", "#") + "_post_" + randomId + "_" + fdf.format(new java.util.Date()) + ".xml";
		
		savePipelineToFile(fileName, __pipeline);
		
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void savePipelineToFilePre (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(savePipelineToFilePre)>> ---
		// @specification wx.interceptor.pub.interceptors:preServiceSpec
		// @sigtype java 3.5
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	__interceptedServiceName = IDataUtil.getString( pipelineCursor, "__interceptedServiceName" );
		
		// __pipeline
		IData	__pipeline = IDataUtil.getIData( pipelineCursor, "__pipeline" );
		if( __interceptedServiceName == null ) {
			System.out.println("__interceptedServiceName is null");
		}
		if ( __pipeline != null) {
			Integer randomId = random.nextInt();
			if( randomId < 0 ) {
				randomId = randomId * -1;
			}
			String serviceName =  __interceptedServiceName.replaceFirst(":", "#");
			String fileName = serviceName + "_pre_" + randomId + "_" + fdf.format(new java.util.Date()) + ".xml";
			savePipelineToFile(fileName, __pipeline);
		
			// add the random Id to the __interceptorStorage so that a post service can pick it up
			IData	__interceptorStorage = IDataFactory.create();
			IDataUtil.put(__interceptorStorage.getCursor(), "randomId", randomId);
			IDataUtil.put( pipelineCursor, "__interceptorStorage", __interceptorStorage );
		}
		// pipeline
		IDataUtil.put( pipelineCursor, "__cancelServiceInvocation", "false" );
		
		IDataUtil.put( pipelineCursor, "__pipeline", __pipeline );
		pipelineCursor.destroy();
		
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static org.apache.commons.lang.time.FastDateFormat fdf = org.apache.commons.lang.time.FastDateFormat.getInstance("yyyyMMdd_HHmmsss");
	private static java.util.Random random = new java.util.Random(new java.util.Date().getTime());
	
	public static final void savePipelineToFile(String name, IData pipeline) throws ServiceException {
	    if(name == null) {
	        throw new ServiceException("Provide the filename to where to store the pipeline");
	    }
	    try {
	        File fPipeline = Server.getPipelineDir();
	        if(!fPipeline.isDirectory()) {
	            fPipeline.mkdir();
	        }
	        File f = new File(fPipeline, name);
	        IDataXMLCoder xc = new IDataXMLCoder();
	        xc.writeToFile(f, pipeline);
	    } catch(FileNotFoundException fnfe) {
	        throw new ServiceException("Error when saving pipeline with name " + name + ":" + fnfe);
	    } catch(Exception e) {
	        throw new ServiceException("Error when saving pipeline with name " + name + ":" + e);
	    }
	}
	// --- <<IS-END-SHARED>> ---
}

