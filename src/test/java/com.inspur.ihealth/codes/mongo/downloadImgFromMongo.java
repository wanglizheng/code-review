package com.inspur.ihealth.codes.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Slf4j
public class downloadImgFromMongo {


    @Autowired
    private MongoDbFactory mongoDbFactory;

    public void downloadImg(HttpServletResponse response, String imgId) {
        GridFS gridFS = new GridFS(mongoDbFactory.getLegacyDb());
        GridFSDBFile gridFSDBFile = gridFS.findOne(new BasicDBObject("_id", new ObjectId(imgId)));
        try {
            //获取回复的输出流
            OutputStream sos = response.getOutputStream();
            //设置编码格式
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            //设置文件返回类型
            response.setContentType("image/x-png");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + gridFSDBFile.getFilename() + "\".png");
            //将查询到的数据放入到输出流sos中
            gridFSDBFile.writeTo(sos);
            sos.flush();
            sos.close();
        } catch (Exception e) {
            log.error("下载png失败");
        }
    }


}
