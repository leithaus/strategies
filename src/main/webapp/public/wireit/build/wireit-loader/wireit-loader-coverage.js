if (typeof __coverage__ === 'undefined') { __coverage__ = {}; }
if (!__coverage__['build/wireit-loader/wireit-loader.js']) {
   __coverage__['build/wireit-loader/wireit-loader.js'] = {"path":"build/wireit-loader/wireit-loader.js","s":{"1":0,"2":0,"3":0,"4":0,"5":0},"b":{"1":[0,0]},"f":{"1":0},"fnMap":{"1":{"name":"(anonymous_1)","line":3,"loc":{"start":{"line":3,"column":10},"end":{"line":3,"column":22}}}},"statementMap":{"1":{"start":{"line":3,"column":0},"end":{"line":158,"column":3}},"2":{"start":{"line":4,"column":3},"end":{"line":150,"column":5}},"3":{"start":{"line":152,"column":3},"end":{"line":154,"column":4}},"4":{"start":{"line":153,"column":6},"end":{"line":153,"column":32}},"5":{"start":{"line":156,"column":3},"end":{"line":156,"column":43}}},"branchMap":{"1":{"line":152,"type":"if","locations":[{"start":{"line":152,"column":3},"end":{"line":152,"column":3}},{"start":{"line":152,"column":3},"end":{"line":152,"column":3}}]}},"code":["(function () { /* This file is auto-generated by src/loader/scripts/meta_join.js */","","YUI().use(function(Y) {","   var CONFIG = {","      groups: {","         'wireit': {","            base: 'wireit/src/',","            combine: false,","            modules: {","    \"arrow-wire\": {","        \"requires\": [","            \"wire-base\"","        ]","    },","    \"bezier-wire\": {","        \"requires\": [","            \"wire-base\"","        ]","    },","    \"bidirectional-arrow-wire\": {","        \"requires\": [","            \"arrow-wire\"","        ]","    },","    \"container\": {","        \"requires\": [","            \"widget-stdmod\",","            \"widget-stack\",","            \"widget-parent\",","            \"widget-child\",","            \"dd\",","            \"resize\",","            \"wires-delegate\",","            \"widget-terminals\",","            \"widget-icons\"","        ],","        \"skinnable\": true","    },","    \"image-container\": {","        \"requires\": [","            \"container\"","        ],","        \"skinnable\": true","    },","    \"inout-container\": {","        \"requires\": [","            \"container\",","            \"terminal-input\",","            \"terminal-output\"","        ],","        \"skinnable\": true","    },","    \"layer\": {","        \"requires\": [","            \"widget-parent\",","            \"container\",","            \"wires-delegate\"","        ],","        \"skinnable\": true","    },","    \"straight-wire\": {","        \"requires\": [","            \"wire-base\"","        ]","    },","    \"terminal\": {","        \"requires\": [","            \"widget\",","            \"widget-child\",","            \"widget-position\",","            \"widget-position-align\",","            \"wire-base\",","            \"wires-delegate\",","            \"terminal-dragedit\",","            \"terminal-scissors\",","            \"terminal-ddgroups\"","        ],","        \"skinnable\": true","    },","    \"terminal-ddgroups\": {","        \"requires\": [","            \"terminal-dragedit\"","        ]","    },","    \"terminal-dragedit\": {","        \"requires\": [","            \"bezier-wire\",","            \"dd-drop\",","            \"dd-drag\",","            \"dd-proxy\"","        ]","    },","    \"terminal-input\": {","        \"requires\": [","            \"terminal\"","        ]","    },","    \"terminal-output\": {","        \"requires\": [","            \"terminal\"","        ]","    },","    \"terminal-scissors\": {","        \"requires\": [","            \"overlay\"","        ],","        \"skinnable\": true","    },","    \"textarea-container\": {","        \"requires\": [","            \"container\"","        ]","    },","    \"widget-icons\": {","        \"requires\": [],","        \"skinnable\": true","    },","    \"widget-terminals\": {","        \"requires\": [","            \"terminal\"","        ]","    },","    \"wire-base\": {","        \"requires\": [","            \"graphics\"","        ],","        \"skinnable\": true","    },","    \"wireit-app\": {","        \"requires\": [","            \"app\",","            \"handlebars\",","            \"model\",","            \"model-list\",","            \"json\",","            \"view\",","            \"layer\",","            \"bezier-wire\",","            \"anim\"","        ]","    },","    \"wires-delegate\": {","        \"requires\": [","            \"wire-base\"","        ]","    }","}","         }","      }","   };","","   if(typeof YUI_config === 'undefined') {","      YUI_config = {groups: {}};","   }","","   Y.mix(YUI_config.groups, CONFIG.groups);","","});","","}());"]};
}
var __cov_MVh41LnQnUATAIVJLrF3xw = __coverage__['build/wireit-loader/wireit-loader.js'];
__cov_MVh41LnQnUATAIVJLrF3xw.s['1']++;YUI().use(function(Y){__cov_MVh41LnQnUATAIVJLrF3xw.f['1']++;__cov_MVh41LnQnUATAIVJLrF3xw.s['2']++;var CONFIG={groups:{'wireit':{base:'wireit/src/',combine:false,modules:{'arrow-wire':{'requires':['wire-base']},'bezier-wire':{'requires':['wire-base']},'bidirectional-arrow-wire':{'requires':['arrow-wire']},'container':{'requires':['widget-stdmod','widget-stack','widget-parent','widget-child','dd','resize','wires-delegate','widget-terminals','widget-icons'],'skinnable':true},'image-container':{'requires':['container'],'skinnable':true},'inout-container':{'requires':['container','terminal-input','terminal-output'],'skinnable':true},'layer':{'requires':['widget-parent','container','wires-delegate'],'skinnable':true},'straight-wire':{'requires':['wire-base']},'terminal':{'requires':['widget','widget-child','widget-position','widget-position-align','wire-base','wires-delegate','terminal-dragedit','terminal-scissors','terminal-ddgroups'],'skinnable':true},'terminal-ddgroups':{'requires':['terminal-dragedit']},'terminal-dragedit':{'requires':['bezier-wire','dd-drop','dd-drag','dd-proxy']},'terminal-input':{'requires':['terminal']},'terminal-output':{'requires':['terminal']},'terminal-scissors':{'requires':['overlay'],'skinnable':true},'textarea-container':{'requires':['container']},'widget-icons':{'requires':[],'skinnable':true},'widget-terminals':{'requires':['terminal']},'wire-base':{'requires':['graphics'],'skinnable':true},'wireit-app':{'requires':['app','handlebars','model','model-list','json','view','layer','bezier-wire','anim']},'wires-delegate':{'requires':['wire-base']}}}}};__cov_MVh41LnQnUATAIVJLrF3xw.s['3']++;if(typeof YUI_config==='undefined'){__cov_MVh41LnQnUATAIVJLrF3xw.b['1'][0]++;__cov_MVh41LnQnUATAIVJLrF3xw.s['4']++;YUI_config={groups:{}};}else{__cov_MVh41LnQnUATAIVJLrF3xw.b['1'][1]++;}__cov_MVh41LnQnUATAIVJLrF3xw.s['5']++;Y.mix(YUI_config.groups,CONFIG.groups);});
