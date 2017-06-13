
import game.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.json.* ;

/**
 * The test class GraphServerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GraphServerTest
{
    GraphServer gserv;
    Integer gameId = 1;

    /**
     * Default constructor for test class GraphServerTest
     */
    public GraphServerTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        gserv = GraphServer.getInstance();
        gserv.resetAll();
        createGame();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        gserv.resetAll();
    }

    public void createGame(){
        String actionString = "{\"action\":\"createGame\",\"numPlayers\":3,\"playerId\":\"jonny5\",\"graphNum\":2}";
        gserv.parseCommand(actionString);
    }

    public void joinGame(String playerName){
        String actionString = "{\"action\":\"joinGame\",\"gameId\":1,\"playerId\":\"" + playerName + "\"}";
        gserv.parseCommand(actionString);
    }

    @Test
    public void testCreateGame() {
        gserv.resetAll();
        String actionString = "{\"action\":\"createGame\",\"numPlayers\":3,\"playerId\":\"jonny5\",\"graphNum\":2}";
        JSONObject metaData = new JSONObject();
        metaData.put("gameId", 1);
        metaData.put("numPlayers", 3);
        metaData.put("graphNum", 2);
        metaData.put("error", "Waiting for players");
        System.out.println(metaData.toString());
        assertEquals(metaData.toString(), gserv.parseCommand(actionString).toString());
    }

    @Test
    public void testGetGames(){

        String actionString = "{\"action\":\"getGames\"}";
        assertEquals("{\"games\":[{\"gameId\":1,\"graphNum\":2,\"numPlayers\":3}]}", gserv.parseCommand(actionString).toString());
    }

    @Test 
    public void testJoinGame(){

        String actionString = "{\"action\":\"joinGame\",\"gameId\":1,\"playerId\":\"foobar\"}";
        assertEquals("{\"currentPlayer\":\"\",\"error\":\"Waiting for players\",\"colorMap\":{},\"gameMetaData\":{\"gameId\":1,\"players\":[\"jonny5\",\"foobar\"],\"graphNum\":2,\"numPlayers\":3}}", gserv.parseCommand(actionString).toString());
    }

    @Test
    public void testCreateMultiGames(){
        createGame();
        String actionString = "{\"action\":\"getGames\"}";
        assertEquals("{\"games\":[{\"gameId\":1,\"graphNum\":2,\"numPlayers\":3},{\"gameId\":2,\"graphNum\":2,\"numPlayers\":3}]}", gserv.parseCommand(actionString).toString());
    }

    @Test
    public void testGetMoves() {
        assertNotNull(gserv.getMoves(gameId));
        gserv.insertMove(1, "red", gameId);
        assertEquals("red", gserv.getMoves(gameId).get(1));
    }

    @Test
    public void testGetNodeColor() {
        gserv.insertMove(4, "blue", gameId);
        assertEquals("blue", gserv.getNodeColor(4, gameId));
    }

    @Test
    public void testGetColorMapWaiting() {
        String actionString = "{\"color\":\"Yellow\",\"action\":\"insertMove\",\"nodeId\":1,\"playerId\":\"jonny5\",\"gameId\":1}";
        assertEquals("{\"currentPlayer\":\"\",\"error\":\"Waiting for players\",\"colorMap\":{},\"gameMetaData\":{\"gameId\":1,\"graphNum\":2,\"numPlayers\":3}}", gserv.parseCommand(actionString).toString());
    }

    @Test
    public void testInsertMove() {
        joinGame("foo");
        joinGame("bar");
        String actionString = "{\"color\":\"Yellow\",\"action\":\"insertMove\",\"nodeId\":1,\"playerId\":\"jonny5\",\"gameId\":1}";
        assertTrue(gserv.parseCommand(actionString).toString().contains("{\"1\":\"Yellow\"}"));
    }

    @Test
    public void testInsertMoveOutOfOrder() {
        joinGame("foo");
        joinGame("bar");
        String actionString = "{\"color\":\"Red\",\"action\":\"insertMove\",\"nodeId\":1,\"playerId\":\"foo\",\"gameId\":1}";

        assertTrue(gserv.parseCommand(actionString).toString().contains("\"currentPlayer\":\"jonny5\""));

        //actionString = "{\"color\":\"Red\",\"action\":\"insertMove\",\"nodeId\":2,\"playerId\":null}";
        //assertEquals("{\"1\":\"Yellow\",\"2\":\"Red\"}", gserv.parseCommand(actionString));
    }

}
