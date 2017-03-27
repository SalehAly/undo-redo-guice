package undo;

/**
 * A manager for undo and redo operations to {@link Document}s, based
 * on {@link Change} objects.
 *
 */
public interface UndoManager {

	/**
	 * Registers a new change in this undo manager. If the buffer
	 * size of the undo manager is filled, replace the oldest change
	 * with the one provided to this method.
	 * 
	 * @param change The change to register.
	 */
	public void registerChange(Change change);
	
	/**
	 * Returns <code>true</code> if there is currently a change that 
	 * can be undone, and <code>false</code> otherwise. 
	 */
	public boolean canUndo();
	
	/**
	 * Performs the undo operation of the current change.
	 * 
	 * @throws IllegalStateException If the manager is in a state that 
	 * 			does not allow an undo (that is if either {@link #canUndo()} 
	 * 			would have returned <code>false</code>, or the application
	 * 			of the change failed).
	 */
	public void undo();
	
	/**
	 * Returns <code>true</code> if there is currently a change that 
	 * can be redone, and <code>false</code> otherwise. 
	 */
	public boolean canRedo();
	
	/**
	 * Performs the redo operation of the current change.
	 * 
	 * @throws IllegalStateException If the manager is in a state that 
	 *  		does not allow an redo (that is if either {@link #canRedo()} 
	 *  		would have returned <code>false</code>, or the application
	 *  		of the change failed).
	 */
	public void redo();
	
}
