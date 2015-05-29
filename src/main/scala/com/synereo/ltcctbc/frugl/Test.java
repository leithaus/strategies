package com.synereo.ltcctbc.frugl;
import java_cup.runtime.*;
import com.synereo.ltcctbc.frugl.*;
import com.synereo.ltcctbc.frugl.Absyn.*;
import java.io.*;

public class Test
{
  public static void main(String args[]) throws Exception
  {
    Yylex l = null;
    parser p;
    try
    {
      if (args.length == 0) l = new Yylex(System.in);
      else l = new Yylex(new FileReader(args[0]));
    }
    catch(FileNotFoundException e)
    {
     System.err.println("Error: File not found: " + args[0]);
     System.exit(1);
    }
    p = new parser(l);
    /* The default parser is the first-defined entry point. */
    /* You may want to change this. Other options are: */
    /* pTypedTerm, pPfExpr, pCoEquation, pTerm, pNominal, pFormalExpr, pValueExpr, pLTyp, pGroundType, pListTerm, pListTypedTerm, pListCoEquation, pListPfExpr, pListNominal, pListFormalExpr */
    try
    {
      com.synereo.ltcctbc.frugl.Absyn.TypedExpr parse_tree = p.pTypedExpr();
      System.out.println();
      System.out.println("Parse Succesful!");
      System.out.println();
      System.out.println("[Abstract Syntax]");
      System.out.println();
      System.out.println(PrettyPrinter.show(parse_tree));
      System.out.println();
      System.out.println("[Linearized Tree]");
      System.out.println();
      System.out.println(PrettyPrinter.print(parse_tree));
    }
    catch(Throwable e)
    {
      System.err.println("At line " + String.valueOf(l.line_num()) + ", near \"" + l.buff() + "\" :");
      System.err.println("     " + e.getMessage());
      System.exit(1);
    }
  }
}
