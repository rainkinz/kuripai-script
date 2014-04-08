package org.kuripai.script;

import java.io.PrintWriter;
import java.util.List;

import junit.framework.TestCase;

public class ArgumentParserTests extends TestCase {
	public void testArguments() {
		StringArgument dest = new StringArgument("--dest", "Destination for requests");
		BooleanArgument help = new BooleanArgument("--help", "Displays help message");
		// PairArgument fromTo = new PairArgument("--");
		
		ArgumentParser parser = new ArgumentParser();
		parser.addArgument(dest);
		parser.addArgument(help);
		
		
		List<Argument> extra = parser.parse(new String[]{});
        if (!extra.isEmpty() || help.getValue()) {
            PrintWriter out = new PrintWriter(System.out);
            parser.printUsage(out);
            out.close();
            System.exit(1);
        }
	}
	
	public static void main(String[] args) {
		StringArgument dest = new StringArgument("--dest", "localhost", "Destination for requests");
		BooleanArgument help = new BooleanArgument("--help", "Displays help message");
		
		ArgumentParser parser = new ArgumentParser();
		parser.addArgument(dest);
		parser.addArgument(help);
		
		List<Argument> extra = parser.parse(args);
		if (!extra.isEmpty() || help.getValue()) {
			PrintWriter out = new PrintWriter(System.out);
			parser.printUsage(out);
			out.close();
			System.exit(1);
		}
		
	}
}
