let apiUrl = {
    interestUrl: "/users/mutualInterests",
    //TODO: 登入成功跳轉頁面需要更新
    interestSuccess: "index.html",
    //TODO: 登入失敗跳轉頁面需要更新
    interestFailure: "/users/login"

}

window.onload = function () {
    let interest = document.getElementById("interest");
    interest.addEventListener("click", function (event) {
        event.preventDefault();
        $.get({
            url: apiUrl.interestUrl,
            success: function (json) {
                if (json.state == 200) {
                    alert("登入成功!");
                    console.log(json.data[0].interests[0])
                    console.log(json.data[0].interests[1])
                    console.log(json.data[0].name)
                    console.log(json.data[0].age)
                    console.log(json.data[0].age)
                    console.log(json.data[0].profileAvatar)
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
