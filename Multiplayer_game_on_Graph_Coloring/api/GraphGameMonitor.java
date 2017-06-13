package api;

import java.util.*;

public class GraphGameMonitor implements IGraphGameServerDelegate {
	ArrayList<GraphGameServerThread> threadList = new ArrayList<GraphGameServerThread>();
	private volatile static GraphGameMonitor uniqueInstance;
	public GraphGameMonitor (){

	}

	public static GraphGameMonitor getInstance(){
		if (uniqueInstance == null) {
			synchronized(GraphGameMonitor.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new GraphGameMonitor();
				}
			}
		}
		return uniqueInstance;
	}

	public void notifyPlayers(String colorMap) {
		Iterator it = threadList.iterator();
		while (it.hasNext()){
			GraphGameServerThread thr = (GraphGameServerThread)it.next();
			thr.notifyPlayer(colorMap);
		}
	}

	public void addThread(GraphGameServerThread t){
		threadList.add(t);
	}

}