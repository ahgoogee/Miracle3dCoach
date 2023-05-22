package connection;

import base.connection.ConnectionException;
import base.connection.IServerConnection;
import base.connection.impl.ServerConnection;
import base.observer.IObserver;
import base.symbol.SymbolNode;
import base.symbol.SymbolTreeParser;
import monitor.msgparser.ISimulationState;
import monitor.msgparser.impl.NodeConversionException;
import monitor.msgparser.impl.RSGConverter;
import utils.scene.impl.BaseNode;
import utils.scene.impl.SceneGraph;
import utils.scene.impl.SceneGraphHeader;

import java.io.UnsupportedEncodingException;

public class SimStateTest {
    public static void main(String[] args) {
        IServerConnection connection = new ServerConnection("localhost",3200);
        connection.attach(new SimStateRuntimeTest());
        try {
            connection.establishConnection();
            connection.startReceiveLoop();
        } catch (ConnectionException e) {
            throw new RuntimeException(e);
        }
    }

    public static class SimStateRuntimeTest implements IObserver<byte[]> {
        private ISimulationState state;
        private SymbolTreeParser parser = new SymbolTreeParser();
        private RSGConverter rsgConverter = new RSGConverter();
        private SceneGraph scene;
        @Override
        public void update(byte[] var1) {
            this.state = null;
            this.scene = null;
            try {
                String content = new String(var1, 0, var1.length, "UTF-8");
                SymbolNode root = parser.parse(content);

                ISimulationState currentState = this.rsgConverter.convertSimulationState((SymbolNode)root.children.get(0));
                SceneGraphHeader header = this.rsgConverter.convertSceneGraphHeader((SymbolNode)root.children.get(1));
                BaseNode node = this.rsgConverter.convertSceneGraphNode((SymbolNode) root.children.get(2));

                this.state = currentState;
                this.scene = new SceneGraph(header,node);


            } catch (NodeConversionException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
