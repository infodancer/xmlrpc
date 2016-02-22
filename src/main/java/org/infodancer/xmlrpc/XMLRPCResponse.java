/**
 * Copyright by matthew on Feb 21, 2006 as part of the infodancer xmlrpc project.
 */
package org.infodancer.xmlrpc;

import java.util.List;

/**
 * @author matthew
 *
 */
public class XMLRPCResponse
{
	List<XMLRPCParam> params;

	public List<XMLRPCParam> getParams()
	{
		return params;
	}

	public void setParams(List<XMLRPCParam> params)
	{
		this.params = params;
	}
}
