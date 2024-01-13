let apiUrl = {
    loginUrl: "/users/login",
    //TODO: 登入成功跳轉頁面需要更新
    loginSuccess: "index.html",
    //TODO: 登入失敗跳轉頁面需要更新
    loginFailure: "/users/login"

}

window.onload = function () {
    let loginBtn = document.getElementById("loginBtn");
    loginBtn.addEventListener("click", function (event) {
        event.preventDefault();
        let username = document.getElementById("inputUsername").value;
        let password = document.getElementById("inputPassword").value;
        $.post({
            url: apiUrl.loginUrl,
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({"username": username, "password": password}),
            dataType: "JSON",
            success: function (json) {
                if (json.state == 200) {
                    alert("登入成功!");
                    location.href = "index.html"
                } else if (json.state == 5002) {
                    alert("密碼錯誤!");
                    location.href = "login.html";
                } else if (json.state == 5001) {
                    alert("帳號不存在!");
                    location.href = "login.html";
                }
            },
            error: function (xhr) {
                alert("登入時產生未知的異常: " + xhr.status);
            }
        })
    })

    function parseJwt (token) {
        var base64Url = token.split('.')[1];
        var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        return JSON.parse(jsonPayload);
    };

    function handleCallback(response) {
        const data = parseJwt(response.credential);
        console.log(data);
    }
}
