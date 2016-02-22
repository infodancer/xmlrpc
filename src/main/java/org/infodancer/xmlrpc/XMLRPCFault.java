package org.infodancer.xmlrpc;

public class XMLRPCFault
{
	int faultCode; 
	String faultString;
	
	public int getFaultCode()
	{
		return faultCode;
	}
	
	public void setFaultCode(int faultCode)
	{
		this.faultCode = faultCode;
	}
	
	public String getFaultString()
	{
		return faultString;
	}
	
	public void setFaultString(String faultString)
	{
		this.faultString = faultString;
	}
}
