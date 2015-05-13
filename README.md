# rule
rule engine based on qlexpress

config/rule.xmlspringimportspring

java

create table `rule` (
`id` int(10) unsigned not null auto_increment comment '',
`rule_name` varchar(50) not null default '' comment '',
`priority` int(4) not null default 0 comment '',
`group_type` tinyint not null default 0 comment ' 1',
`group_name` varchar(50) not null default '' comment '',
`match_condition` varchar(100) not null default 'true' comment  '',
`execute_content` varchar(1000) not null default '' comment '',
`del_flag` tinyint(4) not null default 0 comment ' 0  1',
`op_time` timestamp not null default current_timestamp on update current_timestamp comment '',
primary key (`id`),
index (`group_type`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='wanglei@2014-11-25 ';
jboss drools    


webcontrollerAPI:
RuleRunner.java
    /**
     * 
     * Description:<br> 
     *  
     * @author wanglei<br>
     * @taskId <br> <br>
     */
    public synchronized void refreshRules() {
        ruleList = ruleMapper.getRuleList();
        rulesCache = new HashMap<Integer,List<Rule>>();
    }
memcache

taocodeqlexpress,
qlexpress
http://code.taobao.org/p/QLExpress/wiki/index/