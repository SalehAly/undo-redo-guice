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
    private Stack<Change> undoStack;
    private Stack<Change> redoStack;
    private int bufferSize;
    private Document doc;


    @Inject
    public UndoManagerImpl(@Assisted Document doc, @Assisted int bufferSize) {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
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
    public boolean canUndo() { return !undoStack.isEmpty(); }

    @Override
    public void undo() {
        if (!canUndo())
            throw new IllegalStateException("Can not undo more");

        Change change = undoStack.pop();
        redoStack.push(change);
        change.revert(doc);
    }

    @Override
    public boolean canRedo() { return !redoStack.isEmpty(); }

    @Override
    public void redo() {
        if (!canRedo())
            throw new IllegalStateException("Can not redo more");

        Change change = redoStack.pop();
        // This is an implementation detail, whether to re-add the change
        // after it was re-done or not.
        undoStack.push(change);
        change.apply(doc);
    }
}

