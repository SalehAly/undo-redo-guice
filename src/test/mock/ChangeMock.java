package mock;

import undo.Change;
import undo.Document;

import java.util.Random;

/**
 * Created by aly on 27.03.17.
 */
public class ChangeMock implements Change {
    public static final String TYPE_APPLY = "apply";
    public static final String TYPE_REVERT = "revert";
    private String type;

    public ChangeMock(){
        Random r = new Random();
        int i = r.nextInt(2);

        switch (i) {
            case 0:
                type =  TYPE_APPLY; break;
            case 1:
                type = TYPE_REVERT; break;
            default:
                System.out.print(i);
                throw new IllegalStateException();
        }

    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void apply(Document doc) {
        System.out.println("Change Applied");
    }

    @Override
    public void revert(Document doc) {
        System.out.println("Change Reverted");
    }
}
