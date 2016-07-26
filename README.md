IBM MobileFirst Foundation 8.0
===
## Trust your web client sample 
A sample showing how to add trusted web client by scan a QR code from your mobile app using [MobileFirst Foundation 8.0](http://mobilefirstplatform.ibmcloud.com).  

##Demo
[![Trust your web client](https://img.youtube.com/vi/LFt7FOAQw_8/0.jpg)](https://www.youtube.com/watch?v=LFt7FOAQw_8)

### Usage
* The sample is consisting of 4 components, to run the sample follow each part instructions by click on the following links:

    1. [The UserLogin Security Check] (https://hub.jazz.net/git/imflocalsdk/console-samples/contents/master/UserLogin.zip) - The UserLogin Security Check which will used to autorized the mobile app.
    2. [The QRCodeWebLogin Security Check](qrcode-web-login-security-check/README.md) - The QRCodeWebLogin Security Check which will used to authorized the web client.
    3. [The NodeJS Web App] (node-web-app/README.md) - The web app which will display the QR code. Scanning the QR code will let the mobile app trust the web app.
    4. [The Cordova Mobile App] (cordova-app/README.md) - The mobile app which will be used to scan the QR code on the web app.

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