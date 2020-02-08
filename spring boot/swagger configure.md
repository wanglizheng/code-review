````
    @ApiOperation(value = "根据类别和检索词分页查询")
    @ApiResponses({
            @ApiResponse(code = 200, message = "")
    })
    @GetMapping("/article/list")
    public RestResult<Slice<Article>> findAll(
            @ApiParam(name = "type", value = "类别 (高血压，糖尿病，婴幼儿，孕妈妈)空值代表全部")
            @RequestParam(required = false) String type,
            @ApiParam(name = "sword", value = "检索词（空值代表全部）")
            @RequestParam(name = "sword", required = false) String searchWord,
            @ApiParam(name = "page", value = "当前查询页码", defaultValue = "0",example ="0")
            @RequestParam(name = "page", defaultValue = "0",required = false) int pageNum,
            @ApiParam(name = "size", value = "每页查询数量", defaultValue = "10",example ="10")
            @RequestParam(name = "size", defaultValue = "10",required = false) int pageSize) {
        RestResult<Slice<Article>> response;
        if(pageSize>1 && pageSize<=20){
            response = RestResult.ok(articleService.findByType(type, pageNum, pageSize));
        }
        else{
            response = RestResult.failure(-1,"参数不合法");
        }
        return response;
    }
````