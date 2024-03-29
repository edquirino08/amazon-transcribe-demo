package com.transcribe.aws.batch;

import org.json.JSONException;
import org.json.JSONObject;

public class Teste {

	public static void main(String[] args) {
		
		try {
            String str = "{\"jobName\":\"teste13\",\"accountId\":\"916841971192\",\"results\":{\"transcripts\":[{\"transcript\":\"o tempo todo enfermeiro a minha vida por\"}],\"items\":[{\"start_time\":\"0.58\",\"end_time\":\"0.75\",\"alternatives\":[{\"confidence\":\"1.0\",\"content\":\"o\"}],\"type\":\"pronunciation\"},{\"start_time\":\"0.75\",\"end_time\":\"1.42\",\"alternatives\":[{\"confidence\":\"1.0\",\"content\":\"tempo\"}],\"type\":\"pronunciation\"},{\"start_time\":\"1.42\",\"end_time\":\"1.71\",\"alternatives\":[{\"confidence\":\"0.9998\",\"content\":\"todo\"}],\"type\":\"pronunciation\"},{\"start_time\":\"1.71\",\"end_time\":\"2.39\",\"alternatives\":[{\"confidence\":\"0.5655\",\"content\":\"enfermeiro\"}],\"type\":\"pronunciation\"},{\"start_time\":\"2.39\",\"end_time\":\"2.45\",\"alternatives\":[{\"confidence\":\"0.9838\",\"content\":\"a\"}],\"type\":\"pronunciation\"},{\"start_time\":\"2.45\",\"end_time\":\"2.7\",\"alternatives\":[{\"confidence\":\"1.0\",\"content\":\"minha\"}],\"type\":\"pronunciation\"},{\"start_time\":\"2.7\",\"end_time\":\"3.07\",\"alternatives\":[{\"confidence\":\"1.0\",\"content\":\"vida\"}],\"type\":\"pronunciation\"},{\"start_time\":\"3.07\",\"end_time\":\"3.52\",\"alternatives\":[{\"confidence\":\"0.9327\",\"content\":\"por\"}],\"type\":\"pronunciation\"}]},\"status\":\"COMPLETED\"}";
            JSONObject jsonObject = new JSONObject(str);

    		String results = jsonObject.optJSONObject("results").getJSONArray("transcripts").getJSONObject(0).get("transcript").toString();

    	
            System.out.println(results);
           
        } catch (JSONException err) {
            System.out.println("Exception : "+err.toString());
        }

	}

}
