package undo;

/**
 * An interface that allows creation of a deletion or insertion {@link Change}
 * into a {@link Document}.
 *
 */
public interface ChangeFactory {

	/**
	 * Creates a deletion change.
	 * 
	 * @param pos The position to start the deletion.
	 * @param s The string to delete.
	 * @param oldDot The dot (cursor) position before the deletion.
	 * @param newDot The dot (cursor) position after the deletion.
	 * @return The deletion {@link Change}.
	 */
	public Change createDeletion(int pos, String s, int oldDot, int newDot);
	
	/**
	 * Creates an insertion change.
	 * 
	 * @param pos The position at which to insert.
	 * @param s The string to insert.
	 * @param oldDot The dot (cursor) position before the insertion.
	 * @param newDot The dot (cursor) position after the insertion.
	 * @return The insertion {@link Change}.
	 */
	public Change createInsertion(int pos, String s, int oldDot, int newDot);
}
