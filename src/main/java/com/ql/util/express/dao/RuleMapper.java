package com.ql.util.express.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.ql.util.express.Rule;
/**
 * 
 * <Description>从数据库中获取规则mapper<br> 
 *  
 * @author wanglei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年1月26日 <br>
 */
public interface RuleMapper {
    @Select({"select id,rule_name ruleName,priority,group_type groupType,group_name groupName,match_condition matchCondition,execute_content executeContent from rule where del_flag=0"})
    public List<Rule> getRuleList();
}
