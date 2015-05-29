package com.synereo.ltcctbc.frugl;
import com.synereo.ltcctbc.frugl.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  com.synereo.ltcctbc.frugl.Absyn.TypedExpr.Visitor<com.synereo.ltcctbc.frugl.Absyn.TypedExpr,A>,
  com.synereo.ltcctbc.frugl.Absyn.TypedTerm.Visitor<com.synereo.ltcctbc.frugl.Absyn.TypedTerm,A>,
  com.synereo.ltcctbc.frugl.Absyn.PfExpr.Visitor<com.synereo.ltcctbc.frugl.Absyn.PfExpr,A>,
  com.synereo.ltcctbc.frugl.Absyn.CoEquation.Visitor<com.synereo.ltcctbc.frugl.Absyn.CoEquation,A>,
  com.synereo.ltcctbc.frugl.Absyn.Term.Visitor<com.synereo.ltcctbc.frugl.Absyn.Term,A>,
  com.synereo.ltcctbc.frugl.Absyn.Nominal.Visitor<com.synereo.ltcctbc.frugl.Absyn.Nominal,A>,
  com.synereo.ltcctbc.frugl.Absyn.FormalExpr.Visitor<com.synereo.ltcctbc.frugl.Absyn.FormalExpr,A>,
  com.synereo.ltcctbc.frugl.Absyn.ValueExpr.Visitor<com.synereo.ltcctbc.frugl.Absyn.ValueExpr,A>,
  com.synereo.ltcctbc.frugl.Absyn.LTyp.Visitor<com.synereo.ltcctbc.frugl.Absyn.LTyp,A>,
  com.synereo.ltcctbc.frugl.Absyn.GroundType.Visitor<com.synereo.ltcctbc.frugl.Absyn.GroundType,A>
{
/* TypedExpr */
    public TypedExpr visit(com.synereo.ltcctbc.frugl.Absyn.TypeJudgment p, A arg)
    {
      ListTypedTerm listtypedterm_ = new ListTypedTerm();
      for (TypedTerm x : p.listtypedterm_) {
        listtypedterm_.add(x.accept(this,arg));
      }
      ListCoEquation listcoequation_ = new ListCoEquation();
      for (CoEquation x : p.listcoequation_) {
        listcoequation_.add(x.accept(this,arg));
      }

      return new com.synereo.ltcctbc.frugl.Absyn.TypeJudgment(listtypedterm_, listcoequation_);
    }

/* TypedTerm */
    public TypedTerm visit(com.synereo.ltcctbc.frugl.Absyn.TypeAssignment p, A arg)
    {
      Term term_ = p.term_.accept(this, arg);
      LTyp ltyp_ = p.ltyp_.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.TypeAssignment(term_, ltyp_);
    }

/* PfExpr */
    public PfExpr visit(com.synereo.ltcctbc.frugl.Absyn.ProofExpression p, A arg)
    {
      ListTerm listterm_ = new ListTerm();
      for (Term x : p.listterm_) {
        listterm_.add(x.accept(this,arg));
      }
      ListCoEquation listcoequation_ = new ListCoEquation();
      for (CoEquation x : p.listcoequation_) {
        listcoequation_.add(x.accept(this,arg));
      }

      return new com.synereo.ltcctbc.frugl.Absyn.ProofExpression(listterm_, listcoequation_);
    }

/* CoEquation */
    public CoEquation visit(com.synereo.ltcctbc.frugl.Absyn.CutExpression p, A arg)
    {
      Term term_1 = p.term_1.accept(this, arg);
      Term term_2 = p.term_2.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.CutExpression(term_1, term_2);
    }

/* Term */
    public Term visit(com.synereo.ltcctbc.frugl.Absyn.Fusion p, A arg)
    {
      Term term_1 = p.term_1.accept(this, arg);
      Term term_2 = p.term_2.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.Fusion(term_1, term_2);
    }
    public Term visit(com.synereo.ltcctbc.frugl.Absyn.Separation p, A arg)
    {
      Term term_1 = p.term_1.accept(this, arg);
      Term term_2 = p.term_2.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.Separation(term_1, term_2);
    }
    public Term visit(com.synereo.ltcctbc.frugl.Absyn.Duplication p, A arg)
    {
      Term term_1 = p.term_1.accept(this, arg);
      Term term_2 = p.term_2.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.Duplication(term_1, term_2);
    }
    public Term visit(com.synereo.ltcctbc.frugl.Absyn.Selection p, A arg)
    {
      ListNominal listnominal_ = new ListNominal();
      for (Nominal x : p.listnominal_) {
        listnominal_.add(x.accept(this,arg));
      }
      PfExpr pfexpr_1 = p.pfexpr_1.accept(this, arg);
      PfExpr pfexpr_2 = p.pfexpr_2.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.Selection(listnominal_, pfexpr_1, pfexpr_2);
    }
    public Term visit(com.synereo.ltcctbc.frugl.Absyn.Duration p, A arg)
    {
      Term term_ = p.term_.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.Duration(term_);
    }
    public Term visit(com.synereo.ltcctbc.frugl.Absyn.InjectionLeft p, A arg)
    {
      Term term_ = p.term_.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.InjectionLeft(term_);
    }
    public Term visit(com.synereo.ltcctbc.frugl.Absyn.InjectionRight p, A arg)
    {
      Term term_ = p.term_.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.InjectionRight(term_);
    }
    public Term visit(com.synereo.ltcctbc.frugl.Absyn.Extraction p, A arg)
    {
      ListNominal listnominal_ = new ListNominal();
      for (Nominal x : p.listnominal_) {
        listnominal_.add(x.accept(this,arg));
      }
      ListPfExpr listpfexpr_ = new ListPfExpr();
      for (PfExpr x : p.listpfexpr_) {
        listpfexpr_.add(x.accept(this,arg));
      }

      return new com.synereo.ltcctbc.frugl.Absyn.Extraction(listnominal_, listpfexpr_);
    }
    public Term visit(com.synereo.ltcctbc.frugl.Absyn.Mention p, A arg)
    {
      FormalExpr formalexpr_ = p.formalexpr_.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.Mention(formalexpr_);
    }
    public Term visit(com.synereo.ltcctbc.frugl.Absyn.Value p, A arg)
    {
      ValueExpr valueexpr_ = p.valueexpr_.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.Value(valueexpr_);
    }

/* Nominal */
    public Nominal visit(com.synereo.ltcctbc.frugl.Absyn.Transcription p, A arg)
    {
      PfExpr pfexpr_ = p.pfexpr_.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.Transcription(pfexpr_);
    }
    public Nominal visit(com.synereo.ltcctbc.frugl.Absyn.AtomLiteral p, A arg)
    {
      String lident_ = p.lident_;

      return new com.synereo.ltcctbc.frugl.Absyn.AtomLiteral(lident_);
    }

/* FormalExpr */
    public FormalExpr visit(com.synereo.ltcctbc.frugl.Absyn.Indication p, A arg)
    {
      Nominal nominal_ = p.nominal_.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.Indication(nominal_);
    }
    public FormalExpr visit(com.synereo.ltcctbc.frugl.Absyn.Wildcard p, A arg)
    {

      return new com.synereo.ltcctbc.frugl.Absyn.Wildcard();
    }

/* ValueExpr */
    public ValueExpr visit(com.synereo.ltcctbc.frugl.Absyn.DecimalLiteral p, A arg)
    {
      Double double_ = p.double_;

      return new com.synereo.ltcctbc.frugl.Absyn.DecimalLiteral(double_);
    }
    public ValueExpr visit(com.synereo.ltcctbc.frugl.Absyn.IntegerLiteral p, A arg)
    {
      Integer integer_ = p.integer_;

      return new com.synereo.ltcctbc.frugl.Absyn.IntegerLiteral(integer_);
    }
    public ValueExpr visit(com.synereo.ltcctbc.frugl.Absyn.StringLiteral p, A arg)
    {
      String string_ = p.string_;

      return new com.synereo.ltcctbc.frugl.Absyn.StringLiteral(string_);
    }
    public ValueExpr visit(com.synereo.ltcctbc.frugl.Absyn.UnitLiteral p, A arg)
    {

      return new com.synereo.ltcctbc.frugl.Absyn.UnitLiteral();
    }
    public ValueExpr visit(com.synereo.ltcctbc.frugl.Absyn.CoUnitLiteral p, A arg)
    {

      return new com.synereo.ltcctbc.frugl.Absyn.CoUnitLiteral();
    }
    public ValueExpr visit(com.synereo.ltcctbc.frugl.Absyn.BTCUnitLiteral p, A arg)
    {

      return new com.synereo.ltcctbc.frugl.Absyn.BTCUnitLiteral();
    }

/* LTyp */
    public LTyp visit(com.synereo.ltcctbc.frugl.Absyn.ImplicationType p, A arg)
    {
      LTyp ltyp_1 = p.ltyp_1.accept(this, arg);
      LTyp ltyp_2 = p.ltyp_2.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.ImplicationType(ltyp_1, ltyp_2);
    }
    public LTyp visit(com.synereo.ltcctbc.frugl.Absyn.ExplicationType p, A arg)
    {
      LTyp ltyp_1 = p.ltyp_1.accept(this, arg);
      LTyp ltyp_2 = p.ltyp_2.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.ExplicationType(ltyp_1, ltyp_2);
    }
    public LTyp visit(com.synereo.ltcctbc.frugl.Absyn.FusionType p, A arg)
    {
      LTyp ltyp_1 = p.ltyp_1.accept(this, arg);
      LTyp ltyp_2 = p.ltyp_2.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.FusionType(ltyp_1, ltyp_2);
    }
    public LTyp visit(com.synereo.ltcctbc.frugl.Absyn.SeparationType p, A arg)
    {
      LTyp ltyp_1 = p.ltyp_1.accept(this, arg);
      LTyp ltyp_2 = p.ltyp_2.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.SeparationType(ltyp_1, ltyp_2);
    }
    public LTyp visit(com.synereo.ltcctbc.frugl.Absyn.SelectionType p, A arg)
    {
      LTyp ltyp_1 = p.ltyp_1.accept(this, arg);
      LTyp ltyp_2 = p.ltyp_2.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.SelectionType(ltyp_1, ltyp_2);
    }
    public LTyp visit(com.synereo.ltcctbc.frugl.Absyn.ConfirmationType p, A arg)
    {
      LTyp ltyp_1 = p.ltyp_1.accept(this, arg);
      LTyp ltyp_2 = p.ltyp_2.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.ConfirmationType(ltyp_1, ltyp_2);
    }
    public LTyp visit(com.synereo.ltcctbc.frugl.Absyn.DurationType p, A arg)
    {
      LTyp ltyp_ = p.ltyp_.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.DurationType(ltyp_);
    }
    public LTyp visit(com.synereo.ltcctbc.frugl.Absyn.ReplicationType p, A arg)
    {
      LTyp ltyp_ = p.ltyp_.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.ReplicationType(ltyp_);
    }
    public LTyp visit(com.synereo.ltcctbc.frugl.Absyn.NegationType p, A arg)
    {
      LTyp ltyp_ = p.ltyp_.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.NegationType(ltyp_);
    }
    public LTyp visit(com.synereo.ltcctbc.frugl.Absyn.FoundationType p, A arg)
    {
      GroundType groundtype_ = p.groundtype_.accept(this, arg);

      return new com.synereo.ltcctbc.frugl.Absyn.FoundationType(groundtype_);
    }

/* GroundType */
    public GroundType visit(com.synereo.ltcctbc.frugl.Absyn.UserDefinedType p, A arg)
    {
      String uident_ = p.uident_;

      return new com.synereo.ltcctbc.frugl.Absyn.UserDefinedType(uident_);
    }
    public GroundType visit(com.synereo.ltcctbc.frugl.Absyn.IntegerType p, A arg)
    {

      return new com.synereo.ltcctbc.frugl.Absyn.IntegerType();
    }
    public GroundType visit(com.synereo.ltcctbc.frugl.Absyn.DoubleType p, A arg)
    {

      return new com.synereo.ltcctbc.frugl.Absyn.DoubleType();
    }
    public GroundType visit(com.synereo.ltcctbc.frugl.Absyn.StringType p, A arg)
    {

      return new com.synereo.ltcctbc.frugl.Absyn.StringType();
    }
    public GroundType visit(com.synereo.ltcctbc.frugl.Absyn.UnitType p, A arg)
    {

      return new com.synereo.ltcctbc.frugl.Absyn.UnitType();
    }
    public GroundType visit(com.synereo.ltcctbc.frugl.Absyn.VoidType p, A arg)
    {

      return new com.synereo.ltcctbc.frugl.Absyn.VoidType();
    }
    public GroundType visit(com.synereo.ltcctbc.frugl.Absyn.BTCType p, A arg)
    {

      return new com.synereo.ltcctbc.frugl.Absyn.BTCType();
    }

}