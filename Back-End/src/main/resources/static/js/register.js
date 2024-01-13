let apiUrl = {
    registerUrl: "/users/register",
    registerSuccess: "/userInfo.html",
    registerError: "/error"
}

window.onload = function () {
    let registerBtn = document.getElementById("submitBtn");
    registerBtn.addEventListener("click", function (event) {
        event.preventDefault();
        let username = document.getElementById("username").value;
        let password = document.getElementById("password").value;
        let consistPassword = document.getElementById("consistPassword").value;
        let phone = document.getElementById("phone").value;
        let email = document.getElementById("email").value;
        checkPassword(password, consistPassword);
        $.post({
            url: apiUrl.registerUrl,
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({"username": username, "password": password, "phone": phone, "email": email}),
            dataType: "JSON",
            success: function (json) {
                if (json.state == 201) {
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

function checkPassword(password, consistPassword) {
    if (!(password === consistPassword)) {
        alert("兩次輸入的密碼不一致，請再次輸入!");
        //TODO: 確認要做甚麼事情?
        window.location.href = apiUrl.registerUrl;
    }
}


