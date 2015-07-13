(function(e){if("function"==typeof bootstrap)bootstrap("socialfeed",e);else if("object"==typeof exports)module.exports=e();else if("function"==typeof define&&define.amd)define(e);else if("undefined"!=typeof ses){if(!ses.ok())return;ses.makeSocialFeed=e}else"undefined"!=typeof window?window.SocialFeed=e():global.SocialFeed=e()})(function(){var define,ses,bootstrap,module,exports;
return (function(e,t,n){function i(n,s){if(!t[n]){if(!e[n]){var o=typeof require=="function"&&require;if(!s&&o)return o(n,!0);if(r)return r(n,!0);throw new Error("Cannot find module '"+n+"'")}var u=t[n]={exports:{}};e[n][0].call(u.exports,function(t){var r=e[n][1][t];return i(r?r:t)},u,u.exports)}return t[n].exports}var r=typeof require=="function"&&require;for(var s=0;s<n.length;s++)i(n[s]);return i})({1:[function(require,module,exports){

var API = require('./api')
  , Controller = require('./controller')
  , SocialBase = require('./basemodule')
  , _ = require('./utils')
  ;

var SocialFeed = function (options) {
  if ( !(this instanceof SocialFeed) ) return new SocialFeed();
  if (!options.el) {
    options = {
      el: options
    };
  }
  this.c = new Controller(options);
};
// Expose public API.
_.inherits(SocialFeed, API);

// Make modules available:
SocialFeed.Modules = {
    Disqus: require('./modules/disqus')
  , Github: require('./modules/github')
  , YouTubeUploads: require('./modules/youtubeuploads')
  , Delicious: require('./modules/delicious')
  , RSS: require('./modules/rss')
  , Vimeo: require('./modules/vimeo')
  , Tumblr: require('./modules/tumblr')
  , SocialBase: SocialBase
  , extend: function (module) {
    return SocialBase.extend(module);
  }
};

module.exports = SocialFeed;

},{"./controller":2,"./basemodule":3,"./api":4,"./utils":5,"./modules/delicious":6,"./modules/disqus":7,"./modules/github":8,"./modules/tumblr":9,"./modules/youtubeuploads":10,"./modules/rss":11,"./modules/vimeo":12}],4:[function(require,module,exports){
var API = module.exports = function (controller) {
};

API.prototype = {

  start: function () {
    this.c.emit('start');
    return this;
  }

  , reload: function () {
    this.c.emit('reload');
    return this;
  }

  , addModule: function (module) {
    this.c.emit('addModule', module);
    return this;
  }

  , nextBulk: function () {
    this.c.emit('nextBulk');
    return this;
  }

  , loadNumEntries: function (num) {
    this.c.emit('loadNumEntries', num);
    return this;
  }

  , on: function (eventType, cb) {
    this.c.on(eventType, cb);
    return this;
  }

};
},{}],5:[function(require,module,exports){

exports.timesince = function (date) {
  date = new Date(date);
  var seconds = Math.floor((new Date() - date) / 1000);

  var interval = Math.floor(seconds / 31536000);

  if (interval > 1) {
    return interval + " years ago";
  }
  interval = Math.floor(seconds / 2592000);
  if (interval > 1) {
    return interval + " months ago";
  }
  interval = Math.floor(seconds / 86400);
  if (interval > 1) {
    return interval + " days ago";
  }
  interval = Math.floor(seconds / 3600);
  if (interval > 1) {
    return interval + " hours ago";
  }
  interval = Math.floor(seconds / 60);
  if (interval > 1) {
    return interval + " minutes ago";
  }
  return Math.floor(seconds) + " seconds ago";
};

var isFunc = exports.isFunc = function (obj) {
  return Object.prototype.toString.call(obj) == '[object Function]';
};

var isString = exports.isString = function (obj) {
  return Object.prototype.toString.call(obj) == "[object String]";
};

exports.result = function (object, property) {
  if (object == null) return;
  var value = object[property];
  return isFunc(value) ? value.call(object) : value;
};

exports.bind = function( fn, context ) {
  var args = [].slice.call( arguments, 2 );
  return function() {
    return fn.apply( context || this, args.concat( [].slice.call( arguments ) ) );
  };
};

exports.has = function (object, key) {
  return Object.prototype.hasOwnProperty.call(object, key);
}

exports.extend = function (obj) {
  [].slice.call(arguments, 1).forEach(function(source) {
    if (source) {
      for (var prop in source) {
        obj[prop] = source[prop];
      }
    }
  });
  return obj;
};

exports.template = function (template, o) {
  // From douglas crockfords
  return template.replace(/{([^{}]*)}/g,
    function (a, b) {
      var r = o[b];
      return typeof r === 'string' || typeof r === 'number' ? r : a;
    }
  );
}

// From Node util lib

/**
 * Inherit the prototype methods from one constructor into another.
 *
 * The Function.prototype.inherits from lang.js rewritten as a standalone
 * function (not on Function.prototype). NOTE: If this file is to be loaded
 * during bootstrapping this function needs to be rewritten using some native
 * functions as prototype setup using normal JavaScript does not work as
 * expected during bootstrapping (see mirror.js in r114903).
 *
 * @param {function} ctor Constructor function which needs to inherit the
 *     prototype.
 * @param {function} superCtor Constructor function to inherit prototype from.
 */
exports.inherits = function(ctor, superCtor) {
  ctor.super_ = superCtor;
  ctor.prototype = Object.create(superCtor.prototype, {
    constructor: {
      value: ctor,
      enumerable: false,
      writable: true,
      configurable: true
    }
  });
};


/*
 * ECMAScript 5 Shims.
 * Copyright 2009, 2010 Kristopher Michael Kowal. All rights reserved.
 */

// ES5 9.9
// http://es5.github.com/#x9.9
var toObject = function (o) {
    if (o == null) { // this matches both null and undefined
        throw new TypeError("can't convert "+o+" to object");
    }
    return Object(o);
};


// ES5 15.4.4.18
// http://es5.github.com/#x15.4.4.18
// https://developer.mozilla.org/en/JavaScript/Reference/Global_Objects/array/forEach

// Check failure of by-index access of string characters (IE < 9)
// and failure of `0 in boxedString` (Rhino)
var boxedString = Object("a"),
    splitString = boxedString[0] != "a" || !(0 in boxedString);

if (!Array.prototype.forEach) {
  Array.prototype.forEach = function forEach(fun /*, thisp*/) {
    var object = toObject(this),
        self = splitString && isString(this) ?
            this.split("") :
            object,
        thisp = arguments[1],
        i = -1,
        length = self.length >>> 0;

    // If no callback function or if callback is not a callable function
    if (!isFunc(fun)) {
        throw new TypeError(); // TODO message
    }

    while (++i < length) {
        if (i in self) {
            // Invoke the callback function with call, passing arguments:
            // context, property value, property key, thisArg object
            // context
            fun.call(thisp, self[i], i, object);
        }
    }
  };
}

// ES5 15.4.4.19
// http://es5.github.com/#x15.4.4.19
// https://developer.mozilla.org/en/Core_JavaScript_1.5_Reference/Objects/Array/map
if (!Array.prototype.map) {
    Array.prototype.map = function map(fun /*, thisp*/) {
        var object = toObject(this),
            self = splitString && _toString(this) == "[object String]" ?
                this.split("") :
                object,
            length = self.length >>> 0,
            result = Array(length),
            thisp = arguments[1];

        // If no callback function or if callback is not a callable function
        if (_toString(fun) != "[object Function]") {
            throw new TypeError(fun + " is not a function");
        }

        for (var i = 0; i < length; i++) {
            if (i in self)
                result[i] = fun.call(thisp, self[i], i, object);
        }
        return result;
    };
}

// ES5 15.4.4.20
// http://es5.github.com/#x15.4.4.20
// https://developer.mozilla.org/en/Core_JavaScript_1.5_Reference/Objects/Array/filter
if (!Array.prototype.filter) {
    Array.prototype.filter = function filter(fun /*, thisp */) {
        var object = toObject(this),
            self = splitString && _toString(this) == "[object String]" ?
                this.split("") :
                    object,
            length = self.length >>> 0,
            result = [],
            value,
            thisp = arguments[1];

        // If no callback function or if callback is not a callable function
        if (_toString(fun) != "[object Function]") {
            throw new TypeError(fun + " is not a function");
        }

        for (var i = 0; i < length; i++) {
            if (i in self) {
                value = self[i];
                if (fun.call(thisp, value, i, object)) {
                    result.push(value);
                }
            }
        }
        return result;
    };
}

},{}],13:[function(require,module,exports){
// shim for using process in browser

var process = module.exports = {};

process.nextTick = (function () {
    var canSetImmediate = typeof window !== 'undefined'
    && window.setImmediate;
    var canPost = typeof window !== 'undefined'
    && window.postMessage && window.addEventListener
    ;

    if (canSetImmediate) {
        return function (f) { return window.setImmediate(f) };
    }

    if (canPost) {
        var queue = [];
        window.addEventListener('message', function (ev) {
            var source = ev.source;
            if ((source === window || source === null) && ev.data === 'process-tick') {
                ev.stopPropagation();
                if (queue.length > 0) {
                    var fn = queue.shift();
                    fn();
                }
            }
        }, true);

        return function nextTick(fn) {
            queue.push(fn);
            window.postMessage('process-tick', '*');
        };
    }

    return function nextTick(fn) {
        setTimeout(fn, 0);
    };
})();

process.title = 'browser';
process.browser = true;
process.env = {};
process.argv = [];

process.binding = function (name) {
    throw new Error('process.binding is not supported');
}

// TODO(shtylman)
process.cwd = function () { return '/' };
process.chdir = function (dir) {
    throw new Error('process.chdir is not supported');
};

},{}],14:[function(require,module,exports){
(function(process){if (!process.EventEmitter) process.EventEmitter = function () {};

var EventEmitter = exports.EventEmitter = process.EventEmitter;
var isArray = typeof Array.isArray === 'function'
    ? Array.isArray
    : function (xs) {
        return Object.prototype.toString.call(xs) === '[object Array]'
    }
;
function indexOf (xs, x) {
    if (xs.indexOf) return xs.indexOf(x);
    for (var i = 0; i < xs.length; i++) {
        if (x === xs[i]) return i;
    }
    return -1;
}

// By default EventEmitters will print a warning if more than
// 10 listeners are added to it. This is a useful default which
// helps finding memory leaks.
//
// Obviously not all Emitters should be limited to 10. This function allows
// that to be increased. Set to zero for unlimited.
var defaultMaxListeners = 10;
EventEmitter.prototype.setMaxListeners = function(n) {
  if (!this._events) this._events = {};
  this._events.maxListeners = n;
};


EventEmitter.prototype.emit = function(type) {
  // If there is no 'error' event listener then throw.
  if (type === 'error') {
    if (!this._events || !this._events.error ||
        (isArray(this._events.error) && !this._events.error.length))
    {
      if (arguments[1] instanceof Error) {
        throw arguments[1]; // Unhandled 'error' event
      } else {
        throw new Error("Uncaught, unspecified 'error' event.");
      }
      return false;
    }
  }

  if (!this._events) return false;
  var handler = this._events[type];
  if (!handler) return false;

  if (typeof handler == 'function') {
    switch (arguments.length) {
      // fast cases
      case 1:
        handler.call(this);
        break;
      case 2:
        handler.call(this, arguments[1]);
        break;
      case 3:
        handler.call(this, arguments[1], arguments[2]);
        break;
      // slower
      default:
        var args = Array.prototype.slice.call(arguments, 1);
        handler.apply(this, args);
    }
    return true;

  } else if (isArray(handler)) {
    var args = Array.prototype.slice.call(arguments, 1);

    var listeners = handler.slice();
    for (var i = 0, l = listeners.length; i < l; i++) {
      listeners[i].apply(this, args);
    }
    return true;

  } else {
    return false;
  }
};

// EventEmitter is defined in src/node_events.cc
// EventEmitter.prototype.emit() is also defined there.
EventEmitter.prototype.addListener = function(type, listener) {
  if ('function' !== typeof listener) {
    throw new Error('addListener only takes instances of Function');
  }

  if (!this._events) this._events = {};

  // To avoid recursion in the case that type == "newListeners"! Before
  // adding it to the listeners, first emit "newListeners".
  this.emit('newListener', type, listener);

  if (!this._events[type]) {
    // Optimize the case of one listener. Don't need the extra array object.
    this._events[type] = listener;
  } else if (isArray(this._events[type])) {

    // Check for listener leak
    if (!this._events[type].warned) {
      var m;
      if (this._events.maxListeners !== undefined) {
        m = this._events.maxListeners;
      } else {
        m = defaultMaxListeners;
      }

      if (m && m > 0 && this._events[type].length > m) {
        this._events[type].warned = true;
        console.error('(node) warning: possible EventEmitter memory ' +
                      'leak detected. %d listeners added. ' +
                      'Use emitter.setMaxListeners() to increase limit.',
                      this._events[type].length);
        console.trace();
      }
    }

    // If we've already got an array, just append.
    this._events[type].push(listener);
  } else {
    // Adding the second element, need to change to array.
    this._events[type] = [this._events[type], listener];
  }

  return this;
};

EventEmitter.prototype.on = EventEmitter.prototype.addListener;

EventEmitter.prototype.once = function(type, listener) {
  var self = this;
  self.on(type, function g() {
    self.removeListener(type, g);
    listener.apply(this, arguments);
  });

  return this;
};

EventEmitter.prototype.removeListener = function(type, listener) {
  if ('function' !== typeof listener) {
    throw new Error('removeListener only takes instances of Function');
  }

  // does not use listeners(), so no side effect of creating _events[type]
  if (!this._events || !this._events[type]) return this;

  var list = this._events[type];

  if (isArray(list)) {
    var i = indexOf(list, listener);
    if (i < 0) return this;
    list.splice(i, 1);
    if (list.length == 0)
      delete this._events[type];
  } else if (this._events[type] === listener) {
    delete this._events[type];
  }

  return this;
};

EventEmitter.prototype.removeAllListeners = function(type) {
  if (arguments.length === 0) {
    this._events = {};
    return this;
  }

  // does not use listeners(), so no side effect of creating _events[type]
  if (type && this._events && this._events[type]) this._events[type] = null;
  return this;
};

EventEmitter.prototype.listeners = function(type) {
  if (!this._events) this._events = {};
  if (!this._events[type]) this._events[type] = [];
  if (!isArray(this._events[type])) {
    this._events[type] = [this._events[type]];
  }
  return this._events[type];
};

})(require("__browserify_process"))
},{"__browserify_process":13}],2:[function(require,module,exports){
var EventEmitter = require('events').EventEmitter
  , _ = require('./utils')
  , SocialBase = require('./basemodule')
  ;

var $ = SocialBase.$ || window.jQuery || window.Zepto || window.$;

var Controller = module.exports = function (options) {
  this.modules = [];
  this.feedRendered = null;
  this.$el = $(options.el) || $('#socialfeed');
  this.count = options.count || 1000;
  this._offset = options.offset || 0;

  this.on('start', _.bind(this.start, this));
  this.on('reload', _.bind(this.reload));
  this.on('addModule', _.bind(this.addModule));
  this.on('postFetch', _.bind(this.render));

  // Paging
  this.on('nextBulk', _.bind(this.nextBulk));
  this.on('loadNumEntries', _.bind(this.loadNumEntries));
};
_.inherits(Controller, EventEmitter);

_.extend(Controller.prototype, {
  _sync_count: 0

  , addModule: function (module) {
    var controller = this;

    this.modules.push(module);
    module.on('fetched', _.bind(controller.moduleFetched, controller));
    module.on('error', function () {
      if (controller.listeners('error').length > 0) {
        controller.emit.apply(controller, ['error'].concat(arguments));
      }
      controller.moduleFetched();
    });
  }

  , start: function () {
    var controller = this;
    controller.emit('preFetch');
    controller.modules.forEach(function (module) {
      module.fetch();
    });
  }

  , moduleFetched: function (module, b, c) {
    this.emit('moduleAdded', module);
    if (++this._sync_count === this.modules.length) {
      // all done
      this.emit('postFetch', this.modules);
      this._sync_count = 0;
    }
  }

  , reload: function () {
    this.$el.empty();
    this._offset = 0;
    this.feedRendered = null;
    this.start();
  }

  , nextBulk: function () {
    return this.loadNumEntries(this.count);
  }

  , loadNumEntries: function (num) {
    if (this._offset >= this.feedRendered.length) {
      return this;
    }
    var tmp = this.count;
    this.count = num;
    this.render();
    this.count = tmp;
    return this;
  }

  , render: function () {
    var $el = this.$el;

    if (this.feedRendered === null) {
      this.feedRendered = this._generateOrderedList();
      this.emit('dataReady', this.feedRendered, this.modules);
    }

    var list = this.feedRendered.slice(this._offset, (this._offset + this.count));
    list.forEach(function (item) {
      $el.append(item.html);
    });
    this._offset += this.count;

    this.emit('rendered', list);
    return this;
  }

  , _generateOrderedList: function () {
    var list = [];
    this.modules.forEach(function (module) {
      if (!module || !module.collection) {
        return;
      }
      var collectionlist = module.collection.map(function (item) {
        var html = module.render(item);
        if (!html) {
          return null;
        }

        return {
          orderBy: module.orderBy(item),
          html: html
        };
      });
      collectionlist = collectionlist.filter(function (item) {
        return item !== null;
      });
      list = list.concat(collectionlist);
    });

    return this._orderList(list);
  }

  , _orderList: function (list) {
    return list.sort(function (x, y) {
      var a = x.orderBy;
      var b = y.orderBy;
      if (a > b || a === void 0) return 1;
      if (a <= b || b === void 0) return -1;
    });
  }


});
},{"events":14,"./utils":5,"./basemodule":3}],3:[function(require,module,exports){
var EventEmitter = require('events').EventEmitter
  , _ = require('./utils')
  ;

var root = window;
var $;

var SocialBase = module.exports = function () {
  this.collection = [];
  this.init.apply(this, arguments);

  $ = SocialBase.$ || root.jQuery || root.Zepto || root.$;
  if (!$) throw "jQuery or Zepto is required to use SocialFeed.";
};
_.inherits(SocialBase, EventEmitter);

/**
  Extend from Backbone
  (Copyright (c) 2010-2013 Jeremy Ashkenas, DocumentCloud)
*/
SocialBase.extend = function (protoProps) {
  var parent = this
    , child = function(){
        return parent.apply(this, arguments);
      }
    ;

  _.extend(child, parent);

  var Surrogate = function () {
    this.constructor = child;
  };

  Surrogate.prototype = parent.prototype;
  child.prototype = new Surrogate;
  if (protoProps) {
    _.extend(child.prototype, protoProps);
  }
  child.__super__ = parent.prototype;

  return child;
};
/** // From Backbone */

SocialBase.fetch = function (options) {
  if (options.dataType.toLowerCase() === 'jsonp') {
    options.callback = options.callbackParameter || "callback";
  }

  return $.ajax(options);
};
_.extend(SocialBase.prototype, {

  ajaxSettings: {
    dataType: 'jsonp',
    type: 'GET'
  }

  , init: function (ident) {
    this.ident = ident;
  }

  , fetch: function (options) {
    options = options ? _.clone(options) : {};

    var url = _.result(this, 'url')
      , module = this
      , success = options.success
      ;

    options.url = url;
    options.success = function(resp) {
      var parsed = module.parse(resp);

      module.collection = parsed;
      if (success) success(module, parsed, options);
      module.emit('fetched', module, parsed, options);
    };

    var error = options.error;
    options.error = function(xOptions, textStatus) {
      if (error) error(module, textStatus, xOptions);
      module.emit('error', module, textStatus, xOptions);
    };

    if (!url && this.data) {
      options.success(_.result(this, 'data'));
      return void 0;
    }

    return SocialBase.fetch(_.extend(this.ajaxSettings, options));
  }

  , parse: function (resp) {
    return resp;
  }

  , orderBy: function (item) {  }

  , render: function (item) {  }

});
},{"events":14,"./utils":5}],6:[function(require,module,exports){
var SocialBase = require('../basemodule')
  , templateHtml = require('../resources').delicious
  , _ = require('../utils')
  ;

module.exports = SocialBase.extend({

  url: function () {
    return 'http://feeds.delicious.com/v2/json/' + this.ident;
  }

  , orderBy: function (item) {
    return -(new Date(item.dt)).getTime();
  }

  , render: function (item) {
    item.time_since = _.timesince(item.dt);
    return _.template(templateHtml, item)
  }

});
},{"../basemodule":3,"../resources":15,"../utils":5}],8:[function(require,module,exports){
var SocialBase = require('../basemodule')
  , resources = require('../resources')
  , _ = require('../utils')
  , tmpl = {
      create: resources.github_create
    , createbranch: resources.github_createbranch
    , watch: resources.github_watch
    , push: resources.github_push
    , pullrequest: resources.github_pullrequest
    , fork: resources.github_fork
    , issue: resources.github_issue
  };

var getRepoURL = function (item) {
  return 'https://github.com/' + item.repo.name;
}
, getUserURL = function (item) {
  return 'https://github.com/' + item.actor.login;
}
, templateHelper = function (template, item) {
  return _.template(tmpl[template], {
      profile_url: getUserURL(item)
    , username: item.actor.login
    , repo_name: item.repo.name
    , repo_url: getRepoURL(item)
    , time_since: _.timesince(item.created_at)
    , created_at: item.created_at
  });
}
;

var defaultVisibility = {
    'CreateEvent': true
  , 'WatchEvent': true
  , 'PushEvent': true
  , 'PullRequestEvent': true
  , 'ForkEvent': true
  , 'IssuesEvent': true
};

module.exports = SocialBase.extend({
  init: function (ident, showEntities) {
    this.ident = ident;
    this.show = _.extend(defaultVisibility, showEntities);
  }

  , url: function () {
    return 'https://api.github.com/users/' + this.ident + '/events';
  }

  , orderBy: function (item) {
    return -(new Date(item.created_at)).getTime();
  }

  , renderMethods: {
    'CreateEvent': function (item) {

      if (item.payload.ref === null) {
        return templateHelper('create', item);
      }

      return _.template(templateHelper('createbranch', item), {
          branch_url: getRepoURL(item) + '/tree/' + item.payload.ref
        , branch_name: item.payload.ref
      });
    }

    , 'WatchEvent': function (item) {
      return templateHelper('watch', item);
    }

    , 'PushEvent': function (item) {
      var $html = $(templateHelper('push', item));

      // Add commits:
      var $ul = $html.find('.socialfeed-commit-list')
        , $li = $ul.find('li:first');

      item.payload.commits.forEach(function(commit) {
        var $it = $li.clone();

        $it.find('a')
          .attr('href', getRepoURL(item) + '/commit/' + commit.sha)
          .text(commit.sha.substr(0, 7))
        $it.find('span').text(commit.message);
        $ul.prepend($it);
      });
      $li.remove();
      return $html;
    }

    , 'PullRequestEvent': function (item) {
      return _.template(templateHelper('pullrequest', item), {
          "action": item.payload.action
        , "title": item.payload.pull_request.title
        , "pullrequest_url": item.payload.pull_request.html_url
        , "pullrequest_name": item.repo.name + '#' + item.payload.number
      });
    }

    , 'ForkEvent': function (item) {
      return _.template(templateHelper('fork', item), {
          "forkee_url": item.payload.forkee.html_url
        , "forkee_name": item.payload.forkee.full_name
      });
    }

    , 'IssuesEvent': function (item) {
      return _.template(templateHelper('issue', item), {
          "action": item.payload.action
        , "title": item.payload.issue.title
        , "issue_url": item.payload.issue.html_url
        , "issue_name": item.repo.name + '#' + item.payload.number
      });
    }
  }

  , parse: function (resp) {
    return resp.data;
  }

  , render: function (item) {
    if (item.type && this.renderMethods[item.type] && !!this.show[item.type]) {
      return this.renderMethods[item.type].apply(this, [item]);
    }

    return null;
  }

});
},{"../basemodule":3,"../resources":15,"../utils":5}],9:[function(require,module,exports){
var SocialBase = require('../basemodule')
  , templateHtml = require('../resources').tumblr
  , _ = require('../utils')
  ;

module.exports = SocialBase.extend({
  init: function (url, apiKey) {
    this.blogUrl = url;
    this.apiKey = apiKey;
  }

  , url: function () {
    return 'http://api.tumblr.com/v2/blog/' + this.blogUrl + '/posts/text?api_key=' + this.apiKey;
  }

  , parse: function (resp) {
    if (!resp.meta || resp.meta.status !== 200) {
      return [];
    }
    return resp.response.posts || [];
  }

  , orderBy: function (item) {
    return -(new Date(item.date)).getTime();
  }

  , render: function (item) {
    item.time_since = _.timesince(item.date);
    return _.template(templateHtml, item);
  }
});
},{"../basemodule":3,"../resources":15,"../utils":5}],7:[function(require,module,exports){
var SocialBase = require('../basemodule')
  , templateHtml = require('../resources').disqus
  , _ = require('../utils')
  ;

module.exports = SocialBase.extend({

  init: function(ident, apikey) {
    this.ident = ident;
    this.apikey = apikey;
  }

  , url: function () {
    return 'https://disqus.com/api/3.0/users/listPosts.json?api_key=' + this.apikey + '&user:username=' + this.ident;
  }

  , parse: function (resp) {
    return resp.response;
  }

  , orderBy: function (item) {
    return -(new Date(item.createdAt)).getTime();
  }

  , render: function (item) {
    return _.template(templateHtml, {
      profile_url: item.author.profileUrl,
      author_name: item.author.name,
      created_at: item.createdAt,
      time_since: _.timesince(item.createdAt),
      message: item.message
    });
  }

});
},{"../basemodule":3,"../resources":15,"../utils":5}],10:[function(require,module,exports){
var SocialBase = require('../basemodule')
  , templateHtml = require('../resources').youtubeuploads
  , _ = require('../utils')
  ;

module.exports = SocialBase.extend({

  ajaxSettings: {
    cache: true,
    dataType: 'jsonp'
  }

  , init: function (ident, maxCount) {
    this.ident = ident;
    this.maxCount = maxCount || 10;
  }

  , url: function () {
    return 'http://gdata.youtube.com/feeds/users/' + this.ident + '/uploads?alt=json-in-script&format=5&max-results=' + this.maxCount;
  }

  , parse: function (resp) {
    var feed = resp.feed;
    return feed.entry || [];
  }

  , orderBy: function (item) {
    return -(new Date(item.updated.$t)).getTime();
  }

  , hideAndMakeYoutubeClickable: function (item, html) {

    var $html = $(html)
      , $iframe = $html.find('iframe')
      , thumbnail = item['media$group']['media$thumbnail'][0].url
      ;

    var $img = $('<img />', {
      src: thumbnail,
      'class': 'youtube-preview'
    }).insertAfter($iframe).on('click', function () {
      $iframe.insertAfter($img);
      $img.remove();
    });
    $iframe.remove();

    return $html;
  }

  , render: function (item) {

    var html = _.template(templateHtml, {
        profile_url: item.author[0].uri.$t
      , username: item.author[0].name.$t
      , video_url: item.link[0].href
      , video_name: item.title.$t
      , created_at: item.updated.$t
      , time_since: _.timesince(item.updated.$t)
      , entry_id: item.id.$t.substring(38)
      , desc: item['media$group']['media$description'].$t
    });

    return this.hideAndMakeYoutubeClickable(item, html);
  }

});
},{"../basemodule":3,"../resources":15,"../utils":5}],11:[function(require,module,exports){
var SocialBase = require('../basemodule')
  , templateHtml = require('../resources').rss
  , _ = require('../utils')
  ;

module.exports = SocialBase.extend({
  init: function (url, count) {
    this.feedURL = url;
    this.count = count || 10;
  }

  , url: function () {
    // Use Google API feed service.
    return 'http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num=' + this.count + '&q=' + encodeURIComponent(this.feedURL);
  }

  , parse: function (resp) {
    var feed = resp.responseData.feed;
    if (!feed) {
      return [];
    }
    this.blogname = feed.title;
    this.blogurl = feed.link;
    return feed.entries || [];
  }

  , orderBy: function (item) {
    return -(new Date(item.publishedDate)).getTime();
  }

  , render: function (item) {
    return _.template(templateHtml, {
        "blog_name": this.blogname
      , "blog_url": this.blogurl
      , "url": item.link
      , "title": item.title
      , "date": item.publishedDate
      , "time_since": _.timesince(item.publishedDate)
    });
  }
});
},{"../basemodule":3,"../resources":15,"../utils":5}],12:[function(require,module,exports){
var SocialBase = require('../basemodule')
  , resources = require('../resources')
  , _ = require('../utils')
  , tmpl = {
    like: resources.vimeo_like,
    add_comment: resources.vimeo_add_comment,
    upload: resources.vimeo_upload
  }
  , defaultVisibility = {
      'like': true
    , 'add_comment': true
    , 'upload': true
  }
  , templateHelper = function (template, item) {
    return _.template(tmpl[template], {
        user_url: item.user_url
      , user_name: item.user_name
      , user_portrait: item.user_portrait_small
      , video_title: item.video_title
      , video_url: item.video_url
      , video_thumbnail_large: item.video_thumbnail_large
      , user_portrait: item.user_portrait_small
      , time_since: _.timesince(item.date)
      , created_at: item.date
    });
  }
  ;

module.exports = SocialBase.extend({

  ajaxSettings: {
    cache: true,
    dataType: 'jsonp'
  }

  , init: function (ident, showEntities) {
    this.ident = ident;
    this.show = _.extend(defaultVisibility, showEntities);
  }

  , url: function () {
    return 'http://vimeo.com/api/v2/activity/' + this.ident + '/user_did.json';
  }

  , orderBy: function (item) {
    return -(new Date(item.date)).getTime();
  }

  , renderMethods: {
    'like': function (item) {
      return templateHelper('like', item);
    }

    , 'add_comment': function (item) {
      return _.template(templateHelper('add_comment', item), {
        "comment_text": item.comment_text
      });
    }

    , 'upload': function (item) {
      return templateHelper('upload', item);
    }
  }

  , render: function (item) {
    if (item.type && this.renderMethods[item.type] && !!this.show[item.type]) {
      return this.renderMethods[item.type].apply(this, [item]);
    }

    return null;
  }

});
},{"../basemodule":3,"../resources":15,"../utils":5}],15:[function(require,module,exports){
/* Do not alter. Auto generated file */

module.exports = {
	"delicious": "<div class=\"socialfeed-item socialfeed-delicious\">\n  <i class=\"socialfeed-icon icon-link\"></i>\n  <header>\n    <h2><a href=\"{u}\">{d}</a></h2>\n    <time datetime=\"{dt}\">{time_since}</time>\n  </header>\n  <div class=\"socialfeed-body\">\n    {n}\n  </div>\n</div>",
	"disqus": "<div class=\"socialfeed-item socialfeed-disqus\">\n  <i class=\"socialfeed-icon icon-comment-alt\"></i>\n  <header>\n    <h2><a href=\"{profile_url}\">{author_name}</a></h2>\n    <time datetime=\"{created_at}\">{time_since}</time>\n  </header>\n  <div class=\"socialfeed-body\">\n    {message}\n  </div>\n</div>",
	"github_create": "<div class=\"socialfeed-item socialfeed-github socialfeed-github-create\">\n  <i class=\"socialfeed-icon icon-github\"></i>\n  <header>\n    <h2>\n      <a href=\"{profile_url}\">{username}</a> created repository <a href=\"{repo_url}\">{repo_name}</a>\n    </h2>\n    <time datetime=\"{created_at}\">{time_since}</time>\n  </header>\n</div>",
	"github_createbranch": "<div class=\"socialfeed-item socialfeed-github socialfeed-github-createbranch\">\n  <i class=\"socialfeed-icon icon-github\"></i>\n  <header>\n    <h2>\n      <a href=\"{profile_url}\">{username}</a>\n      created branch <a href=\"{branchurl}\">{branch_name}</a>\n      at <a href=\"{repo_url}\">{repo_name}</a></h2>\n    <time datetime=\"{created_at}\">{time_since}</time>\n  </header>\n</div>",
	"github_fork": "<div class=\"socialfeed-item socialfeed-github socialfeed-github-fork\">\n  <i class=\"socialfeed-icon icon-github\"></i>\n  <header>\n    <h2>\n      <a href=\"{profile_url}\">{username}</a>\n      forked repository <a href=\"{repo_url}\">{repo_name}</a>\n      to <a href=\"{forkee_url}\">{forkee_name}</a>\n    </h2>\n    <time datetime=\"{created_at}\">{time_since}</time>\n  </header>\n</div>",
	"github_issue": "<div class=\"socialfeed-item socialfeed-github socialfeed-github-issue\">\n  <i class=\"socialfeed-icon icon-github\"></i>\n  <header>\n    <h2>\n      <a href=\"{profile_url}\">{username}</a>\n      {action} issue <a href=\"{issue_url}\">{issue_name}</a>\n    </h2>\n    <time datetime=\"{created_at}\">{time_since}</time>\n  </header>\n  <div class=\"socialfeed-body\">\n    {title}\n  </div>\n</div>",
	"github_pullrequest": "<div class=\"socialfeed-item socialfeed-github socialfeed-github-pull-request\">\n  <i class=\"socialfeed-icon icon-github\"></i>\n  <header>\n    <h2>\n      <a href=\"{profile_url}\">{username}</a>\n      {action} pull request <a href=\"{pullrequest_url}\">{pullrequest_name}</a>\n    </h2>\n    <time datetime=\"{created_at}\">{time_since}</time>\n  </header>\n  <div class=\"socialfeed-body\">\n    {title}\n  </div>\n</div>",
	"github_push": "<div class=\"socialfeed-item socialfeed-github socialfeed-github-push\">\n  <i class=\"socialfeed-icon icon-github\"></i>\n  <header>\n    <h2>\n      <a href=\"{profile_url}\">{username}</a>\n      pushed to <a href=\"{repo_url}\">{repo_name}</a>\n    </h2>\n    <time datetime=\"{created_at}\">{time_since}</time>\n  </header>\n  <ul class=\"socialfeed-commit-list\">\n    <li>\n      <a href=\"{commit_url}\">{commit}</a>\n      <span>{commit_message}</span>\n    </li>\n  </ul>\n</div>",
	"github_watch": "<div class=\"socialfeed-item socialfeed-github socialfeed-github-watch\">\n  <i class=\"socialfeed-icon icon-github\"></i>\n  <header>\n    <h2><a href=\"{profile_url}\">{username}</a> starred <a href=\"{repo_url}\">{repo_name}</a></h2>\n    <time datetime=\"{created_at}\">{time_since}</time>\n  </header>\n</div>",
	"rss": "<div class=\"socialfeed-item socialfeed-rss\">\n  <i class=\"socialfeed-icon icon-rss\"></i>\n  <header>\n    <h2>\n      New blog post at\n      <a href=\"{blogurl}\">{blogname}</a>\n    </h2>\n    <time datetime=\"{date}\">{time_since}</time>\n  </header>\n  <div class=\"socialfeed-body\">\n    <a href=\"{url}\">{title}</a>\n  </div>\n</div>",
	"tumblr": "<div class=\"socialfeed-item socialfeed-tumblr\">\n  <i class=\"socialfeed-icon icon-tumblr\"></i>\n  <header>\n    <h2><a href=\"{post_url}\">{title}</a></h2>\n    <time datetime=\"{date}\">{time_since}</time>\n  </header>\n  <div class=\"socialfeed-body\">\n    {body}\n\n    <p>Notes: {note_count}</p>\n  </div>\n</div>",
	"vimeo_add_comment": "<div class=\"socialfeed-item socialfeed-vimeo socialfeed-vimeo-comment\">\n  <i class=\"socialfeed-icon icon-play-sign\"></i>\n  <header>\n    <h2><a href=\"{user_url}\">{user_name}</a> commented on <a href=\"{video_url}\">{video_title}</a> on Vimeo</h2>\n    <time datetime=\"{created_at}\">{time_since}</time>\n  </header>\n\n  <div class=\"socialfeed-body\">\n    {comment_text}\n  </div>\n</div>",
	"vimeo_like": "<div class=\"socialfeed-item socialfeed-vimeo socialfeed-vimeo-like\">\n  <i class=\"socialfeed-icon icon-play-sign\"></i>\n  <header>\n    <h2><a href=\"{user_url}\">{user_name}</a> liked a <a href=\"{video_url}\">video on Vimeo</a></h2>\n    <time datetime=\"{created_at}\">{time_since}</time>\n  </header>\n\n  <div class=\"socialfeed-body\">\n    <h3><a href=\"{video_url}\">{video_title}</a></h3>\n    <a href=\"{video_url}\">\n      <img src=\"{video_thumbnail_large}\" alt=\"{video_title}\">\n    </a>\n  </div>\n</div>",
	"vimeo_upload": "<div class=\"socialfeed-item socialfeed-vimeo socialfeed-vimeo-upload\">\n  <i class=\"socialfeed-icon icon-play-sign\"></i>\n  <header>\n    <h2><a href=\"{user_url}\">{user_name}</a> uploaded a <a href=\"{video_url}\">video on Vimeo</a></h2>\n    <time datetime=\"{created_at}\">{time_since}</time>\n  </header>\n\n  <div class=\"socialfeed-body\">\n    <h3><a href=\"{video_url}\">{video_title}</a></h3>\n    <a href=\"{video_url}\">\n      <img src=\"{video_thumbnail_large}\" alt=\"{video_title}\">\n    </a>\n  </div>\n</div>",
	"youtubeuploads": "<div class=\"socialfeed-item socialfeed-youtube socialfeed-youtube-upload\">\n  <i class=\"socialfeed-icon icon-play-circle\"></i>\n  <header>\n    <h2><a href=\"{profile_url}\">{username}</a> added a video: <a href=\"{video_url}\">{video_name}</a></h2>\n    <time datetime=\"{created_at}\">{time_since}</time>\n  </header>\n  <div class=\"socialfeed-body\">\n    <iframe class=\"youtube-preview\"\n      src=\"http://www.youtube.com/embed/{entry_id}?wmode=transparent&amp;HD=0&amp;rel=0&amp;showinfo=0&amp;controls=1&amp;autoplay=1\"\n      frameborder=\"0\"\n      allowfullscreen>\n    </iframe>\n    <p>{desc}</p>\n  </div>\n</div>",

};
},{}]},{},[1])(1)
});
;