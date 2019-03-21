package com.softwareag.wx.is.interceptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.jxpath.JXPathContext;

import com.wm.app.b2b.server.BaseService;
import com.wm.data.IData;
import com.wm.ps.jxpath.IDataJXPathContext;
import com.wm.ps.test.model.ServiceData;
import com.wm.ps.test.model.TestData;
import com.wm.ps.test.model.TestSuiteData;
import com.wm.ps.test.model.table.ExpressionRow;
import com.wm.ps.test.model.table.TableModel;
import com.wm.ps.test.model.table.TableRow;
import com.wm.util.coder.XMLCoderWrapper;

/*
 * @author Henning Waack
 * @unit GCS Germany
 * This code is inspired and in large parts copied from Ravi Umapathy (GCS NL), WxTestCaseBuilder
 */
public class TestCaseBuilder {

	public static String TEST_SUITE_FILE_PARTIAL_PATH = "/resources/test/setup/";
	public static String SUITE_FILE_NAME = "wmTestSuite.xml";
	private static String templatePath = "packages/WxInterceptor/resources/wmTestSuite_template.xml";
	private static String SAVED_PIPELINES_DIR = "pipeline";

	public void createTestSuite() {
	}

	public void createTestSuite(String serviceName, String testSuitePath,
			TestSuiteData suite, String packageName,
			boolean removePipelineFiles, String[] xpathArr) throws Exception {
		List<TestData> testDataList = createTestData(serviceName, packageName,
				removePipelineFiles, xpathArr);
		if (testDataList != null && testDataList.size() > 0) {
			for (Iterator testDataIterator = testDataList.iterator(); testDataIterator
					.hasNext();) {
				TestData testData = (TestData) testDataIterator.next();
				suite.addTest(testData);
			}
			String suiteXmlData = suite.serializeToXml();
			FileOutputStream fos = new FileOutputStream(
					new File(testSuitePath), false);
			fos.write(suiteXmlData.getBytes());
			fos.close();
		}
	}

	public void createTestCasesForService(String serviceName,
			String packageName, Boolean removePipelineData,
			String testSuiteNamePrefix) throws Exception {
		if (packageName == null) {
			packageName = this.getPackageNameFromServiceName(serviceName);
		}
		if (removePipelineData == null) {
			removePipelineData = false;
		}
		String testSuitePath = constructSuitePath(packageName, serviceName,
				testSuiteNamePrefix);
		TestSuiteData suite = initializeTestSuite(testSuitePath);
		createTestSuite(serviceName, testSuitePath, suite, packageName,
				removePipelineData, new String[0]);
	}

	public void createTestCasesForService(String serviceName,
			String packageName, Boolean removePipelineData, String[] xpathArr,
			String testSuiteNamePrefix) throws Exception {
		if (packageName == null) {
			packageName = this.getPackageNameFromServiceName(serviceName);
		}
		if (removePipelineData == null) {
			removePipelineData = false;
		}
		String testSuitePath = constructSuitePath(packageName, serviceName,
				testSuiteNamePrefix);
		TestSuiteData suite = initializeTestSuite(testSuitePath);
		createTestSuite(serviceName, testSuitePath, suite, packageName,
				removePipelineData, xpathArr);
	}

	private String getPackageNameFromServiceName(String serviceName) {
		BaseService bs = com.wm.app.b2b.server.ns.Namespace
				.getService(com.wm.lang.ns.NSName.create(serviceName));
		if (bs == null) {
			throw new NullPointerException("service " + serviceName
					+ " does not exists.");
		}
		return bs.getPackage().getName();
	}

	public List<TestData> createTestData(final String serviceName,
			String packageName, boolean removePipelineFiles, String[] xpathArr) {
		StringTokenizer st = new StringTokenizer(serviceName, ":");
		String folderName = st.nextToken();
		String service = st.nextToken();
		File savedPipelinesDir = new File(SAVED_PIPELINES_DIR);
		PipelineFiles pfs = new PipelineFiles(serviceName);
		String[] files = savedPipelinesDir.list(pfs);
		if (files == null || files.length < 1) {
			return null;
		}
		List<TestData> testDataList = new LinkedList<TestData>();
		for (String id : pfs.getFiles().keySet()) {
			TestData testData = new TestData();
			ServiceData svcData = new ServiceData();
			svcData.setServiceFolder(folderName);
			svcData.setServiceName(service);
			PipelineFile pf = pfs.getFiles().get(id);
			this.copyPipelineFile(pf.getInput(), packageName,
					removePipelineFiles);
			this.copyPipelineFile(pf.getOutput(), packageName,
					removePipelineFiles);
			svcData.setInputFilename(constructTestSuiteFileName(pf.getInput(),
					packageName));
			svcData = this.createServiceOutputData(svcData, xpathArr, pf, packageName);
			if( svcData == null ) {
				continue;
			}
			testData.setServiceData(svcData);
			testData.setName(folderName + ":" + service + "( " + id + ")");
			testData.setDescription("This test case was created automatically using a wrapper");
			testDataList.add(testData);
		}
		return testDataList;
	}

	private ServiceData createServiceOutputData(ServiceData svcData,
			String[] xpathArr, PipelineFile pf, String packageName) {
		
		IData pipeline = loadPipelineFromInput(pf.getOutput());
		if( pipeline ==  null ) {
			return null;
		}
		List<PathValue> pathValueList = this.createPathValueList(xpathArr,
				pipeline);
		if (pathValueList != null && pathValueList.size() > 0) {
			this.setFIelds(svcData, pathValueList);
		} else {
			svcData.setExpectedFilename(constructTestSuiteFileName(
					pf.getOutput(), packageName));
			svcData.setExpectedType("file");
		}
		return svcData;
	}

	private IData loadPipelineFromInput(String inputFilename) {
		if( inputFilename == null ) {
			return null;
		}
		File source = new File(SAVED_PIPELINES_DIR, inputFilename);
		XMLCoderWrapper xc = new XMLCoderWrapper();
		IData pipeline = null;
		try {
			InputStream is = new FileInputStream(source);
			pipeline = xc.decode(is);
			is.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pipeline;
	}

	private void setFIelds(ServiceData svcData, List<PathValue> pathValueList) {
		TableModel fields = svcData.getFieldsList();
		for (int i = 0; i < pathValueList.size(); i++) {
			String logical = null;
			if (i > 0) {
				logical = "and";
			}
			PathValue pv = pathValueList.get(i);
			TableRow row = new ExpressionRow(fields, logical, null,
					pv.getPath(), "==", pv.getValue(), null);
			fields.addRow(row);
		}
	}

	private void copyPipelineFile(File source, File target,
			boolean removeAfterCopy) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(target);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			is.close();
			if (removeAfterCopy) {
				source.delete();
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException io) {
			}
			try {
				is.close();
			} catch (IOException io) {
			}
		}
	}

	private String constructTestSuiteFileName(String sourceFileName,
			String targetPackageName) {
		File file = getPipelineTargetFile(sourceFileName, targetPackageName);
		return file.getPath().substring(9, file.getPath().length());

	}

	private File getPipelineTargetFile(String pipelineSourceFileName,
			String targetPackageName) {
		File packageDir = new File("packages", targetPackageName);
		File resourceDir = new File(packageDir, "resources");
		File testDir = new File(resourceDir, "test");
		File dataDir = new File(testDir, "data");
		File dest = new File(dataDir, pipelineSourceFileName);
		dataDir.mkdirs();
		return dest;
	}

	private void copyPipelineFile(String fileName, String packageName,
			boolean removeAfterCopy) {
		if (fileName == null || "".equals(fileName)) {
			return;
		}
		File file = new File(SAVED_PIPELINES_DIR, fileName);
		File fileTarget = getPipelineTargetFile(fileName, packageName);
		copyPipelineFile(file, fileTarget, removeAfterCopy);
	}

	private String constructSuitePath(String packageName, String serviceName,
			String testSuiteNamePrefix) throws Exception {
		String testSuiteName = Util.unifyServiceName(serviceName);
		if (testSuiteNamePrefix != null && !"".equals(testSuiteNamePrefix)) {
			testSuiteName = testSuiteNamePrefix + "_" + testSuiteName;
		}
		String testSuitePath = "packages/" + packageName
				+ TEST_SUITE_FILE_PARTIAL_PATH; // + "wmTestSuite.xml";
		testSuiteName += "_" + SUITE_FILE_NAME;

		if (createSuiteDirectory(testSuitePath)) {
			return testSuitePath = testSuitePath + testSuiteName;
		} else {
			throw new Exception("Unable to create the Test Suite directory "
					+ testSuitePath + ". Please check file permission");
		}
	}

	public boolean createSuiteDirectory(String path) {
		File f = new File(path);
		if (!f.exists()) {
			return f.mkdirs();
		}
		return true;
	}

	private boolean checkFileExists(String fileName) {
		File f = new File(fileName);
		return f.exists();
	}

	private TestSuiteData initializeTestSuite(String testSuitePath)
			throws Exception {
		TestSuiteData suite = null;
		/*
		 * if (checkFileExists(testSuitePath)) { suite = new
		 * TestSuiteData(testSuitePath); } else { suite = new
		 * TestSuiteData(templatePath); }
		 */
		suite = new TestSuiteData(templatePath);

		return suite;
	}

	// waiting on Rupinder's response...for .equals implementation of TestData
	// class
	private boolean checkIfTestDataExistInSuite(TestSuiteData suite,
			TestData newTestData) {
		boolean retValue = false; // Using temporarily until Rupinder's code is
									// ready
		ArrayList<TestData> testDataList = suite.getTests();
		for (Iterator iterator = testDataList.iterator(); iterator.hasNext();) {
			TestData testData = (TestData) iterator.next();
			if (compareTestData(newTestData, testData)) {
				retValue = true;
				return retValue;
			}
		}
		return retValue;
	}

	/*
	 * For now, two TestData are equal if input, expected type & output are
	 * equal.
	 */
	private boolean compareTestData(TestData source, TestData target) {
		if (source.getServiceData().getInputFilename()
				.equals(target.getServiceData().getInputFilename()))
			if (source.getServiceData().getExpectedType()
					.equals(target.getServiceData().getExpectedType()))
				if (source.getServiceData().getExpectedType().equals("file")) {
					String sourceFileName = source.getServiceData()
							.getExpectedFilename();
					String targetFileName = target.getServiceData()
							.getExpectedFilename();
					if (sourceFileName == null && targetFileName == null) {
						// both are empty, so we assume they are the same
						// note that we did not compare the fieldList!
						return true;
					} else if (sourceFileName == null || targetFileName == null) {
						// only one is empty, the other not, so they are not the
						// same
						return false;
					} else if (source
							.getServiceData()
							.getExpectedFilename()
							.equals(target.getServiceData()
									.getExpectedFilename())) {
						// the input file names are the same, so the test cases
						// are the same, too
						return true;
					}
				} else if (source.getServiceData().getExpectedType()
						.equals("exception")) {
					if (source
							.getServiceData()
							.getExpectedExceptionClassname()
							.equals(target.getServiceData()
									.getExpectedExceptionClassname()))
						return true;
				}

		return false;
	}

	private List<PathValue> createPathValueList(String[] pathArr, IData pipeline) {
		List<PathValue> pathValueList = new ArrayList<PathValue>();
		for (int i = 0; i < pathArr.length; i++) {
			String path = pathArr[i];
			if (path == null || pipeline == null) {
				continue;
			}
			String value = extractValue(pipeline, path);
			pathValueList.add(new PathValue(path, value));

		}
		return pathValueList;
	}

	private String extractValue(IData pipeline, String xpath) {
		String result = null;
		if (pipeline != null) {
			JXPathContext expectedContext = IDataJXPathContext
					.newContext(pipeline);
			List<?> expectedList = expectedContext.selectNodes((String) xpath);
			if (expectedList != null && expectedList.size() > 0) {
				Object[] array = expectedList.toArray();
				if (array.length == 1) {
					result = array[0].toString();
				} else {
					result = array.toString();
				}
			}
		}
		return result;
	}

	private class PathValue {
		String path;
		String value;

		public PathValue(String path, String value) {
			this.path = path;
			this.value = value;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

}
