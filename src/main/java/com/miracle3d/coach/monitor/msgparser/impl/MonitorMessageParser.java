package com.miracle3d.coach.monitor.msgparser.impl;

import com.miracle3d.coach.base.symbol.SymbolNode;
import com.miracle3d.coach.base.symbol.SymbolTreeParser;
import com.miracle3d.coach.monitor.msgparser.ISimulationState;
import com.miracle3d.coach.monitor.msgparser.IMonitorMessageParser;
import com.miracle3d.coach.utils.scene.impl.BaseNode;
import com.miracle3d.coach.utils.scene.impl.SceneGraph;
import com.miracle3d.coach.utils.scene.impl.SceneGraphHeader;

import java.nio.charset.StandardCharsets;

public class MonitorMessageParser implements IMonitorMessageParser {
    private ISimulationState state;
    private SceneGraph scene;
    private final SymbolTreeParser parser = new SymbolTreeParser();
    private final RSGConverter rsgConverter = new RSGConverter();

    @Override
    public void update(byte[] var1) {
        this.state = null;
        this.scene = null;
        try {
            String content = new String(var1, StandardCharsets.UTF_8);
            SymbolNode root = parser.parse(content);

            assert root.children != null;

            ISimulationState currentState = this.rsgConverter.convertSimulationState((SymbolNode)root.children.get(0));
            SceneGraphHeader header = this.rsgConverter.convertSceneGraphHeader((SymbolNode)root.children.get(1));
            BaseNode node = this.rsgConverter.convertSceneGraphNode((SymbolNode) root.children.get(2));

            this.state = currentState;
            this.scene = new SceneGraph(header,node);

        } catch (NodeConversionException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ISimulationState getSimulationState() {
        return this.state;
    }

    @Override
    public SceneGraph getSceneGraph() {
        return this.scene;
    }
}
