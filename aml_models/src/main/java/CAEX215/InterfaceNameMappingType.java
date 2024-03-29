/**
 */
package CAEX215;

import visitor.CAEXBasicObjectVisitorEx;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Interface Name Mapping Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link CAEX215.InterfaceNameMappingType#getRoleInterfaceName <em>Role Interface Name</em>}</li>
 *   <li>{@link CAEX215.InterfaceNameMappingType#getSystemUnitInterfaceName <em>System Unit Interface Name</em>}</li>
 * </ul>
 *
 * @see CAEX215.CAEX215Package#getInterfaceNameMappingType()
 * @model extendedMetaData="name='InterfaceNameMapping_._type' kind='elementOnly'"
 * @generated
 */
public interface InterfaceNameMappingType extends CAEXBasicObject {
	/**
	 * Returns the value of the '<em><b>Role Interface Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role Interface Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Role Interface Name</em>' attribute.
	 * @see #setRoleInterfaceName(String)
	 * @see CAEX215.CAEX215Package#getInterfaceNameMappingType_RoleInterfaceName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='RoleInterfaceName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getRoleInterfaceName();

	/**
	 * Sets the value of the '{@link CAEX215.InterfaceNameMappingType#getRoleInterfaceName <em>Role Interface Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Role Interface Name</em>' attribute.
	 * @see #getRoleInterfaceName()
	 * @generated
	 */
	void setRoleInterfaceName(String value);

	/**
	 * Returns the value of the '<em><b>System Unit Interface Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>System Unit Interface Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>System Unit Interface Name</em>' attribute.
	 * @see #setSystemUnitInterfaceName(String)
	 * @see CAEX215.CAEX215Package#getInterfaceNameMappingType_SystemUnitInterfaceName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='SystemUnitInterfaceName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getSystemUnitInterfaceName();

	/**
	 * Sets the value of the '{@link CAEX215.InterfaceNameMappingType#getSystemUnitInterfaceName <em>System Unit Interface Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>System Unit Interface Name</em>' attribute.
	 * @see #getSystemUnitInterfaceName()
	 * @generated
	 */
	void setSystemUnitInterfaceName(String value);
	
    /**
     * @param visitor
     *        visitor
     * @param <T>
     *        visitor return type
     * @return visitor return value
     */
    <T> T accept(CAEXBasicObjectVisitorEx<T> visitor);

} // InterfaceNameMappingType
