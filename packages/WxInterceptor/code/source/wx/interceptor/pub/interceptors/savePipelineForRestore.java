package wx.interceptor.pub.interceptors;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2015-11-26 17:35:09 CET
// -----( ON-HOST: sagbase.eur.ad.sag

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import com.wm.app.b2b.server.Server;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
import com.wm.util.coder.IDataXMLCoder;
// --- <<IS-END-IMPORTS>> ---

public final class savePipelineForRestore

{
	// ---( internal utility methods )---

	final static savePipelineForRestore _instance = new savePipelineForRestore();

	static savePipelineForRestore _newInstance() { return new savePipelineForRestore(); }

	static savePipelineForRestore _cast(Object o) { return (savePipelineForRestore)o; }

	// ---( server methods )---




	public static final void restorePipelineAndInvokeService (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(restorePipelineAndInvokeService)>> ---
		// @sigtype java 3.5
		// [i] field:0:required serviceNameFQN
		// [o] field:0:required nrProcessedFiles
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	serviceNameFQN = IDataUtil.getString( pipelineCursor, "serviceNameFQN" );
		final String	renameSuffixAfterProcessing = ".processed";
		pipelineCursor.destroy();
		
		final String serviceName =  transformServiceName(serviceNameFQN);
		File pipelineDir = Server.getPipelineDir(); 
		
		String[] files = pipelineDir.list(new FilenameFilter() {			
			@Override
			public boolean accept(File dir, String name) {
				if( name.contains(serviceName) && !name.endsWith(renameSuffixAfterProcessing)) {
					return true;
				}
				return false;
			}
		});
		int counter = 0;
		for( String pipelineFilename : files ) {
			try {
				IDataXMLCoder xc = new IDataXMLCoder();
				File pFile = new File(pipelineDir, pipelineFilename);
				IData restoredPipeline = xc.readFromFile(pFile);
				IData input = IDataFactory.create();
				IDataCursor inputCursor = input.getCursor();
				inputCursor.destroy();
				String[] serviceFqn = serviceNameFQN.split(":");				
				IData 	output = IDataFactory.create();
				output = Service.doInvoke( serviceFqn[0], serviceFqn[1], restoredPipeline );
				boolean success = pFile.renameTo(new File(pFile.getAbsolutePath() + renameSuffixAfterProcessing));
				if( !success ) {
					throw new ServiceException("Could not add suffix " + renameSuffixAfterProcessing + " to pipeline file " + pFile.getAbsolutePath());
				}
				counter++;
			} catch(IOException ioe) {
				throw new ServiceException("Could not add suffix " + renameSuffixAfterProcessing + " to pipeline file: " + ioe);
			} catch( Exception e) {
				throw new ServiceException("Could not restore pipeline file " + pipelineFilename + " for service " + serviceNameFQN + ": " + e);
			}
		}
		
		IDataUtil.put(pipelineCursor, "nrProcessedFiles", counter + "");
			
		// --- <<IS-END>> ---

                
	}



	public static final void savePipelineForRestorePre (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(savePipelineForRestorePre)>> ---
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
			String serviceName =  transformServiceName(__interceptedServiceName);
			String fileName = serviceName + "_pipelineForRestore_" + "_" + fdf.format(new java.util.Date()) + ".xml";
			savePipelineToFile(fileName, __pipeline);
			
			// add the random Id to the __interceptorStorage so that a post service can pick it up
			IData	__interceptorStorage = IDataFactory.create();
		}
		// pipeline
		IDataUtil.put( pipelineCursor, "__cancelServiceInvocation", "true" );
		
		IDataUtil.put( pipelineCursor, "__pipeline", __pipeline );
		pipelineCursor.destroy();
		
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static org.apache.commons.lang.time.FastDateFormat fdf = org.apache.commons.lang.time.FastDateFormat.getInstance("yyyyMMdd_HHmmsss");
	
	
	private static String transformServiceName(String serviceName) {
		return serviceName.replaceFirst(":", "##");
	}
	
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

