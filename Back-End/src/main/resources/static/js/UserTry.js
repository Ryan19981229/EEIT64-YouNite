let apiURL = {
    userProfileURL: "/users/userProfile",
    userProfileSuccess: "/userInfo.html",
    userProfileError: "/error"
}

window.onload = function() {
    let btn = document.getElementById("userProfileBtn");
    btn.addEventListener("click", function(){
        $.get({
            url: apiUrl.userProfileURL,
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({"username": username, "password": password, "phone": phone, "email": email}),
            dataType: "JSON",
            success: function (json) {
                if (json.state == 200) {
                    alert("註冊成功!");
                    window.location.href = apiUrl.registerSuccess;
                } else if (json.state == 4000) {
                    alert("帳號已存在!")
                    window.location.href = apiUrl.registerUrl;
                }
            },
            error: function (xhr) {
                alert("註冊時產生未知的異常: " + xhr.status);
            }
        });
    })
}

