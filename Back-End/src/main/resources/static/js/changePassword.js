let apiUrl = {
    changePasswordUrl: "/users/resetPassword",
    //TODO: 修改密碼成功跳轉頁面需要更新
    changePasswordSuccess: "index.html",
    //TODO: 登入失敗跳轉頁面需要更新
    changePasswordFailure: "/users/login"

}

window.onload = function () {
    let changePasswordBtn = document.getElementById("changePasswordBtn");
    changePasswordBtn.addEventListener("click", function (event) {
        event.preventDefault();
        let currentPassword = document.getElementById("currentPassword").value;
        let newPassword = document.getElementById("newPassword").value;
        $.ajax({
            url: apiUrl.changePasswordUrl,
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "PUT",
            data: {"oldPassword": currentPassword, "newPassword": newPassword},
            success: function (json) {
                if (json.state == 200) {
                    alert("修改成功!");
                    location.href = apiUrl.changePasswordSuccess;
                } else  {
                    alert("修改失敗!");
                    console.log(json.state);
                }
            },
            error: function (xhr) {
                alert("登入時產生未知的異常: " + xhr.status);
            }
        })
    })
}
