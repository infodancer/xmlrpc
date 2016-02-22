/**
 * Copyright by matthew on Feb 21, 2006 as part of the infodancer xmlrpc project.
 */
package org.infodancer.xmlrpc;

/**
 * @author matthew
 *
 */
public class XMLRPCParam
{
	Object value;
	String originalValue;
		
	/** 
	 * Provides the generic Object version of this parameter.
	 * 
	 * @return
	 */
	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public void setOriginalValue(String value)
	{
		this.originalValue = value;
	}
	
	/**
	 * Provides the string version of the param, exactly as it appears in the XML document.
	 * @return
	 */
	public String getOriginalValue()
	{
		return originalValue;
	}
}
