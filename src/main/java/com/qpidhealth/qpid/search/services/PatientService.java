package com.qpidhealth.qpid.search.services;

import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.ejb.LockType.READ;


/* Jamie Brandon - QPID coding challenge 11/28/17
 * Changes I made:
 * - the constructor for Patient takes ID and name as parameters, helps readability
 * - the addDocument method reduces the use of space
 * - The field documents holds the documents that match the most recent search
 * - The field allDocuments holds all the documents the patient has
 * 
 * Changes I would make after understanding front-end:
 * - I'm not exactly sure how the front end works, but it seems to show all documents
 *   the patient has, rather than just the ones that match the search.
 *   I thought that if I left the field as 'documents' (rather than relDocuments, my 
 *   first choice) everything would follow through. This doesn't seem to be the case.
 *   I tried to change things, but I think I need more JavaScript experience to get 
 *   it right.
 * - It's awfully ineffecient (space-wise) to create a new list of patients to return.
 *   When working with lot of patients, it would be better to keep a list of indices
 *   of the patients that have a document that matches the query. Then we would return
 *   loop through the list of indices, printing only the relevant documents for each
 *   patient. 
 */


@Path("/patients")
@Singleton
@Lock(READ)
public class PatientService {
    
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> searchPatients(@QueryParam("query") String query) {
        
        //initialize list of all patients
        List<Patient> patients = new ArrayList<Patient>();
        
        Patient p1 = new Patient(1000000L, "Mary TestPerson");
        p1.addDocument("Patient Note:::6/20/2010:::" + resource("Mary_1"));
        p1.addDocument("Patient Note:::6/20/2010:::" + resource("Mary_2"));
        patients.add(p1);
        
        Patient p2 = new Patient(1000001L, "Joe TestPerson");
        p2.addDocument("Clinical Note:::4/6/2010:::" + resource("Joe_1"));
        p2.addDocument("Summary:::7/2/2010:::" + resource("Joe_2"));
        patients.add(p2);
        
        Patient p3 = new Patient(1000002L, "Sam TestPerson");
        p3.addDocument("Patient Note:::8/3/2012:::" + resource("Sam_1"));
        patients.add(p3);

        //intialize results: a list of patients that match the query
        List<Patient> results = new ArrayList<Patient>();
        
        //traverse the list of patients
        for(Patient p:patients){
            boolean patientInList = false;
            
            //traverse each document in each patient
            for(String doc:p.getDocuments()){
                if(doc.toLowerCase().contains(query.toLowerCase())){
                    
                    //only add the documents that match the search
                    p.addRelDocument(doc);
                    
                    //only add the patient to the list if they aren't already in it. 
                    if(!patientInList){
                        results.add(p);
                    }
                    
                }
            }   
        }
        //now, when returning results, we loop through each patient in results and
        //print each document in their field documents
        //We need to change front-end to reflect this, unfortunately, I don't know how
        return results;
    }
    
    private static String resource(String fileName) {	
    	ClassLoader classLoader = PatientService.class.getClassLoader();
    	try {
    	    return IOUtils.toString(classLoader.getResourceAsStream("documents/"+fileName+".txt"));
    	} catch (IOException e) {
    	    e.printStackTrace();
    	    return "Failed to retrieve resource "+fileName;
    	}
    }
    
    @XmlRootElement
    public static class Patient {
        
        private Long id;    
        private String name;
        private List<String> documents; // id ::: date ::: contents
        //documents stores the documents that come up after a search
        private List<String> allDocuments; 
        
        public Patient(Long id, String name) {
            this.id = id;
            this.name = name;
            this.documents = new ArrayList<String>();
            this.allDocuments = new ArrayList<String>();
        }
        
        
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public List<String> getDocuments() {
            return allDocuments;
        }
        
        public void addDocument(String document) {
            this.allDocuments.add(document);
        }
        
        public void addRelDocument(String document) {
            this.documents.add(document);
        }
        
        public List<String> getRelDocuments() {
            return documents;
        }
        
        /* These methods are commented out because they are never used.
         * After talking to the team to make sure no one else depends on them,
         * consider deleting them.

        public void setDocuments(List<String> documents) {
            this.documents = documents;
        }
        
        
        public static Patient create(Long id, String name) {
            return new Patient(id, name);
        }
        
         */
        
    }    


}
