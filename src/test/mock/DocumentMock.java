package mock;

import undo.Document;

/**
 * Created by aly on 27.03.17.
 */
public class DocumentMock implements Document {
    @Override
    public void delete(int pos, String s) {
        System.out.print("Deleted Position " + pos);
    }

    @Override
    public void insert(int pos, String s) {
        System.out.print("Inserted Position " + pos);
    }

    @Override
    public void setDot(int pos) {
        System.out.print("Set Cursor Position " + pos);
    }
}
