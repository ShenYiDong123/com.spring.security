### 版本号 ###
version=1.0-20210105-001

### com.spring.security ###
SpringSecurity认证授权项目


### 理论 ###
1. SpringSecurity本质是一个过滤器链（有很多过滤器）

FilterSecurityInterceptor：方法级的权限过滤器，基本位于过滤链的最底部
ExceptionTranslationFilter：异常过滤器，用来处理在认证授权书中抛出的异常
UsernamePasswordAuthenticationFilter：对/login的POST请求做拦截，校验表单中用户名密码


2. 过滤器如何进行加载的？
使用SpringSecuritry配置过滤器
delegatingFilterProxy

3. 两个重要的接口
UserDetailsService接口：查询数据库用户名和密码过程
  --创建类继承UsernamePasswordAuthenticationFilter,重写三个方法。
  --创建类实现UserDetailService,编写查询数据过程，返回User对象，这个User对象是安全框架提供对象。
  
PasswordEncoder
  -- 数据加密接口，用于返回User对象里面密码加密

4. Web权限方案
4.1 设置登录的用户名和密码
第一种方式是：通过配置文件
第二种方式是：通过配置类
第三种方式是：自定义编写实现类（企业常用）


### 实践 ###
1. pom引入相关类
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>

2. 自定义编写实现类
第一步，创建配置类，设置使用哪个userDetailsService实现类
第二步，编写实现类，返回User对象，User对象有用户名密码和操作权限

3. 整合MybatisPlus完成数据库操作


