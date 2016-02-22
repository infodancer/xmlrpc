/**
 * Copyright by matthew on Feb 21, 2006 as part of the infodancer xmlrpc project.
 */
package org.infodancer.xmlrpc;

import java.util.*;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parses an XML-RPC method call into a Java object.
 * @author matthew
 *
 */
public class XMLRPCRequest
{
	public static final String TYPE_BOOLEAN = "boolean";
	public static final String TYPE_INTEGER = "int";
	public static final String TYPE_INTEGER4 = "i4";
	public static final String TYPE_STRING = "string";
	public static final String TYPE_DOUBLE = "double";
	public static final String TYPE_DATETIME = "dateTime.iso8601";
	public static final String TYPE_BASE64 = "base64";
	public static final String TYPE_STRUCT = "struct";
	public static final String TYPE_ARRAY = "array";
	String methodName;
	List<XMLRPCParam> params;
		
	public XMLRPCRequest()
	{
		
	}
	
	public String getMethodName()
	{
		return methodName;
	}
	
	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}
	
	public List<XMLRPCParam> getParams()
	{
		return params;
	}

	public void setParams(List<XMLRPCParam> params)
	{
		this.params = params;
	}

	void parse(Node node)
	{
		String name = node.getNodeName();
		if (name.equalsIgnoreCase("methodCall"))
		{
			NodeList children = node.getChildNodes();
			for (int count = 0; count < children.getLength(); count++)
			{
				Node child = children.item(count);
				String childname = child.getNodeName();
				if (childname.equalsIgnoreCase("methodName")) 
				{
					methodName = getNodeValue(child);
				}
				else if (childname.equalsIgnoreCase("params")) 
				{
					params = createParams(child);
				}
			}
		}
	}
	
	private static ArrayList<XMLRPCParam> createParams(Node node)
	{
		ArrayList<XMLRPCParam> result = new ArrayList<XMLRPCParam>();
		String name = node.getNodeName();
		if (name.equalsIgnoreCase("params"))
		{
			NodeList children = node.getChildNodes();
			for (int count = 0; count < children.getLength(); count++)
			{
				Node child = children.item(count);
				String childname = child.getNodeName();
				if (childname.equalsIgnoreCase("param")) 
				{
					
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Provides the value of a text node's contents.
	 * @param node
	 * @return
	 */
	private static String getNodeValue(Node node)
	{
		StringBuilder buffer = new StringBuilder();
		NodeList children = node.getChildNodes();
		for (int count = 0; count < children.getLength(); count++)
		{
			Node child = children.item(count);
			String childname = child.getNodeName();
			if (childname.equalsIgnoreCase("#text")) {
				buffer.append(child.getNodeValue());
			}
		}
		return buffer.toString();
	}
	
	private static XMLRPCParam createParam(Node node)
	{
		XMLRPCParam result = null;
		String name = node.getNodeName();
		if (name.equalsIgnoreCase("value"))
		{
			NodeList children = node.getChildNodes();
			for (int count = 0; count < children.getLength(); count++)
			{
				Node child = children.item(count);
				String childname = child.getNodeName();
				if (childname.equalsIgnoreCase(TYPE_BOOLEAN))
				{
					result = createBooleanParam(child);
				}
				else if (childname.equalsIgnoreCase(TYPE_INTEGER))
				{
					result = createIntegerParam(child);
				}
				else if (childname.equalsIgnoreCase(TYPE_INTEGER4))
				{
					result = createIntegerParam(child);
				}
				else if (childname.equalsIgnoreCase(TYPE_STRING))
				{
					result = createStringParam(child);
				}
				else if (childname.equalsIgnoreCase(TYPE_DATETIME))
				{
					result = createDateTimeParam(child);
				}
				else if (childname.equalsIgnoreCase(TYPE_BASE64))
				{
					result = createBinaryParam(child);
				}
				else if (childname.equalsIgnoreCase(TYPE_STRUCT))
				{
					result = createStructParam(child);
				}
				else if (childname.equalsIgnoreCase(TYPE_ARRAY))
				{
					result = createArrayParam(child);
				}
			}
		}
		return result;
	}
	
	static XMLRPCBinary createBinaryParam(Node node)
	{
		return null;
	}
	
	static XMLRPCDateTime createDateTimeParam(Node node)
	{
		return null;
	}
	
	static XMLRPCInteger createIntegerParam(Node node)
	{
		return null;
	}
	
	static XMLRPCString createStringParam(Node node)
	{
		return null;
	}
	
	static XMLRPCBoolean createBooleanParam(Node node)
	{
		return null;
	}
	
	static XMLRPCStruct createStructParam(Node node)
	{
		return null;
	}
	
	static XMLRPCArray createArrayParam(Node node)
	{
		return null;
	}
}
