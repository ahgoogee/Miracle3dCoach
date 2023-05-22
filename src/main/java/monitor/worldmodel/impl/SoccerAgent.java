package monitor.worldmodel.impl;

import base.geometry.Pose3D;
import monitor.worldmodel.ISoccerAgent;
import monitor.worldmodel.SoccerAgentBodyPart;
import utils.scene.IBaseNode;
import utils.scene.IMeshNode;
import utils.scene.ITransformNode;
import utils.scene.NodeType;

import java.util.HashMap;
import java.util.Map;

public class SoccerAgent extends SimulationObject implements ISoccerAgent {
    private String teamName;
    private int playerID;
    private String robotModel;
    private Map<SoccerAgentBodyPart, Pose3D> bodyPartPoses = new HashMap();

    public SoccerAgent(IBaseNode graphRoot, String teamName) {
        super(graphRoot);
        this.teamName = teamName;
        if (graphRoot != null) {
            IMeshNode meshNode = (IMeshNode)graphRoot.getNode(IMeshNode.class, "objName", "models/naobody[0-9]*[.]obj");
            if (meshNode != null && meshNode.getMaterials() != null) {
                String[] materials = meshNode.getMaterials();
                String[] var5 = materials;
                int var6 = materials.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    String material = var5[var7];
                    if (material.startsWith("matNum") && material.length() > 6) {
                        String numberStr = material.substring(6, material.length());

                        try {
                            this.playerID = Integer.parseInt(numberStr);
                        } catch (NumberFormatException var11) {
                            System.out.println("Error parsing playerID in: " + numberStr);
                            var11.printStackTrace();
                        }
                    }
                }

                this.robotModel = meshNode.getObjName();
            }

            this.refresh(0.0F);
        }

    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public void refresh(float deltaT) {
        if (this.graphRoot != null && this.graphRoot.getChildren() != null) {
            this.graphRoot.getChildren().stream().filter((childNode) -> {
                return childNode.getNodeType() == NodeType.TRANSFORM;
            }).forEach((childNode) -> {
                ITransformNode node = (ITransformNode)childNode;
                this.checkBodyPart(node, "models/naohead.obj", SoccerAgentBodyPart.HEAD);
                this.checkBodyPart(node, "models/naobody[0-9]*[.]obj", SoccerAgentBodyPart.BODY);
                this.checkBodyPart(node, "models/lupperarm[0-9]*[.]obj", SoccerAgentBodyPart.LEFT_UPPER_ARM);
                this.checkBodyPart(node, "models/rupperarm[0-9]*[.]obj", SoccerAgentBodyPart.RIGHT_UPPER_ARM);
                this.checkBodyPart(node, "models/llowerarm.obj", SoccerAgentBodyPart.LEFT_LOWER_ARM);
                this.checkBodyPart(node, "models/rlowerarm.obj", SoccerAgentBodyPart.RIGHT_LOWER_ARM);
                this.checkBodyPart(node, "models/lthigh.obj", SoccerAgentBodyPart.LEFT_THIGH);
                this.checkBodyPart(node, "models/rthigh.obj", SoccerAgentBodyPart.RIGHT_THIGH);
                this.checkBodyPart(node, "models/lfoot.obj", SoccerAgentBodyPart.LEFT_FOOT);
                this.checkBodyPart(node, "models/rfoot.obj", SoccerAgentBodyPart.RIGHT_FOOT);
            });
            IBaseNode child = (IBaseNode)this.graphRoot.getChildren().get(0);
            if (child.getNodeType() == NodeType.TRANSFORM) {
                this.position = ((ITransformNode)child).getPosition();
            }

        }
    }

    private void checkBodyPart(ITransformNode node, String model, SoccerAgentBodyPart bodyPart) {
        if (this.hasModel(node, model)) {
            this.bodyPartPoses.put(bodyPart, new Pose3D(node.getPosition(), node.getOrientation()));
        }

    }

    private boolean hasModel(ITransformNode node, String model) {
        return node.getNode(IMeshNode.class, "objName", model) != null;
    }

    public Pose3D getBodyPartPose(SoccerAgentBodyPart bodyPart) {
        return (Pose3D)this.bodyPartPoses.get(bodyPart);
    }

    public String getRobotModel() {
        return this.robotModel;
    }
}
