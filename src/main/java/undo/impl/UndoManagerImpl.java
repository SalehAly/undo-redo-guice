package undo.impl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import undo.Change;
import undo.Document;
import undo.UndoManager;

import java.util.Stack;


/**
 * Created by aly on 27.03.17.
 */


public class UndoManagerImpl implements UndoManager {
    private Stack<Change> undoStack = new Stack<>();
    private Stack<Change> redoStack = new Stack<>();
    private int bufferSize;
    private Document doc;


    @Inject
    public UndoManagerImpl(@Assisted Document doc, @Assisted int bufferSize) {
        this.bufferSize = bufferSize;
        this.doc = doc;
    }

    @Override
    public void registerChange(Change change) {
        if (undoStack.size() < bufferSize) {
            undoStack.push(change);
            return;
        }

        while (undoStack.size() >= bufferSize) {
            Change oldChange = undoStack.remove(0);
            System.out.println("Removing " + oldChange.toString());
        }

        undoStack.push(change);

    }

    @Override
    public boolean canUndo() {
        if (undoStack.isEmpty())
            return false;
        return true;
    }

    @Override
    public void undo() {
        if (undoStack.isEmpty())
            throw new IllegalStateException("Can not undo more");

        Change change = undoStack.pop();
        redoStack.push(change);
        change.revert(doc);

    }

    @Override
    public boolean canRedo() {
        if (redoStack.isEmpty())
            return false;
        return true;
    }

    @Override
    public void redo() {
        if (redoStack.isEmpty())
            throw new IllegalStateException("Can not redo more");

        Change change = redoStack.pop();
        change.apply(doc);
    }

}
