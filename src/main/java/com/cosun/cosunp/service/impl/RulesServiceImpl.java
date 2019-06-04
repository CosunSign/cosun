package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.Rules;
import com.cosun.cosunp.mapper.RulesMapper;
import com.cosun.cosunp.service.IrulesServ;
import com.cosun.cosunp.tool.FileUtil;
import com.cosun.cosunp.tool.StringUtil;
import com.cosun.cosunp.tool.WordToHtml;
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

    public Rules getRulesByName(String name) throws Exception {
        return rulesMapper.getRulesByName(name);
    }


    public boolean saveRuleByRuleBean(MultipartFile file, Rules rules) throws Exception {
        System.out.println(file.getOriginalFilename());
        int index = file.getOriginalFilename().indexOf(".");
        String filenamecenter = file.getOriginalFilename().substring(0, index);
        //step1 存文件在文件服务器  取得地址
        String descDir = this.finalDirPath + rules.getDeptId() + "/" + filenamecenter + "/" + file.getOriginalFilename();
        String descFolder = this.finalDirPath + rules.getDeptId() + "/" + filenamecenter + "/";
        if (file.getOriginalFilename().endsWith(".docx") || file.getOriginalFilename().endsWith(".DOCX")) {
            FileUtil.uploadFileForRules(file, descFolder);
            WordToHtml.word2007ToHtml(descFolder, descDir, file, rules.getDeptId());
        } else if (file.getOriginalFilename().endsWith(".doc") || file.getOriginalFilename().endsWith(".DOC")) {
            FileUtil.uploadFileForRules(file, descFolder);
            WordToHtml.DocToHtml(descFolder, descDir, file, rules.getDeptId());
        } else {
            return false;
        }
        rules.setFileDir(descDir);
        rules.setFileName(file.getOriginalFilename());
        rulesMapper.saveRulesBean(rules);
        return true;
    }

    public int findAllRulesCount() throws Exception {
        return rulesMapper.findAllRulesCount();
    }

    public Rules getRulesById(Integer id) throws Exception {
        return rulesMapper.getRulesById(id);
    }

    public int getRulesByNameAndId(String filename, Integer deptId) throws Exception {
        return rulesMapper.getRulesByNameAndId(filename, deptId);
    }

    public List<Rules> findAllRulesByDeptId(Rules rules) throws Exception {
        return rulesMapper.findAllRulesByDeptId(rules);
    }

    public int findAllRulesByDeptIdCount(Rules rules) throws Exception {
        return rulesMapper.findAllRulesByDeptIdCount(rules);

    }

    public boolean updateRulesById(MultipartFile file, Rules rules) throws Exception {
        // E:/ftpserver/5/4月车间考勤记1录.xls
        //String[] centerPaths = rules.getFileDir().split("\\.");
        //String htmlName = centerPaths[0] + ".html";
        //FileUtil.delFile(rules.getFileDir());
        // FileUtil.delFile(htmlName);

        int index = rules.getFileDir().lastIndexOf("/");
        String centerPaths = rules.getFileDir().substring(0, index);
        FileUtil.delFolderNew(centerPaths);

        String origName = file.getOriginalFilename();
        String centerPath = StringUtil.subMyString(rules.getFileDir(), "/");
        String descDir = centerPath + origName;
        FileUtil.uploadFileForRules(file, centerPath);
        if (file.getOriginalFilename().endsWith(".docx") || file.getOriginalFilename().endsWith(".DOCX")) {
            WordToHtml.word2007ToHtml(centerPath, descDir, file, rules.getDeptId());
        } else if (file.getOriginalFilename().endsWith(".doc") || file.getOriginalFilename().endsWith(".DOC")) {
            WordToHtml.DocToHtml(centerPath, descDir, file, rules.getDeptId());
        } else {
            return false;
        }
        rules.setFileDir(centerPath + file.getOriginalFilename());
        rules.setFileName(file.getOriginalFilename());
        rulesMapper.updateRulesBean(rules);
        return true;
    }

    public void deleteRulesByBatch(List<Integer> ids) throws Exception {
        rulesMapper.deleteRulesByBatch(ids);
    }


    public void deleteRulesById(Integer id) throws Exception {
        Rules rules = rulesMapper.getRulesById(id);
        if (rules != null) {
            int index = rules.getFileDir().lastIndexOf("/");
            String centerPaths = rules.getFileDir().substring(0, index);
            //String htmlName = centerPaths[0]+".html";
            // FileUtil.delFolder(centerPaths[0]);
            FileUtil.delFolderNew(centerPaths);
            // FileUtil.delFile(rules.getFileDir());
            // FileUtil.delFile(htmlName);
            rulesMapper.deleteRulesById(id);
        }
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
