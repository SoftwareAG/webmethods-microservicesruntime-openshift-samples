package wx.interceptor.pub.interceptors;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2015-03-02 12:12:59 CET
// -----( ON-HOST: sagbase.eur.ad.sag

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
// --- <<IS-END-IMPORTS>> ---

public final class measureRuntimeInMemory

{
	// ---( internal utility methods )---

	final static measureRuntimeInMemory _instance = new measureRuntimeInMemory();

	static measureRuntimeInMemory _newInstance() { return new measureRuntimeInMemory(); }

	static measureRuntimeInMemory _cast(Object o) { return (measureRuntimeInMemory)o; }

	// ---( server methods )---




	public static final void getStatistics (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getStatistics)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional serviceFqn
		// [o] recref:1:required statisticsList wx.interceptor.pub.interceptors.measureRuntimeInMemory:Statistics
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		String serviceFqn = IDataUtil.getString(pipelineCursor, "serviceFqn");
		
		ArrayList<IData> statisticsList = new ArrayList<IData>();
		for( String serviceName : statsMap.keySet() ) {
			if( serviceFqn != null && !serviceName.equalsIgnoreCase(serviceFqn) ) {
				continue;
			}
			SummaryStatistics sumStats = statsMap.get(serviceName); 
			IData statistics = IDataFactory.create();
			IDataCursor statisticsC = statistics.getCursor();
			IDataUtil.put( statisticsC, "serviceName", serviceName );
			IDataUtil.put( statisticsC, "avgRuntimeNano", sumStats.getMean()/1/1 );
			IDataUtil.put( statisticsC, "standardDeviationNano", sumStats.getStandardDeviation()/1/1 );
			IDataUtil.put( statisticsC, "minNano", sumStats.getMin()/1/1 );
			IDataUtil.put( statisticsC, "maxNano", sumStats.getMax()/1/1 );
			IDataUtil.put( statisticsC, "varianceNano", sumStats.getVariance()/1/1 );
			IDataUtil.put( statisticsC, "sumNano", sumStats.getSum()/1/1 );
			IDataUtil.put( statisticsC, "invocations", sumStats.getN() );
			IDataUtil.put( statisticsC, "summerys", sumStats.getSummary().toString() );
			statisticsC.destroy();
			statisticsList.add(statistics);
		}
		
		// statisticsList
		IDataUtil.put( pipelineCursor, "statisticsList", statisticsList.toArray(new IData[0]) );
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
		if ( __interceptorStorage != null)
		{
			Long startTimeNano = (Long)IDataUtil.get(__interceptorStorage.getCursor(), "startTimeNano");
			if( startTimeNano != null ) {
				
				Long runtime = System.nanoTime()-startTimeNano;
				SummaryStatistics stats = statsMap.get(__interceptedServiceName);
				if( stats == null ) {
					stats = new SummaryStatistics();
					statsMap.put(__interceptedServiceName, stats);
				}
				stats.addValue(runtime.doubleValue());
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
			
		long startTimeNano = System.nanoTime();
		IData	__interceptorStorage = IDataFactory.create();
		IDataUtil.put(__interceptorStorage.getCursor(), "startTimeNano", startTimeNano);
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "__cancelServiceInvocation", "false" );		
		// __interceptorStorage
		IDataUtil.put( pipelineCursor_1, "__interceptorStorage", __interceptorStorage );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void resetStatistics (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(resetStatistics)>> ---
		// @sigtype java 3.5
		statsMap.clear();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	private static HashMap<String, SummaryStatistics> statsMap = new HashMap<String, SummaryStatistics>();
		
	// --- <<IS-END-SHARED>> ---
}

