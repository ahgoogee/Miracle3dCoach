package base.symbol;

import java.util.List;

public class SymbolNode {
    public final List<Object> children;

    public SymbolNode() {
        this.children = null;
    }

    public SymbolNode(List<Object> children) {
        this.children = children;
    }

    public String toString() {
        String ret = "";
        if (this.children != null && !this.children.isEmpty()) {
            for(int i = 0; i < this.children.size(); ++i) {
                Object child = this.children.get(i);
                if (i > 0) {
                    ret = ret + " ";
                }

                if (child instanceof SymbolNode) {
                    ret = ret + "(" + child.toString() + ")";
                } else {
                    ret = ret + child.toString();
                }
            }

            return ret;
        } else {
            return "";
        }
    }
}
