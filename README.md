IBM MobileFirst Foundation 8.0
===
## Trust your web client sample 
A sample showing how to add trusted device by scan a QR code from your mobile app using [MobileFirst Foundation 8.0](http://mobilefirstplatform.ibmcloud.com).

##Demo
[![Trust your web client](https://img.youtube.com/vi/LFt7FOAQw_8/0.jpg)](https://www.youtube.com/watch?v=LFt7FOAQw_8)

### Prerequisites
1. Understanding the IBM MobileFirst Platform [Authentication and Security](https://mobilefirstplatform.ibmcloud.com/tutorials/en/foundation/8.0/authentication-and-security/).
2. Understanding the IBM MobileFirst Platform [Java Adapters](https://mobilefirstplatform.ibmcloud.com/tutorials/en/foundation/8.0/adapters/java-adapters/).
3. Pre-installed IBM MobileFirst Platform [development environment](https://mobilefirstplatform.ibmcloud.com/tutorials/en/foundation/8.0/setting-up-your-development-environment/).
4. Installed [NodeJS runtime](https://nodejs.org/en/) 

### Usage
#### The sample is consisting of the following components:

* The `UserLogin` Security Check - The `UserLogin` Security Check which will used to autorized the mobile app.
    - Download the UserLogin Security from the [following link](https://hub.jazz.net/git/imflocalsdk/console-samples/contents/master/UserLogin.zip)
    - Use either Maven or MobileFirst Developer CLI to [build and deploy adapter](https://mobilefirstplatform.ibmcloud.com/tutorials/en/foundation/8.0/adapters/creating-adapters/).

* [QRCodeWebLogin Security Check](/qrcode-web-login-security-check) - The `QRCodeWebLogin` Security Check which will be used to authorized the web client / add trust in device.
    - Change the `webURLForNotify` property in the [adapter.xml](src/main/adapter-resources/adapter.xml) to point to the Node web app.
    - Deploy the adapter same as you did for `UserLogin` Security Check.
> To be able generating the QR Code image, the security check uses [qrgen](https://github.com/kenglxn/QRGen).

* [NodeJS Web App](/node-web-app) - The web app which will display the QR code. Scanning the QR code will let the mobile app trust the web app.
    -  In the file [`app.js`](app.js) change `mfpServer` to point to you MobileFirst Foundation server URL.
    -  From a command-line window, navigate to the project's root folder and run the commands:
        - `npm install` - to install all the node dependencies.
        - `node app.js` - to run the node web server.
> To allow access to MobileFirst Foundation server, the NodeJS web app uses [IBM MobileFirst Web SDK](https://mobilefirstplatform.ibmcloud.com/tutorials/en/foundation/8.0/adding-the-mfpf-sdk/web/). 
> To allow the auto refresh with push, the NodeJS web app uses [socket.io](http://socket.io/).

* [The Cordova Mobile App](cordova-app) - The mobile app which will be used to scan the QR code on the web app.
    - From a command-line window, navigate to the project's root folder and run the commands:
        - `cordova platform add {your favorite platform}` - to add a platform. 
        - `mfpdev app register` - to register the application.
        - `cordova run` - to run the application.
        
    
### Supported Levels
IBM MobileFirst Platform Foundation 8.0

### License
Copyright 2016 IBM Corp.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.