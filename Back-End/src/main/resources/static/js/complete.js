// window.onload = function() {
//     const messageElement = document.getElementById("message");
//     messageElement.innerText = "交易完成囉! redirect to here!";
//
//     // 發送 POST 請求修改訂單的 unlocked 欄位值
//     fetch('/orders', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//         },
//         body: JSON.stringify({ unlocked: true }), // 設置 unlocked 欄位的值為 1
//     })
//         .then(response => response.json())
//         .then(res => {
//             // 處理回應
//             console.log(res);
//         })
//         .catch(error => console.error('錯誤:', error));
// };
//
//
//
//
//
//
