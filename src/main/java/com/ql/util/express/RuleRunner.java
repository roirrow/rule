package com.ql.util.express;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ql.util.express.dao.RuleMapper;

/**
 * 
 * <Description>规则调度-执行<br> 
 *  
 * @author wanglei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年1月26日 <br>
 */
public class RuleRunner{
    //表达式执行器
    private ExpressRunner expressRunner = new ExpressRunner();
    //全部规则
    private List<Rule> ruleList;
    //各种组别的规则的缓存
    private Map<Integer,List<Rule>> rulesCache = new HashMap<Integer,List<Rule>>();
    @Resource
    private RuleMapper ruleMapper;
    /**
     * 
     * Description:规则调度<br> 
     *  
     * @author wanglei<br>
     * @taskId <br>
     * @param ruleList
     * @param ruleContext
     * @return
     * @throws Exception <br>
     */
    public boolean dispatch(RuleContext<String,Object> ruleContext) throws Exception {
        // 延迟初始化
        if(null == ruleList) {
            synchronized(this) {
                if(null == ruleList) {
                    ruleList = ruleMapper.getRuleList();
                }
            }
        }
        //如果规则为空，则返回true
        if(null == ruleList || 0 ==ruleList.size()) {
            return true;
        }
        //如果规则不为空，且规则上下文为null,返回false
        if(null == ruleContext) {
            return false;
        }
        //获取并缓存基于组别的适应的规则
        List<Rule> rules = this.obtainRulesWithCache(ruleContext.getGroupType());
        //匹配并执行匹配的规则
        this.executeRules(rules, ruleContext);
        return true;
    }
    
    /**
     * 
     * Description:筛选并对规则按照优先级排序<br> 
     *  
     * @author wanglei<br>
     * @taskId <br>
     * @param ruleList
     * @param groupType
     * @return <br>
     */
    private List<Rule> obtainRulesWithCache(Integer groupType) {
        List<Rule> rules = rulesCache.get(groupType);
        if(null == rules) {
            rules = new ArrayList<Rule>();
            //筛选规则
            for(Rule rule : ruleList) {
                if(groupType == rule.getGroupType()) {
                    rules.add(rule);
                }
            }
            //按照优先级排序
            Collections.sort(rules, new Comparator(){
                @Override
                public int compare(Object o1, Object o2) {
                    Rule rule1 = (Rule)o1;
                    Rule rule2 = (Rule)o2;
                    return rule1.getGroupType().compareTo(rule2.getGroupType());
                }
            });
            rulesCache.put(groupType, rules);
        }
        return rules;
    }
    
    /**
     * 
     * Description:规则匹配并执行<br> 
     *  
     * @author wanglei<br>
     * @taskId <br>
     * @param rules
     * @param ruleContext
     * @throws Exception <br>
     */
    private void executeRules(List<Rule> rules,RuleContext<String,Object> ruleContext) throws Exception {
        for(Rule rule : rules) {
            //如果规则匹配，则执行
            Boolean result = (Boolean)expressRunner.execute(rule.getMatchCondition(), ruleContext, null, true, false);
            if(result) {
                expressRunner.execute(rule.getExecuteContent(), ruleContext, null, true, false);
            }
        }
    }

    /**
     * 
     * Description:刷新规则内容<br> 
     *  
     * @author wanglei<br>
     * @taskId <br> <br>
     */
    public synchronized void refreshRules() {
        ruleList = ruleMapper.getRuleList();
        rulesCache = new HashMap<Integer,List<Rule>>();
    }
    
    public List<Rule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }

    public ExpressRunner getExpressRunner() {
        return expressRunner;
    }

    public void setExpressRunner(ExpressRunner expressRunner) {
        this.expressRunner = expressRunner;
    }

    public RuleMapper getRuleMapper() {
        return ruleMapper;
    }

    public void setRuleMapper(RuleMapper ruleMapper) {
        this.ruleMapper = ruleMapper;
    }
    
}
