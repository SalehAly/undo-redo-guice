package mock;

import undo.Change;
import undo.Document;

import java.util.Random;

/**
 * Created by aly on 27.03.17.
 */
public class ChangeMock implements Change {
    public static final String TYPE_DELETE = "delete";
    public static final String TYPE_INSERT = "insert";
    private String type;
    private int id;

    public ChangeMock() {
        Random r = new Random();
        int i = r.nextInt(2);
        switch (i) {
            case 0: type = TYPE_DELETE; break;
            case 1: type = TYPE_INSERT; break;
            default: throw new IllegalStateException("No option for" + i);
        }

    }

    public ChangeMock(int id) {
        this();
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void apply(Document doc) {
        System.out.println("Change Applied " + toString());
    }

    @Override
    public void revert(Document doc) { System.out.println("Change Reverted " + toString()); }

}
