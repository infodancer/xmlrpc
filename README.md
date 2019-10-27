The XML-RPC project provides a Java wrapper around the XML-RPC web api.
More information on XML-RPC is available here: http://www.xmlrpc.com/spec

To perform a release with the current configuration,
and starting with a snapshot that is ready for release:

$ mvn release:clean release:prepare -P release
$ mvn release:perform -P release