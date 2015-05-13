package com.ql.util.express;
/**
 * 
 * <Description>规则上下文<br> 
 *  
 * @author wanglei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年1月24日 <br>
 */
public class RuleContext<K,V> extends DefaultContext<K, V> {
    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = 20150124L;
    private Integer groupType;
    private String groupName;
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
    
}
