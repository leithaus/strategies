package com.synereo.ltcctbc.frugl;
import com.synereo.ltcctbc.frugl.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* TypedExpr */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.TypeJudgment p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(com.synereo.ltcctbc.frugl.Absyn.TypedExpr p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* TypedTerm */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.TypeAssignment p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(com.synereo.ltcctbc.frugl.Absyn.TypedTerm p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* PfExpr */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.ProofExpression p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(com.synereo.ltcctbc.frugl.Absyn.PfExpr p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* CoEquation */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.CutExpression p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(com.synereo.ltcctbc.frugl.Absyn.CoEquation p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Term */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Fusion p, A arg) { return visitDefault(p, arg); }

    public R visit(com.synereo.ltcctbc.frugl.Absyn.Separation p, A arg) { return visitDefault(p, arg); }

    public R visit(com.synereo.ltcctbc.frugl.Absyn.Duplication p, A arg) { return visitDefault(p, arg); }

    public R visit(com.synereo.ltcctbc.frugl.Absyn.Selection p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Duration p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.InjectionLeft p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.InjectionRight p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Extraction p, A arg) { return visitDefault(p, arg); }

    public R visit(com.synereo.ltcctbc.frugl.Absyn.Mention p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Value p, A arg) { return visitDefault(p, arg); }

    public R visitDefault(com.synereo.ltcctbc.frugl.Absyn.Term p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Nominal */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Transcription p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.AtomLiteral p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(com.synereo.ltcctbc.frugl.Absyn.Nominal p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* FormalExpr */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Indication p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Wildcard p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(com.synereo.ltcctbc.frugl.Absyn.FormalExpr p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* ValueExpr */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.DecimalLiteral p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.IntegerLiteral p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.StringLiteral p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.UnitLiteral p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.CoUnitLiteral p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.BTCUnitLiteral p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(com.synereo.ltcctbc.frugl.Absyn.ValueExpr p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* LTyp */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.ImplicationType p, A arg) { return visitDefault(p, arg); }

    public R visit(com.synereo.ltcctbc.frugl.Absyn.ExplicationType p, A arg) { return visitDefault(p, arg); }

    public R visit(com.synereo.ltcctbc.frugl.Absyn.FusionType p, A arg) { return visitDefault(p, arg); }

    public R visit(com.synereo.ltcctbc.frugl.Absyn.SeparationType p, A arg) { return visitDefault(p, arg); }

    public R visit(com.synereo.ltcctbc.frugl.Absyn.SelectionType p, A arg) { return visitDefault(p, arg); }

    public R visit(com.synereo.ltcctbc.frugl.Absyn.ConfirmationType p, A arg) { return visitDefault(p, arg); }

    public R visit(com.synereo.ltcctbc.frugl.Absyn.DurationType p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.ReplicationType p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.NegationType p, A arg) { return visitDefault(p, arg); }

    public R visit(com.synereo.ltcctbc.frugl.Absyn.FoundationType p, A arg) { return visitDefault(p, arg); }

    public R visitDefault(com.synereo.ltcctbc.frugl.Absyn.LTyp p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* GroundType */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.UserDefinedType p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.IntegerType p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.DoubleType p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.StringType p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.UnitType p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.VoidType p, A arg) { return visitDefault(p, arg); }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.BTCType p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(com.synereo.ltcctbc.frugl.Absyn.GroundType p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
