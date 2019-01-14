package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.DownloadView;
import com.cosun.cosunp.entity.FileManFileInfo;
import com.cosun.cosunp.entity.FilemanRight;
import com.cosun.cosunp.entity.FilemanUrl;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    @Insert("insert into FilemanRight(uId,userName,fileName,createUser,createTime,fileInfoId,fileUrlId,opRight)" +
            " values(#{uId},#{userName},#{fileName},#{createUser},#{createTime},#{fileInfoId},#{fileUrlId},#{opRight})")
    void addFilemanRightDataByUpload(FilemanRight filemanRight);

    @Insert("insert into FilemanUrl(fileInfoId,userName,orginname,opRight,logur1,uptime) " +
            "values(#{fileInfoId},#{userName},#{orginName},#{opRight},#{logur1},#{upTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void  addfilemanUrlByUpload(FilemanUrl filemanUrl);

    @Insert("insert into FileManFileInfo(uid,username,filename,createuser,createtime,extinfo1,ordernum,projectname,totalFilesNum,filedescribtion,remark) " +
            "values(#{uId},#{userName},#{fileName},#{createUser},#{createTime},#{extInfo1},#{orderNum},#{projectName},#{totalFilesNum},#{filedescribtion},#{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addfileManFileDataByUpload(FileManFileInfo fileManFile);


    @Select("select ffi.id,ffi.username as creator,ffi.updateuser as lastUpdator, ffi.filename as fileName,ffi.extinfo1 as salor," +
            "    ffi.ordernum as orderNo,ffi.projectname as projectName,ffi.createtime as lastUpdateTime" +
            ",ffi.updatecount as totalUpdateNum,fmu.opright as opRight,fmu.logur1 as urlAddr,ffi.createtime as createTime,fmu.opRight  " +
            "    from filemanfileinfo ffi" +
            "    LEFT JOIN filemanurl fmu on ffi.id = fmu.fileInfoId" +
            "    and ffi.uid = #{uId}   order by ffi.createtime desc limit #{currentPageTotalNum},#{PageSize} ")
    List<DownloadView> findAllUploadFileByCondition(Integer uId, int currentPageTotalNum, int PageSize);

    @Select("select ffi.id,ffi.username as creator,ffi.updateuser as lastUpdator, ffi.filename as fileName,ffi.extinfo1 as salor," +
            "    ffi.ordernum as orderNo,ffi.projectname as projectName,ffi.createtime as lastUpdateTime" +
            ",ffi.updatecount as totalUpdateNum,fmu.opright as opRight,fmu.logur1 as urlAddr,ffi.createtime as createTime,fmu.opRight  " +
            "    from filemanfileinfo ffi" +
            "    LEFT JOIN filemanurl fmu on ffi.id = fmu.fileInfoId" +
            "    and ffi.uid = #{uId}  order by ffi.createtime desc ")
    List<DownloadView> findAllUploadFileByUserId(Integer uId);

    @Select("select count(ffi.id) as recordCount " +
            "                from filemanfileinfo ffi " +
            "               LEFT JOIN filemanurl fmu on ffi.id = fmu.fileInfoId " +
            "                and ffi.uid = #{uId}  ")
    int findAllUploadFileCountByUserId(Integer uId);

    @Select("select * from  filemanurl where orginname = #{orginName}")
    List<FilemanUrl> findIsExistFile(String orginName);

    @Select("select * from  filemanurl where orginname = #{orginName} and fileInfoId = #{fileInfoId} ")
    FilemanUrl findFileUrlByFileInFoDataAndFileName(String orginName,Integer fileInfoId);

    @Select("select * from  filemanurl where fileInfoId = #{id}")
    List<FilemanUrl> findFileUrlByFileInFoData(Integer id);


    @Select("select * from  filemanfileinfo where createuser= #{createUser} and ordernum= #{orderNum} and extinfo1= #{extInfo1} ")
    List<FileManFileInfo> isSameOrderNoandOtherMessage(@Param("createUser") String createUser,@Param("orderNum") String orderNum,@Param("extInfo1") String extInfo1);


    @Update("update filemanright set opright= #{privileflag} where fileInfoId = #{filesId}  and uid = #{selectuser} ")
    void saveOrUpdateFilePrivilege(Integer selectuser, Integer filesId, String privileflag);

    @Update("update filemanfileinfo set totalFilesNum = #{totalFilesNum} , updatecount = #{updateCount} , updatetime = #{updateTime}  where id= #{id} ")
    int updateFileManFileInfo(@Param("totalFilesNum") Integer totalFilesNum,@Param("updateCount") Integer updateCount,@Param("updateTime") Date updateTime,@Param("id") Integer id);

    @Update("update filemanurl set opRight= #{privileflag} where fileInfoId = #{filesId}  ")
    void saveOrUpdateFileUrlPrivilege(Integer filesId, String privileflag);

    @Update("update filemanurl set singleFileUpdateNum= #{singleFileUpdateNum},uptime = #{upTime},modifyReason= #{modifyReason} where id = #{id}")
    void updateFileUrlById(Date upTime, Integer singleFileUpdateNum, String modifyReason,Integer id);

    @Update("update filemanfileinfo set  updatecount = #{updateCount} , updatetime = #{updateTime},updateUser = #{updateUser}  where id= #{id} ")
    void updateFileManFileInfo2(@Param("updateCount") Integer updateCount,@Param("updateTime") Date updateTime,@Param("updateUser") String updateUser,@Param("id") Integer id);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllByParaCondition")
    List<DownloadView> findAllUploadFileByParaCondition(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllCountByParaCondition")
    int findAllUploadFileCountByParaCondition(DownloadView view);

    /**
     * 功能描述: 多条件查询 建内部类拼SQL
     *
     * @auther: homey Wong
     * @date: 2019/1/2 0002 下午 6:13
     * @param:
     * @return:
     * @describtion
     */
    class DownloadViewDaoProvider {

        public String findAllCountByParaCondition(DownloadView view) {
            StringBuilder sql=new StringBuilder(" select count(ffi.id) " );
            sql.append("                from filemanfileinfo ffi " );
            sql.append("                LEFT JOIN filemanurl fmu on ffi.id = fmu.fileInfoId" );
            sql.append("                where ffi.uid = #{uId}   " );

            if(view.getSalor()!=null && view.getSalor().trim().length()>0){
                sql.append(" and ffi.extinfo1 like CONCAT('%',#{salor},'%')");
            }

            if(view.getOrderNo()!=null && view.getOrderNo().trim().length()>0) {
                sql.append(" and ffi.ordernum like CONCAT('%',#{orderNo},'%')");
            }

            if(view.getProjectName()!=null && view.getProjectName().trim().length()>0) {
                sql.append( " and ffi.projectname like CONCAT('%',#{projectName},'%')");
            }

            if(view.getFileName()!=null && view.getFileName().trim().length()>0) {
                sql.append(" and ffi.filename like  CONCAT('%',#{fileName},'%')");
            }

            if(view.getStartNewestSaveDateStr()!=null && view.getEndNewestSaveDateStr()!=null){
                sql.append(" and ffi.createtime  >= #{startNewestSaveDateStr} and ffi.createtime  <= #{endNewestSaveDateStr}");
            }else if(view.getStartNewestSaveDateStr()!=null){
                sql.append(" and ffi.createtime >= #{startNewestSaveDateStr}");
            } else if(view.getEndNewestSaveDateStr()!=null) {
                sql.append( " and ffi.createtime <= #{endNewestSaveDateStr}");
            }

            return sql.toString();

        }


        public String findAllByParaCondition(DownloadView view) {
            StringBuilder sql=new StringBuilder(" select ffi.id,ffi.username as creator,ffi.updateuser as lastUpdator, ffi.filename as fileName,ffi.extinfo1 as salor,");
                                sql.append("  ffi.ordernum as orderNo,ffi.projectname as projectName,ffi.createtime as lastUpdateTime");
                    sql.append("            ,ffi.updatecount as totalUpdateNum,fmu.opright as opRight,fmu.logur1 as urlAddr,ffi.createtime as createTime,fmu.opRight  " );
                    sql.append("                from filemanfileinfo ffi " );
                    sql.append("                LEFT JOIN filemanurl fmu on ffi.id = fmu.fileInfoId" );
                    sql.append("                where ffi.uid = #{uId}   " );

                    if(view.getSalor()!=null && view.getSalor().trim().length()>0){
                        sql.append(" and ffi.extinfo1 like CONCAT('%',#{salor},'%')");
                    }

                    if(view.getOrderNo()!=null && view.getOrderNo().trim().length()>0) {
                        sql.append(" and ffi.ordernum like CONCAT('%',#{orderNo},'%')");
                    }

                    if(view.getProjectName()!=null && view.getProjectName().trim().length()>0) {
                        sql.append( " and ffi.projectname like CONCAT('%',#{projectName},'%')");
                    }

                    if(view.getFileName()!=null && view.getFileName().trim().length()>0) {
                        sql.append(" and ffi.filename like  CONCAT('%',#{fileName},'%')");
                    }

                    if(view.getStartNewestSaveDateStr()!=null && view.getEndNewestSaveDateStr()!=null){
                        sql.append(" and ffi.createtime  >= #{startNewestSaveDateStr} and ffi.createtime  <= #{endNewestSaveDateStr}");
                    }else if(view.getStartNewestSaveDateStr()!=null){
                        sql.append(" and ffi.createtime >= #{startNewestSaveDateStr}");
                    } else if(view.getEndNewestSaveDateStr()!=null) {
                        sql.append( " and ffi.createtime <= #{endNewestSaveDateStr}");
                    }

                    sql.append( "  order by ffi.createtime desc limit #{currentPageTotalNum},#{pageSize}");
            return sql.toString();

        }
    }
}
