package com.ql.util.express;

/**
 * <Description>自定义规则内容<br>
 * 
 * @author wanglei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年1月26日 <br>
 */
public class Rule {
    private Integer id;

    private String ruleName;

    // 优先级，越大执行优先级越高
    private Integer priority;

    private Integer groupType;

    private String groupName;

    // 规则匹配判断条件-表达式
    private String matchCondition;

    // 规则匹配执行内容-表达式
    private String executeContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMatchCondition() {
        return matchCondition;
    }

    public void setMatchCondition(String matchCondition) {
        this.matchCondition = matchCondition;
    }

    public String getExecuteContent() {
        return executeContent;
    }

    public void setExecuteContent(String executeContent) {
        this.executeContent = executeContent;
    }


}
