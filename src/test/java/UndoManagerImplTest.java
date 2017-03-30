import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import mock.ChangeMock;
import mock.DocumentMock;
import org.junit.Before;
import org.junit.Test;
import undo.*;
import undo.impl.UndoManagerImpl;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by aly on 27.03.17.
 */
public class UndoManagerImplTest {
    private UndoManagerFactory undoManagerFactory;
    private ChangeFactory changeFactory;
    private int bufferSize = 100;
    private Document doc = new DocumentMock();

    @Before
    public void Init() {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                install(new FactoryModuleBuilder()
                        .implement(UndoManager.class, UndoManagerImpl.class)
                        .build(UndoManagerFactory.class));

                install(new FactoryModuleBuilder()
                        .implement(Change.class, ChangeMock.class)
                        .build(ChangeFactory.class));

            }
        });
        undoManagerFactory = injector.getInstance(UndoManagerFactory.class);
        changeFactory = injector.getInstance(ChangeFactory.class);
    }

    @Test
    public void canUndoTest() {
        UndoManager undoManager = undoManagerFactory.createUndoManager(doc, bufferSize);
        assertFalse(undoManager.canUndo());
        Change change = changeFactory.createDeletion(1,"",1,1);
        undoManager.registerChange(change);
        assertTrue(undoManager.canUndo());
        undoManager.undo();
        assertFalse(undoManager.canUndo());
    }

    @Test
    public void canRedoTest() {
        UndoManager undoManager = undoManagerFactory.createUndoManager(doc, bufferSize);
        assertFalse(undoManager.canRedo());
        Change change = changeFactory.createInsertion(1,"",1,1);
        undoManager.registerChange(change);
        undoManager.undo();
        assertTrue(undoManager.canRedo());
        undoManager.redo();
        assertFalse(undoManager.canRedo());
    }

    @Test(expected = IllegalStateException.class)
    public void noUndoTest() {
        UndoManager undoManager = undoManagerFactory.createUndoManager(doc, bufferSize);
        // Should throw exception
        undoManager.undo();
    }

    @Test(expected = IllegalStateException.class)
    public void noRedoTest() {
        UndoManager undoManager = undoManagerFactory.createUndoManager(doc, bufferSize);
        // Should throw exception
        undoManager.redo();
    }

    @Test
    public void undoTest() {
        int bufferSize = 5;
        UndoManager undoManager = undoManagerFactory.createUndoManager(doc, bufferSize);

        for (int i = 0; i < bufferSize; i++) {
            Change change = new ChangeMock(i);
            undoManager.registerChange(change);
        }

        for (int i = 0; i < bufferSize; i++) {
            Change change = new ChangeMock(i + bufferSize);
            undoManager.registerChange(change);
        }

        for (int i = 0; i < bufferSize; i++) {
            undoManager.undo();
        }
    }

    @Test
    public void redoTest() {
        int bufferSize = 5;
        UndoManager undoManager = undoManagerFactory.createUndoManager(doc, bufferSize);

        for (int i = 0; i < bufferSize; i++) {
            Change change = new ChangeMock(i);
            undoManager.registerChange(change);
        }

        for (int i = 0; i < bufferSize; i++)
            undoManager.undo();

        for (int i = 0; i < bufferSize; i++)
            undoManager.redo();
    }
}

