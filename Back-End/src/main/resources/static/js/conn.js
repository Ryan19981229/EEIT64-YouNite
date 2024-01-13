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
      $('#fileInput').change(function (event) {
            let file = event.target.files[0];
            console.log('Selected file:', file);

            let formData = new FormData();
            formData.append("file", file);

            axios.post("http://localhost:8080/storage/uploadChat", formData, {
                  headers: {
                        "Content-Type": "multipart/form-data"
                  }
            })
                .then((res) => {
                      let data = res.data;
                      console.log("image", data);
                      let msg = {
                            "type": "image",
                            "data": data.data,
                            "ud": uid.value
                      };
                      webSocket.send(JSON.stringify(msg));

                      let msgtodb = {
                            "senderId": 333,
                            "receiverId": 6,
                            "messageType": "image",
                            "roomId": 36,
                            "messageContent": data.data
                      };

                      axios.post("http://localhost:8080/message/add", JSON.stringify(msgtodb), {
                            headers: {
                                  "Content-Type": "application/json"
                            }
                      })
                          .then(() => console.log("add ok"))
                          .catch((error) => console.error("Error adding message:", error));
                })
                .catch((error) => {
                      console.error("File upload error:", error);
                });
      });







      $("#start").on("click", function () {
            connect();
      });


      $("#send").on("click", function () {
            sendmessage();
      });
      $("#msg").on("keydown", function () {
            if (event.keyCode === 13) {
                  event.preventDefault();
                  sendmessage();
            }
      })

      function sendmessage() {
            if (webSocket.readyState === WebSocket.CLOSED) {
                  connect();}
            else {
                  let message = {
                        message: msg.value,
                        type: "text",
                        uid: uid.value,
                  };
                  webSocket.send(JSON.stringify(message));




                  let msgg={
                        "senderId":333,
                        "receiverId":6,
                        "messageType":"text",
                        "roomId":36,
                        "messageContent":msg.value
                  }
                  axios.post("http://localhost:8080/message/add",JSON.stringify(msgg),{
                        headers:({
                              "Content-Type": "application/json"
                        })
                  })
                      .then((res)=>console.log(res.data.state))

                  msg.value = "";
                  imagedata = "";
                  chunks = [];
                  $('#fileInput').val(null);
                  $("#imageElement").attr("src", "");
            }


                  }



      let start = document.getElementById("start");
      let msgDiv = document.getElementById("msgDiv");

      start.style.display = "block";
      msgDiv.style.display = "none";

      function connect() {
            // totalimgChunks = 0;
            receivedImgChunkCount = 0;
            blobContainer = [];
            totalaudioChunks = 0;
            receivedAudioChunkCount = 0;
            audioBlobContainer = [];

            let roomid = $("#room").val();
            let userid = $("#uid").val();

            console.log("connecting....");
            // webSocket = new WebSocket("ws://10.0.104.120:8080/websocket/" + roomid);
            webSocket = new WebSocket("ws://localhost:8080/websocket/" + userid + "/" + roomid);

            webSocket.roomId = roomid;
            $("#room").prop("disabled", true);
            $("#uid").prop("disabled", true);
            webSocket.onerror = function (event) {
                  // console.log("onerror");
            };
            webSocket.onopen = function (event) {
                  // console.log("open");
                  start.style.display = "none";
                  msgDiv.style.display = "block";
            };
            webSocket.onclose = function (event) {
                  console.log("WebSocket close：" + event.code + "，原因：" + event.reason);
                  // start.style.display = "block";
                  // msgDiv.style.display = "none";
            };
            webSocket.onmessage = function (event) {

                  let ud = $("#uid").val();
                  // console.log("Received data:", event.data);
                  if (typeof event.data === "string") {
                        console.log(event.data)
                        try {
                              let obj = JSON.parse(event.data);
                              if (obj.type === "image") {
                                    if(obj.ud===ud){
                                          let div = document.createElement("div");
                                          let img = document.createElement("img");
                                          img.src = obj.data;
                                          div.appendChild(img);
                                          div.style.cssFloat = "left";
                                          log.append(div);
                                    }else{
                                          let div = document.createElement("div");
                                          let img = document.createElement("img");
                                          img.src = obj.data;
                                          div.appendChild(img);
                                          div.style.cssFloat = "right";
                                          log.append(div);
                                    }


                              }  else if (obj.type === "audio") {
                                    if(obj.ud===uid){
                                          let div = document.createElement("div");
                                          let audio = document.createElement("audio");
                                          audio.src = obj.data;
                                          audio.controls=true;
                                          div.appendChild(audio);
                                          div.style.cssFloat = "left";
                                          log.append(audio);
                                    }else{
                                          let div = document.createElement("div");
                                          let audio = document.createElement("audio");
                                          audio.src = obj.data;
                                          audio.controls=true;
                                          div.appendChild(audio);
                                          div.style.cssFloat = "right";
                                          log.append(audio);
                                    }

                              } else if (obj.type === "text") {
                                    if (obj.uid == ud) {
                                          log.innerHTML += "<span>" + obj.uid + ":" + obj.message + "</span>" + "<br>";
                                    } else {
                                          log.innerHTML += "<span style='float: right;'>" + obj.uid + ":" + obj.message + "</span>" + "<br>";
                                    }
                              } else if (obj.type === "phone") {
                                    if (obj.uid == ud) {
                                          log.innerHTML += "<span>" + "<a style='color: brown'>start chat</a>" + "</span>" + "<br>";
                                    } else {
                                          log.innerHTML += "<span style='float: right;'>" + obj.uid + ":" + "<button class='chat'><img src='/images/call.png' height='20' width='20'></button>" + "</span>" + "<br>";
                                    }
                              }

                        } catch (error) {
                              console.error("Error parsing JSON:", error);
                        }
                  }
            }





            $('#recordButton').click(function () {
                  if (!isRecording) {
                        chunks = [];
                        isRecording = true;

                        navigator.mediaDevices.getUserMedia({ audio: true })
                            .then(function (mediaStream) {
                                  stream = mediaStream;
                                  mediaRecorder = new MediaRecorder(mediaStream);

                                  mediaRecorder.addEventListener('dataavailable', function (event) {
                                        chunks.push(event.data);
                                        console.log('Received data chunk:', event.data);
                                  });

                                  mediaRecorder.start();
                                  $('#recordButton').text('');
                                  $('#recordButton').css({ "background-image": 'url(/images/record.gif)', 'background-size': 'cover', })
                            })
                            .catch(function (error) {
                                  console.error('麥克風不給:', error);
                            });
                  } else if (isRecording === true) {
                        mediaRecorder.addEventListener('stop', function () {
                              isRecording = false;
                              stream.getTracks().forEach(function (track) {
                                    track.stop();
                              });
                              $('#recordButton').text('start');
                              $('#recordButton').css("background-image", 'url()');

                              let blob = chunks[0];
                              let form = new FormData();
                              let file = new File([blob], 'audio_recording.webm', { type: 'audio/webm;codecs=opus' });

                              form.append("file", file);

                              axios.post("http://localhost:8080/storage/uploadChat", form, {
                                    headers: {
                                          "Content-Type": "multipart/form-data"
                                    }
                              })
                                  .then((res) => {
                                        let msgaudio = {
                                              "type": "audio",
                                              "data": res.data.data,
                                              "ud": uid.value
                                        };
                                        webSocket.send(JSON.stringify(msgaudio));

                                        let msgaudiotodb = {
                                              "senderId": userid,
                                              "receiverId": 6, //對方id變數
                                              "messageType": "audio",
                                              "roomId": roomid,
                                              "messageContent": res.data.data
                                        };

                                        axios.post("http://localhost:8080/message/add", JSON.stringify(msgaudiotodb), {
                                              headers: {
                                                    "Content-Type": "application/json"
                                              }
                                        })
                                            .then(() => console.log("ok"))
                                            .catch((error) => console.error("Error adding audio message:", error));
                                  });
                        });

                        mediaRecorder.stop();
                  }
            });




      }
})
