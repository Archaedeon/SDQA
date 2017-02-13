package ai.wide.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import ai.wide.utils.HTMLHandler;

@Path("elastic")
public class ElasticResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        

        String r = HTMLHandler.restCall("POST", "http://api.wide-eyes.it/SearchByAttributes", 
                "{\"page\":0,\"attributes\":{\"included\":[],\"excluded\":[]},\"filters\":{},\"ranges\":{\"price\":{\"min\":0,\"max\":1000},\"discount\":{\"min\":0,\"max\":100}},\"maxNumResults\":50}", 
                "application/json", null, "28ecd9090551dbe8ca7614ada843a15d7fc9f751");
        System.out.println(r);
        return Response.status(200).entity(r).build();
    
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postIt(String req) {
        
        System.out.println(req);
        JSONObject json = new JSONObject(req);
        
        JSONObject response = new JSONObject();
        
        response.put("id", json.get("id"));
        response.put("timestamp", json.get("timestamp"));
        response.put("lang", json.get("lang"));
        
        JSONObject result = new JSONObject();
        result.put("source", json.getJSONObject("result").get("source"));
        result.put("resolvedQuery", json.getJSONObject("result").get("resolvedQuery"));
        result.put("speech", json.getJSONObject("result").get("speech"));
        result.put("action", json.getJSONObject("result").get("action"));
        result.put("actionIncomplete", json.getJSONObject("result").get("actionIncomplete"));
        result.put("parameters", json.getJSONObject("result").getJSONObject("parameters"));
        
        result.put("contexts", json.getJSONObject("result").getJSONArray("contexts"));
        result.put("metadata", json.getJSONObject("result").getJSONObject("metadata"));
        
        JSONObject fulfillment = new JSONObject();
        fulfillment.put("speech", json.getJSONObject("result").getJSONObject("fulfillment").get("speech"));
        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("type", 0);
        message.put("speech", "Hola soy Fashbot");
        messages.put(message);
        fulfillment.put("messages", messages);
        result.put("fulfillment", fulfillment);
        result.put("score", json.getJSONObject("result").get("score"));
        response.put("result", result);
        
        result.put("status", json.getJSONObject("status"));
        result.put("sessionId", json.get("sessionId"));
        result.put("originalRequest", json.getJSONObject("originalRequest"));
        
        
        return Response.status(200).entity(response.toString()).build();
    }
}

/* {
   "id":"6a673dd2-e33f-451b-a856-c14d5f9ca387",
   "timestamp":"2017-02-13T13:41:43.998Z",
   "lang":"en",
   "result":{
      "source":"agent",
      "resolvedQuery":"I need a red jacket",
      "speech":"",
      "action":"",
      "actionIncomplete":false,
      "parameters":{
         "date-period":""
      },
      "contexts":[
         {
            "name":"generic",
            "parameters":{
               "date-period.original":"",
               "facebook_sender_id":"1416930015047968",
               "date-period":""
            },
            "lifespan":4
         }
      ],
      "metadata":{
         "intentId":"0976239c-2801-4cd3-876f-2b974f415016",
         "webhookUsed":"true",
         "webhookForSlotFillingUsed":"false",
         "intentName":"jacketcoat"
      },
      "fulfillment":{
         "speech":"Look if this is interesting for you",
         "messages":[
            {
               "type":0,
               "speech":"Your words are orders for me...my lord"
            },
            {
               "title":"Jacket 389353",
               "imageUrl":"http://imagenpng.com/wp-content/uploads/img/jacket_PNG8031.png",
               "buttons":[
                  {
                     "text":"Buy",
                     "postback":"http://www.yoox.com"
                  }
               ],
               "type":1
            }
         ]
      },
      "score":0.87
   },
   "status":{
      "code":200,
      "errorType":"success"
   },
   "sessionId":"2e02589c-301c-4740-a522-e0fe7ef9de60",
   "originalRequest":{
      "source":"facebook",
      "data":{
         "sender":{
            "id":"1416930015047968"
         },
         "recipient":{
            "id":"139458133235368"
         },
         "message":{
            "mid":"mid.1486993303560:95afcf7b94",
            "text":"I need a red jacket",
            "seq":1602
         },
         "timestamp":1.48699330356E12
      }
   }
}
 */


/*
 * curl -X POST -H "apikey: 28ecd9090551dbe8ca7614ada843a15d7fc9f751" 
 * -H "Content-Type: application/json" 
 * -d '{"page":0,"attributes":{"included":[],"excluded":[]},"filters":{},
 * "ranges":{"price":{"min":0,"max":1000},"discount":{"min":0,"max":100}},"maxNumResults":50}' 
 * http://api.wide-eyes.it/SearchByAttributes
 */