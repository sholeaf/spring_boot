const replyService = {
	insert:function(reply,callback,err){
		$.ajax({
			type:"POST",
			url:"/reply/regist",
			data:JSON.stringify(reply),
			contentType:"application/json;charset=utf-8",
			success:function(){
				callback();
			},
			error:function(){
				err();
			}
		})
	},
	selectAll:function(data, callback){
		const {pagenum,boardnum} = data;
		//121번 게시글의 2페이지 댓글 리스트 요청 : /reply/121/2
		$.getJSON(
			`/reply/${boardnum}/${pagenum}`,
			function(data){
				//data : 응답되는 JSON 형태의 객체({replyCnt:댓글개수, list:[...]})
				callback(data.replyCnt, data.list);
			}
		)
	},
	delete:function(replynum,callback){
		$.ajax({
			url:`/reply/${replynum}`,
			type:"DELETE",
			success:function(result){
				callback(result);
			}
		})
	},
	update:function(reply, callback){
		$.ajax({
			url:`/reply/${reply.replynum}`,
			type:"PUT",
			data:JSON.stringify(reply),
			contentType:"application/json;charset=utf-8",
			success:function(result){
				callback(result);
			}
		})
	}
}










