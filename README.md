# strategies
A simple implementation of session-based streams

   A winning player strategy (WPS) is a sequence of well-bracketed winning
   opponent strategies.
   A winning opponent strategy (WOS) is a sequence of well-bracketed
   winning player strategies.

   In symbols

   WPS ::= ( WBOS )*

   WOS ::= ( WBPS )*

   WBOS ::= "(" WOS ")"

   WBPS ::= "[" WPS "]"
  
   We interpret these as denoting successful sessions. For example,
   to interpret HTTP we think of Player as the client and Opponent
   as the server; thus, "(" is taken as an outgoing HTTP GET request
   (i.e. the request as seen from the client code), and ")" as an
   incoming HTTP GET server response (i.e. the response as seen from
   the client code ). Then, the trace of a non-crashing HTTP client
   is ()()() ... (), and this trace clearly inhabits WPS.

   Similarly, if we take "[" as the incoming HTTP GET request
   (i.e. the request as seen from the server code) and "]" as the
   outgoing HTTP GET response (i.e. the response as seen from the
   server code), then the trace of a non-crashing HTTP server is
   [][][] ... [], and this trace clearly inhabits WOS.

   Note, however, that in HTTP the server cannot initiate a call
   back to a client in response to a request. This is tantamount to
   a mechanism for session state, and HTTP is noted for its lack of
   ability to properly handle session state -- a short-coming in
   protocol hygene that has left cookie crumbs all over the
   Internet.

   In the view of protocols taken here we do get sessions, without
   sacrificing much of what makes HTTP attractive. Obviously, the
   protocol structure is simple. It takes a maximum of four lines to
   specify all legal traces. Yet, it's rich enough to model basic
   HTTP. Beyond that it provides an explicit representation of
   session state, while making client and server roles completely
   symmetric. Of equal importance, this model maps directly to
   models of Linear Logic. As a result, all of the tools of nearly
   30 years of investigation into the computational interpretation
   of that logic are available to allow us to reason about protocols
   structured in this way.
