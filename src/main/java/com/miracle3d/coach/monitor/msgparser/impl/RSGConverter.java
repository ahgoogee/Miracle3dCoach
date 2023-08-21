//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.miracle3d.coach.monitor.msgparser.impl;

import com.miracle3d.coach.base.symbol.SymbolNode;
import com.miracle3d.coach.monitor.enums.Foul;
import com.miracle3d.coach.utils.scene.impl.*;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import com.miracle3d.coach.utils.scene.NodeType;


public class RSGConverter {
    public RSGConverter() {
    }

    public SimulationState convertSimulationState(SymbolNode node) throws NodeConversionException {
        SimulationState state = new SimulationState();

        for(int i = 0; i < node.children.size(); ++i) {
            SymbolNode child = this.getSymbolNode(node.children.get(i));
            if (child != null && child.children.size() >= 2) {
                boolean valid = true;

                for(int j = 0; j < child.children.size(); ++j) {
                    if (!(child.children.get(j) instanceof String)) {
                        valid = false;
                        break;
                    }
                }

                if (valid) {
                    String param = (String)child.children.get(0);
                    String value = (String)child.children.get(1);

                    try {
                        switch (param) {
                            case "time":
                                state.setTime(Float.parseFloat(value));
                                break;
                            case "score_left":
                                state.setLeftScore(Integer.parseInt(value));
                                break;
                            case "score_right":
                                state.setRightScore(Integer.parseInt(value));
                                break;
                            case "play_mode":
                                state.setPlayMode(Integer.parseInt(value));
                                break;
                            case "FieldLength":
                                state.setFieldLength(Float.parseFloat(value));
                                break;
                            case "FieldWidth":
                                state.setFieldWidth(Float.parseFloat(value));
                                break;
                            case "FieldHeight":
                                state.setFieldHeight(Float.parseFloat(value));
                                break;
                            case "GoalWidth":
                                state.setGoalWidth(Float.parseFloat(value));
                                break;
                            case "GoalDepth":
                                state.setGoalDepth(Float.parseFloat(value));
                                break;
                            case "GoalHeight":
                                state.setGoalHeight(Float.parseFloat(value));
                                break;
                            case "BorderSize":
                                state.setBorderSize(Float.parseFloat(value));
                                break;
                            case "FreeKickDistance":
                                state.setFreeKickDistance(Float.parseFloat(value));
                                break;
                            case "WaitBeforeKickOff":
                                state.setWaitBeforeKickOff(Float.parseFloat(value));
                                break;
                            case "AgentRadius":
                                state.setAgentRadius(Float.parseFloat(value));
                                break;
                            case "BallRadius":
                                state.setBallRadius(Float.parseFloat(value));
                                break;
                            case "BallMass":
                                state.setBallMass(Float.parseFloat(value));
                                break;
                            case "RuleGoalPauseTime":
                                state.setRuleGoalPauseTime(Float.parseFloat(value));
                                break;
                            case "RuleKickInPauseTime":
                                state.setRuleKickInPauseTime(Float.parseFloat(value));
                                break;
                            case "RuleHalfTime":
                                state.setRuleHalfTime(Float.parseFloat(value));
                                break;
                            case "play_modes":
                                String[] playModes = new String[child.children.size() - 1];
                                playModes[0] = value;

                                for(int j = 2; j < child.children.size(); ++j) {
                                    playModes[j - 1] = (String)child.children.get(j);
                                }
                                state.setPlayModes(playModes);
                                break;
                            case "team_left":
                                state.setLeftTeam(value);
                                break;
                            case "team_right":
                                state.setRightTeam(value);
                                break;
                            case "half":
                                state.setHalf(Integer.parseInt(value));
                                break;
                            case "foul":
                                Foul foul = new Foul(state.getTime(), this.parseInteger(child, 1), Foul.FoulType.values()[this.parseInteger(child, 2)], this.parseInteger(child, 3), this.parseInteger(child, 4));
                                state.addFoul(foul);
                                break;
                            case "PassModeMinOppBallDist":
                                state.setPassModeMinOppBallDist(Integer.parseInt(value));
                                break;
                            case "PassModeDuration":
                                state.setPassModeDuration(Integer.parseInt(value));
                                break;
                            default:
                                System.err.println("Unknown Parameter: " + child);
                        }
                    } catch (NumberFormatException var12) {
                        System.err.println("Value conversion error in: " + node.children.get(i));
                        var12.printStackTrace();
                    } catch (Exception var13) {
                        System.err.println("Unexpected Exception while parsing state parameter in Node: " + node.children.get(i));
                        var13.printStackTrace();
                    }
                }
            }
        }

        return state;
    }

    public SceneGraphHeader convertSceneGraphHeader(SymbolNode node) throws NodeConversionException {
        SceneGraphHeader header = new SceneGraphHeader();
        if (node.children.size() < 3) {
            throw new NodeConversionException("Unexpected size of SceneGraphHeader: " + node);
        } else {
            String type = this.getString(node.children.get(0));
            if (!"RSG".equals(type) && !"RDS".equals(type)) {
                throw new NodeConversionException("Unexpected scene type: " + node);
            } else {
                String major = this.getString(node.children.get(1));
                String minor = this.getString(node.children.get(2));
                if (major != null && minor != null) {
                    try {
                        header.setType(type);
                        header.setMajorVersion(Integer.parseInt(major));
                        header.setMinorVersion(Integer.parseInt(minor));
                    } catch (NumberFormatException var7) {
                        throw new NodeConversionException("Value conversion error in: " + node);
                    } catch (Exception var8) {
                        System.err.println("Unexpected Exception while parsing scene graph header of Node: " + node);
                        var8.printStackTrace();
                    }

                    return header;
                } else {
                    throw new NodeConversionException("Unexpected SceneGraphHeader format: " + node);
                }
            }
        }
    }

    public BaseNode convertSceneGraphNode(SymbolNode node) throws NodeConversionException {
        switch (this.determineNodeType(node)) {
            case LIGHT:
                return this.convertLightNode(node);
            case MESH:
                return this.convertMeshNode(node);
            case TRANSFORM:
                return this.convertTransformNode(node);
            case BASE:
            default:
                return this.convertBaseNode(node);
        }
    }

    private NodeType determineNodeType(SymbolNode node) {
        if (node.children.size() > 1 && "nd".equals(this.getString(node.children.get(0)))) {
            String typeString = this.getString(node.children.get(1));
            if (typeString != null) {
                return NodeType.determineNodeType(typeString);
            }

            SymbolNode child = this.getSymbolNode(node.children.get(1));
            if (child.children.size() > 0 && "SLT".equals(this.getString(child.children.get(0)))) {
                return NodeType.TRANSFORM;
            }
        }

        return NodeType.BASE;
    }

    private LightNode convertLightNode(SymbolNode node) throws NodeConversionException {
        LightNode lightNode = new LightNode();

        for(int i = 0; i < node.children.size(); ++i) {
            SymbolNode child = this.getSymbolNode(node.children.get(i));
            if (child != null) {
                if (child.children.size() < 5) {
                    System.err.println("Unexpected Parameter node length while parsing light node: " + child);
                } else {
                    String parameter = this.getString(child.children.get(0));
                    float[] values = null;

                    try {
                        switch (parameter) {
                            case "setDiffuse":
                                lightNode.setDiffuse(this.parseFloatArray(child));
                                break;
                            case "setAmbient":
                                lightNode.setAmbient(this.parseFloatArray(child));
                                break;
                            case "setSpecular":
                                lightNode.setSpecular(this.parseFloatArray(child));
                                break;
                            default:
                                System.err.println("Unknown Parameter node while parsing light node: " + child);
                        }
                    } catch (NullPointerException | NumberFormatException var9) {
                        System.err.println("Value conversion error in: " + child);
                        var9.printStackTrace();
                    } catch (Exception var10) {
                        System.err.println("Unexpected Exception while parsing light-parameter node: " + child);
                        var10.printStackTrace();
                    }
                }
            }
        }

        return lightNode;
    }

    private MeshNode convertMeshNode(SymbolNode node) throws NodeConversionException {
        MeshNode meshNode = new MeshNode();
        float radius = 1.0F;
        float length = 1.0F;

        for(int i = 0; i < node.children.size(); ++i) {
            SymbolNode child = this.getSymbolNode(node.children.get(i));
            if (child != null && child.children.size() > 0) {
                String parameter = this.getString(child.children.get(0));

                try {
                    String value;
                    switch (parameter) {
                        case "setVisible":
                            value = this.getString(child.children.get(1));
                            if (value != null) {
                                meshNode.setVisible(!"0".equals(value));
                            }
                            break;
                        case "setTransparent":
                            meshNode.setTransparent(true);
                            break;
                        case "load":
                            value = this.getString(child.children.get(1));
                            if (value != null) {
                                meshNode.setObjName(value);
                            } else {
                                System.err.println("second value of load-parameter not valid: " + child);
                            }

                            if (child.children.size() > 3) {
                                radius = this.parseFloat(child, 2);
                                length = this.parseFloat(child, 3);
                            }
                            break;
                        case "sSc":
                            double x = this.parseDouble(child, 1) * (double)radius;
                            double y = this.parseDouble(child, 2) * (double)radius;
                            double z = this.parseDouble(child, 3) * (double)length;
                            meshNode.setScale(new Vector3D(x, y, z));
                            break;
                        case "resetMaterials":
                        case "sMat":
                            String[] newMaterials = new String[child.children.size() - 1];
                            int j = 1;

                            for(; j < child.children.size(); ++j) {
                                value = this.getString(child.children.get(j));
                                if (value != null) {
                                    newMaterials[j - 1] = value;
                                } else {
                                    System.err.println("a material-parameter is not valid: " + child);
                                }
                            }

                            meshNode.setMaterials(newMaterials);
                            break;
                        default:
                            System.err.println("Unknown Parameter while parsing mesh node: " + child);
                    }
                } catch (NullPointerException | NumberFormatException var19) {
                    System.err.println("Value conversion error in: " + child);
                    var19.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException var20) {
                    System.err.println("Unexpected parameter node format: " + child);
                    var20.printStackTrace();
                } catch (Exception var21) {
                    System.err.println("Unexpected Exception while parsing mesh-parameter node: " + child);
                    var21.printStackTrace();
                }
            }
        }

        return meshNode;
    }

    private TransformNode convertTransformNode(SymbolNode node) throws NodeConversionException {
        TransformNode transformNode = new TransformNode();

        for(int i = 0; i < node.children.size(); ++i) {
            SymbolNode child = this.getSymbolNode(node.children.get(i));
            if (child != null) {
                if (child.children.size() > 16 && "SLT".equals(child.children.get(0))) {
                    try {
                        float[] matrix = new float[16];

                        for(int j = 0; j < 16; ++j) {
                            matrix[j] = this.parseFloat(child, j + 1);
                        }

                        transformNode.setLocalTransform(matrix);
                    } catch (NullPointerException | NumberFormatException var7) {
                        if (!var7.getMessage().contains("-nan")) {
                            System.err.println("Value conversion error in: " + child);
                            var7.printStackTrace();
                        }
                    } catch (Exception var8) {
                        System.err.println("Unexpected Exception while parsing 4x4 matrix of SLT Node: " + child);
                        var8.printStackTrace();
                    }
                } else {
                    transformNode.addChildNode(this.convertSceneGraphNode(child));
                }
            }
        }

        return transformNode;
    }

    private BaseNode convertBaseNode(SymbolNode node) throws NodeConversionException {
        BaseNode baseNode = new BaseNode();
        if (node.children.size() > 0) {
            for(int i = 0; i < node.children.size(); ++i) {
                SymbolNode child = this.getSymbolNode(node.children.get(i));
                if (child != null) {
                    baseNode.addChildNode(this.convertSceneGraphNode(child));
                }
            }
        }

        return baseNode;
    }

    private SymbolNode getSymbolNode(Object obj) {
        return obj instanceof SymbolNode ? (SymbolNode)obj : null;
    }

    private String getString(Object obj) {
        return obj instanceof String ? (String)obj : null;
    }

    private Integer parseInteger(SymbolNode node, int child) {
        return Integer.parseInt(this.getString(node.children.get(child)));
    }

    private Float parseFloat(SymbolNode node, int child) {
        return Float.parseFloat(this.getString(node.children.get(child)));
    }

    private Double parseDouble(SymbolNode node, int child) {
        return Double.parseDouble(this.getString(node.children.get(child)));
    }

    private float[] parseFloatArray(SymbolNode node) {
        float[] a = new float[]{this.parseFloat(node, 1), this.parseFloat(node, 2), this.parseFloat(node, 3), this.parseFloat(node, 4)};
        return a;
    }
}