### 说明 ###
请下载下来，使用Node++打开md文件，格式才会正确

### 版本号 ###
version=1.0-20210111-001

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
	2.1 第一步，创建配置类，设置使用哪个userDetailsService实现类
	2.2 第二步，编写实现类，返回User对象，User对象有用户名密码和操作权限

3. 整合MybatisPlus完成数据库操作

4. 实现自定义登录页
	4.1 自定义设置登录页
	4.2 不需要认证可以访问

	做法：
	在配置类实现相关的配置
 
5. 基于角色或权限进行访问控制
	5.1 第一个方法hasAuthority方法（如果当前的主体具有指定的权限，有返回true，否则返回false）
	在配置类配置当前访问地址有哪些权限
	在UserDetailsService,要返回User对象设置权限

	5.2 第二个方法hasAnyAuthority(多个)
	在配置类配置当前访问地址有哪些权限
	在UserDetailsService,要返回User对象设置权限

	5.3 第三个方法hasRole角色（单个）
	在配置类配置当前访问地址有哪些权限
	在UserDetailsService,要返回User对象设置权限（注意配置权限时，前面要加ROLE_）

	5.4 第四个方法hasAnyRole角色（单个）
	在配置类配置当前访问地址有哪些权限
	在UserDetailsService,要返回User对象设置权限（注意配置权限时，前面要加ROLE_）

6. 自定义403没有权限页面
	注意： http.exceptionHandling().accessDeniedPage("unauth.html");
	上面是个错误案例，没有加/，会报Caused by: java.lang.IllegalArgumentException: errorPage must begin with '/'异常


7. 认证授权注解使用
	7.1 @PreAuthorize（用户具有某个角色，可以访问方法）
	启动类开启注解、在controller上面使用注解设置角色、在UserDetailsService设置用户角色

8. 实现登录退出功能

9. 实现自动登录原理(cookie技术和security安全框架机制)
	9.1 第一步认证成功cookie保存加密串，数据库保存加密串和用户信息字符串
	9.2 第二步获取cookie信息与数据库进行比对，如果查询到对应信息，认证成功，可以登录
	9.3 第二步是如何进行比对的，；浏览器->UsernamePasswordAuthenticationFilter->RemeberMeService->将token写入数据库DB
	9.4 再次请求，RememberMeAuthenticationFilter->读取Cookie中的token——>TokenRepository->到数据库查找Token->UserDetailService
	
	
### 实现微服务下的安全认证 ###
1. 建立数据模型
菜单表，角色表，用户表，角色菜单关系表(多对多)，用户角色关系表（多对多)

