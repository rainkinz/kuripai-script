// Copyright (C) 2001 - 2002 by Oliver Goldman
// All Rights Reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted.
//
// Additional information available at http://software.charlie-dog.com.

package org.kuripai.script;

import java.util.*;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * A Number argument for use with ArgumentParser. A Number argument may have a
 * default value. If specified on the command line, a Number argument takes the value
 * of the argument immediately following its flag.
 */
public class NumberArgument implements Argument {
    private String flag;
    private String desc;
    private BigDecimal defaultValue;
    private BigDecimal value = null;

    /**
     * Initialize a new Number argument with the given flag and description but without
     * a default value.
     */

    public NumberArgument( String flag, String description ) {
        this.flag = flag;
        this.desc = description;
    }

    /**
     * Initialize a new Number argument with the given flag, description, and default
     * value.
     */

    public NumberArgument( String flag, BigDecimal defaultValue, String description ) {
        this.flag = flag;
        this.desc = description;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    /**
     * Return the value of this argument, which may be null. Returns the
     * default value if the value was not set when the command line was parsed.
     */

    public BigDecimal getValue() {
        return value;
    }

    /**
     * Parsing method invoked by ArgumentParser.
     */

    public int parse( List values ) {
        if( values.get( 0 ).equals( flag )) {
            if( values.size() == 1 ) {
                throw new IllegalArgumentException( "should be " + flag + " <number>" );
            }

            value = new BigDecimal( (String)values.get( 1 ));
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
