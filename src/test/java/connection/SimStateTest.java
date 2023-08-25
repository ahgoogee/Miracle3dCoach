package connection;

import com.miracle3d.coach.base.connection.ConnectionException;
import com.miracle3d.coach.base.connection.IServerConnection;
import com.miracle3d.coach.base.connection.impl.ServerConnection;
import com.miracle3d.coach.base.observer.IObserver;
import com.miracle3d.coach.base.symbol.SymbolNode;
import com.miracle3d.coach.base.symbol.SymbolTreeParser;
import com.miracle3d.coach.monitor.msgparser.ISimulationState;
import com.miracle3d.coach.monitor.msgparser.impl.NodeConversionException;
import com.miracle3d.coach.monitor.msgparser.impl.RSGConverter;
import com.miracle3d.coach.utils.scene.impl.BaseNode;
import com.miracle3d.coach.utils.scene.impl.SceneGraph;
import com.miracle3d.coach.utils.scene.impl.SceneGraphHeader;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

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
        private final SymbolTreeParser parser = new SymbolTreeParser();
        private final RSGConverter rsgConverter = new RSGConverter();
        private SceneGraph scene;
        @Override
        public void update(byte[] var1) {
            this.state = null;
            this.scene = null;
            try {
                String content = new String(var1, StandardCharsets.UTF_8);
                SymbolNode root = parser.parse(content);

                ISimulationState currentState = this.rsgConverter.convertSimulationState((SymbolNode)root.children.get(0));
                SceneGraphHeader header = this.rsgConverter.convertSceneGraphHeader((SymbolNode)root.children.get(1));
                BaseNode node = this.rsgConverter.convertSceneGraphNode((SymbolNode) root.children.get(2));

                this.state = currentState;
                this.scene = new SceneGraph(header,node);


            } catch (NodeConversionException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
