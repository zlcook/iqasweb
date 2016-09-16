package com.noumenon.Integration;

import com.hp.hpl.jena.query.ResultSet;

public interface AddDeleteModifyQueryInterface {
	public void addInstance(String yourClass, String yourInstance);
	public void deleteInstance(String yourClass, String yourInstance);
	public void deleteProperty(String yourInstance, String yourProperty);
	public void ModifyInstance(String yourProperty, String yourInstance,
			String propertyLabel);
	public ResultSet checkInstance(String yourClass);
	public void checkProperty(String yourClass);
}
