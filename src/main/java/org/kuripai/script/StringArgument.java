package org.kuripai.script;

import java.util.*;
import java.io.PrintWriter;

/**
 * A String argument for use with ArgumentParser. A String argument may have a default
 * value. If specified on the command line, a string argument takes the value of the
 * argument immediately following its flag.
 */
public class StringArgument implements Argument {
    private String flag;
    private String desc;
    private String defaultValue;
    private String value = null;

    /**
     * Initialize a new String argument with the given flag and description but without
     * a default value.
     */

    public StringArgument( String flag, String description ) {
        this.flag = flag;
        this.desc = description;
    }

    /**
     * Initialize a new String argument with the given flag, description, and default
     * value.
     */

    public StringArgument( String flag, String defaultValue, String description ) {
        this.flag = flag;
        this.desc = description;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    /**
     * Return the string value of this argument, which may be null. Returns the
     * default value if the value was not set when the command line was parsed.
     */

    public String getValue() {
        return value;
    }

    /**
     * Parsing method invoked by ArgumentParser.
     */

    public int parse( List values ) {
        if( values.get( 0 ).equals( flag )) {
            if( values.size() == 1 ) {
                throw new IllegalArgumentException( "should be " + flag + " <string>" );
            }

            value = (String)( values.get( 1 ));
            return 2;
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

        if( defaultValue != null ) {
            toPrint.append( " (default: " );
            toPrint.append( defaultValue );
            toPrint.append( ")" );
        }

        out.println( toPrint.toString());
    }
}
