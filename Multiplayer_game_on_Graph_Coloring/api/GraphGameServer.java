
package api ;

import java.net.*;
import java.io.*;
import java.util.*;

import org.restlet.*;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import game.*;


public class GraphGameServer extends Application {

    public static void main(String[] args) throws Exception {
        // REST maintenance server
        Component server = new Component() ;
        server.getServers().add(Protocol.HTTP, 9080) ;
        server.getDefaultHost().attach(new GraphGameServer()) ;
        server.start();

        // Socket server
        System.out.println("Starting main server ");
        int portNumber = 8080;
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

            while (listening) {
                GraphGameServerThread t = new GraphGameServerThread(serverSocket.accept());
                t.setDelegate(GraphGameMonitor.getInstance());
                GraphGameMonitor.getInstance().addThread(t);
                t.start();
            }

        } catch (IOException e) {
            System.err.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.err.println(e.getMessage());
        }
    }

       @Override
        public Restlet createInboundRoot() {
            Router router = new Router(getContext()) ;
            router.attach("/graphgame", GraphResource.class);        
            return router;
        }

    


}

