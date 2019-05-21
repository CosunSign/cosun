package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.Rules;
import com.cosun.cosunp.mapper.RulesMapper;
import com.cosun.cosunp.service.IrulesServ;
import com.cosun.cosunp.tool.FileUtil;
import com.cosun.cosunp.tool.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/20 0020 下午 1:42
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RulesServiceImpl implements IrulesServ {

    private static Logger logger = LogManager.getLogger(RulesServiceImpl.class);

    @Autowired
    RulesMapper rulesMapper;

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

    public int getRulesByDeptId(Integer deptId) throws Exception {
        return rulesMapper.getRulesByDeptId(deptId);
    }

    public void saveRuleByRuleBean(MultipartFile file, Rules rules) throws Exception {
        //step1 存文件在文件服务器  取得地址
        String descDir = this.finalDirPath + rules.getDeptId() + "/" + file.getOriginalFilename();
        String descFolder = this.finalDirPath + rules.getDeptId() + "/";
        FileUtil.uploadFileForRules(file, descFolder);
        rules.setFileDir(descDir);
        rules.setFileName(file.getOriginalFilename());
        rulesMapper.saveRulesBean(rules);

    }

    public int findAllRulesCount() throws Exception {
        return rulesMapper.findAllRulesCount();
    }

    public Rules getRulesById(Integer id) throws Exception {
        return rulesMapper.getRulesById(id);
    }

    public void updateRulesById(MultipartFile file, Rules rules) throws Exception {
        // E:/ftpserver/5/4月车间考勤记1录.xls
        String descDir = rules.getFileDir();
        String centerPath = StringUtil.subMyString(descDir, "/");
        FileUtil.delFile(descDir);
        FileUtil.uploadFileForRules(file,centerPath);
        rules.setFileDir(centerPath+file.getOriginalFilename());
        rules.setFileName(file.getOriginalFilename());
        rulesMapper.updateRulesBean(rules);
    }

    public void deleteRulesById(Integer id) throws Exception {
        rulesMapper.deleteRulesById(id);
    }


    public List<Rules> findAllRules(Rules rules) throws Exception {
        return rulesMapper.findAllRules(rules);
    }

    public List<Rules> queryRulesByCondition(Rules rules) throws Exception {
        return rulesMapper.queryRulesByCondition(rules);
    }

    public int queryRulesByConditionCount(Rules rules) throws Exception {
        return rulesMapper.queryRulesByConditionCount(rules);
    }

    public List<Rules> findAllRulesAll() throws Exception {
        return rulesMapper.findAllRulesAll();
    }



}
