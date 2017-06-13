
import java.io.*;
import java.net.*;
import java.util.*;

import com.fasterxml.jackson.databind.*;
/**
 * Write a description of class GraphClient here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GraphClient implements IServerProxy
{
private static String ENV = "prod";
    private volatile static GraphClient uniqueInstance;
    private String hostName;
    private int portNumber;
    private Socket kkSocket;
    private PrintWriter out;
    private BufferedReader in;
    private IServerCallbackDelegate delegate = null;
    private SocketRunnable sr;

    private ArrayList<String> actions = new ArrayList<String>();

    // public class ThreadRunnable implements Runnable{
    // public void run(){
    // try {
    // while (actions.size() == 0){
    // Thread.sleep(1000);
    // }
    // } catch (InterruptedException ie) {
    // System.out.println("Child thread interrupted! " + ie);
    // }
    // }
    // }

    public class SocketRunnable implements Runnable {
        private volatile boolean running = true;
        public void end(){
            running = false;
        }

        public void run() {
            System.out.println("runnable run");

            try  {
                // Get initial state of board
                // out.println("{\"action\":\"getMoves\"}");
                // out.flush();

                while (running) {

                    String fromServer = null;
                    if (in.ready()){
                        fromServer = in.readLine();
                    }
                    if (fromServer != null){
                        System.out.println("Server: " + fromServer);
                        if (delegate != null){
                            boolean keepGoing = delegate.receiveMove(fromServer);
                            if (! keepGoing)
                                break;
                        }

                    }

                    // else if fromServer has colorMap
                    // populate the colorMap

                    if (actions.size() > 0) {
                        System.out.println("Client: " + actions.get(0));
                        out.println(actions.get(0));
                        actions.remove(0);
                    }

                }
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostName);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
                System.exit(1);
            } catch (Exception e) {
                e.printStackTrace();
                // System.err.println("Exception caused " + e);
                System.exit(1);
            } finally{
                try{
                    kkSocket.close();
                } catch (Exception e) {
                    System.err.println("exception thrown on socket close" + e);
                }

            }
            System.out.println("exiting socket thread");
        }
    }
    /**
     * Constructor for objects of class GraphClient
     */
    public GraphClient()
    {
        init();
    }

    private void setEnvironment(){
        switch (ENV){
            case "local":
            {
                hostName = "localhost";
                portNumber = 8080;
            }
            break;
            case "docker":
            {
                hostName = "192.168.99.100";
                portNumber = 80;
            }
            break;
            case "prod":
            {
                hostName = "52.87.216.222";
                portNumber = 80;
            }
        }
    }

    private void init(){
        setEnvironment();
        try  {

            kkSocket = new Socket(hostName, portNumber);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));

            sr = new SocketRunnable();
            Thread t = new Thread(sr);
            t.start();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }

    public void sendAction(GraphAction action){
        ObjectMapper mapper = new ObjectMapper();
        try{
            String jsonRep = mapper.writeValueAsString(action);
            send(jsonRep);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void send(String payload){
        System.out.println("queue payload" + payload);
        actions.add(payload);
    }

    public void setDelegate(IServerCallbackDelegate delegate){
        this.delegate = delegate;
    }

    public static GraphClient getInstance() {
        if (uniqueInstance == null) {
            synchronized(GraphClient.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new GraphClient();
                }
            }
        }
        return uniqueInstance;
    }

    public void reset(){
        try{
            if (kkSocket != null){
                kkSocket.close();
                out.close();
                in.close();
                sr.end();
                init();
            }

        }
        catch (Exception e){
            System.err.println(e);
        }

    }

}
