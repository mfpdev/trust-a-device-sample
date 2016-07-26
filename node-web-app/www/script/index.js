var socket = io();

var wlInitOptions = {
    mfpContextRoot: '/mfp',
    applicationId: 'com.sample.qrcode'
};


QRCodeChallengeHandler = WL.Client.createSecurityCheckChallengeHandler("qrcode");

QRCodeChallengeHandler.handleChallenge = function (challenge) {
    if (challenge.isRegistered) {
        QRCodeChallengeHandler.submitChallengeAnswer({});
    } else {
        document.getElementById("displayTxt").innerHTML = "To turst this device, use your mobile app to scan the QR code.";
        var qrImage = document.getElementById("qrCode");
        qrImage.src = "data:image/jpg;base64," + challenge.qrCode;
        document.getElementById("titleDisplayText").innerHTML = "IBM <span class='bold'>MobileFirst</span> Foundation";
        
        socket.on(challenge.qrUUID, function (data) {
                if (data.refresh) {
                    location.reload();   
                }
        });
    }
};

function getWebUser() {
    var resourceRequest = new WLResourceRequest("/adapters/QRCodeWebLogin/user", WLResourceRequest.GET);
    resourceRequest.send().then(
        function (response) {
            document.getElementById("displayTxt").innerHTML = "";
            document.getElementById("titleDisplayText").innerHTML = "<span class='bold'>Hello " + response.responseJSON["displayName"] + "</span>"
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
