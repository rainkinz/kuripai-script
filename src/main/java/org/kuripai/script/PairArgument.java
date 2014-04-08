package org.kuripai.script;
import java.util.*;
import java.io.PrintWriter;

/**
 * A Pair argument for use with ArgumentParser. A Pair argument has
 * two values, a source and a destination, and is useful for programs which
 * translate (or otherwise process) some input to produce some output. Source
 * and destination could be, for example, either file formats or file names.
 *
 * On the command line, a translate argument consists of a flag followed by
 * the source and destination arguments, in that order:
 * <PRE>
 *   -&lt;flag&gt; &lt;source&gt; &lt;dest&gt;
 * </PRE>
 */
public class PairArgument implements Argument {
    private String flag;
    private String desc;
    private String source = null;
    private String destination = null;

    /**
     * Initialize a new Pair argument.
     */

    public PairArgument( String flag, String description ) {
        this.flag = flag;
        this.desc = description;
    }

    /**
     * Return the source value of this argument, which may be null.
     */

    public String getSource() {
        return source;
    }

    /**
     * Return the destination value of this argument, which may be null.
     */

    public String getDestination() {
        return destination;
    }

    /**
     * Parsing method invoked by ArgumentParser.
     */

    public int parse( List values ) {
        if( values.get( 0 ).equals( flag )) {
            if( values.size() < 3 ) {
                throw new IllegalArgumentException( "should be " + flag + " <source> <dest>" );
            }

            source = (String)( values.get( 1 ));
            destination = (String)( values.get( 2 ));
            return 3;
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
