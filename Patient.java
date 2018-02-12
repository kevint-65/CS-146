/*
 * Patient is a Helper Class 
 * This class is used to declare parameters 
 * for the Patients from BST.java 
 * Mainly getting and setter methods
 
 */
public class Patient {
	private String name;
	private int key; // priority

	// Method will initial values
	public Patient(String name, int key) {
		this.name = name;
		this.key = key;
	}
	/*
	 * Method will get the name of the patient
	 * 
	 * return the Patient name
	 */
	public String getName() {
		return this.name;
	}

	// Method will setName
	public void setName(String name) {
		this.name = name;
	}
                  
	/*
	 * Method will get the priority of Patient return the Patient key
	 */
	public int getKey() {
		return this.key;
	}

	// Method will set the priority of Patient
	public void setKey(int key) {
		this.key = key;
	}

	/*
	 * Convert to String This method also helps print out the string when called
	 * return String
	 */
	public String toString() {
		String string = this.name + " " + this.key + " ";
		return string;
	}
}
