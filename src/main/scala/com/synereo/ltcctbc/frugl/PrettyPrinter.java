package com.synereo.ltcctbc.frugl;
import com.synereo.ltcctbc.frugl.Absyn.*;

public class PrettyPrinter
{
  //For certain applications increasing the initial size of the buffer may improve performance.
  private static final int INITIAL_BUFFER_SIZE = 128;
  //You may wish to change the parentheses used in precedence.
  private static final String _L_PAREN = new String("(");
  private static final String _R_PAREN = new String(")");
  //You may wish to change render
  private static void render(String s)
  {
    if (s.equals("{"))
    {
       buf_.append("\n");
       indent();
       buf_.append(s);
       _n_ = _n_ + 2;
       buf_.append("\n");
       indent();
    }
    else if (s.equals("(") || s.equals("["))
       buf_.append(s);
    else if (s.equals(")") || s.equals("]"))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals("}"))
    {
       _n_ = _n_ - 2;
       backup();
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals(","))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals(";"))
    {
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals("")) return;
    else
    {
       buf_.append(s);
       buf_.append(" ");
    }
  }


  //  print and show methods are defined for each category.
  public static String print(com.synereo.ltcctbc.frugl.Absyn.TypedExpr foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.TypedExpr foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.TypedTerm foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.TypedTerm foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.PfExpr foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.PfExpr foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.CoEquation foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.CoEquation foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.Term foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.Term foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.Nominal foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.Nominal foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.FormalExpr foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.FormalExpr foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.ValueExpr foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.ValueExpr foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.LTyp foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.LTyp foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.GroundType foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.GroundType foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.ListTerm foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.ListTerm foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.ListTypedTerm foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.ListTypedTerm foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.ListCoEquation foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.ListCoEquation foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.ListPfExpr foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.ListPfExpr foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.ListNominal foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.ListNominal foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(com.synereo.ltcctbc.frugl.Absyn.ListFormalExpr foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(com.synereo.ltcctbc.frugl.Absyn.ListFormalExpr foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.TypedExpr foo, int _i_)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.TypeJudgment)
    {
       com.synereo.ltcctbc.frugl.Absyn.TypeJudgment _typejudgment = (com.synereo.ltcctbc.frugl.Absyn.TypeJudgment) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_typejudgment.listtypedterm_, 0);
       render(")");
       render("{");
       pp(_typejudgment.listcoequation_, 0);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.TypedTerm foo, int _i_)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.TypeAssignment)
    {
       com.synereo.ltcctbc.frugl.Absyn.TypeAssignment _typeassignment = (com.synereo.ltcctbc.frugl.Absyn.TypeAssignment) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_typeassignment.term_, 0);
       render(":");
       pp(_typeassignment.ltyp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.PfExpr foo, int _i_)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.ProofExpression)
    {
       com.synereo.ltcctbc.frugl.Absyn.ProofExpression _proofexpression = (com.synereo.ltcctbc.frugl.Absyn.ProofExpression) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_proofexpression.listterm_, 0);
       render(")");
       render("{");
       pp(_proofexpression.listcoequation_, 0);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.CoEquation foo, int _i_)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.CutExpression)
    {
       com.synereo.ltcctbc.frugl.Absyn.CutExpression _cutexpression = (com.synereo.ltcctbc.frugl.Absyn.CutExpression) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("txn");
       render("(");
       pp(_cutexpression.term_1, 0);
       render(",");
       pp(_cutexpression.term_2, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.Term foo, int _i_)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Fusion)
    {
       com.synereo.ltcctbc.frugl.Absyn.Fusion _fusion = (com.synereo.ltcctbc.frugl.Absyn.Fusion) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_fusion.term_1, 0);
       render("#");
       pp(_fusion.term_2, 1);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Separation)
    {
       com.synereo.ltcctbc.frugl.Absyn.Separation _separation = (com.synereo.ltcctbc.frugl.Absyn.Separation) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_separation.term_1, 1);
       render("*");
       pp(_separation.term_2, 2);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Duplication)
    {
       com.synereo.ltcctbc.frugl.Absyn.Duplication _duplication = (com.synereo.ltcctbc.frugl.Absyn.Duplication) foo;
       if (_i_ > 2) render(_L_PAREN);
       pp(_duplication.term_1, 2);
       render("@");
       pp(_duplication.term_2, 3);
       if (_i_ > 2) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Selection)
    {
       com.synereo.ltcctbc.frugl.Absyn.Selection _selection = (com.synereo.ltcctbc.frugl.Absyn.Selection) foo;
       if (_i_ > 3) render(_L_PAREN);
       render("choose");
       render("(");
       pp(_selection.listnominal_, 0);
       render(")");
       render("{");
       pp(_selection.pfexpr_1, 0);
       render(";");
       pp(_selection.pfexpr_2, 0);
       render("}");
       if (_i_ > 3) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Duration)
    {
       com.synereo.ltcctbc.frugl.Absyn.Duration _duration = (com.synereo.ltcctbc.frugl.Absyn.Duration) foo;
       if (_i_ > 3) render(_L_PAREN);
       render("?");
       pp(_duration.term_, 3);
       if (_i_ > 3) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.InjectionLeft)
    {
       com.synereo.ltcctbc.frugl.Absyn.InjectionLeft _injectionleft = (com.synereo.ltcctbc.frugl.Absyn.InjectionLeft) foo;
       if (_i_ > 3) render(_L_PAREN);
       render("inl");
       render("(");
       pp(_injectionleft.term_, 3);
       render(")");
       if (_i_ > 3) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.InjectionRight)
    {
       com.synereo.ltcctbc.frugl.Absyn.InjectionRight _injectionright = (com.synereo.ltcctbc.frugl.Absyn.InjectionRight) foo;
       if (_i_ > 3) render(_L_PAREN);
       render("inr");
       render("(");
       pp(_injectionright.term_, 3);
       render(")");
       if (_i_ > 3) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Extraction)
    {
       com.synereo.ltcctbc.frugl.Absyn.Extraction _extraction = (com.synereo.ltcctbc.frugl.Absyn.Extraction) foo;
       if (_i_ > 3) render(_L_PAREN);
       render("!");
       render("(");
       pp(_extraction.listnominal_, 0);
       render(")");
       render("{");
       pp(_extraction.listpfexpr_, 0);
       render("}");
       if (_i_ > 3) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Mention)
    {
       com.synereo.ltcctbc.frugl.Absyn.Mention _mention = (com.synereo.ltcctbc.frugl.Absyn.Mention) foo;
       if (_i_ > 4) render(_L_PAREN);
       pp(_mention.formalexpr_, 0);
       if (_i_ > 4) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Value)
    {
       com.synereo.ltcctbc.frugl.Absyn.Value _value = (com.synereo.ltcctbc.frugl.Absyn.Value) foo;
       if (_i_ > 4) render(_L_PAREN);
       pp(_value.valueexpr_, 0);
       if (_i_ > 4) render(_R_PAREN);
    }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.Nominal foo, int _i_)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Transcription)
    {
       com.synereo.ltcctbc.frugl.Absyn.Transcription _transcription = (com.synereo.ltcctbc.frugl.Absyn.Transcription) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("@");
       render("<");
       pp(_transcription.pfexpr_, 0);
       render(">");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.AtomLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.AtomLiteral _atomliteral = (com.synereo.ltcctbc.frugl.Absyn.AtomLiteral) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_atomliteral.lident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.FormalExpr foo, int _i_)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Indication)
    {
       com.synereo.ltcctbc.frugl.Absyn.Indication _indication = (com.synereo.ltcctbc.frugl.Absyn.Indication) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_indication.nominal_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Wildcard)
    {
       com.synereo.ltcctbc.frugl.Absyn.Wildcard _wildcard = (com.synereo.ltcctbc.frugl.Absyn.Wildcard) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("_");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.ValueExpr foo, int _i_)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.DecimalLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.DecimalLiteral _decimalliteral = (com.synereo.ltcctbc.frugl.Absyn.DecimalLiteral) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_decimalliteral.double_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.IntegerLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.IntegerLiteral _integerliteral = (com.synereo.ltcctbc.frugl.Absyn.IntegerLiteral) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_integerliteral.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.StringLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.StringLiteral _stringliteral = (com.synereo.ltcctbc.frugl.Absyn.StringLiteral) foo;
       if (_i_ > 0) render(_L_PAREN);
       printQuoted(_stringliteral.string_);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.UnitLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.UnitLiteral _unitliteral = (com.synereo.ltcctbc.frugl.Absyn.UnitLiteral) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("star");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.CoUnitLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.CoUnitLiteral _counitliteral = (com.synereo.ltcctbc.frugl.Absyn.CoUnitLiteral) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("hash");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.BTCUnitLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.BTCUnitLiteral _btcunitliteral = (com.synereo.ltcctbc.frugl.Absyn.BTCUnitLiteral) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("satoshi");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.LTyp foo, int _i_)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.ImplicationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.ImplicationType _implicationtype = (com.synereo.ltcctbc.frugl.Absyn.ImplicationType) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_implicationtype.ltyp_1, 0);
       render("-o");
       pp(_implicationtype.ltyp_2, 1);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.ExplicationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.ExplicationType _explicationtype = (com.synereo.ltcctbc.frugl.Absyn.ExplicationType) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_explicationtype.ltyp_1, 1);
       render("->");
       pp(_explicationtype.ltyp_2, 2);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.FusionType)
    {
       com.synereo.ltcctbc.frugl.Absyn.FusionType _fusiontype = (com.synereo.ltcctbc.frugl.Absyn.FusionType) foo;
       if (_i_ > 2) render(_L_PAREN);
       pp(_fusiontype.ltyp_1, 2);
       render("#");
       pp(_fusiontype.ltyp_2, 3);
       if (_i_ > 2) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.SeparationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.SeparationType _separationtype = (com.synereo.ltcctbc.frugl.Absyn.SeparationType) foo;
       if (_i_ > 3) render(_L_PAREN);
       pp(_separationtype.ltyp_1, 3);
       render("*");
       pp(_separationtype.ltyp_2, 4);
       if (_i_ > 3) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.SelectionType)
    {
       com.synereo.ltcctbc.frugl.Absyn.SelectionType _selectiontype = (com.synereo.ltcctbc.frugl.Absyn.SelectionType) foo;
       if (_i_ > 4) render(_L_PAREN);
       pp(_selectiontype.ltyp_1, 4);
       render("+");
       pp(_selectiontype.ltyp_2, 5);
       if (_i_ > 4) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.ConfirmationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.ConfirmationType _confirmationtype = (com.synereo.ltcctbc.frugl.Absyn.ConfirmationType) foo;
       if (_i_ > 5) render(_L_PAREN);
       pp(_confirmationtype.ltyp_1, 5);
       render("&");
       pp(_confirmationtype.ltyp_2, 6);
       if (_i_ > 5) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.DurationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.DurationType _durationtype = (com.synereo.ltcctbc.frugl.Absyn.DurationType) foo;
       if (_i_ > 6) render(_L_PAREN);
       render("?");
       pp(_durationtype.ltyp_, 7);
       if (_i_ > 6) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.ReplicationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.ReplicationType _replicationtype = (com.synereo.ltcctbc.frugl.Absyn.ReplicationType) foo;
       if (_i_ > 6) render(_L_PAREN);
       render("!");
       pp(_replicationtype.ltyp_, 7);
       if (_i_ > 6) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.NegationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.NegationType _negationtype = (com.synereo.ltcctbc.frugl.Absyn.NegationType) foo;
       if (_i_ > 6) render(_L_PAREN);
       render("~");
       pp(_negationtype.ltyp_, 7);
       if (_i_ > 6) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.FoundationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.FoundationType _foundationtype = (com.synereo.ltcctbc.frugl.Absyn.FoundationType) foo;
       if (_i_ > 7) render(_L_PAREN);
       pp(_foundationtype.groundtype_, 0);
       if (_i_ > 7) render(_R_PAREN);
    }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.GroundType foo, int _i_)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.UserDefinedType)
    {
       com.synereo.ltcctbc.frugl.Absyn.UserDefinedType _userdefinedtype = (com.synereo.ltcctbc.frugl.Absyn.UserDefinedType) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_userdefinedtype.uident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.IntegerType)
    {
       com.synereo.ltcctbc.frugl.Absyn.IntegerType _integertype = (com.synereo.ltcctbc.frugl.Absyn.IntegerType) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Int");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.DoubleType)
    {
       com.synereo.ltcctbc.frugl.Absyn.DoubleType _doubletype = (com.synereo.ltcctbc.frugl.Absyn.DoubleType) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Double");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.StringType)
    {
       com.synereo.ltcctbc.frugl.Absyn.StringType _stringtype = (com.synereo.ltcctbc.frugl.Absyn.StringType) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("String");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.UnitType)
    {
       com.synereo.ltcctbc.frugl.Absyn.UnitType _unittype = (com.synereo.ltcctbc.frugl.Absyn.UnitType) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Unit");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.VoidType)
    {
       com.synereo.ltcctbc.frugl.Absyn.VoidType _voidtype = (com.synereo.ltcctbc.frugl.Absyn.VoidType) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("Void");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.BTCType)
    {
       com.synereo.ltcctbc.frugl.Absyn.BTCType _btctype = (com.synereo.ltcctbc.frugl.Absyn.BTCType) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("BTC");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.ListTerm foo, int _i_)
  {
     for (java.util.Iterator<Term> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.ListTypedTerm foo, int _i_)
  {
     for (java.util.Iterator<TypedTerm> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.ListCoEquation foo, int _i_)
  {
     for (java.util.Iterator<CoEquation> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
     }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.ListPfExpr foo, int _i_)
  {
     for (java.util.Iterator<PfExpr> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.ListNominal foo, int _i_)
  {
     for (java.util.Iterator<Nominal> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(com.synereo.ltcctbc.frugl.Absyn.ListFormalExpr foo, int _i_)
  {
     for (java.util.Iterator<FormalExpr> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }


  private static void sh(com.synereo.ltcctbc.frugl.Absyn.TypedExpr foo)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.TypeJudgment)
    {
       com.synereo.ltcctbc.frugl.Absyn.TypeJudgment _typejudgment = (com.synereo.ltcctbc.frugl.Absyn.TypeJudgment) foo;
       render("(");
       render("TypeJudgment");
       render("[");
       sh(_typejudgment.listtypedterm_);
       render("]");
       render("[");
       sh(_typejudgment.listcoequation_);
       render("]");
       render(")");
    }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.TypedTerm foo)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.TypeAssignment)
    {
       com.synereo.ltcctbc.frugl.Absyn.TypeAssignment _typeassignment = (com.synereo.ltcctbc.frugl.Absyn.TypeAssignment) foo;
       render("(");
       render("TypeAssignment");
       sh(_typeassignment.term_);
       sh(_typeassignment.ltyp_);
       render(")");
    }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.PfExpr foo)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.ProofExpression)
    {
       com.synereo.ltcctbc.frugl.Absyn.ProofExpression _proofexpression = (com.synereo.ltcctbc.frugl.Absyn.ProofExpression) foo;
       render("(");
       render("ProofExpression");
       render("[");
       sh(_proofexpression.listterm_);
       render("]");
       render("[");
       sh(_proofexpression.listcoequation_);
       render("]");
       render(")");
    }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.CoEquation foo)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.CutExpression)
    {
       com.synereo.ltcctbc.frugl.Absyn.CutExpression _cutexpression = (com.synereo.ltcctbc.frugl.Absyn.CutExpression) foo;
       render("(");
       render("CutExpression");
       sh(_cutexpression.term_1);
       sh(_cutexpression.term_2);
       render(")");
    }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.Term foo)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Fusion)
    {
       com.synereo.ltcctbc.frugl.Absyn.Fusion _fusion = (com.synereo.ltcctbc.frugl.Absyn.Fusion) foo;
       render("(");
       render("Fusion");
       sh(_fusion.term_1);
       sh(_fusion.term_2);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Separation)
    {
       com.synereo.ltcctbc.frugl.Absyn.Separation _separation = (com.synereo.ltcctbc.frugl.Absyn.Separation) foo;
       render("(");
       render("Separation");
       sh(_separation.term_1);
       sh(_separation.term_2);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Duplication)
    {
       com.synereo.ltcctbc.frugl.Absyn.Duplication _duplication = (com.synereo.ltcctbc.frugl.Absyn.Duplication) foo;
       render("(");
       render("Duplication");
       sh(_duplication.term_1);
       sh(_duplication.term_2);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Selection)
    {
       com.synereo.ltcctbc.frugl.Absyn.Selection _selection = (com.synereo.ltcctbc.frugl.Absyn.Selection) foo;
       render("(");
       render("Selection");
       render("[");
       sh(_selection.listnominal_);
       render("]");
       sh(_selection.pfexpr_1);
       sh(_selection.pfexpr_2);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Duration)
    {
       com.synereo.ltcctbc.frugl.Absyn.Duration _duration = (com.synereo.ltcctbc.frugl.Absyn.Duration) foo;
       render("(");
       render("Duration");
       sh(_duration.term_);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.InjectionLeft)
    {
       com.synereo.ltcctbc.frugl.Absyn.InjectionLeft _injectionleft = (com.synereo.ltcctbc.frugl.Absyn.InjectionLeft) foo;
       render("(");
       render("InjectionLeft");
       sh(_injectionleft.term_);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.InjectionRight)
    {
       com.synereo.ltcctbc.frugl.Absyn.InjectionRight _injectionright = (com.synereo.ltcctbc.frugl.Absyn.InjectionRight) foo;
       render("(");
       render("InjectionRight");
       sh(_injectionright.term_);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Extraction)
    {
       com.synereo.ltcctbc.frugl.Absyn.Extraction _extraction = (com.synereo.ltcctbc.frugl.Absyn.Extraction) foo;
       render("(");
       render("Extraction");
       render("[");
       sh(_extraction.listnominal_);
       render("]");
       render("[");
       sh(_extraction.listpfexpr_);
       render("]");
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Mention)
    {
       com.synereo.ltcctbc.frugl.Absyn.Mention _mention = (com.synereo.ltcctbc.frugl.Absyn.Mention) foo;
       render("(");
       render("Mention");
       sh(_mention.formalexpr_);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Value)
    {
       com.synereo.ltcctbc.frugl.Absyn.Value _value = (com.synereo.ltcctbc.frugl.Absyn.Value) foo;
       render("(");
       render("Value");
       sh(_value.valueexpr_);
       render(")");
    }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.Nominal foo)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Transcription)
    {
       com.synereo.ltcctbc.frugl.Absyn.Transcription _transcription = (com.synereo.ltcctbc.frugl.Absyn.Transcription) foo;
       render("(");
       render("Transcription");
       sh(_transcription.pfexpr_);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.AtomLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.AtomLiteral _atomliteral = (com.synereo.ltcctbc.frugl.Absyn.AtomLiteral) foo;
       render("(");
       render("AtomLiteral");
       sh(_atomliteral.lident_);
       render(")");
    }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.FormalExpr foo)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Indication)
    {
       com.synereo.ltcctbc.frugl.Absyn.Indication _indication = (com.synereo.ltcctbc.frugl.Absyn.Indication) foo;
       render("(");
       render("Indication");
       sh(_indication.nominal_);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.Wildcard)
    {
       com.synereo.ltcctbc.frugl.Absyn.Wildcard _wildcard = (com.synereo.ltcctbc.frugl.Absyn.Wildcard) foo;
       render("Wildcard");
    }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.ValueExpr foo)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.DecimalLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.DecimalLiteral _decimalliteral = (com.synereo.ltcctbc.frugl.Absyn.DecimalLiteral) foo;
       render("(");
       render("DecimalLiteral");
       sh(_decimalliteral.double_);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.IntegerLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.IntegerLiteral _integerliteral = (com.synereo.ltcctbc.frugl.Absyn.IntegerLiteral) foo;
       render("(");
       render("IntegerLiteral");
       sh(_integerliteral.integer_);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.StringLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.StringLiteral _stringliteral = (com.synereo.ltcctbc.frugl.Absyn.StringLiteral) foo;
       render("(");
       render("StringLiteral");
       sh(_stringliteral.string_);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.UnitLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.UnitLiteral _unitliteral = (com.synereo.ltcctbc.frugl.Absyn.UnitLiteral) foo;
       render("UnitLiteral");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.CoUnitLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.CoUnitLiteral _counitliteral = (com.synereo.ltcctbc.frugl.Absyn.CoUnitLiteral) foo;
       render("CoUnitLiteral");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.BTCUnitLiteral)
    {
       com.synereo.ltcctbc.frugl.Absyn.BTCUnitLiteral _btcunitliteral = (com.synereo.ltcctbc.frugl.Absyn.BTCUnitLiteral) foo;
       render("BTCUnitLiteral");
    }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.LTyp foo)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.ImplicationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.ImplicationType _implicationtype = (com.synereo.ltcctbc.frugl.Absyn.ImplicationType) foo;
       render("(");
       render("ImplicationType");
       sh(_implicationtype.ltyp_1);
       sh(_implicationtype.ltyp_2);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.ExplicationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.ExplicationType _explicationtype = (com.synereo.ltcctbc.frugl.Absyn.ExplicationType) foo;
       render("(");
       render("ExplicationType");
       sh(_explicationtype.ltyp_1);
       sh(_explicationtype.ltyp_2);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.FusionType)
    {
       com.synereo.ltcctbc.frugl.Absyn.FusionType _fusiontype = (com.synereo.ltcctbc.frugl.Absyn.FusionType) foo;
       render("(");
       render("FusionType");
       sh(_fusiontype.ltyp_1);
       sh(_fusiontype.ltyp_2);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.SeparationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.SeparationType _separationtype = (com.synereo.ltcctbc.frugl.Absyn.SeparationType) foo;
       render("(");
       render("SeparationType");
       sh(_separationtype.ltyp_1);
       sh(_separationtype.ltyp_2);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.SelectionType)
    {
       com.synereo.ltcctbc.frugl.Absyn.SelectionType _selectiontype = (com.synereo.ltcctbc.frugl.Absyn.SelectionType) foo;
       render("(");
       render("SelectionType");
       sh(_selectiontype.ltyp_1);
       sh(_selectiontype.ltyp_2);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.ConfirmationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.ConfirmationType _confirmationtype = (com.synereo.ltcctbc.frugl.Absyn.ConfirmationType) foo;
       render("(");
       render("ConfirmationType");
       sh(_confirmationtype.ltyp_1);
       sh(_confirmationtype.ltyp_2);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.DurationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.DurationType _durationtype = (com.synereo.ltcctbc.frugl.Absyn.DurationType) foo;
       render("(");
       render("DurationType");
       sh(_durationtype.ltyp_);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.ReplicationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.ReplicationType _replicationtype = (com.synereo.ltcctbc.frugl.Absyn.ReplicationType) foo;
       render("(");
       render("ReplicationType");
       sh(_replicationtype.ltyp_);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.NegationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.NegationType _negationtype = (com.synereo.ltcctbc.frugl.Absyn.NegationType) foo;
       render("(");
       render("NegationType");
       sh(_negationtype.ltyp_);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.FoundationType)
    {
       com.synereo.ltcctbc.frugl.Absyn.FoundationType _foundationtype = (com.synereo.ltcctbc.frugl.Absyn.FoundationType) foo;
       render("(");
       render("FoundationType");
       sh(_foundationtype.groundtype_);
       render(")");
    }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.GroundType foo)
  {
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.UserDefinedType)
    {
       com.synereo.ltcctbc.frugl.Absyn.UserDefinedType _userdefinedtype = (com.synereo.ltcctbc.frugl.Absyn.UserDefinedType) foo;
       render("(");
       render("UserDefinedType");
       sh(_userdefinedtype.uident_);
       render(")");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.IntegerType)
    {
       com.synereo.ltcctbc.frugl.Absyn.IntegerType _integertype = (com.synereo.ltcctbc.frugl.Absyn.IntegerType) foo;
       render("IntegerType");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.DoubleType)
    {
       com.synereo.ltcctbc.frugl.Absyn.DoubleType _doubletype = (com.synereo.ltcctbc.frugl.Absyn.DoubleType) foo;
       render("DoubleType");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.StringType)
    {
       com.synereo.ltcctbc.frugl.Absyn.StringType _stringtype = (com.synereo.ltcctbc.frugl.Absyn.StringType) foo;
       render("StringType");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.UnitType)
    {
       com.synereo.ltcctbc.frugl.Absyn.UnitType _unittype = (com.synereo.ltcctbc.frugl.Absyn.UnitType) foo;
       render("UnitType");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.VoidType)
    {
       com.synereo.ltcctbc.frugl.Absyn.VoidType _voidtype = (com.synereo.ltcctbc.frugl.Absyn.VoidType) foo;
       render("VoidType");
    }
    if (foo instanceof com.synereo.ltcctbc.frugl.Absyn.BTCType)
    {
       com.synereo.ltcctbc.frugl.Absyn.BTCType _btctype = (com.synereo.ltcctbc.frugl.Absyn.BTCType) foo;
       render("BTCType");
    }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.ListTerm foo)
  {
     for (java.util.Iterator<Term> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.ListTypedTerm foo)
  {
     for (java.util.Iterator<TypedTerm> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.ListCoEquation foo)
  {
     for (java.util.Iterator<CoEquation> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.ListPfExpr foo)
  {
     for (java.util.Iterator<PfExpr> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.ListNominal foo)
  {
     for (java.util.Iterator<Nominal> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(com.synereo.ltcctbc.frugl.Absyn.ListFormalExpr foo)
  {
     for (java.util.Iterator<FormalExpr> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }


  private static void pp(Integer n, int _i_) { buf_.append(n); buf_.append(" "); }
  private static void pp(Double d, int _i_) { buf_.append(d); buf_.append(" "); }
  private static void pp(String s, int _i_) { buf_.append(s); buf_.append(" "); }
  private static void pp(Character c, int _i_) { buf_.append("'" + c.toString() + "'"); buf_.append(" "); }
  private static void sh(Integer n) { render(n.toString()); }
  private static void sh(Double d) { render(d.toString()); }
  private static void sh(Character c) { render(c.toString()); }
  private static void sh(String s) { printQuoted(s); }
  private static void printQuoted(String s) { render("\"" + s + "\""); }
  private static void indent()
  {
    int n = _n_;
    while (n > 0)
    {
      buf_.append(" ");
      n--;
    }
  }
  private static void backup()
  {
     if (buf_.charAt(buf_.length() - 1) == ' ') {
      buf_.setLength(buf_.length() - 1);
    }
  }
  private static void trim()
  {
     while (buf_.length() > 0 && buf_.charAt(0) == ' ')
        buf_.deleteCharAt(0); 
    while (buf_.length() > 0 && buf_.charAt(buf_.length()-1) == ' ')
        buf_.deleteCharAt(buf_.length()-1);
  }
  private static int _n_ = 0;
  private static StringBuilder buf_ = new StringBuilder(INITIAL_BUFFER_SIZE);
}

