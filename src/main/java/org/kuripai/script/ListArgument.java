package org.kuripai.script;

import java.util.*;
import java.io.PrintWriter;

/**
 * A List argument for use with ArgumentParser. A List argument consumes all values
 * it sees in the command line arguments and returns them as a list. The order of the
 * values is preserved. Because this argument consumes all available values, it
 * should be registered as the last argument added to an ArgumentParser instance.
 */

public class ListArgument implements Argument {
    private String desc;
    private List value = Collections.EMPTY_LIST;

    /**
     * Initialize a new List argument with the given description.
     */

    public ListArgument( String description ) {
        this.desc = description;
    }

    /**
     * Returns the list of values collected by this argument. The list may
     * be empty but is never null.
     */

    public List getValue() {
        return value;
    }

    /**
     * Parsing method invoked by ArgumentParser.
     */

    public int parse( List values ) {
		value = new LinkedList( values );
		return values.size();
    }

    /**
     * Usage method invoked by ArgumentParser.
     */

    public void printUsage( PrintWriter out ) {
        StringBuffer toPrint = new StringBuffer( "  " );
        toPrint.append( "\t" );
        toPrint.append( desc );

        out.println( toPrint.toString());
    }
}
