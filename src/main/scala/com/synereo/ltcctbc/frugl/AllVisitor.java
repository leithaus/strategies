package com.synereo.ltcctbc.frugl;

import com.synereo.ltcctbc.frugl.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  com.synereo.ltcctbc.frugl.Absyn.TypedExpr.Visitor<R,A>,
  com.synereo.ltcctbc.frugl.Absyn.TypedTerm.Visitor<R,A>,
  com.synereo.ltcctbc.frugl.Absyn.PfExpr.Visitor<R,A>,
  com.synereo.ltcctbc.frugl.Absyn.CoEquation.Visitor<R,A>,
  com.synereo.ltcctbc.frugl.Absyn.Term.Visitor<R,A>,
  com.synereo.ltcctbc.frugl.Absyn.Nominal.Visitor<R,A>,
  com.synereo.ltcctbc.frugl.Absyn.FormalExpr.Visitor<R,A>,
  com.synereo.ltcctbc.frugl.Absyn.ValueExpr.Visitor<R,A>,
  com.synereo.ltcctbc.frugl.Absyn.LTyp.Visitor<R,A>,
  com.synereo.ltcctbc.frugl.Absyn.GroundType.Visitor<R,A>
{}
