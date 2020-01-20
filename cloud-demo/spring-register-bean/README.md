### spring 代码片段

### 自定义bean的加载,方式

BeanFactoryPostProcessor 
    bean工厂的bean属性处理容器，说通俗一些就是可以管理我们的bean工厂内所有的beandefinition（未实例化）数据，可以随心所欲的修改属性。

BeanPostProcessor
    从范围上来说，从上面的所有的bean成为了特定的bean，其次BeanFactoryPostProcessor可以在初始化前修改bean的属性等情况，但是BeanPostProcessor只能在初始化后（注意初始化不包括init方法）执行一些操作。
    网上很多文章都说BeanPostProcessor不能修改bean属性，其实未必，当其实例化之后，完全可以拿到实例化后的对象，对对象进行一些改值操作也完全可以的
    
...
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        this.defaultListableBeanFactory = (DefaultListableBeanFactory)configurableListableBeanFactory;

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition("com.xian.register.bean.DynamicCreateBean");

        //用于设置指定的类中需要引入的其他bean
        //beanDefinitionBuilder.addPropertyValue("otherBeanName","otherBeanName");
        this.defaultListableBeanFactory.registerBeanDefinition("dynamicCreateBean",beanDefinitionBuilder.getBeanDefinition());

        beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DeptDTO.class.getName());
        //用于设置指定的类中需要引入的其他bean
        //beanDefinitionBuilder.addPropertyValue("otherBeanName","otherBeanName");
        this.defaultListableBeanFactory.registerBeanDefinition("deptDTO",beanDefinitionBuilder.getBeanDefinition());
        rabbitReceiverConfig();
    }
...

#### 参考资料

    //设置Bean的构造函数传入的参数值
    public BeanDefinitionBuilder addConstructorArgValue(Object value)
    //设置构造函数引用其他的bean
    public BeanDefinitionBuilder addConstructorArgReference(String beanName)
    //设置这个bean的 init方法和destory方法
    public BeanDefinitionBuilder setInitMethodName(String methodName) 
    public BeanDefinitionBuilder setDestroyMethodName(String methodName) 
    //设置单例/多例
    public BeanDefinitionBuilder setScope(String scope) 
    //设置是否是个抽象的BeanDefinition，如果为true，表明这个BeanDefinition只是用来给子BeanDefinition去继承的，Spring不会去尝试初始化这个Bean。
    public BeanDefinitionBuilder setAbstract(boolean flag) 
    //是否懒加载，默认是false
    public BeanDefinitionBuilder setLazyInit(boolean lazy) 
    //自动注入依赖的模式，默认不注入
    public BeanDefinitionBuilder setAutowireMode(int autowireMode) 
    //检测依赖。
    public BeanDefinitionBuilder setDependencyCheck(int dependencyCheck) 


### RabbitMQ

rabbMq 声明队列、交换机、绑定操作。通过一个枚举类给实现了。
	spring 加载beanDefinition 时候先加载到里面去。然后通过spring 的bean工厂自动生成 需要的队列、交换机 bean 对象。
	在程序加载完毕利用CommandLineRunner 进行绑定操作。

扩展
    通过CommandLineRunner绑定操作，可以实现程序完全启动后延迟消费队列。

