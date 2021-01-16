package utils;

public class word2vec {

	 public static float sendToVec(String word1, String word2) throws Exception
	    {
		 /*
		  * Takes two string as input, hits the internal microservice to retrive how similar they are using 
		  * word2vec. 
		  * */
		 //TODO: Transfer the url to config.property file. 
	    	String urlParameters = "word1=" + word1 + "&word2=" + word2;
	    	//String url = "http://34.122.114.106:8080/similarity";
                String url = "http://0.0.0.0:8080/similarity";
	    	sendRequest http = new sendRequest();
	    	String response_post_id = http.sendPostRequest(url, urlParameters);
	    	return Float.parseFloat(response_post_id);
	    }
}
