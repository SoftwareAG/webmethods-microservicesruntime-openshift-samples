package com.softwareag.wx.is.interceptor;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PipelineFiles implements FilenameFilter {

	Map<String, PipelineFile> files = new HashMap<String, PipelineFile>();
	private String serviceName;
	
	public PipelineFiles(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Override
	public boolean accept(File dir, String name) {
		//reiff_utils.log_logMsg_pre_588394759_20150409_1722059.xml
		String regex = Util.unifyServiceName(serviceName) + "_(pre|post)_([0-9]{1,})_[0-9_]{1,}\\.xml";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(name);
		if( m.find() ) {
			String type = m.group(1);
			String id = m.group(2);
			PipelineFile pf = null;
			if( this.files.containsKey(id) ) {
				pf = this.files.get(id);
			} else {
				pf = new PipelineFile(id);
				this.files.put(id, pf);
			}
			if( type.equalsIgnoreCase("pre") ) {
				pf.setInput(name);
			} else if( type.equalsIgnoreCase("post" ) ) {
				pf.setOutput(name);
			} else {
				throw new RuntimeException("Unknown pipeline type " + type);
			}
			return true;
		}
		return false;
		
	}

	public Map<String, PipelineFile> getFiles() {
		return this.files;
	}
	
	

}
