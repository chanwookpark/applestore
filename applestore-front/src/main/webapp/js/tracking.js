function push(eventType, paramId) {

    if ('CATEGORY' === eventType) {
        var idArray = paramId.split('_');

        // 내가 만든 API로 보내기
        $.ajax({
            method: "POST",
            url: "/c/category/" + /*categoryId*/ idArray[0] + "/" + /*productId*/ idArray[1]
        })
            .done(function (msg) {
                console('PUSH Event Success[CATEGORY, ' + idArray[0] + ', ' + idArray[1]);
            })
            .error(function (msg){
                console('PUSH Event Error[CATEGORY, ' + idArray[0] + ', ' + idArray[1]);
            })
        ;
    }
}