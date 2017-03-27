

import com.google.inject.*;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import mock.ChangeMock;
import mock.DocumentMock;
import org.junit.Before;
import org.junit.Test;
import undo.Change;
import undo.Document;
import undo.UndoManager;
import undo.UndoManagerFactory;
import undo.impl.UndoManagerImpl;


import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by aly on 27.03.17.
 */
public class UndoManagerImplTest {
    private UndoManagerFactory factory;
    private int bufferSize = 100;

 @Before
    public void Init(){
     Injector injector = Guice.createInjector(new AbstractModule() {
         @Override
         protected void configure() {
            install(new FactoryModuleBuilder()
                    .implement(UndoManager.class, UndoManagerImpl.class)
                    .build(UndoManagerFactory.class));
         }
     });

     factory = injector.getInstance(UndoManagerFactory.class);

 }

 @Test
    public void canUndoTest(){
        Document doc = new DocumentMock();
        UndoManager undoManager = factory.createUndoManager(doc , bufferSize);
        assertFalse(undoManager.canUndo());

        Change change = new ChangeMock();


        undoManager.registerChange(change);
        assertTrue(undoManager.canUndo());
        undoManager.undo();

        assertFalse(undoManager.canUndo());

    }
    @Test
    public void canRedoTest(){
        Document doc = new DocumentMock();
        UndoManager undoManager = factory.createUndoManager(doc , bufferSize);
        assertFalse(undoManager.canRedo());
        Change change = new ChangeMock();
        undoManager.registerChange(change);
        undoManager.undo();
        assertTrue(undoManager.canRedo());
        undoManager.redo();
        assertFalse(undoManager.canRedo());

    }
    @Test//(expected = IllegalStateException.class)
    public void undoTest(){
        Document doc = new DocumentMock();
        UndoManager undoManager = factory.createUndoManager(doc , 2);
        Change change;
        for( int i = 0; i< 2; i++){
            change = new ChangeMock();
            undoManager.registerChange(change);
        }


    }


}
