package com.synereo.ltcctbc.frugl;

import com.synereo.ltcctbc.frugl.Absyn.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/** BNFC-Generated Fold Visitor */
public abstract class FoldVisitor<R,A> implements AllVisitor<R,A> {
    public abstract R leaf(A arg);
    public abstract R combine(R x, R y, A arg);

/* TypedExpr */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.TypeJudgment p, A arg) {
      R r = leaf(arg);
      for (TypedTerm x : p.listtypedterm_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      for (CoEquation x : p.listcoequation_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }

/* TypedTerm */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.TypeAssignment p, A arg) {
      R r = leaf(arg);
      r = combine(p.term_.accept(this, arg), r, arg);
      r = combine(p.ltyp_.accept(this, arg), r, arg);
      return r;
    }

/* PfExpr */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.ProofExpression p, A arg) {
      R r = leaf(arg);
      for (Term x : p.listterm_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      for (CoEquation x : p.listcoequation_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }

/* CoEquation */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.CutExpression p, A arg) {
      R r = leaf(arg);
      r = combine(p.term_1.accept(this, arg), r, arg);
      r = combine(p.term_2.accept(this, arg), r, arg);
      return r;
    }

/* Term */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Fusion p, A arg) {
      R r = leaf(arg);
      r = combine(p.term_1.accept(this, arg), r, arg);
      r = combine(p.term_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Separation p, A arg) {
      R r = leaf(arg);
      r = combine(p.term_1.accept(this, arg), r, arg);
      r = combine(p.term_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Duplication p, A arg) {
      R r = leaf(arg);
      r = combine(p.term_1.accept(this, arg), r, arg);
      r = combine(p.term_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Selection p, A arg) {
      R r = leaf(arg);
      for (Nominal x : p.listnominal_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      r = combine(p.pfexpr_1.accept(this, arg), r, arg);
      r = combine(p.pfexpr_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Duration p, A arg) {
      R r = leaf(arg);
      r = combine(p.term_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.InjectionLeft p, A arg) {
      R r = leaf(arg);
      r = combine(p.term_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.InjectionRight p, A arg) {
      R r = leaf(arg);
      r = combine(p.term_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Extraction p, A arg) {
      R r = leaf(arg);
      for (Nominal x : p.listnominal_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      for (PfExpr x : p.listpfexpr_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Mention p, A arg) {
      R r = leaf(arg);
      r = combine(p.formalexpr_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Value p, A arg) {
      R r = leaf(arg);
      r = combine(p.valueexpr_.accept(this, arg), r, arg);
      return r;
    }

/* Nominal */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Transcription p, A arg) {
      R r = leaf(arg);
      r = combine(p.pfexpr_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.AtomLiteral p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* FormalExpr */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Indication p, A arg) {
      R r = leaf(arg);
      r = combine(p.nominal_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Wildcard p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* ValueExpr */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.DecimalLiteral p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.IntegerLiteral p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.StringLiteral p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.UnitLiteral p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.CoUnitLiteral p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.BTCUnitLiteral p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* LTyp */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.ImplicationType p, A arg) {
      R r = leaf(arg);
      r = combine(p.ltyp_1.accept(this, arg), r, arg);
      r = combine(p.ltyp_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.ExplicationType p, A arg) {
      R r = leaf(arg);
      r = combine(p.ltyp_1.accept(this, arg), r, arg);
      r = combine(p.ltyp_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.FusionType p, A arg) {
      R r = leaf(arg);
      r = combine(p.ltyp_1.accept(this, arg), r, arg);
      r = combine(p.ltyp_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.SeparationType p, A arg) {
      R r = leaf(arg);
      r = combine(p.ltyp_1.accept(this, arg), r, arg);
      r = combine(p.ltyp_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.SelectionType p, A arg) {
      R r = leaf(arg);
      r = combine(p.ltyp_1.accept(this, arg), r, arg);
      r = combine(p.ltyp_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.ConfirmationType p, A arg) {
      R r = leaf(arg);
      r = combine(p.ltyp_1.accept(this, arg), r, arg);
      r = combine(p.ltyp_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.DurationType p, A arg) {
      R r = leaf(arg);
      r = combine(p.ltyp_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.ReplicationType p, A arg) {
      R r = leaf(arg);
      r = combine(p.ltyp_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.NegationType p, A arg) {
      R r = leaf(arg);
      r = combine(p.ltyp_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.FoundationType p, A arg) {
      R r = leaf(arg);
      r = combine(p.groundtype_.accept(this, arg), r, arg);
      return r;
    }

/* GroundType */
    public R visit(com.synereo.ltcctbc.frugl.Absyn.UserDefinedType p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.IntegerType p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.DoubleType p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.StringType p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.UnitType p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.VoidType p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.BTCType p, A arg) {
      R r = leaf(arg);
      return r;
    }


}
