$(function () {

    let receivedChunks = [];   //存放多個blob
    let totalimgChunks = 0;     //
    let receivedImgChunkCount = 0;
    let imagedata = "";
    let webSocket;
    let msg = document.getElementById("msg");
    let log = document.getElementById("log");
    let uid = document.getElementById("uid");
    let room = document.getElementById('room');
    let blobContainer = [];
    let imgSender = "";

    //audio
    let chunks = [];
    let isRecording = false;
    let mediaRecorder;
    let stream;
    let receivedAudioChunks = [];   //存放多個blob
    let receivedAudioChunkCount = 0;
    let audioSender = "";
    let audioBlobContainer = [];
    let totalaudioChunks = 0;

    //合併分割的 block 事件
    function dataURItoBuffer(dataURI) {
        let byteString = atob(dataURI.split(",")[1]);
        let buffer = new ArrayBuffer(byteString.length);
        let view = new Uint8Array(buffer);
        totalimgChunks = 0;     //

        for (let i = 0; i < byteString.length; i++) {
            view[i] = byteString.charCodeAt(i);
        }

        return buffer;
    }

    // 按鈕 : 通話按鈕綁定的點擊事件
    $(document).on('click', ".chat", () => {
        let roomId = room.value
        let rtc = window.open('/rtc.html');
        rtc.sessionStorage.setItem('room', roomId);
        if (typeof webSocket.onopen === 'function') {
            let phoneMessage = {
                "type": "phone",
                "uid": uid.value,
            }
            webSocket.send(JSON.stringify(phoneMessage));
        }
    })

    //按鈕 : 選擇圖片傳輸按鈕
    $('#fileInput').change(function (event) {
        let file = event.target.files[0];
        console.log('Selected file:', file);
        let img = '';
        let reader = new FileReader();

        reader.onload = function (event) {
            let imageData = event.target.result;
            $("#imageElement").attr("src", imageData);

            imagedata = dataURItoBuffer(imageData);
        };
        reader.readAsDataURL(file);
    });

    // 開始 webstock 連線開始，( connect 也可以分開來呼叫 )
    let start = document.getElementById("start");
    $("#start").on("click", function () {
        connect();
    });

    // 按鈕 : 傳輸訊息 ( 傳遞 icon )
    $("#send").on("click", function () {
        sendmessage();
    });
    // 按鈕 : 傳遞訊息 ( 綁定 enter )
    $("#msg").on("keydown", function () {
        if (event.keyCode === 13) {
            event.preventDefault();
            sendmessage();
        }
    })

    //方法 : 傳輸資料的方法
    function sendmessage() {
        if (webSocket.readyState === WebSocket.CLOSED) {
            //如果關閉會打開
            connect();
            //圖片跟文字輸入框判斷式
        } else {
            if (imagedata === "") {
                // 傳到 webstocket 資料庫的格式
                let message = {
                    message: msg.value,
                    type: "text",
                    uid: uid.value,
                };
                webSocket.send(JSON.stringify(message));
                //axios ( 後面都要存到資料庫)
                msg.value = ""; // 把input清空 claeripute function
                imagedata = "";
                chunks = [];

            } else {
                if (msg.value !== "") {
                    let message1 = {
                        message: msg.value,
                        type: "text",
                        uid: uid.value,
                    };
                    webSocket.send(JSON.stringify(message1));
                }
                // console.log(imagedata);
                //因為誠誠 img size 分批傳
                let chunkSize = 8192;
                totalimgChunks = Math.ceil(imagedata.byteLength / chunkSize);
                let currentChunk = 0;

                while (currentChunk < totalimgChunks) {
                    let start = currentChunk * chunkSize;
                    let end = start + chunkSize;
                    let chunk = imagedata.slice(start, end);
                    if (currentChunk === 0) {
                        let message = {
                            type: "image",
                            uid: uid.value,
                            chunks: totalimgChunks,
                        }
                        webSocket.send(JSON.stringify(message));
                    }
                    webSocket.send(chunk);
                    currentChunk++;
                }

                msg.value = "";
                imagedata = "";
                chunks = [];
                $('#fileInput').val(null);
                $("#imageElement").attr("src", "");
            }
        }
    }

    //選擇性增加 : 文字輸入框，沒有webstock連線是沒辦法打字的
    let msgDiv = document.getElementById("msgDiv");
    start.style.display = "block";
    msgDiv.style.display = "none";

    //方法 : 宣告這次對話的暫存區 ( 建立 socket 連線後坐的事情都在這裡 (
    function connect() {
        totalimgChunks = 0;
        receivedImgChunkCount = 0;
        blobContainer = [];
        totalaudioChunks = 0;
        receivedAudioChunkCount = 0;
        audioBlobContainer = [];

        //宣告這次連線的房間號碼
        let roomid = $("#room").val();
        console.log("connecting....");
        //下面進入連線
        // webSocket = new WebSocket("ws://10.0.104.120:8080/websocket/" + roomid);
        webSocket = new WebSocket("ws://localhost:8080/websocket/" + roomid);
        webSocket.roomId = roomid;

        //下面刪掉 不能再輸入room id 、 id
        $("#room").prop("disabled", true);
        $("#uid").prop("disabled", true);
        //上面刪掉


        //連線失敗的訊息要補在這裡
        webSocket.onerror = function (event) {
            // console.log("onerror");
        };

        //開啟連線使用輸入框可以寫
        webSocket.onopen = function (event) {
            // console.log("open");
            start.style.display = "none";
            msgDiv.style.display = "block";
        };

        //關閉連線 ( 下面有相對應的判斷，他會自動在建立連線 所以放著 )
        webSocket.onclose = function (event) {
            console.log("WebSocket close：" + event.code + "，原因：" + event.reason);
            // start.style.display = "block";
            // msgDiv.style.display = "none";
        };

        // 最重要 ::: webstock 後端吐回來的東西
        webSocket.onmessage = function (event) {
            //宣告使用者id ( 需要用 axios 打進來的帶入 )
            //這個應該在開啟房間的最上面就宣告全愈變數
            let ud = $("#uid").val();
            // console.log("Received data:", event.data);

            //型別判斷
            if (typeof event.data === "string") {
                console.log(event.data)
                try {
                    //解析json
                    let obj = JSON.parse(event.data);

                    // 如果是圖片
                    if (obj.type === "image") {
                        // chunks是純數字
                        receivedImgChunkCount = obj.chunks;
                        imgSender = obj.uid;
                        startTimer(); //開始接10個blob
                    } else if (obj.type === "audio") {
                        receivedAudioChunkCount = obj.chunks;
                        audioSender = obj.uid;
                        startaudioTimer();
                        //文字尋席處理
                    } else if (obj.type === "text") {
                        if (obj.uid == ud) { //自己傳送
                            log.innerHTML += "<span>" + obj.uid + ":" + obj.message + "</span>" + "<br>";
                        } else { //別人傳送的
                            log.innerHTML += "<span style='float: right;'>" + obj.uid + ":" + obj.message + "</span>" + "<br>";
                        }
                    } else if (obj.type === "phone") { // Todo : 不接電話的邏輯 這個會比較麻煩一點，因為會跳Alert需要傳遞 Value
                        if (obj.uid == ud) { //必須跳 alert 電話接通
                            log.innerHTML += "<span>" + "<a style='color: brown'>start chat</a>" + "</span>" + "<br>";
                        } else {
                            log.innerHTML += "<span style='float: right;'>" + obj.uid + ":" + "<button class='chat'><img src='/images/call.png' height='20' width='20'></button>" + "</span>" + "<br>";
                        }
                    }

                } catch (error) {
                    //訊息傳遞失敗
                    console.error("Error parsing JSON:", error);
                }
                //如果接到的是 音檔 傳 Jason 跟他說圖片 這邊就真的團圖片且收到
            } else if (event.data instanceof Blob) {
                if (receivedAudioChunkCount !== 0) {
                    let reader = new FileReader();
                    reader.onload = function (e) {
                        let audio = e.target.result;
                        audioBlobContainer.push(audio);
                        totalaudioChunks++;

                    };
                    reader.readAsDataURL(event.data);
                    //如果接到的是 圖片 傳 Jason 跟他說圖片 這邊就真的團圖片且收到
                } else if (receivedImgChunkCount !== 0) {
                    let reader = new FileReader();
                    reader.onload = function (e) {
                        let image = e.target.result;
                        blobContainer.push(image);
                        totalimgChunks++;
                        // console.log(totalimgChunks);
                        // console.log(blobContainer);
                    };
                    reader.readAsDataURL(event.data);
                }

            }
        }


        function mergeimgBlobs() {
            let mergedArray = new Uint8Array(0);

            for (let i = 0; i < blobContainer.length; i++) {
                let dataURI = blobContainer[i];
                let base64Data = dataURI.split(",")[1];
                let decodedData = atob(base64Data);
                let dataArray = new Uint8Array(decodedData.length);

                for (let j = 0; j < decodedData.length; j++) {
                    dataArray[j] = decodedData.charCodeAt(j);
                }

                mergedArray = concatenateUint8Arrays(mergedArray, dataArray);
            }

            let mergedBlob = new Blob([mergedArray]);
            let imageURL = URL.createObjectURL(mergedBlob);
            console.log(imageURL);
            let imageElement = document.createElement
        }
    }
})