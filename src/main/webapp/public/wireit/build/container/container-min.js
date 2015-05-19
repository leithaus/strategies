YUI.add("container",function(e,t){"use strict";e.Container=e.Base.create("container",e.Widget,[e.WidgetStdMod,e.WidgetStack,e.WidgetParent,e.WidgetChild,e.WiresDelegate,e.WidgetTerminals,e.WidgetIcons],{renderUI:function(){this._renderDrag(),this._renderResize()},bindUI:function(){this.resize&&this.resize.on("resize:resize",this._onResize,this),this.drag.on("drag:drag",function(){this.redrawAllWires()},this)},syncUI:function(){e.later(0,this,function(){this.alignTerminals()})},_renderDrag:function(){this.drag=new e.DD.Drag({node:this.get("boundingBox"),handles:[this._findStdModSection(e.WidgetStdMod.HEADER)]})},_renderResize:function(){if(!this.get("resizable"))return;this.resize=new e.Resize({node:this.get("contentBox"),handles:"br"})},_onResize:function(e){this._fillHeight(),this.alignTerminals();var t=this.get("boundingBox").get("region");this.set("width",e.details[0].info.offsetWidth),this.set("height",e.details[0].info.offsetHeight)},_onCloseClick:function(){this.destroy()},SERIALIZABLE_ATTRS:function(){var e=["x","y"];return this.get("resizable")&&(e.push("width"),e.push("height")),e},toJSON:function(){var t={},n=this;return e.Array.each(this.SERIALIZABLE_ATTRS(),function(e){t[e]=n.get(e)}),t},destructor:function(){this.drag.destroy(),this.resize&&this.resize.destroy()}},{ATTRS:{x:{lazyAdd:!1,getter:function(){return parseInt(this.get("boundingBox").getStyle("left"),10)},setter:function(e){this.get("boundingBox").setStyle("left",e)},validator:function(t){return e.Lang.isNumber(t)}},y:{lazyAdd:!1,getter:function(){return parseInt(this.get("boundingBox").getStyle("top"),10)},setter:function(e){this.get("boundingBox").setStyle("top",e)},validator:function(t){return e.Lang.isNumber(t)}},zIndex:{value:5},resizable:{value:!0},fillHeight:{value:!0},preventSelfWiring:{value:!0},icons:{value:[{title:"close",click:"_onCloseClick",className:"ui-silk ui-silk-cancel"}]}}})},"@VERSION@",{requires:["widget-stdmod","widget-stack","widget-parent","widget-child","dd","resize","wires-delegate","widget-terminals","widget-icons"],skinnable:!0});
