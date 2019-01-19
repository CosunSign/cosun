package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

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

    @Select("select fio.username,fio.ordernum as orderNo,fio.extinfo1 as salor,fu.orginname as fileName,fu.uptime as lastUpdateTime,fu.singlefileupdatenum as singleFileUpdateNum from filemanurl fu " +
            "left join filemanfileinfo fio on fu.fileInfoId = fio.id " +
            "where " +
            " fu.username= #{userName}  " +
            "and fio.extinfo1 = #{salor} " +
            "and fio.ordernum = #{orderNo} ")
    List<DownloadView> findFileUrlDatabyOrderNoandSalorandUserName(String userName, String salor, String orderNo);

    @Insert("insert into FilemanRight(uId,userName,fileName,createUser,createTime,fileInfoId,fileUrlId,opRight)" +
            " values (#{uId},#{userName},#{fileName},#{createUser},#{createTime},#{fileInfoId},#{fileUrlId},#{opRight})")
    void addFilemanRightDataByUpload(FilemanRight filemanRight);

    @Insert("insert into FilemanUrl(uid,fileInfoId,userName,orginname,opRight,logur1,uptime) " +
            "values (#{uId},#{fileInfoId},#{userName},#{orginName},#{opRight},#{logur1},#{upTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addfilemanUrlByUpload(FilemanUrl filemanUrl);

    @Insert("insert into FileManFileInfo(uid,username,filename,createuser,createtime,extinfo1,ordernum,projectname,totalFilesNum,filedescribtion,remark) " +
            "values (#{uId},#{userName},#{fileName},#{createUser},#{createTime},#{extInfo1},#{orderNum},#{projectName},#{totalFilesNum},#{filedescribtion},#{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addfileManFileDataByUpload(FileManFileInfo fileManFile);

    @Select("SELECT ffi.id, " +
            "                      IFNULL(fmu.username,fmu.updateuser) as lastUpdator, " +
            "                     IFNULL(fmu.uptime,fmu.updateTime) as lastUpdateTime, " +
            "                      fmu.orginname AS fileName, " +
            "                      ffi.extinfo1 AS salor, " +
            "                     ffi.ordernum AS orderNo, " +
            "                    ffi.projectname AS projectName, " +
            "                      fmu.singlefileupdatenum AS singleFileUpdateNum, " +
            "                    fmu.logur1 AS urlAddr, " +
            "                      ffi.filedescribtion as filedescribtion, " +
            "                      ffi.remark as remark " +
            "                     FROM  " +
            "                     filemanfileinfo ffi " +
            "                     LEFT JOIN filemanurl fmu ON ffi.id = fmu.fileInfoId " +
                               " ORDER BY " +
            "   ffi.createtime DESC limit #{currentPageTotalNum},#{PageSize} ")
    List<DownloadView> findAllFileUrlByCondition(Integer uid,int currentPageTotalNum,int PageSize);

    @Select("SELECT\n" +
            "\t fr.id,\n" +
            "\t fu.username AS creator,\n" +
            "\t fu.updateuser AS lastUpdator,\n" +
            "\t fr.filename AS fileName,\n" +
            "\t ffi.extinfo1 AS salor,\n" +
            "\t ffi.ordernum AS orderNo,\n" +
            "\t ffi.projectname AS projectName,\n" +
            "  fr.createtime as createTime,\n" +
            "\t fu.updateTime AS lastUpdateTime,\n" +
            "\t fu.singlefileupdatenum AS singleFileUpdateNum,\n" +
            "\t fu.modifyReason as modifyReason,\n" +
            "\t fr.opRight as opRight\n" +
            " FROM\n" +
            "\t filemanfileinfo ffi\n" +
            " LEFT JOIN filemanright fr ON ffi.id = fr.fileInfoId\n" +
            " left join filemanurl fu on fu.id = fr.fileurlid\n" +
            " ORDER BY\n" +
            "\t ffi.createtime DESC limit #{currentPageTotalNum},#{PageSize} ")
    List<DownloadView> findAllUploadFileByCondition(Integer uId, int currentPageTotalNum, int PageSize);

    @Select("select ffi.id,ffi.username as creator,ffi.updateuser as lastUpdator, ffi.filename as fileName,ffi.extinfo1 as salor," +
            "    ffi.ordernum as orderNo,ffi.projectname as projectName,ffi.createtime as lastUpdateTime" +
            ",ffi.updatecount as totalUpdateNum,fmu.opright as opRight,fmu.logur1 as urlAddr,ffi.createtime as createTime,fmu.opRight  " +
            "    from filemanfileinfo ffi" +
            "    LEFT JOIN filemanurl fmu on ffi.id = fmu.fileInfoId" +
            "    and ffi.uid = #{uId}  order by ffi.createtime desc ")
    List<DownloadView> findAllUploadFileByUserId(Integer uId);

    @Select("select count(ffi.id) as recordCount " +
            " FROM\n" +
            "\t filemanfileinfo ffi\n" +
            " LEFT JOIN filemanright fr ON ffi.id = fr.fileInfoId\n" +
            " left join filemanurl fu on fu.id = fr.fileurlid\n")
    int findAllUploadFileCountByUserId(Integer uId);

    @Select("select * from  filemanurl where orginname = #{orginName}")
    List<FilemanUrl> findIsExistFile(String orginName);

    @Select("select * from  filemanurl where orginname = #{orginName} and fileInfoId = #{fileInfoId} ")
    FilemanUrl findFileUrlByFileInFoDataAndFileName(String orginName, Integer fileInfoId);

    @Select("select * from  filemanurl where fileInfoId = #{id}")
    List<FilemanUrl> findFileUrlByFileInFoData(Integer id);


    @Select("select * from  filemanfileinfo where createuser= #{createUser} and ordernum= #{orderNum} and extinfo1= #{extInfo1} ")
    List<FileManFileInfo> isSameOrderNoandOtherMessage(@Param("createUser") String createUser, @Param("orderNum") String orderNum, @Param("extInfo1") String extInfo1);

    @Select("select * from userinfo ")
    List<UserInfo> findAllUserInfo();

    @Select("select * from filemanright where id = #{fileurlid}  and uid = #{uid} ")
    FilemanRight findFileRightByUserIdandFileUrlId( Integer uid,Integer fileurlid);

    @Select("SELECT\n" +
            "\tfr.uid,fr.id as fileRightId,fr.filename,fi.extinfo1,fi.ordernum\n" +
            "FROM\n" +
            "\tfilemanright fr\n" +
            "LEFT JOIN filemanfileinfo fi ON fr.fileInfoId = fi.id  where fr.filename = #{fileName} " +
            " and fi.extinfo1 = #{salor} and fi.ordernum = #{orderNum} and fr.uid = #{uId} order by fr.filename desc   ")
    DownloadView findFielRightFileByUidOrderNoSalorFileName(String fileName,String salor,String orderNum,Integer uId);

    @Delete("delete from filemanright where id = #{id} ")
    void deleteFileRightPrivileg(Integer id);

    @Select("select * from userinfo where uid = #{uId}")
    UserInfo getUserInfoByUid(Integer uId);

    @Select("select * from  filemanfileinfo where id= #{id} ")
    FileManFileInfo getFileManFileInfoByInfoId(Integer id);

    @Update("update filemanright set opright= #{privileflag},updateuser = #{userName},updatetime = #{date} where id = #{fileurlid}  and uid = #{uid} ")
    void updateFileRightPrivileg( Integer uid,Integer fileurlid, String privileflag, String userName, Date date);


    @Insert("INSERT INTO filemanright (uid,fileurlid,createuser,createtime,opright,fileInfoId,filename) VALUES (#{uid},#{fileurlid},#{userName},#{date},#{privileflag},#{fileInfoId},#{fileName}) ")
    void saveFileRightPrivileg(Integer uid, Integer fileurlid, String privileflag, String userName, Date date,Integer fileInfoId,String fileName);

    @Update("update filemanfileinfo set totalFilesNum = #{totalFilesNum} , updatecount = #{updateCount} , updatetime = #{updateTime}  where id= #{id} ")
    int updateFileManFileInfo(@Param("totalFilesNum") Integer totalFilesNum, @Param("updateCount") Integer updateCount, @Param("updateTime") Date updateTime, @Param("id") Integer id);

    @Update("update filemanurl set opRight= #{privileflag} where fileInfoId = #{filesId}  ")
    void saveOrUpdateFileUrlPrivilege(Integer filesId, String privileflag);

    @Update("update filemanurl set singleFileUpdateNum= #{singleFileUpdateNum},updateTime = #{updateTime},modifyReason= #{modifyReason},updateUser= #{updateUser} where id = #{id}")
    void updateFileUrlById(Date updateTime, Integer singleFileUpdateNum, String modifyReason, Integer id,String updateUser);

    @Update("update filemanfileinfo set  updatecount = #{updateCount} , updatetime = #{updateTime},updateUser = #{updateUser}  where id= #{id} ")
    void updateFileManFileInfo2(@Param("updateCount") Integer updateCount, @Param("updateTime") Date updateTime, @Param("updateUser") String updateUser, @Param("id") Integer id);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllByParaCondition")
    List<DownloadView> findAllUploadFileByParaCondition(DownloadView view);

    @Select("select * from filemanright where id = #{id}")
    FilemanRight findFileRightById(Integer id);

    @Select("SELECT\n" +
            "\tfm.orginname\n" +
            "FROM\n" +
            "\tfilemanurl fm\n" +
            "LEFT JOIN filemanfileinfo ffi ON fm.fileInfoId = ffi.id\n" +
            "WHERE\n" +
            "\tfm.uId = #{uId} \n" +
            "AND ffi.extinfo1 = #{salor} \n" +
            "AND ffi.ordernum = #{orderNo}  ")
    List<String> findAllFileUrlNameByCondition(Integer uId,String salor,String orderNo);

    @Select(" SELECT\n" +
            "\t fm.logur1 \n" +
            "FROM\n" +
            "\tfilemanurl fm\n" +
            "LEFT JOIN filemanfileinfo ffi ON fm.fileInfoId = ffi.id\n" +
            "WHERE\n" +
            "\tfm.uId = #{uId} \n" +
            "AND ffi.extinfo1 = #{salor} \n" +
            "AND ffi.ordernum = #{orderNo}  ")
    List<String> findAllFileUrlLogursByOrderNoandSalorUserName(Integer uId,String salor,String orderNo);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllCountByParaCondition")
    int findAllUploadFileCountByParaCondition(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllFilesByParam")
    List<DownloadView> findAllFilesByCondParam(DownloadView view);

    @SelectProvider(type = DownloadViewDaoProvider.class, method = "findAllFilesCountByParam")
    int findAllFilesByCondParamCount(DownloadView view);

    @Select("select fu.logur1 as addr from filemanurl fu left join \n" +
            "filemanfileinfo ffi on fu.fileInfoId = ffi.id\n" +
            "where ffi.extinfo1 = #{salor} \n" +
            "and ffi.uid = #{engineer} \n" +
            "and ffi.ordernum = #{orderno} ")
    List<String> findAllUrlByParamThree(String salor,Integer engineer,String orderno);

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

        public String findAllFilesByParam(DownloadView view) {

            StringBuilder sql = new StringBuilder(" SELECT\n");
            sql.append("\t fr.id,\n");
            sql.append("\t fu.username AS creator,\n");
            sql.append("\t fu.updateuser AS lastUpdator,\n");
            sql.append("\t fr.filename AS fileName,\n");
            sql.append("\t ffi.extinfo1 AS salor,\n");
            sql.append("\t ffi.ordernum AS orderNo,\n");
            sql.append("\t ffi.projectname AS projectName,\n");
            sql.append("  fr.createtime as createTime,\n");
            sql.append("\t fr.updatetime AS lastUpdateTime,\n");
            sql.append("\t fu.singlefileupdatenum AS singleFileUpdateNum,\n");
            sql.append("\t fu.modifyReason as modifyReason,\n");
            sql.append("\t fr.opRight as opRight\n");
            sql.append(" FROM\n");
            sql.append("\t filemanfileinfo ffi\n");
            sql.append(" LEFT JOIN filemanright fr ON ffi.id = fr.fileInfoId\n");
            sql.append(" left join filemanurl fu on fu.id = fr.fileurlid\n");
            sql.append("   WHERE  ");
            sql.append("    1 = 1  ");

            if (view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and ffi.extinfo1 like CONCAT('%',#{salor},'%')");
            }

            if (view.getOprighter() != null && view.getOprighter().length() > 0) {
                sql.append(" and fr.uid =  #{oprighter}    ");
            }

            if (view.getOrderNo() != null && view.getOrderNo().trim().length() > 0) {
                sql.append(" and ffi.ordernum like CONCAT('%',#{orderNo},'%')");
            }

            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and ffi.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and fu.orginname like  CONCAT('%',#{fileName},'%')");
            }

            if (view.getEngineer() != null && view.getEngineer().trim().length() > 0) {
                sql.append(" and fu.uid = #{engineer} ");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime  >= #{startNewestSaveDateStr} and fmu.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime <= #{endNewestSaveDateStr}");
            }

            sql.append("  order by fu.uptime desc limit #{currentPageTotalNum},#{pageSize}");
            return sql.toString();
        }

        public String findAllFilesCountByParam(DownloadView view) {

            StringBuilder sql = new StringBuilder(" SELECT count(ffi.id)  ");
            sql.append(" FROM\n");
            sql.append("\t filemanfileinfo ffi\n");
            sql.append(" LEFT JOIN filemanright fr ON ffi.id = fr.fileInfoId\n");
            sql.append(" left join filemanurl fu on fu.id = fr.fileurlid\n");
            sql.append("   WHERE  ");
            sql.append("    1 = 1  ");

            if (view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and ffi.extinfo1 like CONCAT('%',#{salor},'%')");
            }

            if (view.getOprighter() != null && view.getOprighter().length() > 0) {
                sql.append(" and fr.uid =  #{oprighter}    ");
            }

            if (view.getOrderNo() != null && view.getOrderNo().trim().length() > 0) {
                sql.append(" and ffi.ordernum like CONCAT('%',#{orderNo},'%')");
            }

            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and ffi.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and fu.orginname like  CONCAT('%',#{fileName},'%')");
            }

            if (view.getEngineer() != null && view.getEngineer().trim().length() > 0) {
                sql.append(" and fu.uid = #{engineer} ");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime  >= #{startNewestSaveDateStr} and fmu.uptime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and fu.uptime <= #{endNewestSaveDateStr}");
            }
            return sql.toString();
        }

        public String findAllCountByParaCondition(DownloadView view) {
            StringBuilder sql = new StringBuilder(" select count(ffi.id) ");
            sql.append("                from filemanfileinfo ffi ");
            sql.append("                LEFT JOIN filemanurl fmu on ffi.id = fmu.fileInfoId");
            sql.append("                where 1=1   ");

            if(view.getuId()!=null && view.getuId()!=0) {
                sql.append(" and fmu.uid = #{uId}");
            }

            if (view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and ffi.extinfo1 like CONCAT('%',#{salor},'%')");
            }

            if (view.getOrderNo() != null && view.getOrderNo().trim().length() > 0) {
                sql.append(" and ffi.ordernum like CONCAT('%',#{orderNo},'%')");
            }

            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and ffi.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and ffi.filename like  CONCAT('%',#{fileName},'%')");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and ffi.createtime  >= #{startNewestSaveDateStr} and ffi.createtime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and ffi.createtime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and ffi.createtime <= #{endNewestSaveDateStr}");
            }

            return sql.toString();

        }


        public String findAllByParaCondition(DownloadView view) {
            StringBuilder sql = new StringBuilder(" SELECT \tffi.id,\n" +
                    "  IFNULL(fmu.username,fmu.updateuser) as lastUpdator,\n" +
                    "  IFNULL(fmu.uptime,fmu.updateTime) as lastUpdateTime,\n" +
                    "\tfmu.orginname AS fileName,\n" +
                    "\tffi.extinfo1 AS salor,\n" +
                    "\tffi.ordernum AS orderNo,\n" +
                    "\tffi.projectname AS projectName,\n" +
                    "  fmu.singlefileupdatenum AS singleFileUpdateNum,\n" +
                    "\tfmu.logur1 AS urlAddr,\n" +
                    "  ffi.filedescribtion as filedescribtion,\n" +
                    "  ffi.remark as remark " +
                    " FROM "+
                    " filemanfileinfo ffi"+
                    " LEFT JOIN filemanurl fmu ON ffi.id = fmu.fileInfoId"+
                    " WHERE "+
                    " 1=1 ");
            if(view.getuId()!=null&&view.getuId()!=0){
                sql.append(" and fmu.uid =#{uId} ");
            }

            if (view.getSalor() != null && view.getSalor().trim().length() > 0) {
                sql.append(" and ffi.extinfo1 like CONCAT('%',#{salor},'%')");
            }

            if (view.getOrderNo() != null && view.getOrderNo().trim().length() > 0) {
                sql.append(" and ffi.ordernum like CONCAT('%',#{orderNo},'%')");
            }

            if (view.getProjectName() != null && view.getProjectName().trim().length() > 0) {
                sql.append(" and ffi.projectname like CONCAT('%',#{projectName},'%')");
            }

            if (view.getFileName() != null && view.getFileName().trim().length() > 0) {
                sql.append(" and ffi.filename like  CONCAT('%',#{fileName},'%')");
            }

            if (view.getStartNewestSaveDateStr() != null && view.getEndNewestSaveDateStr() != null) {
                sql.append(" and ffi.createtime  >= #{startNewestSaveDateStr} and ffi.createtime  <= #{endNewestSaveDateStr}");
            } else if (view.getStartNewestSaveDateStr() != null) {
                sql.append(" and ffi.createtime >= #{startNewestSaveDateStr}");
            } else if (view.getEndNewestSaveDateStr() != null) {
                sql.append(" and ffi.createtime <= #{endNewestSaveDateStr}");
            }

            sql.append("  order by ffi.createtime desc limit #{currentPageTotalNum},#{pageSize}");
            return sql.toString();

        }
    }


}
