package de.lmu.ifi.dbs.utilities.optionhandling;

/**
 * Interface to define the required methods for command line interaction.
 * 
 * @author Arthur Zimek (<a href="mailto:zimek@dbs.ifi.lmu.de">zimek@dbs.ifi.lmu.de</a>)
 */
public interface Parameterizable
{
    /**
     * Returns a description of the class and the required parameters.
     * 
     * @return String a description of the class and the required parameters
     */
    String description();
    
    /**
     * Sets the attributes of the class accordingly to the given parameters.
     * 
     * @param args parameters to set the attributes accordingly to
     * @return String[] an array containing the unused parameters 
     * @throws IllegalArgumentException in case of wrong parameter-setting
     */
    String[] setParameters(String[] args) throws IllegalArgumentException;
 
}
