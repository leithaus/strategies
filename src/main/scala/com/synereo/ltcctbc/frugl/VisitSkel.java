package com.synereo.ltcctbc.frugl;
import com.synereo.ltcctbc.frugl.Absyn.*;
/*** BNFC-Generated Visitor Design Pattern Skeleton. ***/
/* This implements the common visitor design pattern.
   Tests show it to be slightly less efficient than the
   instanceof method, but easier to use. 
   Replace the R and A parameters with the desired return
   and context types.*/

public class VisitSkel
{
  public class TypedExprVisitor<R,A> implements TypedExpr.Visitor<R,A>
  {
    public R visit(com.synereo.ltcctbc.frugl.Absyn.TypeJudgment p, A arg)
    {
      /* Code For TypeJudgment Goes Here */

      for (TypedTerm x : p.listtypedterm_) {
      }
      for (CoEquation x : p.listcoequation_) {
      }

      return null;
    }

  }
  public class TypedTermVisitor<R,A> implements TypedTerm.Visitor<R,A>
  {
    public R visit(com.synereo.ltcctbc.frugl.Absyn.TypeAssignment p, A arg)
    {
      /* Code For TypeAssignment Goes Here */

      p.term_.accept(new TermVisitor<R,A>(), arg);
      p.ltyp_.accept(new LTypVisitor<R,A>(), arg);

      return null;
    }

  }
  public class PfExprVisitor<R,A> implements PfExpr.Visitor<R,A>
  {
    public R visit(com.synereo.ltcctbc.frugl.Absyn.ProofExpression p, A arg)
    {
      /* Code For ProofExpression Goes Here */

      for (Term x : p.listterm_) {
      }
      for (CoEquation x : p.listcoequation_) {
      }

      return null;
    }

  }
  public class CoEquationVisitor<R,A> implements CoEquation.Visitor<R,A>
  {
    public R visit(com.synereo.ltcctbc.frugl.Absyn.CutExpression p, A arg)
    {
      /* Code For CutExpression Goes Here */

      p.term_1.accept(new TermVisitor<R,A>(), arg);
      p.term_2.accept(new TermVisitor<R,A>(), arg);

      return null;
    }

  }
  public class TermVisitor<R,A> implements Term.Visitor<R,A>
  {
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Fusion p, A arg)
    {
      /* Code For Fusion Goes Here */

      p.term_1.accept(new TermVisitor<R,A>(), arg);
      p.term_2.accept(new TermVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Separation p, A arg)
    {
      /* Code For Separation Goes Here */

      p.term_1.accept(new TermVisitor<R,A>(), arg);
      p.term_2.accept(new TermVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Duplication p, A arg)
    {
      /* Code For Duplication Goes Here */

      p.term_1.accept(new TermVisitor<R,A>(), arg);
      p.term_2.accept(new TermVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Selection p, A arg)
    {
      /* Code For Selection Goes Here */

      for (Nominal x : p.listnominal_) {
      }
      p.pfexpr_1.accept(new PfExprVisitor<R,A>(), arg);
      p.pfexpr_2.accept(new PfExprVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Duration p, A arg)
    {
      /* Code For Duration Goes Here */

      p.term_.accept(new TermVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.InjectionLeft p, A arg)
    {
      /* Code For InjectionLeft Goes Here */

      p.term_.accept(new TermVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.InjectionRight p, A arg)
    {
      /* Code For InjectionRight Goes Here */

      p.term_.accept(new TermVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Extraction p, A arg)
    {
      /* Code For Extraction Goes Here */

      for (Nominal x : p.listnominal_) {
      }
      for (PfExpr x : p.listpfexpr_) {
      }

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Mention p, A arg)
    {
      /* Code For Mention Goes Here */

      p.formalexpr_.accept(new FormalExprVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Value p, A arg)
    {
      /* Code For Value Goes Here */

      p.valueexpr_.accept(new ValueExprVisitor<R,A>(), arg);

      return null;
    }

  }
  public class NominalVisitor<R,A> implements Nominal.Visitor<R,A>
  {
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Transcription p, A arg)
    {
      /* Code For Transcription Goes Here */

      p.pfexpr_.accept(new PfExprVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.AtomLiteral p, A arg)
    {
      /* Code For AtomLiteral Goes Here */

      //p.lident_;

      return null;
    }

  }
  public class FormalExprVisitor<R,A> implements FormalExpr.Visitor<R,A>
  {
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Indication p, A arg)
    {
      /* Code For Indication Goes Here */

      p.nominal_.accept(new NominalVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Wildcard p, A arg)
    {
      /* Code For Wildcard Goes Here */


      return null;
    }

  }
  public class ValueExprVisitor<R,A> implements ValueExpr.Visitor<R,A>
  {
    public R visit(com.synereo.ltcctbc.frugl.Absyn.DecimalLiteral p, A arg)
    {
      /* Code For DecimalLiteral Goes Here */

      //p.double_;

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.IntegerLiteral p, A arg)
    {
      /* Code For IntegerLiteral Goes Here */

      //p.integer_;

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.StringLiteral p, A arg)
    {
      /* Code For StringLiteral Goes Here */

      //p.string_;

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.UnitLiteral p, A arg)
    {
      /* Code For UnitLiteral Goes Here */


      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.CoUnitLiteral p, A arg)
    {
      /* Code For CoUnitLiteral Goes Here */


      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.BTCUnitLiteral p, A arg)
    {
      /* Code For BTCUnitLiteral Goes Here */


      return null;
    }

  }
  public class LTypVisitor<R,A> implements LTyp.Visitor<R,A>
  {
    public R visit(com.synereo.ltcctbc.frugl.Absyn.ImplicationType p, A arg)
    {
      /* Code For ImplicationType Goes Here */

      p.ltyp_1.accept(new LTypVisitor<R,A>(), arg);
      p.ltyp_2.accept(new LTypVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.ExplicationType p, A arg)
    {
      /* Code For ExplicationType Goes Here */

      p.ltyp_1.accept(new LTypVisitor<R,A>(), arg);
      p.ltyp_2.accept(new LTypVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.FusionType p, A arg)
    {
      /* Code For FusionType Goes Here */

      p.ltyp_1.accept(new LTypVisitor<R,A>(), arg);
      p.ltyp_2.accept(new LTypVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.SeparationType p, A arg)
    {
      /* Code For SeparationType Goes Here */

      p.ltyp_1.accept(new LTypVisitor<R,A>(), arg);
      p.ltyp_2.accept(new LTypVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.SelectionType p, A arg)
    {
      /* Code For SelectionType Goes Here */

      p.ltyp_1.accept(new LTypVisitor<R,A>(), arg);
      p.ltyp_2.accept(new LTypVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.ConfirmationType p, A arg)
    {
      /* Code For ConfirmationType Goes Here */

      p.ltyp_1.accept(new LTypVisitor<R,A>(), arg);
      p.ltyp_2.accept(new LTypVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.DurationType p, A arg)
    {
      /* Code For DurationType Goes Here */

      p.ltyp_.accept(new LTypVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.ReplicationType p, A arg)
    {
      /* Code For ReplicationType Goes Here */

      p.ltyp_.accept(new LTypVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.NegationType p, A arg)
    {
      /* Code For NegationType Goes Here */

      p.ltyp_.accept(new LTypVisitor<R,A>(), arg);

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.FoundationType p, A arg)
    {
      /* Code For FoundationType Goes Here */

      p.groundtype_.accept(new GroundTypeVisitor<R,A>(), arg);

      return null;
    }

  }
  public class GroundTypeVisitor<R,A> implements GroundType.Visitor<R,A>
  {
    public R visit(com.synereo.ltcctbc.frugl.Absyn.UserDefinedType p, A arg)
    {
      /* Code For UserDefinedType Goes Here */

      //p.uident_;

      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.IntegerType p, A arg)
    {
      /* Code For IntegerType Goes Here */


      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.DoubleType p, A arg)
    {
      /* Code For DoubleType Goes Here */


      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.StringType p, A arg)
    {
      /* Code For StringType Goes Here */


      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.UnitType p, A arg)
    {
      /* Code For UnitType Goes Here */


      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.VoidType p, A arg)
    {
      /* Code For VoidType Goes Here */


      return null;
    }
    public R visit(com.synereo.ltcctbc.frugl.Absyn.BTCType p, A arg)
    {
      /* Code For BTCType Goes Here */


      return null;
    }

  }
}