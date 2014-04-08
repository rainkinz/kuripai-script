package org.kuripai.script;

import java.util.*;
import java.io.PrintWriter;

/**
 * A Boolean argument for use with ArgumentParser. It is false unless the flag is
 * found in the command line, in which case it is true.
 */

public class BooleanArgument implements Argument {
    private String flag;
    private String desc;
    private boolean value = false;

    /**
     * Construct a new boolean argument with the given flag and description.
     */

    public BooleanArgument(String flag, String description) {
        this.flag = flag;
        this.desc = description;
    }
    
    /**
     * Returns the boolean value of this argument. By default, the argument's
     * value is false; it is true if the flag was found when the command line
     * was parsed.
     */

    public boolean getValue() {
        return value;
    }

    /**
     * Parsing method invoked by ArgumentParser.
     */

    public int parse(List values) {
        if( values.get(0).equals( flag )) {
            value = true;
            return 1;
        }
        return 0;
    }

    /**
     * Usage method invoked by ArgumentParser.
     */

    public void printUsage( PrintWriter out ) {
        StringBuffer toPrint = new StringBuffer( "  " );
        toPrint.append( flag );
        toPrint.append( "\t" );
        toPrint.append( desc );

        out.println( toPrint.toString());
    }
}
