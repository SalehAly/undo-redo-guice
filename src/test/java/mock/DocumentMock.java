package mock;

import undo.Document;

/**
 * Created by aly on 27.03.17.
 */
public class DocumentMock implements Document {
    @Override
    public void delete(int pos, String s) {
        return;
    }

    @Override
    public void insert(int pos, String s) {
        return;
    }

    @Override
    public void setDot(int pos) {
        return;
    }
}
