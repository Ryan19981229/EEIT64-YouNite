
show();
// ===================以下是socket=======================
const room = sessionStorage.getItem('room');
// const room =2;
const user = Math.round(Math.random() * 1000) + "";
const socketUrl = `ws://192.168.0.155:8081/msgServer2/${user}/${room}`;
let socket = null;
let socketRead = false;
let videoEnabled = true;

startVideo();
document.addEventListener('DOMContentLoaded', () => {
    socket = new WebSocket(socketUrl);
    socket.addEventListener('open', () => {
        // console.log("成功连接到服务器...");
        socketRead = true;
    });
    socket.addEventListener('close', (e) => {
        console.log('与服务器连接关闭: ' + e.code);
        socketRead = false;
    });
    socket.addEventListener('message', (res) => {
        const evt = JSON.parse(res.data);
        console.log(evt);
        if (evt.type === 'offer') {
            console.log("接收到offer,set offer,send answer....");
            onOffer(evt);
        } else if (evt.type === 'answer' && peerStarted) {
            console.log('get answer,set answer SDP');
            onAnswer(evt);
        } else if (evt.type === 'candidate' && peerStarted) {
            console.log('get ice candidate..');
            onCandidate(evt);
        } else if (evt.type === 'bye' && peerStarted) {
            console.log("WebRTC close");
            stop();
        }
    });

    const startVideoButton = document.getElementById('startVideo');
    // startVideoButton.addEventListener('click', startVideo);

    const connectButton = document.getElementById('connect');
    connectButton.addEventListener('click', connect);

    const hangUpButton = document.getElementById('hangUp');
    hangUpButton.addEventListener('click', hangUp);


});

// ===================以上是socket=======================

const localVideo = document.getElementById('local-video');
const remoteVideo = document.getElementById('remote-video');
let localStream = null;
let peerConnection = null;
let peerStarted = false;
const mediaConstraints = {
    'mandatory': {
        'OfferToReceiveAudio': false,
        'OfferToReceiveVideo': true
    }
};

//----------------------交换信息 -----------------------

function onOffer(evt) {
    console.log("get offer...");
    console.log(evt);
    setOffer(evt);
    sendAnswer(evt);
    peerStarted = true;
}

function onAnswer(evt) {
    console.log("get Answer...");
    console.log(evt);
    setAnswer(evt);
}

function onCandidate(evt) {
    const candidate = new RTCIceCandidate({
        sdpMLineIndex: evt.sdpMLineIndex,
        sdpMid: evt.sdpMid,
        candidate: evt.candidate
    });
    console.log("accept Candidate...");
    console.log(candidate);
    peerConnection.addIceCandidate(candidate);
}

function sendSDP(sdp) {
    const text = JSON.stringify(sdp);
    console.log('send sdp.....');
    console.log(text);
    socket.send(text);
}

function sendCandidate(candidate) {
    const text = JSON.stringify(candidate);
    console.log(text);
    socket.send(text);
}

//---------------------- 视频处理 -----------------------
async function startVideo() {
    try {
        const stream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
        localStream = stream;
        localVideo.srcObject = stream;
        localVideo.play();
        localVideo.volume = 0;
    } catch (error) {
        console.error('error: [error code：' + error.code + ']');
    }
}

function refreshPage() {
    location.reload();
}

//---------------------- 处理连接 -----------------------
function prepareNewConnection() {
    const pc_config = {
        iceServers: []
    };
    let peer = null;
    try {
        peer = new RTCPeerConnection(pc_config);
    } catch (e) {
        console.log("建立连接失败，错误：" + e.message);
    }

    peer.onicecandidate = function (evt) {
        if (evt.candidate) {
            console.log(evt.candidate);
            sendCandidate({
                type: "candidate",
                sdpMLineIndex: evt.candidate.sdpMLineIndex,
                sdpMid: evt.candidate.sdpMid,
                candidate: evt.candidate.candidate
            });
        }
    };

    console.log('add local...');
    peer.addStream(localStream);

    peer.addEventListener("addstream", onRemoteStreamAdded, false);
    peer.addEventListener("removestream", onRemoteStreamRemoved, false);

    // 当接收到远程视频流时，使用本地video元素进行显示
    function onRemoteStreamAdded(event) {
        console.log("添加远程视频流");
        // remoteVideo.src = window.URL.createObjectURL(event.stream);
        remoteVideo.srcObject = event.stream;
    }

    // 当远程结束通信时，取消本地video元素中的显示
    function onRemoteStreamRemoved(event) {
        console.log("移除远程视频流");
        remoteVideo.src = "";
    }

    return peer;
}
$("#videoIO").on('click',()=>{toggleVideo();})
function toggleVideo() {
    videoEnabled = !videoEnabled; // Toggle video enable state
    let videoio = document.getElementById("videoIO");
    if (localStream) {
        const videoTracks = localStream.getVideoTracks();
        if (videoTracks.length > 0) {
            videoTracks[0].enabled = videoEnabled; // Enable or disable video track
            if (videoEnabled === false) {
                videoio.textContent = "open";
            } else {
                videoio.textContent = "close";
            }
        }
    }
}
function sendOffer() {
    peerConnection = prepareNewConnection();
    peerConnection.createOffer(function (sessionDescription) { //成功时调用
        peerConnection.setLocalDescription(sessionDescription);
        console.log("send: SDP");
        console.log(sessionDescription);
        sendSDP(sessionDescription);
    }, function (err) { //失败时调用
        console.log("offer fail");
    }, mediaConstraints);
}

function setOffer(evt) {
    if (peerConnection) {
        console.error('peerConnection已存在!');
        return;
    }
    peerConnection = prepareNewConnection();
    peerConnection.setRemoteDescription(new RTCSessionDescription(evt));
}

function sendAnswer(evt) {
    console.log('发送Answer,创建远程会话描述...');
    if (!peerConnection) {
        console.error('peerConnection不存在!');
        return;
    }

    peerConnection.createAnswer(function (sessionDescription) { //成功时
        peerConnection.setLocalDescription(sessionDescription);
        console.log("发送: SDP");
        console.log(sessionDescription);
        sendSDP(sessionDescription);
    }, function () { //失败时
        console.log("创建Answer失败");
    }, mediaConstraints);
}

function setAnswer(evt) {
    if (!peerConnection) {
        console.error('peerConnection不存在!');
        return;
    }
    peerConnection.setRemoteDescription(new RTCSessionDescription(evt));
}

//-------- 处理用户UI事件 -----
// 开始建立连接
function connect() {
    if (!localStream) {
        alert("no local.");
    } else if (peerStarted || !socketRead) {
        alert("refresh please.");
    } else {
        sendOffer();
        peerStarted = true;
        $("#connect").hide();
        sessionStorage.setItem("accept","false");
    }

}

// 停止连接
function hangUp() {
    console.log("hang up.");
    let msg={
        "type":"bye"
    }
    socket.send(JSON.stringify(msg));
    stop();
}

function stop() {
    peerConnection.close();
    peerConnection = null;
    peerStarted = false;
}
function show(){
    if(sessionStorage.getItem("accept")==="true"){
        $("#connect").show();
    }else {
        $("#connect").hide();

    }
}