package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.service.IrulesServ;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
