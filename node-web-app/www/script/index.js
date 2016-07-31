var socket = io();

var wlInitOptions = {
    mfpContextRoot: '/mfp',
    applicationId: 'com.sample.qrcode'
};


QRCodeChallengeHandler = WL.Client.createSecurityCheckChallengeHandler("qrcode");

QRCodeChallengeHandler.handleChallenge = function (challenge) {
    if (challenge.isRegistered) {
        //In case the page is refreshed manually 
        QRCodeChallengeHandler.submitChallengeAnswer({});
    } else {
        document.getElementById("displayTxt").innerHTML = "To turst this device, <br>use your mobile app to<br> scan the QR code.";
        var qrImage = document.getElementById("qrCode");
        qrImage.src = "data:image/jpg;base64," + challenge.qrCode;
        
        socket.on(challenge.qrUUID, function (data) {
            if (data.refresh) {
                QRCodeChallengeHandler.submitChallengeAnswer({});
            }
        });
    }
};

function getWebUser() {
    var resourceRequest = new WLResourceRequest("/adapters/QRCodeWebLogin/user", WLResourceRequest.GET);
    resourceRequest.send().then(
        function (response) {
            document.getElementById("displayTxt").innerHTML = "<span class='medium'>Hello " + response.responseJSON["displayName"] + "</span>"
            var qrImage = document.getElementById("qrCode");
            qrImage.src = "www/images/foundation.png";
        },
        function (error) {
            alert(JSON.stringify(error));
        }
    );
}

WL.Client.init(wlInitOptions).then(
    function () {
        getWebUser();
    }, function (error) {
        alert(error);
    }
);
