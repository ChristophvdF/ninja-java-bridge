var util = require('util');
var stream = require('stream');
var spawn = require('child_process').spawn;
var byline = require('byline');

util.inherits(Driver,stream);

var javaClass = 'com.ninjablocks.client.driver.test.ElliotsTestDriver';

function Driver(opts,app) {

  var self = this;
  var device = {};

  var p = spawn('java', ['-jar', 'lib/java-bridge.jar']);

  function write(message) {
    console.log('To Java : ' + JSON.stringify(message));
    p.stdin.write(JSON.stringify(message) + '\n');
  }

  byline(p.stdout).on('data', function(message) {
    console.log("From java : " + message);
    message = JSON.parse(message);

    if (message == 'ready') {
      write({method:'init', args:[javaClass]});
    } else {
      var method;
      if (message.context == 'app') { // Context 'app'
        method = app[message.method];
      } else if (!message.context) { // No context, use 'this'
        method = this[message.method];
      } else if (device[message.context]){ // Context can be a device guid
        var d = device[message.context];
        method = d[message.method];
      } else {
        console.log("Unknown context! " + message.context);
      }

      if (message.method == 'on') {
        method.call(null, message.args[0], function() {
          console.log("CB from on " + message.args[0]);
          write({
            method:'on',
            args: message.args.concat(Array.prototype.slice.call(arguments, 0))
          });
        });
      } else {
        if (message.method == 'emit' && message.args[0] == 'register') {
          var newDevice = message.args[1];
          device[newDevice.guid] = newDevice;
          newDevice.emit = function(channel, data) {
            console.log("Device.emit", channel, data);
          };
          console.log('Registered a new java device', newDevice);
        }
        method.apply(this, message.args);
      }

    }

  });

  byline(p.stderr).on('data', function(data) {
    console.log(data.red);
  });

}

module.exports = Driver;
