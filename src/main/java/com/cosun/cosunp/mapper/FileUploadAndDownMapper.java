package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.DownloadView;
import com.cosun.cosunp.entity.FileManFileInfo;
import com.cosun.cosunp.entity.FilemanRight;
import com.cosun.cosunp.entity.FilemanUrl;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/20 0020 下午 6:40
 * @Modified By:
 * @Modified-date:2018/12/20 0020 下午 6:40
 */
@Repository
public interface FileUploadAndDownMapper {

    @Insert("insert into FilemanRight(uId,userName,fileName,createUser,createTime,fileInfoId)" +
            " values(#{uId},#{userName},#{fileName},#{createUser},#{createTime},#{fileInfoId})")
    void addFilemanRightDataByUpload(FilemanRight filemanRight);

    @Insert("insert into FilemanUrl(fileInfoId,userName,fileName,upTime,logur1) " +
            "values(#{fileInfoId},#{userName},#{fileName},#{upTime},#{logur1})")
    void addfilemanUrlByUpload(FilemanUrl filemanUrl);

    @Insert("insert into FileManFileInfo(uid,username,filename,createuser,createtime,extinfo1,ordernum,projectname) " +
            "values(#{uId},#{userName},#{fileName},#{createUser},#{createTime},#{extInfo1},#{orderNum},#{projectName})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void addfileManFileDataByUpload(FileManFileInfo fileManFile);


    @Select("select ffi.id,ffi.username as creator,ffi.updateuser as lastUpdator, ffi.filename as fileName,ffi.extinfo1 as salor," +
            "    ffi.ordernum as orderNo,ffi.projectname as projectName,ffi.updatetime as lastUpdateTime" +
            ",ffi.updatecount as totalUpdateNum,fmu.opright as opRight,fmu.logur1 as urlAddr,ffi.createtime as createTime,fmu.opRight  " +
            "    from filemanfileinfo ffi" +
            "    LEFT JOIN filemanurl fmu on ffi.id = fmu.fileInfoId" +
            "    and ffi.uid = #{uId} order by ffi.createtime desc ")
    List<DownloadView> findAllUploadFileByUserId(Integer uId);



    @Update("update filemanright set opright= #{privileflag} where fileInfoId = #{filesId}  and uid = #{selectuser} ")
    void saveOrUpdateFilePrivilege(Integer selectuser,Integer filesId,String privileflag);


    @Update("update filemanurl set opRight= #{privileflag} where fileInfoId = #{filesId}  ")
    void saveOrUpdateFileUrlPrivilege(Integer filesId,String privileflag);
}
