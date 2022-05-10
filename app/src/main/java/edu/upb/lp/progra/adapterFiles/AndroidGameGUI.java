package edu.upb.lp.progra.adapterFiles;

/**
 * This interface shows all methods available for clients in the Android library
 * for graphical interaction with the user.
 * 
 * @author Alexis Marechal
 * @author Alfredo Villalba
 * @author Luis Frontanilla
 * @author Jordi Ugarte
 */
public interface AndroidGameGUI {

	/**
	 * Configure the whole screen
	 * 
	 * @param numberOfRows
	 * @param numberOfColumns
	 * @param verticalSpacing
	 * @param horizontalSpacing
	 * @param vertical
	 * @param bottomSectionProportion
	 */
	public void configureScreen(int numberOfRows, int numberOfColumns,
			int verticalSpacing, int horizontalSpacing, boolean vertical, double bottomSectionProportion);

	/**
	 * Add a button. If the button already exists, it is updated
	 * 
	 * @param name
	 * @param textSize
	 * @param buttonSize
	 */
	public void addButton(String name, int textSize, int buttonSize);

	/**
	 * Remove a button
	 * 
	 * @param name
	 */
	public void removeButton(String name);

	/**
	 * Remove all buttons
	 */
	public void removeAllButtons();

	/**
	 * Add a text field. If it already exists, it will be updated
	 * 
	 * @param name
	 * @param text
	 * @param textSize
	 * @param textFieldSize
	 */
	public void addTextField(String name, String text, int textSize,
			int textFieldSize);

	/**
	 * Remove a text field
	 * 
	 * @param name
	 */
	public void removeTextField(String name);

	/**
	 * Remove all text fields
	 */
	public void removeAllTextFields();

	/**
	 * Update a text field
	 * 
	 * @param name
	 * @param message
	 */
	public void updateTextField(String name, String message);

	/**
	 * Set some text in a cell of the grid
	 * 
	 * @param horizontal
	 * @param vertical
	 * @param text
	 */
	public void setTextOnCell(int vertical, int horizontal, String text);

	/**
	 * Set the size of the text in a cell
	 * 
	 * @param horizontal
	 * @param vertical
	 * @param size
	 */
	public void setTextSizeOnCell(int vertical, int horizontal, int size);

	/**
	 * Show a temporary message on the screen
	 * 
	 * @param message
	 *            The message to be displayed
	 */
	public void showTemporaryMessage(String message);

	/**
	 * Set an image in the cell
	 * 
	 * @param horizontal
	 * @param vertical
	 * @param image
	 */
	public void setImageOnCell(int vertical, int horizontal, String image);

	/**
	 * Reproduce a sound, verify that the name of the sound file (without
	 * extension) you put on res/raw folder is the same as the String param
	 * 
	 * @param sound
	 */
	public void reproduceSound(String sound);

	/**
	 * Stop all sounds
	 */
	public void stopSounds();
	

	/**
	 * Ask the user for text. This method will show a popup window with the
	 * title parameter.
	 * 
	 * @param title
	 *            The title of the window popup to be shown
	 * @param listener
	 *            An object that will wait for the answer of the user and act
	 *            accordingly
	 * @return The text introduced by the user
	 */
	public void askUserText(String title, TextListener listener);

	/**
	 * Store a String in the phone memory, to be retrieved anytime, even after
	 * shutting down the telephone.
	 * 
	 * @param key The key to store and retrieve the String.
	 * @param value The String to be stored.
	 */
	public void storeString(String key, String value);

	/**
	 * Store an int in the phone memory, to be retrieved anytime, even after
	 * shutting down the telephone.
	 * 
	 * @param key The key to store and retrieve the int.
	 * @param value The int to be stored.
	 */
	public void storeInt(String key, int value);

	/**
	 * Store a float in the phone memory, to be retrieved anytime, even after
	 * shutting down the telephone.
	 * 
	 * @param key The key to store and retrieve the float.
	 * @param value The float to be stored.
	 */
	public void storeFloat(String key, float value);
	
	/**
	 * Store a boolean in the phone memory, to be retrieved anytime, even after
	 * shutting down the telephone.
	 * 
	 * @param key The key to store and retrieve the boolean.
	 * @param value The boolean to be stored.
	 */
	public void storeBoolean(String key, boolean value);
	
	/**
	 * Retrieve a value from the phone memory.
	 * @param key The with which the value was stored.
	 * @return The String that was stored.
	 */
	public String retrieveString(String key);
	
	/**
	 * Retrieve a value from the phone memory.
	 * @param key The with which the value was stored.
	 * @return The int that was stored.
	 */
	public int retrieveInt(String key);
	
	/**
	 * Retrieve a value from the phone memory.
	 * @param key The with which the value was stored.
	 * @return The float that was stored.
	 */
	public float retrieveFloat(String key);
	
	/**
	 * Retrieve a value from the phone memory.
	 * @param key The with which the value was stored.
	 * @return The boolean that was stored.
	 */
	public boolean retrieveBoolean(String key);
	
	/**
	 * Executes the runnable parameter in r milliseconds
	 * @param r The runnable to be executed
	 * @param ms the time delay
	 */
	
	public void executeLater(Runnable r, int ms);
}