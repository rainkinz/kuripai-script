package org.kuripai.script;

import java.util.*;
import java.io.PrintWriter;

/**
 * Interface for arguments that may be registered and parsed by the
 * ArgumentParser.
 */
public interface Argument {
    /**
     * Must check whether values begins with this argument.
     * If it does, this method must return the remainder of
     * the list after all elements belonging to this argument
     * have been removed. Otherwise, values must be returned
     * without modification.
     *
     * ArgumentParser.parse( values ) will invoke this method once
     * for each possible starting position of this argument
     * in values.
     */
    public int parse(List<String> values );

    /**
     * Write out the usage for this argument
     */
    public void printUsage( PrintWriter out );
}
