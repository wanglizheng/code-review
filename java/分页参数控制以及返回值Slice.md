``````

public RestResult<Slice<ArticleEntity>> findAll(
            @RequestParam(name="page",defaultValue = "0") int pageNum,
            @RequestParam(name="size",defaultValue = "10") int pageSize) {
        RestResult<Slice<ArticleEntity>> response;
        if(pageSize>1 && pageSize<=20){
            response = RestResult.ok(articleService.findAll(pageNum, pageSize));
        }
        else{
            response = RestResult.failure(-1,"参数不合法");
        }
        return response;
    }

``````