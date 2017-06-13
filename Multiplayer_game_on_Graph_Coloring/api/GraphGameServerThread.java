
package api ;

import java.net.*;
import java.io.*;

import org.restlet.*;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.json.* ;

import game.*;


public class GraphGameServerThread extends Thread {
    private Socket socket = null;

    BufferedReader in;
    PrintWriter out;
    IGraphGameServerDelegate delegate;

    public GraphGameServerThread(Socket socket){
        super("GraphGameServerThread");
        this.socket = socket;
        System.out.println("Starting Thread server ");
        try 
        {
          out = new PrintWriter(socket.getOutputStream(), true);
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          
      } catch (IOException e){
        System.err.println(e);
    }

}

public void setDelegate(IGraphGameServerDelegate d){
    delegate = d;
    // return current state of board
    //JSONObject moves = GraphServer.getInstance().parseCommand("{\"action\":\"getMoves\"}");
    //d.notifyPlayers(moves.toString());
}

public void notifyPlayer(String colorMap){
    System.out.println("notifying " + colorMap);

    out.println(colorMap);
    out.flush();
}

public void run() {

    try {
        System.out.println("Starting thread");


        String inputLine = null;
        while (true) {
            if (in.ready()){
                inputLine = in.readLine();
                if (inputLine != null){
                    System.out.println(inputLine);
                    if (inputLine.equals("Bye"))
                        break;
                        // register input line to game instance
                    JSONObject returnObj = GraphServer.getInstance().parseCommand(inputLine);
                    System.out.println(returnObj.toString());

                    String errString = returnObj.optString("error");
                    if (errString != null && errString.length() > 0) 
                        out.println(returnObj.toString());
                    else
                        delegate.notifyPlayers(returnObj.toString());
                    // out.println(colorMap);
                    // out.flush();


                }
            }


        }
        socket.close();
    } catch (IOException e) {

        System.err.println(e);
    }
}
}

