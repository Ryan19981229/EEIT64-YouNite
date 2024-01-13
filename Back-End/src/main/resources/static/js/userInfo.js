let apiUrl = {
    userInfoUrl: "/users/userInfo",
    userInfoBuildSuccess: "/index.html",
    userInfoBuildError: "/error"
}

window.onload = function () {
    let buildUserInfoBtn = document.getElementById("buildUserInfoBtn");
    buildUserInfoBtn.addEventListener("click", function (event) {
        event.preventDefault();
        let fullName = document.getElementById("fullName").value;
        // let birthday = new Date(document.getElementById("birthday").value).toISOString();
        let birthday = document.getElementById("birthday").value;
        let gender = document.getElementById("gender").value;
        let sexualOrientation = document.getElementById("sexualOrientation").value;
        let location = document.getElementById("location").value;
        let self_intro = document.getElementById("self_intro").value;
        //Avatar Upload
        let avatarInput = document.getElementById("file");
        let avatar = avatarInput.files[0];
        //User Profile Pics Upload
        let profilePicInput = document.querySelectorAll("input[name='profile_pic']");
        let profilePics = [];
        let avatarImg = document.getElementById("avatarImg");

        let formData = new FormData();
        formData.append("fullName", fullName);
        formData.append("birthday", formatDate(birthday));
        formData.append("gender", gender);
        formData.append("sexualOrientation", sexualOrientation);
        formData.append("location", location);
        formData.append("self_intro", self_intro);
        formData.append("file", avatar);

        $.post({
            url: apiUrl.userInfoUrl,
            contentType: false,
            processData: false,
            data: formData,
            success: function (json) {
                if (json.state == 200) {
                    alert("用戶資料建立成功!");
                    avatarImg.src = json.data;
                    window.location.href = apiUrl.userInfoBuildSuccess;
                } else if (json.state == 4001) {
                    alert("暱稱重複，請重新輸入!")
                    window.location.href = apiUrl.userInfoBuildError;
                } else if (json.state == 5002) {
                    alert("伺服器異常，無法新增個人資料，請稍後再嘗試!")
                    window.location.href = apiUrl.userInfoBuildError;
                }
            },
            error: function (xhr) {
                alert("建立帳號時產生未知的異常: " + xhr.status);
            }
        });
    })
}

function formatDate(dateStr) {
    // 將日期字串轉換為指定格式（例如：yyyy-MM-dd）
    let dateObj = new Date(dateStr);
    let year = dateObj.getFullYear();
    let month = String(dateObj.getMonth() + 1).padStart(2, '0');
    let day = String(dateObj.getDate()).padStart(2, '0');
    let formattedDate = `${year}-${month}-${day}`;
    return formattedDate;
}



