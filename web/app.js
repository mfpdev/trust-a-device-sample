var express = require('express');
var request = require('request');
var cfenv = require('cfenv');

var app = express();
var http = require('http').Server(app);
var io = require('socket.io')(http);

var appEnv = cfenv.getAppEnv();
var mfpServer = "https://mobilefoundation-bb-server.mybluemix.net:443";
//var mfpServer = "http://localhost:9080";

app.use('/www', express.static(__dirname + '/www'));
app.use('/node_modules/ibm-mfp-web-sdk', express.static(__dirname + '/node_modules/ibm-mfp-web-sdk'));

// Web server - serves the web application
app.get('/', function (req, res) {
  // Website you wish to allow to connect
  res.sendFile(__dirname + '/index.html');
});

// Reverse proxy, pipes the requests to/from MobileFirst Server
app.use('/mfp/*', function (req, res) {
  var url = mfpServer + req.originalUrl;
  console.log('::: server.js ::: Passing request to URL: ' + url);
  req.pipe(request[req.method.toLowerCase()](url)).pipe(res);
});

io.on('connection', function (socket) {
  console.log('a user connected');
});

app.get('/refresh/:uuid', function (req, res) {
  var uuid = req.params.uuid;
  console.log('Get refresh event from uuid ' + uuid);
  io.sockets.emit(uuid, { 'refresh': true });
  res.statusCode = 200;
  return res.send('Sent refresh event to client id ' + uuid);
});



http.listen(appEnv.port, '0.0.0.0', function () {
  // print a message when the server starts listening
  console.log("server starting on " + appEnv.url);
});
