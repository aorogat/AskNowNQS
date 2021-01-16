/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmark;

import java.util.ArrayList;

/**
 *
 * @author aorogat
 */
public class Question {
    String questionString;
    String generatedSparql;

    public Question(String questionString, String generatedSparql) {
        this.questionString = questionString;
        this.generatedSparql = generatedSparql;
    }
    
    
}
