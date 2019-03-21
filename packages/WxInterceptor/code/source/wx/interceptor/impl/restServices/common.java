package wx.interceptor.impl.restServices;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2015-11-13 19:28:42 CET
// -----( ON-HOST: sagbase.eur.ad.sag

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.FlowSvcImpl;
import java.util.Stack;
import com.wm.app.b2b.server.InvokeState;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
import com.wm.lang.ns.NSField;
import com.wm.lang.ns.NSService;
import com.wm.util.coder.IDataCodable;
// --- <<IS-END-IMPORTS>> ---

public final class common

{
	// ---( internal utility methods )---

	final static common _instance = new common();

	static common _newInstance() { return new common(); }

	static common _cast(Object o) { return (common)o; }

	// ---( server methods )---




	public static final void getResponseString (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getResponseString)>> ---
		// @sigtype java 3.5
		// [o] field:0:required responseString
		com.wm.app.b2b.server.InvokeState is = com.wm.app.b2b.server.InvokeState.getCurrentState();
		byte response[] = (byte[])is.getPrivateData("$msgBytesOut");
		String responseString = new String(response);
		IDataUtil.put(pipeline.getCursor(), "responseString", responseString);
		// --- <<IS-END>> ---

                
	}



	public static final void tracePipelineToString (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(tracePipelineToString)>> ---
		// @sigtype java 3.5
		// [i] record:0:required doc
		// [o] field:0:required pipelineAsString
		StringBuilder buffer = new StringBuilder(); 
		// used as tree to drill down the pipeline 
		java.util.Hashtable<Object, Object> table = new java.util.Hashtable<Object, Object>();
		table.put(pipeline, pipeline); 
		dumpIData(buffer, 0, pipeline, 0, table); 
		
		pipeline.getCursor().insertAfter("pipelineAsString", buffer.toString()); 
		pipeline.getCursor().destroy(); 
		
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	private static final String NL = System.getProperty("line.separator"); 
	
	private static void dumpIData(StringBuilder sb, int level, IData pipeline, int nextLevel, java.util.Hashtable<Object, Object> table) { 
	    if (pipeline == null) { 
	      return; 
	    } 
	    IDataCursor cursor = pipeline.getCursor(); 
	    for (int i = 0; cursor.next(); i++) { 
	      String str = cursor.getKey(); 
	      Object currentValue = cursor.getValue(); 
	      if ((currentValue != null) && (table.get(currentValue) != null)) { 
	        sb.append(NL) 
	                .append(str.toString()) 
	                .append(" {") 
	                        .append(((Object)currentValue).getClass().getName()) 
	                .append("}"); 
	        return; 
	      } 
	      if ((currentValue instanceof IDataCodable)) { 
	        currentValue = ((IDataCodable)currentValue).getIData(); 
	        if ((currentValue != null) && (table.get(currentValue) != null)) { 
	          sb.append(NL)
	          			.append("[")
	                  .append(new Integer(nextLevel).toString()) 
	                  .append("] ") 
	                  .append(str.toString()); 
	          return; 
	        } 
	      } 
	      Object[] castValue; 
	      int j; 
	      if ((currentValue instanceof String[][])) { 
	            String[][] localObject3 = (String[][])currentValue; 
	        for (j = 0; j < localObject3.length; j++) { 
	          for (int k = 0; k < localObject3[0].length; k++) { 
	            sb 
	                    .append(NL)
	                    .append("[")
	                    .append(nextLevel) 
	                    .append("]")
	                    .append(" ").append(str); 
	                    /*for (int z = 0; z < j; z++) { 
	                            sb.append("\t"); 
	                    }*/ 
	                    sb.append(j) 
	                            .append("[") 
	                                    .append(k) 
	                            .append("] {java.lang.String[][]} = ") 
	                            .append("'") 
	                                    .append(localObject3[j][k]) 
	                            .append("'"); 
	          } 
	        } 
	      } 
	      else if ((currentValue instanceof String[])) { 
	        castValue = (String[])currentValue; 
	        for (j = 0; j < castValue.length; j++) { 
	                sb.append(NL); 
	      			sb.append("[" + nextLevel + "]"); 
	                        for (int z = 0; z < nextLevel; z++) { 
	//	                                sb.append("\t"); 
	                        };                                 
	                        sb.append(" ") 
	                        .append(str);                         
	                sb.append("{java.lang.String[]} = ") 
	                        .append("'") 
	                                .append(castValue[j]) 
	                        .append("'"); 
	        } 
	      } 
	      else if ((currentValue instanceof IData[])) 
	      { 
	        castValue = (IData[])currentValue; 
	        for (j = 0; j < castValue.length; j++) { 
	          if (castValue[j] != null) { 
	            table.put(castValue[j], castValue[j]); 
	          } 
	          sb.append(NL); 
				sb.append("[" + nextLevel + "]"); 
	                  for (int z = 0; z < nextLevel; z++) { 
	//	                        sb.append("\t"); 
	                };                           
	                  sb.append(" ") 
	                  .append(str);                   
	          sb.append(((Object)currentValue).getClass().getName()).append("} => "); 
	          // recursion down the tree 
	          dumpIData(sb, level, (IData)castValue[j], nextLevel + 1, table); 
	          if (castValue[j] != null) { 
	            table.remove(castValue[j]); 
	          } 
	        } 
	      } else if ((currentValue instanceof IData)) { 
	        table.put(currentValue, currentValue); 
	        sb.append(NL); 
			sb.append("[" + nextLevel + "]"); 
	                for (int z = 0; z < nextLevel; z++) { 
	//	                        sb.append("\t"); 
	                }                         
	                sb.append(" ") 
	                        .append(str) 
	                .append(" {") 
	                .append(((Object)currentValue).getClass().getName()) 
	                .append("} => "); 
	        // recursion down the tree 
	        dumpIData(sb, level, (IData)currentValue, nextLevel + 1, table); 
	        table.remove(currentValue); 
	      } 
	      else if ((currentValue instanceof IDataCodable[])) { 
	        castValue = (IDataCodable[])currentValue; 
	        for (j = 0; j < castValue.length; j++) { 
	          if (castValue[j] != null) { 
	            table.put(castValue[j], castValue[j]); 
	          } 
	          sb.append(NL); 
	          			sb.append("[" + nextLevel + "]"); 
	                  for (int z = 0; z < nextLevel; z++) { 
	//	                          sb.append("\t"); 
	                  } 
	                  sb.append(" ") 
	                          .append(str) 
	                  .append("[") 
	                          .append(j) 
	                  .append("] {") 
	                          .append(((Object)currentValue).getClass().getName()) 
	                  .append("} => "); 
	          // recursion down the tree 
	          dumpIData(sb, level, ((IDataCodable)castValue[j]).getIData(), nextLevel + 1, table); 
	          if (castValue[j] != null) { 
	            table.remove(castValue[j]); 
	          } 
	        } 
	      } 
	      else if (currentValue != null) { 
	        sb.append(NL); 
	                sb.append("[" + nextLevel + "]"); 
	                for (int z = 0; z < nextLevel; z++) { 
	//	                          sb.append("\t"); 
	                  } 
	                sb.append(" ") 
	                .append(str) 
	                        .append(" {") 
	                                .append(((Object)currentValue).getClass().getName()) 
	                        .append("} = ") 
	                .append("'") 
	                        .append(currentValue) 
	                .append("'"); 
	      } else { 
	        sb.append(NL + "[" + nextLevel + "] " + str + " = null"); 
	      } 
	    } 
	    cursor.destroy(); 
	  } 
	
	
		
	// --- <<IS-END-SHARED>> ---
}

