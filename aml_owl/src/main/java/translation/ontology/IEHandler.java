package translation.ontology;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import CAEX215.AttributeType;
import CAEX215.CAEXObject;
import CAEX215.InterfaceClassType;
import CAEX215.InternalElementType;
import CAEX215.RoleRequirementsType;
import CAEX215.SupportedRoleClassType;
import constants.AMLObjectPropertyIRIs;
import constants.Consts;

/**
 * Transformation Handler for AML Internal Element
 * @author hua
 *
 */
public class IEHandler extends AMLEntityHandler {

	private OWLDataFactory factory;
	private AttributeHandler attribute_handler;
	private EIHandler ic_handler;
	
	public IEHandler(OWLOntologyManager manager, String ns) {
		// TODO Auto-generated constructor stub
		this.factory = manager.getOWLDataFactory();
		this.ns = ns;
		this.manager = manager;
		this.attribute_handler = new AttributeHandler(manager, ns);
		this.ic_handler = new EIHandler(manager, ns);
	}
	
	@Override
	public OWLEntity cvt2Owl(CAEXObject obj, OWLOntology ont) {
		// TODO Auto-generated method stub				
		
		// For AML, ID is important
		// For Concept Learning, ID confuses the learner since then each IE is different
//		OWLNamedIndividual ind_ie = factory.getOWLNamedIndividual(IRI.create(ns + ie.getName() + "_" + ie.getID()));		
		// extend simple name to the full path to cope with ILP config
//		OWLNamedIndividual ind_ie = factory.getOWLNamedIndividual(IRI.create(ns + "[ie:" + obj.getName() + ":" + obj.getID() + "]"));						
//		OWLNamedIndividual ind_ie = factory.getOWLNamedIndividual(IRI.create(ns + ie.getName()));
		
//		InternalElementType ie = (InternalElementType) obj;
//		OWLNamedIndividual ind_ie = factory.getOWLNamedIndividual(IRI.create(ns + "ie_" + obj.getName() + "_" + obj.getID()));
		OWLNamedIndividual ind_ie = factory.getOWLNamedIndividual(createIRI(obj));
				
		return ind_ie;
	}

	@Override
	public void add2Owl(CAEXObject obj, OWLEntity entity, OWLOntology ont) {
		// TODO Auto-generated method stub		
		
		// create the OWL individual for the IE
		InternalElementType ie = (InternalElementType) obj;
		OWLNamedIndividual ind_ie = (OWLNamedIndividual) cvt2Owl(obj, ont);		
		OWLDeclarationAxiom ax_ie = factory.getOWLDeclarationAxiom(ind_ie);
		manager.applyChange(new AddAxiom(ont, ax_ie));	
		
		// handle all attributes of the IE
		for(AttributeType attr : ie.getAttribute()) {
			attribute_handler.add2Owl(attr, ind_ie, ont);
		}
		
		// handle all interfaces of the IE
		for(InterfaceClassType ic : ie.getExternalInterface()) {
			ic_handler.add2Owl(ic, ind_ie, ont);
		}
		
		// handle RoleRequirement of the IE: class assertion axioms
		RoleRequirementsType rr = ie.getRoleRequirements();
		OWLClass role = null;
		if(rr != null) {
			String role_path = rr.getRefBaseRoleClassPath();
			String[] tokens = role_path.split("/");
			String role_name = tokens[tokens.length-1];					
//			if(factory.getOWLClass(IRI.create(ns + role_name)) != null)
//				role = factory.getOWLClass(IRI.create(ns + role_name));
//			else if(factory.getOWLClass(IRI.create(Consts.aml_pref + role_name)) != null) {
//				role = factory.getOWLClass(IRI.create(Consts.aml_pref + role_name));
//			}else {
//				System.out.println("Can not find the OWL class definition for the role class: " + role_name);
//			}
			
			for(OWLClass owlClass : ont.getClassesInSignature()) {
				if(owlClass.getIRI().toString().equals(Consts.aml_pref + role_name)) {
					role = owlClass;
				}
			}		
		} 
		

		// For all attributes in the AML role requirement:
		// a data property is created to constraint the Class membership of this IE to the Role Class 
		Set<OWLClassExpression> constraints = new HashSet<OWLClassExpression>();		
		if(rr != null) {
			for(AttributeType attr : rr.getAttribute()) {
	
				/**
				 * the easiest variant: bring the role requirement attributes to the IE: data property of the IE  
				 */
	//			this.attribute_handler.add2Owl(attr, ind_ie, ont);
				/**
				 * this would add the attribute constraints to the original role class...
				 */
	//			attribute_handler.add2Owl(attr, role, ont);
				
				/**
				 * We do the variant that: IE will be an instance of the RoleClass and attribute constraints
				 */
	//			OWLDataProperty hasAttribute = (OWLDataProperty) attribute_handler.cvt2Owl(attr);
	//			String datatype = attr.getAttributeDataType();		
	//			OWLDatatype owlDatatype = this.attribute_handler.getOWLDatatype(datatype);
	//			OWLDataPropertyRangeAxiom rangeAxiom = factory.getOWLDataPropertyRangeAxiom(
	//					hasAttribute, owlDatatype);
	//			this.manager.applyChange(new AddAxiom(ont, rangeAxiom));
	//			
	//			String value = this.attribute_handler.getAttributeValue(attr);		
	//			
	//			if(value != null) {
	//				// class of all individuals which has the attribute with given value								
	//				OWLLiteral literal = factory.getOWLLiteral(value, owlDatatype);
	//				constraints.add(factory.getOWLDataHasValue(hasAttribute, literal));
	//			}else{
	//				// if value is not given, ignore
	////				constraint = factory.getOWLDataSomeValuesFrom(hasAttribute, factory.getTopDatatype());
	//			}
				constraints.addAll(this.attribute_handler.getConstraintExpressions(attr, ont, attr.getName()));
			}	
		}
//		OWLClassAssertionAxiom ax_assertion = factory.getOWLClassAssertionAxiom(role, ind_ie);
		
		OWLClassExpression intersectedConstraints = null;		
		if(constraints.size() != 0)
			intersectedConstraints = factory.getOWLObjectIntersectionOf(constraints);
		OWLClassAssertionAxiom ax_assertion = null;
		if(role != null && intersectedConstraints != null)
		{
			OWLClassExpression constraintedRole = factory.getOWLObjectIntersectionOf(role, intersectedConstraints);
			ax_assertion = factory.getOWLClassAssertionAxiom(constraintedRole, ind_ie);			
		}else if(role != null)
		{
			ax_assertion = factory.getOWLClassAssertionAxiom(role, ind_ie);
		}else if(intersectedConstraints != null)
			ax_assertion = factory.getOWLClassAssertionAxiom(intersectedConstraints, ind_ie);
			
		if(ax_assertion != null)
			manager.applyChange(new AddAxiom(ont, ax_assertion));
		
		// handle the SupportedRoleClass of the iE: class assertion axiom
		Set<OWLClass> roles = new HashSet<OWLClass>();
		for(SupportedRoleClassType src : ie.getSupportedRoleClass()) {
			String role_path = src.getRefRoleClassPath();
			String[] tokens = role_path.split("/");
			String role_name = tokens[tokens.length-1];
			
			for(OWLClass owlClass : ont.getClassesInSignature()) {
				if(owlClass.getIRI().toString().equals(Consts.aml_pref + role_name)) {
					roles.add(owlClass);
				}
			}
		}
		if(!roles.isEmpty())
			manager.applyChange(new AddAxiom(ont, factory.getOWLClassAssertionAxiom(factory.getOWLObjectIntersectionOf(roles), ind_ie)));
			
		// add this IE to its parent object
		OWLObjectPropertyExpression hasIE = factory.getOWLObjectProperty(AMLObjectPropertyIRIs.HAS_IE);
		if(entity != null) {
			OWLObjectPropertyAssertionAxiom ax_hasIE = factory.getOWLObjectPropertyAssertionAxiom(hasIE, (OWLIndividual) entity, ind_ie);
			manager.applyChange(new AddAxiom(ont, ax_hasIE));	
		}		
		
		// add annotation semantic to the AML Structure
//		OWLDataPropertyExpression hasAMLSemantic = factory.getOWLDataProperty(AMLDataPropertyIRIs.HAS_AML_SEMANTIC);
//		OWLDataPropertyAssertionAxiom ax_hasAMLSemantic = factory.getOWLDataPropertyAssertionAxiom(hasAMLSemantic, ind_ie, "InternalElement");
//		manager.applyChange(new AddAxiom(ont, ax_hasAMLSemantic));
		
		//TODO: change to AMLClassIRI
		OWLAnnotation hasSemantic = factory.getOWLAnnotation(factory.getRDFSComment(), factory.getOWLLiteral("InternalElement")); 
		OWLAnnotationAssertionAxiom ax_hasSemantic = factory.getOWLAnnotationAssertionAxiom(ind_ie.getIRI(), hasSemantic);
		manager.applyChange(new AddAxiom(ont, ax_hasSemantic));
		
//		OWLClassAssertionAxiom isIE = factory.getOWLClassAssertionAxiom(factory.getOWLClass(AMLClassIRIs.INTERNAL_ELEMENT), ind_ie);
//		manager.applyChange(new AddAxiom(ont, isIE));
		
		// handle sub elements
		for(InternalElementType subIE : ie.getInternalElement()) {
			add2Owl(subIE, ind_ie, ont);
		}		

	}

	@Override
	public IRI createIRI(CAEXObject obj) {
		InternalElementType ie = (InternalElementType) obj;
		return IRI.create(ns + "ie_" + ie.getName() + "_" + ie.getID());
	}

	public static String deserializeIRI(IRI iri) {
		
		String post = iri.getShortForm();
		
		String[] tokens = post.split("_");
		
		// the id of the IE shall be always the last element in tokens
		// if the id is standard: parts of it shall be concatenated by "-" 
		if(tokens != null && tokens.length>0)
			return tokens[tokens.length-1];
		else
			return null;
	}

}
