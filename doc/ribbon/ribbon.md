
### ribbon 组件


|接口 | 作用 | 默认值 |
|  ----  | ----  |   ----  | 
| IClientConfig | 读取配置 | DefaultclientConfigImpl |
| 工Ru1e | 负载均衡规则,选择实例 | ZoneAvoidancerule |
|工Ping |筛选掉ping不通的实例 |Dummying |
|Serverlist<server> |交给 Ribbon的实例列表|Ribbon: ConfiqurationBasedServerList Spring cloud Alibaba: NacosServerList |
| ServerlistFilter< Server> | 过滤掉不符合条件的实例 |ZonepreferenceserverlistFilter |
| ILoadBalancer | Ribbon的入口 | ZoneAwareloadBalancer |
| ServerListUpdater | 更新交给Ribbon的List的策略 | PollingserverlistUpdater |



### IClientConfig 读取配置



### IRule 负载均衡算法


### IPing 筛选ping不通的实例


### Serverlist<server>  Ribbon的实例列表


### ServerlistFilter< Server>  过滤掉不符合条件的实例


### ILoadBalancer Ribbon的入口


### ServerListUpdater 更新提交



