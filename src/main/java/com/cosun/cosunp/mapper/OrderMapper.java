package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.OrderHead;
import com.cosun.cosunp.entity.OrderItem;
import com.cosun.cosunp.entity.OrderItemAppend;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderMapper {


    @Select("SELECT\n" +
            "\te.empno,e.`name`\n" +
            "FROM\n" +
            "\temployee e\n" +
            "LEFT JOIN dept t ON t.id = e.deptId\n" +
            "WHERE\n" +
            "\tt.deptname IN (\n" +
            "\t\t'销售中心',\n" +
            "\t\t'项目中心'\n" +
            "\t)")
    List<Employee> findAllSalor();


    @Insert(" INSERT INTO orderhead (\n" +
            "\t\tsingleOrProject,\n" +
            "\t\torderNo,\n" +
            "\t\tproductName,\n" +
            "\t\torderTime,\n" +
            "\t\tdeliverTime,\n" +
            "\t\torderSetNum,\n" +
            "\t\tSalorNo\n" +
            "\t)\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t#{singleOrProject},\n" +
            "\t\t#{orderNo},\n" +
            "\t\t#{productName},\n" +
            "\t\t#{orderTimeStr},\n" +
            "\t\t#{deliverTimeStr},\n" +
            "\t\t#{orderSetNum},\n" +
            "\t\t#{SalorNo}\n" +
            "\t) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addOrderHeadByBean(OrderHead orderHead);

    @Insert(" INSERT INTO orderitem (\n" +
            "\torderHeadId,\n" +
            "\tproductBigType,\n" +
            "\tproductMainShape,\n" +
            "\tnewFinishProudNo,\n" +
            "\tproductSize,\n" +
            "\tedgeHightSize,\n" +
            "\tmainMateriAndArt,\n" +
            "\tbackInstallSelect,\n" +
            "\telectMateriNeeds,\n" +
            "\tinstallTransfBacking,\n" +
            "\totherRemark\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t#{orderHeadId},\n" +
            "\t\t#{productBigType},\n" +
            "\t\t#{productMainShape},\n" +
            "\t\t#{newFinishProudNo},\n" +
            "\t\t#{productSize},\n" +
            "\t\t#{edgeHightSize},\n" +
            "\t\t#{mainMateriAndArt},\n" +
            "\t\t#{backInstallSelect},\n" +
            "\t\t#{electMateriNeeds},\n" +
            "\t\t#{installTransfBacking},\n" +
            "\t\t#{otherRemark}\n" +
            "\t) ")
    @Options(useGeneratedKeys = true, keyProperty = "itemId", keyColumn = "id")
    void addOrderItemByBean(OrderHead orderHead);

    @Insert("INSERT INTO orderitemappend (\n" +
            "\titemId,\n" +
            "\torderNo,\n" +
            "\tfileName,\n" +
            "\turlName\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\t" +
            "#{itemId},\n" +
            "\t#{orderNo},\n" +
            "\t#{fileName},\n" +
            "\t#{urlName})")
    void saveOrderItemAppend(OrderItemAppend orderItemAppend);

    @Select("select orderNo from orderhead where SalorNo = #{empNo} and orderTime >= #{startTime} " +
            "AND orderTime <= #{endTime} order by id desc limit 1 ")
    String findNewestOrderNoBySalor(String empNo,String startTime,String endTime);
}
