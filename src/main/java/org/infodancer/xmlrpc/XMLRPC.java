package org.infodancer.xmlrpc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Provides static methods for sending and receiving XMLRPC invocations.
 * @author matthew
 *
 */
public class XMLRPC
{
	private static final String XMLRPC_PARAM_VALUE = "value";
	private static final String XMLRPC_PARAM_STRING = "string";
	private static final String XMLRPC_PARAM = "param";
	private static final String XMLRPC_PARAMS = "params";
	private static final String XMLRPC_METHOD_NAME = "methodName";
	private static final String XMLRPC_METHOD_CALL = "methodCall";
	private static final String XMLRPC_METHOD_RESPONSE = "methodResponse";

	public static String createFaultResponse(int code, String message)
	{
		StringBuilder r = new StringBuilder();
		r.append("<?xml version=\"1.0\"?>\n");
		r.append("<methodReponse>\n");
		r.append("\t<fault>\n");
		r.append("\t\t<value>\n");
		r.append("\t\t\t<struct>\n");
		r.append("\t\t\t\t<member>\n");
		r.append("\t\t\t\t\t<name>faultCode</name>\n");
		r.append("\t\t\t\t\t<value><int>" + code + "</int></value>\n");
		r.append("\t\t\t\t</member>\n");
		r.append("\t\t\t\t<member>\n");
		r.append("\t\t\t\t\t<name>faultString</name>\n");
		r.append("\t\t\t\t\t<value><string>" + message + "</string></value>\n");
		r.append("\t\t\t\t</member>\n");
		r.append("\t\t\t</struct>\n");
		r.append("\t\t</value>\n");
		r.append("\t</fault>\n");
		r.append("</methodCall>\n");
		return r.toString();				
	}
	
	public static String createSuccessResponse(Object... params)
	{
		StringBuilder r = new StringBuilder();
		r.append("<?xml version=\"1.0\"?>\n");
		r.append("<methodReponse>\n");
		if ((params != null) && (params.length > 0))
		{
			r.append("\t<params>\n");
			for (Object o : params)
			{
				if (o != null)
				{
					r.append("\t<param><value>");
					if (o instanceof String)
					{
						r.append("<string>");
						r.append((String) o);
						r.append("</string>");
					}
					r.append("</value></param>\n");
				}
			}
			r.append("\t</params>\n");
		}
		r.append("</methodCall>\n");
		return r.toString();		
	}
	
	/**
	 * Given a method name and a list of parameters, creates an XMLRPC document (in a String format) to invoke that method.
	 * @param methodName
	 * @param params
	 * @return
	 */
	public static String createMethodCall(String methodName, Object... params)
	{
		if (methodName == null) throw new IllegalArgumentException("methodName must not be null!");
		
		StringBuilder r = new StringBuilder();
		r.append("<?xml version=\"1.0\"?>\n");
		r.append("<methodCall>\n");
		r.append("\t<methodName>"); 
		r.append(methodName);
		r.append("</methodName>\n");
		if ((params != null) && (params.length > 0))
		{
			r.append("\t<params>\n");
			for (Object o : params)
			{
				if (o != null)
				{
					r.append("\t<param><value>");
					if (o instanceof String)
					{
						r.append("<string>");
						r.append((String) o);
						r.append("</string>");
					}
					r.append("</value></param>\n");
				}
			}
			r.append("\t</params>\n");
		}
		r.append("</methodCall>\n");
		return r.toString();
	}

	private static String extractValue(Node node)
	{
		return node.getTextContent();
	}	

	/**
	 * Given a String input, parses that input for an XMLRPC method call and returns the result.
	 * @param input
	 * @return The parsed structure of the request.
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static XMLRPCRequest parseMethodCall(String input) throws ParserConfigurationException, SAXException, IOException
	{
		return parseMethodCall(new ByteArrayInputStream(input.getBytes()));
	}

	/**
	 * Given a String input, parses that input for an XMLRPC method call and returns the result.
	 * @param input
	 * @return The parsed structure of the response.
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static XMLRPCResponse parseMethodResponse(String input) throws ParserConfigurationException, SAXException, IOException
	{
		return parseMethodResponse(new ByteArrayInputStream(input.getBytes()));
	}

	/**
	 * Given an InputStream, parses that input for an XMLRPC method call and returns the result.
	 * @param input
	 * @return
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static XMLRPCRequest parseMethodCall(InputStream input) throws ParserConfigurationException, SAXException, IOException
	{
        DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(input);
        return parseXMLRPCRequestDocument(document);
	}

	/**
	 * Given a CharSequence input, parses that input for an XMLRPC method call and returns the result.
	 * @param input
	 * @return
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static XMLRPCResponse parseMethodResponse(InputStream input) throws ParserConfigurationException, SAXException, IOException
	{
        DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(input);
        return parseXMLRPCResponseDocument(document);
	}

	private static XMLRPCRequest parseXMLRPCRequestDocument(Document doc)
	{
		NodeList nodes = doc.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++)
		{
			Node node = nodes.item(i);
			String name = node.getNodeName();
			if (XMLRPC_METHOD_CALL.equalsIgnoreCase(name))
			{
				return parseMethodCall(node);
			}
		}
		return null;
	}

	private static XMLRPCResponse parseXMLRPCResponseDocument(Document doc)
	{
		NodeList nodes = doc.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++)
		{
			Node node = nodes.item(i);
			String name = node.getNodeName();
			if (XMLRPC_METHOD_RESPONSE.equalsIgnoreCase(name))
			{
				return parseMethodResponse(node);
			}
		}
		return null;
	}

	private static XMLRPCResponse parseMethodResponse(Node node)
	{
		if (XMLRPC_METHOD_RESPONSE.equalsIgnoreCase(node.getNodeName()))
		{
			XMLRPCResponse result = new XMLRPCResponse();
			NodeList nodes = node.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++)
			{
				Node child = nodes.item(i);
				String name = child.getNodeName();
				if (XMLRPC_PARAMS.equalsIgnoreCase(name))
				{
					List<XMLRPCParam> params = parseParams(child);
					result.setParams(params);
				}
			}
			return result;
		}
		else throw new RuntimeException(node.getNodeName() + " is not an xmlrpc call!");
	}

	private static XMLRPCRequest parseMethodCall(Node node)
	{
		if (XMLRPC_METHOD_CALL.equalsIgnoreCase(node.getNodeName()))
		{
			XMLRPCRequest result = new XMLRPCRequest();
			NodeList nodes = node.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++)
			{
				Node child = nodes.item(i);
				String name = child.getNodeName();
				if (XMLRPC_METHOD_NAME.equalsIgnoreCase(name))
				{
					String methodName = extractValue(child);
					if (methodName != null)
					{
						result.setMethodName(methodName);
					}
				}
				else if (XMLRPC_PARAMS.equalsIgnoreCase(name))
				{
					List<XMLRPCParam> params = parseParams(child);
					result.setParams(params);
				}
			}
			return result;
		}
		else throw new RuntimeException(node.getNodeName() + " is not an xmlrpc call!");
	}

	private static List<XMLRPCParam> parseParams(Node node)
	{
		List<XMLRPCParam> params = new LinkedList<XMLRPCParam>();
		if (XMLRPC_PARAMS.equalsIgnoreCase(node.getNodeName()))
		{
			NodeList nodes = node.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++)
			{
				Node child = nodes.item(i);
				String name = child.getNodeName();
				if (XMLRPC_PARAM.equalsIgnoreCase(name))
				{
					XMLRPCParam param = parseParam(child);
					params.add(param);
				}
			}
		}
		return params;
	}

	private static XMLRPCParam parseParam(Node node)
	{
		XMLRPCParam param = null;
		if (XMLRPC_PARAM.equalsIgnoreCase(node.getNodeName()))
		{
			NodeList nodes = node.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++)
			{
				Node child = nodes.item(i);
				String name = child.getNodeName();
				if (XMLRPC_PARAM_VALUE.equalsIgnoreCase(name))
				{
					param = parseParamValue(child);
				}
			}			
		}
		return param;
	}

	private static XMLRPCParam parseParamValue(Node node)
	{
		XMLRPCParam param = null;
		if (XMLRPC_PARAM_VALUE.equalsIgnoreCase(node.getNodeName()))
		{
			NodeList nodes = node.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++)
			{
				Node child = nodes.item(i);
				String name = child.getNodeName();
				if (XMLRPC_PARAM_STRING.equalsIgnoreCase(name))
				{
					param = new XMLRPCString();
					param.setValue(extractValue(child));
				}
				else // Default parameter type is string
				{
					param = new XMLRPCString();
					param.setValue(extractValue(child));
				}
			}
		}
		return param;
	}
}
