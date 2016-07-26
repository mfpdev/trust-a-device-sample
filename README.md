IBM MobileFirst Foundation 8.0
===
## Trust your web client sample 
A sample showing how to add trusted web client by scan a QR code from your mobile app using MobileFirst Foundation 8.0.  

##Demo
[![Trust your web client](https://img.youtube.com/vi/HgA-aPUWEeE/0.jpg)](https://www.youtube.com/watch?v=HgA-aPUWEeE)


### Usage
* The sample is consist from 4 parts, to run the sample follow each part instructions by click on the following links:

1. [Deploy the UserLogin Security Check] (https://github.com/MobileFirst-Platform-Developer-Center/SecurityCheckAdapters/tree/release80) 
2. [Deploy the QRCodeWebLogin Security Check](qrcode-web-login-security-check/README.md)
3. [Run the NodeJS Web App] (node-web-app/README.md) - The web application which will display the QRCode. After scan it the web will display "Hello user" message.
4. [Build and deploy the Cordova Mobile App] (cordova-app/README.md) - The mobile application which will be used to scan the QRCode on the web app.

To run it follow each README file for each part.

> This sample can work either on local server or on [BlueMix](www.bluemix.com).  If you want it to work on BlueMix you will need to have [IBM MobileFirst Foundation] and Node runtime.

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