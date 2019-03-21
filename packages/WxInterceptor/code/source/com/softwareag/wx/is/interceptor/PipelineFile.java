package com.softwareag.wx.is.interceptor;

public class PipelineFile {
	String input;
	String output;
	String id;

	public PipelineFile(String id) {
		this.id = id;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}
