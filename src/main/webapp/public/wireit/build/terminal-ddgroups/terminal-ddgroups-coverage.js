if (typeof __coverage__ === 'undefined') { __coverage__ = {}; }
if (!__coverage__['build/terminal-ddgroups/terminal-ddgroups.js']) {
   __coverage__['build/terminal-ddgroups/terminal-ddgroups.js'] = {"path":"build/terminal-ddgroups/terminal-ddgroups.js","s":{"1":0,"2":0,"3":0,"4":0,"5":0,"6":0,"7":0,"8":0,"9":0,"10":0,"11":0,"12":0},"b":{"1":[0,0],"2":[0,0]},"f":{"1":0,"2":0,"3":0,"4":0,"5":0},"fnMap":{"1":{"name":"(anonymous_1)","line":1,"loc":{"start":{"line":1,"column":29},"end":{"line":1,"column":48}}},"2":{"name":"(anonymous_2)","line":13,"loc":{"start":{"line":13,"column":21},"end":{"line":13,"column":39}}},"3":{"name":"(anonymous_3)","line":28,"loc":{"start":{"line":28,"column":20},"end":{"line":28,"column":32}}},"4":{"name":"(anonymous_4)","line":38,"loc":{"start":{"line":38,"column":19},"end":{"line":38,"column":31}}},"5":{"name":"(anonymous_5)","line":53,"loc":{"start":{"line":53,"column":25},"end":{"line":53,"column":36}}}},"statementMap":{"1":{"start":{"line":1,"column":0},"end":{"line":61,"column":53}},"2":{"start":{"line":13,"column":0},"end":{"line":16,"column":2}},"3":{"start":{"line":14,"column":3},"end":{"line":14,"column":51}},"4":{"start":{"line":15,"column":3},"end":{"line":15,"column":60}},"5":{"start":{"line":18,"column":0},"end":{"line":24,"column":2}},"6":{"start":{"line":26,"column":0},"end":{"line":57,"column":2}},"7":{"start":{"line":29,"column":6},"end":{"line":31,"column":7}},"8":{"start":{"line":30,"column":9},"end":{"line":30,"column":31}},"9":{"start":{"line":40,"column":6},"end":{"line":49,"column":7}},"10":{"start":{"line":42,"column":9},"end":{"line":45,"column":12}},"11":{"start":{"line":47,"column":9},"end":{"line":47,"column":92}},"12":{"start":{"line":54,"column":6},"end":{"line":54,"column":114}}},"branchMap":{"1":{"line":29,"type":"if","locations":[{"start":{"line":29,"column":6},"end":{"line":29,"column":6}},{"start":{"line":29,"column":6},"end":{"line":29,"column":6}}]},"2":{"line":40,"type":"if","locations":[{"start":{"line":40,"column":6},"end":{"line":40,"column":6}},{"start":{"line":40,"column":6},"end":{"line":40,"column":6}}]}},"code":["(function () { YUI.add('terminal-ddgroups', function (Y, NAME) {","","/**"," * @module terminal-ddgroups"," */","","/**"," * Extension to add \"groups\" labels when hovering the terminal"," * @class TerminalDDGroups"," * @constructor"," * @param {Object} config configuration object"," */","Y.TerminalDDGroups = function (config) {","   Y.after(this._renderUIgroups, this, \"renderUI\");","   Y.after(this._showOverlayDDGroups, this, \"_showOverlay\");","};","","Y.TerminalDDGroups.ATTRS = {","   ","   showGroups: {","      value: true","   }","   ","};","","Y.TerminalDDGroups.prototype = {","   ","   _renderUIgroups: function () {","      if( this.get('editable') ) {","         this._renderTooltip();","      }","   },","   ","   /**","    * create a persisting tooltip with the dd-groups class","    * @method _renderTooltip","    */","   _renderTooltip: function () {","      ","      if(this.get('showGroups')) {","         ","         this._ddGroupsOverlay = new Y.Overlay({","            render: this.get('boundingBox'),","            bodyContent: this.get('ddGroupsDrag').join(',')","         });","","         this._ddGroupsOverlay.get('contentBox').addClass( this.getClassName(\"dd-groups\") );","","      }","      ","   },","","   _showOverlayDDGroups: function() {","      this._ddGroupsOverlay.align( this.get('contentBox'), [Y.WidgetPositionAlign.TC, Y.WidgetPositionAlign.BC] );","   }","   ","};","","","","}, '@VERSION@', {\"requires\": [\"terminal-dragedit\"]});","","}());"]};
}
var __cov_nGBCivflfNA2Os9Q7Y7vJA = __coverage__['build/terminal-ddgroups/terminal-ddgroups.js'];
__cov_nGBCivflfNA2Os9Q7Y7vJA.s['1']++;YUI.add('terminal-ddgroups',function(Y,NAME){__cov_nGBCivflfNA2Os9Q7Y7vJA.f['1']++;__cov_nGBCivflfNA2Os9Q7Y7vJA.s['2']++;Y.TerminalDDGroups=function(config){__cov_nGBCivflfNA2Os9Q7Y7vJA.f['2']++;__cov_nGBCivflfNA2Os9Q7Y7vJA.s['3']++;Y.after(this._renderUIgroups,this,'renderUI');__cov_nGBCivflfNA2Os9Q7Y7vJA.s['4']++;Y.after(this._showOverlayDDGroups,this,'_showOverlay');};__cov_nGBCivflfNA2Os9Q7Y7vJA.s['5']++;Y.TerminalDDGroups.ATTRS={showGroups:{value:true}};__cov_nGBCivflfNA2Os9Q7Y7vJA.s['6']++;Y.TerminalDDGroups.prototype={_renderUIgroups:function(){__cov_nGBCivflfNA2Os9Q7Y7vJA.f['3']++;__cov_nGBCivflfNA2Os9Q7Y7vJA.s['7']++;if(this.get('editable')){__cov_nGBCivflfNA2Os9Q7Y7vJA.b['1'][0]++;__cov_nGBCivflfNA2Os9Q7Y7vJA.s['8']++;this._renderTooltip();}else{__cov_nGBCivflfNA2Os9Q7Y7vJA.b['1'][1]++;}},_renderTooltip:function(){__cov_nGBCivflfNA2Os9Q7Y7vJA.f['4']++;__cov_nGBCivflfNA2Os9Q7Y7vJA.s['9']++;if(this.get('showGroups')){__cov_nGBCivflfNA2Os9Q7Y7vJA.b['2'][0]++;__cov_nGBCivflfNA2Os9Q7Y7vJA.s['10']++;this._ddGroupsOverlay=new Y.Overlay({render:this.get('boundingBox'),bodyContent:this.get('ddGroupsDrag').join(',')});__cov_nGBCivflfNA2Os9Q7Y7vJA.s['11']++;this._ddGroupsOverlay.get('contentBox').addClass(this.getClassName('dd-groups'));}else{__cov_nGBCivflfNA2Os9Q7Y7vJA.b['2'][1]++;}},_showOverlayDDGroups:function(){__cov_nGBCivflfNA2Os9Q7Y7vJA.f['5']++;__cov_nGBCivflfNA2Os9Q7Y7vJA.s['12']++;this._ddGroupsOverlay.align(this.get('contentBox'),[Y.WidgetPositionAlign.TC,Y.WidgetPositionAlign.BC]);}};},'@VERSION@',{'requires':['terminal-dragedit']});
