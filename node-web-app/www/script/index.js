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
        document.getElementById("displayTxt").innerHTML = "Use the QR code login app to scan the code.";
        var qrImage = document.getElementById("qrCode");
        qrImage.src = "data:image/jpg;base64," + challenge.qrCode;
        
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
            document.getElementById("titleDisplayText").innerHTML = "Hello " + response.responseJSON["displayName"]
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
