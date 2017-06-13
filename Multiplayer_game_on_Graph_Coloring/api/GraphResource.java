package api;

import game.*;
import org.json.* ;
import org.restlet.ext.jackson.*;
import org.restlet.ext.json.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

import java.io.IOException;

public class GraphResource extends ServerResource {

    GraphServer gserv = GraphServer.getInstance() ;
    /**
    * Server returns the state of the Graph Game
    **/
    @Get
    public Representation get_request() {

        JSONObject gamesJSON = gserv.getGamesJson();
        // TODO Get games - not moves
        
        System.out.println("games: "+ gamesJSON.toString());
        return new JsonRepresentation(gamesJSON) ;
    }

    //  @Get
    // public Representation get_request() throws JSONException {
    //    JSONObject mail = new JSONObject() ;
    //    mail.put( "status", "Received" ) ;
    //    mail.put( "subject", "RE: Message to Self" ) ;
    //    mail.put( "content", "Hello There!" ) ;
    //    return new JsonRepresentation ( mail ) ;
    // }


    /**
     *
     * @param rep action:string,
     * @return
     **/
    @Post
    public Representation post_request(JsonRepresentation rep) throws IOException {

    	JSONObject command = rep.getJsonObject();
        JSONObject result = gserv.parseCommand(command.toString());

        return new JsonRepresentation(result);

    }
}

