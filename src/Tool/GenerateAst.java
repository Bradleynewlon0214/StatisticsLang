package Tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
	public static void main(String[] args) throws IOException {
//		if(args.length != 1) {
//			System.err.println("Usage: generate_ast <output_dir>");
//			System.exit(1);
//		}
		String outputDir = "C:\\Users\\bradley\\eclipse-workspace\\Newlon\\src\\loxInterpreter";
	    defineAst(outputDir, "Expr", Arrays.asList(          
	    	      	"Assign   : Token name, Expr value",
	    			"Binary   : Expr left, Token operator, Expr right",
	    			"Call	  : Expr callee, Token paren, List<Expr> arguments",
	    			"Get 	  : Expr object, Token name",
	    			"Grouping : Expr expression",
	    			"SeriesGet: Expr object, Token index",
	    			"Literal  : Object value",
	    			"Logical  : Expr left, Token operator, Expr right",
	    			"Set      : Expr object, Token name, Expr value",
	    			"SeriesSet: Expr object, Token index, Expr value",
	    			"This	  : Token keyword",
	    			"Unary    : Token operator, Expr right",
	    			"Variable : Token name"
	    	    ));
	    
	    defineAst(outputDir, "Stmt", Arrays.asList(
	    			"Block		: List<Stmt> statements",
	    			"Class		: Token name, List<Stmt.Function> methods",
	    			"Expression : Expr expression",
	    			"Function	: Token name, List<Token> params, List<Stmt> body",
	    			"If			: Expr condition, Stmt thenBranch, Stmt elseBranch",
	    			"Print 		: Expr expression",
	    			"Return		: Token keyword, Expr value",
	    			"Let		: Token name, Expr initializer",
	    			"Series		: Token name, List<Object> values",
	    			"DataFrame	: Token name, Object param"
	    		));
	}
	
	private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
		writer.println(" static class " + className + " extends " + baseName + " {");
		
		//Constructor
		writer.println("	" + className + "(" + fieldList + ") {");
		
		//Store paramaters in fields
		String[] fields = fieldList.split(", ");
		for(String field: fields) {
			String name = field.split(" ")[1];
			writer.println("	this." + name + "=" + name + ";");
		}
		
		writer.println("	}");
		
		
		//Visitor pattern
		writer.println();
		writer.println("	<R> R accept(Visitor<R> visitor) {");
		writer.println("	return visitor.visit" + className + baseName + "(this);");
		writer.println("	}");
		
		//Fields
		writer.println();
		for(String field: fields) {
			writer.println("	final " + field + ";");
		}
		
		writer.println("	}");
	}
	
	private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
		writer.println("	interface Visitor<R> {");
		
		for(String type : types) {
			String typeName = type.split(":")[0].trim();
			writer.println("	R visit" + typeName + baseName + "(" +
					typeName + " " + baseName.toLowerCase() + ");");
		}
		
		writer.println(" }");
	}
	
	private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
		String path = outputDir + "/" + baseName + ".java";
		PrintWriter writer = new PrintWriter(path, "UTF-8");
		
		writer.println("package loxInterpreter;");
		writer.println();
		writer.println("import java.util.List;");
		writer.println();
		writer.println("abstract class " + baseName + " {");
		
		defineVisitor(writer, baseName, types);
		
		for(String type: types) {
			String className = type.split(":")[0].trim();
			String fields = type.split(":")[1].trim();
			defineType(writer, baseName, className, fields);
		}
		
		writer.println();
		writer.println("	abstract <R> R accept(Visitor<R> visitor);");
		
		writer.println("}");
		writer.close();
	}
}
