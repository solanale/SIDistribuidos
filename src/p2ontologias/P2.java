package p2ontologias;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import propias.Teclado;

import java.io.File;
import java.util.Set;

import javax.net.ssl.ManagerFactoryParameters;

public class P2 {
	private static final String RUTA_IRI = "http://sid.cps.unizar.es";
    public static void main(String[] args) {
    	
        try {
            IRI ontologyIRI = IRI.create(RUTA_IRI);
            File file = new File("Ontologia.owl");
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntology ont = manager.loadOntologyFromOntologyDocument(file);
            OWLDataFactory factory = manager.getOWLDataFactory();
            OWLObjectRenderer renderer = new DLSyntaxObjectRenderer(); 
            int exit = 0;
            do {
            	System.out.println("****************************");
            	System.out.println("* 1.- Intro instance perro *");
            	System.out.println("* 2.- Intro instance gato  *");
            	System.out.println("* 3.- Consultas            *");
            	System.out.println("* 0.- Salir                *");
            	System.out.println("****************************");
            	exit = Teclado.leerEntero("Opción:");
            	switch (exit) {
	            	case 0: System.out.println("Gracias por su visita.");
	            		break;
	            	case 1: introPerro(factory, manager, ont);
	            		break;
	            	case 2: introGato(factory, manager, ont);
	            		break;
	            	case 3: consultas(factory, ont);
            			break;
            	}
            } while (exit != 0);
            
           
            //reasoner.getSubClasses("DueñoDeMascota", false);
            
            manager.saveOntology(ont, IRI.create(file.toURI()));
            System.out.println("Ontología salvada correctamente");
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
	private static void consultas(OWLDataFactory factory, OWLOntology ont) {
		OWLClass subAnc = factory.getOWLClass(IRI.create(RUTA_IRI + "/Anciano"));
        OWLClass subDM = factory.getOWLClass(IRI.create(RUTA_IRI + "/DueñoDeMascota"));
        OWLClass instMujer = factory.getOWLClass(IRI.create(RUTA_IRI + "/Mujer"));
        OWLClass instAf = factory.getOWLClass(IRI.create(RUTA_IRI + "/AficionadoDeGatos"));
        OWLClass instancesGato = factory.getOWLClass(IRI.create(RUTA_IRI + "/Gato"));
        OWLClass instancesPerro = factory.getOWLClass(IRI.create(RUTA_IRI + "/Perro"));
        OWLReasoner reasoner = new Reasoner.ReasonerFactory().createReasoner(ont);
        System.out.println(reasoner.isConsistent());
        reasoner.precomputeInferences();
        System.out.println("Consultas:\n");
        // Subclase DueñoDeMascotas
        NodeSet<OWLClass> subClses = reasoner.getSubClasses(subDM, true);
        Set<OWLClass> clses = subClses.getFlattened();
        boolean isSub = false;
        for(OWLClass cls : clses) {
            if (cls.toString().contains("DueñoDeGatos")) {
            	isSub = true;
            }
        }
        
        if (isSub) {
			System.out.println("DueñoDeGatos si es subclase de DueñoDeMascota");
		} else {
			System.out.println("DueñoDeGatos no es subclase de DueñoDeMascota");
		}
        
        // Subclase anciano
        NodeSet<OWLClass> subClses1 = reasoner.getSubClasses(subAnc, true);
        Set<OWLClass> clses1 = subClses1.getFlattened();
        isSub = false;
        for(OWLClass cls : clses1) {
            if (cls.toString().contains("ViejecitaConGatos")) {
            	isSub = true;
            }
        }
        
        if (isSub) {
			System.out.println("ViejecitaConGatos si es subclase de Anciano");
		} else {
			System.out.println("ViejecitaConGatos no es subclase de Anciano");
		}
        
        // Get instaces mujer
        NodeSet<OWLNamedIndividual> individualsNodeSet1 = reasoner.getInstances(instMujer, true);
        Set<OWLNamedIndividual> mujeres = individualsNodeSet1.getFlattened();
        boolean isInstance = false;
        for(OWLNamedIndividual cls : mujeres) {
        	if (cls.toString().contains("CrazyCatLady")) {
        		isInstance = true;
            }
        }
        if (isInstance) {
			System.out.println("CrazyCatLady si es instancia de Mujer");
		} else {
			System.out.println("CrazyCatLady no es instancia de Mujer");
		}
        
        // Get instaces AficionadoDeGatos
        NodeSet<OWLNamedIndividual> individualsNodeSet2 = reasoner.getInstances(instAf, true);
        Set<OWLNamedIndividual> aficionados = individualsNodeSet2.getFlattened();
        isInstance = false;
        for(OWLNamedIndividual cls : aficionados) {
        	if (cls.toString().contains("CrazyCatLady")) {
        		isInstance = true;
            }
        }
        if (isInstance) {
			System.out.println("CrazyCatLady si es instancia de AficionadoDeGatos");
		} else {
			System.out.println("CrazyCatLady no es instancia de AficionadoDeGatos");
		}
        
        // Get instaces gato
        NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(instancesGato, true);
        Set<OWLNamedIndividual> ooo = individualsNodeSet.getFlattened();
        System.out.println("Instaces Gato: ");
        isInstance = false;
        for(OWLNamedIndividual cls : ooo) {
        	if (cls.toString().contains("Tom")) {
        		isInstance = true;
            }
            System.out.println("    " + cls);
        }
        if (isInstance) {
			System.out.println("Tom si es instancia de Gato");
		} else {
			System.out.println("Tom no es instancia de Gato");
		}
        // Get instaces gato
        NodeSet<OWLNamedIndividual> individualsPerro= reasoner.getInstances(instancesPerro, true);
        Set<OWLNamedIndividual> per = individualsPerro.getFlattened();
        System.out.println("Instances Perro: ");
        for(OWLNamedIndividual cls : per) {
            System.out.println("    " + cls);
        }
        System.out.println("\n\n");
	}
	
	
	private static void introPerro(OWLDataFactory factory, OWLOntologyManager manager, OWLOntology ont) {
		String nombrePerro = Teclado.leerCadena("Introducir nombre de perro:");
		OWLClass perro = factory.getOWLClass(IRI.create(RUTA_IRI + "/Perro"));
		OWLNamedIndividual nombre =  factory.getOWLNamedIndividual(IRI.create(RUTA_IRI + "/" + nombrePerro));
		OWLClassAssertionAxiom assertionAxiom =	factory.getOWLClassAssertionAxiom(perro, nombre);
		manager.addAxiom(ont, assertionAxiom);

        try {
            manager.saveOntology(ont);
        } catch (OWLOntologyStorageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	private static void introGato(OWLDataFactory factory, OWLOntologyManager manager, OWLOntology ont) {
		String nombreGato = Teclado.leerCadena("Introducir nombre de gato:");
		OWLClass perro = factory.getOWLClass(IRI.create(RUTA_IRI + "/Gato"));
		OWLNamedIndividual nombre =  factory.getOWLNamedIndividual(IRI.create(RUTA_IRI + "/" + nombreGato));
		OWLClassAssertionAxiom assertionAxiom =	factory.getOWLClassAssertionAxiom(perro, nombre);
		manager.addAxiom(ont, assertionAxiom);

        try {
            manager.saveOntology(ont);
        } catch (OWLOntologyStorageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
