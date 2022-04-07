
public class TSTNode {
	char ch;
	TSTNode left;
	TSTNode middle;
	TSTNode right;
	boolean completed;

	public TSTNode(char ch) {
		this.ch = ch;
		this.left = null;
        this.middle = null;
        this.right = null;
        completed = false;
	}

}
