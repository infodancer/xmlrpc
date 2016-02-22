package org.infodancer.xmlrpc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import junit.framework.TestCase;

public class XMLRPCTest extends TestCase
{
	public void testXMLRPCCreateAndParseMethodCall() throws ParserConfigurationException, SAXException, IOException
	{
		String uri1 = "http://www.example.com/uri1";
		String uri2 = "http://www.example.com/uri2";
		String result = XMLRPC.createMethodCall("pingback.ping", uri1, uri2);
		XMLRPCRequest request = XMLRPC.parseMethodCall(new ByteArrayInputStream(result.getBytes()));
		assertEquals("pingback.ping", request.getMethodName());
		List<XMLRPCParam> params = request.getParams();
		assertEquals(uri1,params.get(0).getValue());
		assertEquals(uri2,params.get(1).getValue());
	}
	
	public void testXMLRPCParseMethodCall() throws ParserConfigurationException, SAXException, IOException
	{
		String uri1 = "http://www.example.com/uri1";
		String uri2 = "http://www.example.com/uri2";
		String method = "<?xml version=\"1.0\"?><methodCall><methodName>pingback.ping</methodName><params><param><value><string>" + uri1 + "</string></value></param><param><value><string>" + uri2 + "</string></value></param></params></methodCall>";
		XMLRPCRequest request = XMLRPC.parseMethodCall(new ByteArrayInputStream(method.getBytes()));
		assertEquals("pingback.ping", request.getMethodName());
		List<XMLRPCParam> params = request.getParams();
		assertEquals(uri1,params.get(0).getValue());
		assertEquals(uri2,params.get(1).getValue());
	}
}


