# rule
rule engine based on qlexpress

config/rule.xml是规则引擎的配置文件，若使用了spring，请把该文件import到spring的上下文配置文件中；

规则引擎采用了jboss drools的思路：规则 ＝ 规则执行条件 ＋ 规则执行内容 ＋ 规则分组、优先级；

规则的执行条件和内容建议以java脚本的形式存储在数据库中，数据库建表语句如下：
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

规则会被缓存在服务部署的服务器内存中，所以可以提供一个后门用于刷新规则缓存，规则模块提供了一个API:
RuleRunner.java
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
后续会考虑使用memcache做分布式的缓存。如果规则比较简单，服务器集群比较少，使用memcache有种杀机用牛刀的感觉；
表达式解析基于taocode的qlexpress
qlexpress请参考：
http://code.taobao.org/p/QLExpress/wiki/index/