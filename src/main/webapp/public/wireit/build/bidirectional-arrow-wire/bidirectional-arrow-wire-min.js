YUI.add("bidirectional-arrow-wire",function(e,t){e.BidirectionalArrowWire=function(t){e.BidirectionalArrowWire.superclass.constructor.apply(this,arguments)},e.BidirectionalArrowWire.NAME="bidirectionalarrowwire",e.extend(e.BidirectionalArrowWire,e.ArrowWire,{_draw:function(){this.clear();var e=this.get("src").getXY(),t=this.get("tgt").getXY();this.moveTo(e[0]+6,e[1]+6),this.lineTo(t[0]+6,t[1]+6),this._drawArrow(e,t),this._drawArrow(t,e),this.end()}}),e.BidirectionalArrowWire.ATTRS=e.merge(e.ArrowWire.ATTRS,{})},"@VERSION@",{requires:["arrow-wire"]});
