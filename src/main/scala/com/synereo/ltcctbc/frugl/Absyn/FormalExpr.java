package com.synereo.ltcctbc.frugl.Absyn; // Java Package generated by the BNF Converter.

public abstract class FormalExpr implements java.io.Serializable {
  public abstract <R,A> R accept(FormalExpr.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Indication p, A arg);
    public R visit(com.synereo.ltcctbc.frugl.Absyn.Wildcard p, A arg);

  }

}
