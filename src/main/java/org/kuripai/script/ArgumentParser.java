package org.kuripai.script;

import java.util.*;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * A parser for processing command line arguments. It is typically used as such:
 * <PRE>
 *     // Initialize arguments that may appear in the command line. By convention,
 *     // '-help' simply prints the command line usage and exits.
 *
 *     StringArgument destination = new StringArgument( "-dest", "localhost", "Destination for request tests" );
 *     BooleanArgument help = new BooleanArgument( "-help", "Describe command line args" );
 *
 *     // Initialize and invoke the parser. Note that arguments not consumed during
 *     // the parse are returned in case they may be subject to additional processing,
 *     // etc. Variable 'args' is assumed to contain the String array passed to main().
 *
 *     ArgumentParser parser = new ArgumentParser();
 *     parser.addArgument( destination );
 *     parser.addArgument( skipClasses );
 *     parser.addArgument( skipRequests );
 *     List extra = parser.parse( args );
 *
 *     // For this application, extra arguments will be treated as a usage error.
 *
 *     if( !extra.isEmpty() || help.getValue())
 *     {
 *         PrintWriter out = new PrintWriter( System.out );
 *         parser.printUsage( out );
 *         out.close();
 *         System.exit( 0 );
 *     }
 * </PRE>
 *
 * @see BooleanArgument
 * @see StringArgument
 */

public class ArgumentParser {
    private List<Argument> arguments = new LinkedList();

    /**
     * Add a new argument to be taken into consideration during the next parse.
     * Behavior when an argument is added multiple times or if multiple arguments
     * share the same flag is undefined.
     */

    public void addArgument( Argument arg ) {
        arguments.add( arg );
    }

    /**
     * Dumps the usage of each registered argument to out.
     */

    public void printUsage( PrintWriter out ) {
        out.println( "usage:" );

        Iterator i = arguments.iterator();
        while( i.hasNext()) {
            Argument arg = (Argument)( i.next());
            arg.printUsage( out );
        }
    }

    /**
     * Parses the given argument list according to all Arguments
     * registered with this parser. Returns any arguments not
     * consumed by the parse.
     */

    public List parse( String args[] ) {
        List values = Arrays.asList( args );
		List extras = new LinkedList();

		perValue: while( !values.isEmpty()) {
			// Give each Argument a shot at parsing the list in its
			// current form. Stop on the first match.

            Iterator i = arguments.iterator();
            while( i.hasNext()) {
                Argument arg = (Argument)( i.next());
                int numArgsConsumed = arg.parse( values );

				if( numArgsConsumed > 0 ) {
					values = values.subList( numArgsConsumed, values.size());
					continue perValue;
				}
			}

			// If no matches were found, move the first value to the extras
			// list and try again. Don't use values.remove( 0 ) here because
			// it is an optional method.

			extras.add( values.get( 0 ));
			values = values.subList( 1, values.size());
        }

        return extras;
    }

    /**
     * Test driver.
     */

    public static void main( String ignored[] ) {
        String empty[] = {};

        String exact[] = {
            "-string1",
            "value1",
            "-n1",
            "0.0314159e2"
        };

        String extra[] = {
            "-string1",
            "value1",
            "-n2",
            "10",
            "-bool1",
            "-string2",
            "value2"
        };

        StringArgument string1 = new StringArgument( "-string1", "" );
        StringArgument string3 = new StringArgument( "-string3", "value3", "" );
        BooleanArgument bool1 = new BooleanArgument( "-bool1", "" );
		NumberArgument n1 = new NumberArgument( "-n1", "" );

        ArgumentParser parser = new ArgumentParser();
        parser.addArgument( string1 );
        parser.addArgument( string3 );
        parser.addArgument( bool1 );
        parser.addArgument( n1 );

		// ----------------------------------------------------------------------

        System.out.println( "Test 01-001: Run arg parser w/ no args" );
        parser.parse( empty );

		if( string1.getValue() != null ) {
			throw new RuntimeException( "Default value for string1 failed" );
		}
		if( string3.getValue() != "value3" ) {
			throw new RuntimeException( "Default value for string3 failed" );
		}
		if( bool1.getValue() != false ) {
			throw new RuntimeException( "Default value for bool1 failed" );
		}
		if( n1.getValue() != null ) {
			throw new RuntimeException( "Default value for n1 failed" );
		}

		// ----------------------------------------------------------------------

        System.out.println( "Test 01-002: Run arg parser w/ args" );
        List remaining = parser.parse( exact );

        System.out.println( "Test 02-001: Check string1" );
        if( !string1.getValue().equals( "value1" )) {
            throw new RuntimeException( "Parsing string1 failed" );
        }
		if( string3.getValue() != "value3" ) {
			throw new RuntimeException( "Default value for string3 failed" );
		}
		if( bool1.getValue() != false ) {
			throw new RuntimeException( "Default value for bool1 failed" );
		}
		if( !n1.getValue().equals( new BigDecimal( "3.14159" ))) {
			throw new RuntimeException( "Default value for n1 failed" );
		}
		if( !remaining.isEmpty()) {
			throw new RuntimeException( "Should be no remaining args" );
		}

		// ----------------------------------------------------------------------

        System.out.println( "Test 01-003: Run arg parser w/ extra args" );
        remaining = parser.parse( extra );

        System.out.println( "Test 02-001: Check string1" );
        if( !string1.getValue().equals( "value1" )) {
            throw new RuntimeException( "Parsing string1 failed" );
        }

        System.out.println( "Test 02-002: Check bool1" );
        if( !bool1.getValue()) {
            throw new RuntimeException( "bool1 should have been true" );
        }

        System.out.println( "Test 02-003: Check remaining args" );
        if( !remaining.equals( Arrays.asList( new String[] {
            "-n2",
            "10",
            "-string2",
            "value2"
        }))) {
            throw new RuntimeException( "Remaining args incorrect" );
        }

        System.out.println( "Test 02-004: Check string3" );
        if( !string3.getValue().equals( "value3" )) {
            throw new RuntimeException( "Default for string3 failed" );
        }

		// ----------------------------------------------------------------------

		System.out.println( "Test 3: ListArgument" );

		ListArgument la = new ListArgument( "vacuum" );
		parser.addArgument( la );
		remaining = parser.parse( extra );

		// List will consume everything starting with -n2, which is the
		// first argument not recognized by some other parser.

		if( !la.getValue().equals( Arrays.asList( new String[] {
            "-n2",
            "10",
            "-bool1",
            "-string2",
            "value2"
		}))) {
			throw new RuntimeException( "List args incorrect" );
		}
    }
}
