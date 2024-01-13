window.onload = function() {
    // 發送 GET 請求取得所有商品
    fetch('/items',{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => response.json())
        .then(res => {
            const itemList = document.getElementById('item-list');
            res.data.forEach(item => {
                let id = item.id;
                let name = item.itemName;
                let price = item.price;
                console.log(id, name, price);
                itemList.innerHTML += `<li id:${id}>${name}<br>${price}</li>`;
            });
        })
        .catch(error => console.error('錯誤:', error));
}