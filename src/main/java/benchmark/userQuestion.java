package benchmark;

import java.util.ArrayList;
import java.util.Scanner;

import annotation.AnnotationOrch;
import annotation.relationAnnotationToken;
import com.google.gson.Gson;
import init.initializer;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import phrase.phrase;
import phrase.phraseOrch;
import phraseMerger.phraseMergerOrch;
import queryConstructor.SparqlSelector;
import question.quesOrch;
import question.questionAnnotation;
import utils.qaldQuery;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class userQuestion {

    public static void main(String args[]) {
        answerBenchmark("qald_1.json", "qald_1");
        answerBenchmark("qald_2.json", "qald_2");
        answerBenchmark("qald_3.json", "qald_3");
        answerBenchmark("qald_4.json", "qald_4");
        answerBenchmark("qald_5.json", "qald_5");
        answerBenchmark("qald_6.json", "qald_6");
        answerBenchmark("qald_7.json", "qald_7");
        answerBenchmark("qald_8.json", "qald_8");
        answerBenchmark("qald_9.json", "qald_9");
        answerBenchmark("lcquad.json", "lcquad");
    }

    private static void answerBenchmark(String filePath, String benchmark) {
        ArrayList<Question> questions = new ArrayList<>();
        /*
         Assume that the input is a json file as follow
         {
         "questions":[
         {
         "question":"Who .....",
         "generated_sparql":""
         }, ....
         ]
         }
         */

        initializer init = new initializer(); //boiler plate initializer. 

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(filePath));
            //Object obj = parser.parse(new FileReader("LCQuad_All_answer_output.json"));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonQus = (JSONArray) jsonObject.get("questions");
            JSONObject questionResponse = null;

            for (Object c : jsonQus) {
                JSONObject questionObj = (JSONObject) c;
                String questionString = (String) questionObj.get("question");
                String generated_query = generate_A_sparql(questionString);
                System.out.println("The generated sparql is :" + generated_query);
                questions.add(new Question(questionString, generated_query));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         Assume that the output is a json file as follow
         {
         "questions":[
         {
         "question":"Who .....",
         "generated_sparql":"SELECT ...."
         }, ....
         ]
         }
         */
        //Generated output files
        Gson gson = new Gson();
        String jsonArray = gson.toJson(questions);

        try (PrintStream out = new PrintStream(new FileOutputStream(benchmark+"_answers.json"))) {
            out.print(jsonArray);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private static String generate_A_sparql(String question) {
        try{
            System.out.println(question);
        //ArrayList<String> askNow_answer = null;	//This will store the answer generated after the question is executed.
        quesOrch question_orch = new quesOrch();
        questionAnnotation ques_annotation = question_orch.questionOrchestrator(question);
        phraseOrch phrase = new phraseOrch();
        ArrayList<phrase> phraseList = phrase.startPhraseMerger(ques_annotation);
        phraseMergerOrch phraseMergerOrchestrator = new phraseMergerOrch();
        AnnotationOrch annotation = new AnnotationOrch();
        ArrayList<ArrayList<relationAnnotationToken>> relAnnotation = annotation.startAnnotationOrch(phraseList, ques_annotation);
        ArrayList<ArrayList<phrase>> conceptList = phraseMergerOrchestrator.startPhraseMergerOrch(ques_annotation, phraseList);
        ques_annotation.setPhraseList(phraseList);
        String askNow_sparql = SparqlSelector.sparqlSelector(ques_annotation);
        

           if (askNow_sparql.equals("") || askNow_sparql==null){
                return "Cannot generate Query: Empty";
        }
        //else{
        //        System.out.println("the sparql returend was null. The system can't handel the question right now.");
        //}	
        return askNow_sparql;
        }catch(Exception e)
        {
            e.printStackTrace();
            return "Cannot generate Query: Exception";
        }
    }

}
