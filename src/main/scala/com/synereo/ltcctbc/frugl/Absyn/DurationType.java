package com.synereo.ltcctbc.frugl.Absyn; // Java Package generated by the BNF Converter.

public class DurationType extends LTyp {
  public final LTyp ltyp_;

  public DurationType(LTyp p1) { ltyp_ = p1; }

  public <R,A> R accept(com.synereo.ltcctbc.frugl.Absyn.LTyp.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof com.synereo.ltcctbc.frugl.Absyn.DurationType) {
      com.synereo.ltcctbc.frugl.Absyn.DurationType x = (com.synereo.ltcctbc.frugl.Absyn.DurationType)o;
      return this.ltyp_.equals(x.ltyp_);
    }
    return false;
  }

  public int hashCode() {
    return this.ltyp_.hashCode();
  }


}