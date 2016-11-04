package tokenAnnotation;

import java.util.ArrayList;

import nlp.nlp;

public class tokenAnnotation {

	/*
	 * Class which actually does the annotation and also all other services 
	 * 
	 * */
	public ArrayList<token> getWordTokenArrayList(String question){
		//returns array list of annotated word tokens object. 
		nlp nlp_pipeline = new nlp();
		//TODO: OPtimize the below code
		ArrayList<String> tokens = nlp_pipeline.getNERTags(nlp_pipeline.getAnnotatedToken(question));
		ArrayList<String> posTags = nlp_pipeline.getPosTags(nlp_pipeline.getAnnotatedToken(question));
		ArrayList<String> NERTags = nlp_pipeline.getTokens(nlp_pipeline.getAnnotatedToken(question));
		ArrayList<String> lemmas = nlp_pipeline.getLemmas(nlp_pipeline.getAnnotatedToken(question));
		//Array List of annotated word token  
		ArrayList<token> tokenlist = new ArrayList();
		for (int i=0 ; i<tokens.size();i++){
			token word = new token(i, tokens.get(i), posTags.get(i), NERTags.get(i), lemmas.get(i));
			tokenlist.add(word);
		}
		return tokenlist;
	}
	
}
