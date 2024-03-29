package concept.model;

import CAEX215.CAEXObject;

public interface IAMLConceptModel {

//	public CAEXObject getObj ();
//	
//	public void setObj (CAEXObject obj);
//	
//	public IAMLConceptConfig getConfig ();
//	
//	public void setConfig (IAMLConceptConfig config);
	
	/**
	 * instantiate the AML concept model from a CAEX object which has the config parameters as CAEX attributes
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void fromConfiguredCAEXObj (CAEXObject obj);
	
	/**
	 * serialize the AML concept model to a caex object that has the config parameters as its attributes explicitly
	 * @return the caex object representing the AML concept model
	 */
	public CAEXObject toConfiguredCAEXObject();
	
	
	/**
	 * instantiate the AML concept model from a CAEX object which does not have the config parameters as CAEX attributes
	 * @param obj
	 */
	public void fromUnConfiguredCAEXObj (CAEXObject obj);

	/**
	 * instantiate the AML concept model from a CAEX object
	 * - if config parameters can be found, call fromConfiguredCAEXObj
	 * - otherwise, call fromUnConfiguredCAEXObj
	 * @param obj
	 */
	public void fromCAEXObject (CAEXObject obj);
}
