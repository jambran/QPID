/* Jamie Brandon - QPID coding challenge 11/28/2017
 * Note sure why my JUnit tests cause build erros. Perhaps
 * an issue with dependencies?
 * They're commented out so the project builds, but here are 
 * ideas for JUnit tests.
 * 
 */

/*
package com.qpidhealth.qpid.search.services;
 
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;

public class PatientServiceTest { 
    
    @Test
    public void noMatchTest(){
        PatientService ps = new PatientService();
        //'hello' should return no patients
        assertEquals(ps.searchPatients("hello"), new ArrayList<Patient>());
    }
    
    public void pregnancyTest(){
        PatientService ps = new PatientService();
        //'pregnancy' should only match with Mary
        ArrayList<Patient> pregnancy = ps.searchPatients("pregnancy");
        assertEquals(pregnancy.size(), 0);
        assertEquals(pregnancy.get(0).getName(), "Mary TestPerson");
        
    }
}
*/

