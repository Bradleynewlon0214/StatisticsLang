package loxInterpreter;

import java.util.List;

abstract class Stmt {
	interface Visitor<R> {
	R visitBlockStmt(Block stmt);
	R visitExpressionStmt(Expression stmt);
	R visitFunctionStmt(Function stmt);
	R visitIfStmt(If stmt);
	R visitPrintStmt(Print stmt);
	R visitReturnStmt(Return stmt);
	R visitLetStmt(Let stmt);
 }
 static class Block extends Stmt {
	Block(List<Stmt> statements) {
	this.statements=statements;
	}

	<R> R accept(Visitor<R> visitor) {
	return visitor.visitBlockStmt(this);
	}

	final List<Stmt> statements;
	}
 static class Expression extends Stmt {
	Expression(Expr expression) {
	this.expression=expression;
	}

	<R> R accept(Visitor<R> visitor) {
	return visitor.visitExpressionStmt(this);
	}

	final Expr expression;
	}
 static class Function extends Stmt {
	Function(Token name, List<Token> params, List<Stmt> body) {
	this.name=name;
	this.params=params;
	this.body=body;
	}

	<R> R accept(Visitor<R> visitor) {
	return visitor.visitFunctionStmt(this);
	}

	final Token name;
	final List<Token> params;
	final List<Stmt> body;
	}
 static class If extends Stmt {
	If(Expr condition, Stmt thenBranch, Stmt elseBranch) {
	this.condition=condition;
	this.thenBranch=thenBranch;
	this.elseBranch=elseBranch;
	}

	<R> R accept(Visitor<R> visitor) {
	return visitor.visitIfStmt(this);
	}

	final Expr condition;
	final Stmt thenBranch;
	final Stmt elseBranch;
	}
 static class Print extends Stmt {
	Print(Expr expression) {
	this.expression=expression;
	}

	<R> R accept(Visitor<R> visitor) {
	return visitor.visitPrintStmt(this);
	}

	final Expr expression;
	}
 static class Return extends Stmt {
	Return(Token keyword, Expr value) {
	this.keyword=keyword;
	this.value=value;
	}

	<R> R accept(Visitor<R> visitor) {
	return visitor.visitReturnStmt(this);
	}

	final Token keyword;
	final Expr value;
	}
 static class Let extends Stmt {
	Let(Token name, Expr initializer) {
	this.name=name;
	this.initializer=initializer;
	}

	<R> R accept(Visitor<R> visitor) {
	return visitor.visitLetStmt(this);
	}

	final Token name;
	final Expr initializer;
	}

	abstract <R> R accept(Visitor<R> visitor);
}
