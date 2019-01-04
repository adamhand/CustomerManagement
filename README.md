#CustomerManagement

一个简单的用户管理系统。主要功能：

- 添加客户:添加客户的信息
- 查询客户:查询数据库中所有的客户，查询结果将客户信息以列表的形式给出，可以对列表中的客户信息进行编辑及删除
- 高级搜索:多条件组合在数据库中进行客户信息的查询，搜索结果以列表的形式给出，可以对列表中的客户信息进行编辑及删除
- 分页

运用知识：

- 数据库基本查询
- 数据库连接池c3p0
- 运用dbutil.jar包一键封装表单数据到bean对象中
- 利用dbutils.jar包简化对数据库增、删、改、查的代码
- JavaWeb三层框架的分离
- 将servlet的转发、重定向及方法进行封装，这样当设计到多个功能时不用建立多个servlet类。
- 反射

# 项目总结

# c3p0连接池配合QueryRunner的使用

##简介
### c3p0
实际开发中“获得连接”或“释放资源”是非常消耗系统资源的两个过程，为了解决此类性能问题，通常情况我们采用连接池技术，来共享连接Connection。这样我们就不需要每次都创建连接、释放连接了，这些操作都交给了连接池。c3p0就是这样一个连接池。

有了池，所以我们就不用自己来创建Connection，而是通过池来获取Connection对象。当使用完Connection后，调用Connection的close()方法也不会真的关闭Connection，而是把Connection“归还”给池。池就可以再利用这个Connection对象了。Java为数据库连接池提供了公共的接口：javax.sql.DataSource，各个厂商需要让自己的连接池实现这个接口。

目前使用它的开源项目有：Spring、Hibernate等。

###QueryRunner
QueryRunner是dbutils提供的类。Commons DbUtils是Apache组织提供的一个对JDBC进行简单封装的开源工具类库，提供数据库操作的简单实现，包含增、删、改、查、批量以及事务等操作。

DbUtils有两个核心对象：QueryRunner类和ResultSetHandler接口。

DbUtils处理结果集主要靠Handler，主要有9个，作用如下所示：

- ArrayHandler：把结果集中的第一行数据转成对象数组。
- ArrayListHandler：把结果集中的每一行数据都转成一个对象数组，再存放到List中。
- BeanHandler：将结果集中的第一行数据封装到一个对应的JavaBean实例中。
- BeanListHandler：将结果集中的每一行数据都封装到一个对应的JavaBean实例中，存放到List里。
- ColumnListHandler：将结果集中某一列的数据存放到List中。
- KeyedHandler：将结果集中的每一行数据都封装到一个Map里，然后再根据指定的key把每个Map再存放到一个Map里。
- MapHandler：将结果集中的第一行数据封装到一个Map里，key是列名，value就是对应的值。
- MapListHandler：将结果集中的每一行数据都封装到一个Map里，然后再存放到List。
- ScalarHandler：将结果集中某一条记录的其中某一列的数据存成Object。

QueryRunner可以使用可插拔的策略执行SQL查询，并处理 ResultSet结果集。QueryRunner主要有updata、insert和query三中函数，以及进行批量操作的batch函数。

## 使用
### c3p0
使用c3p0需要注意两个问题：jar包和配置文件。

使用c3p0需要导入jar包：`c3p0-0.9.2.1.jar`，另外还需要数据库连接的jar包`mysql-connector-java-5.1.40-bin.jar`。或者使用mavren，依赖如下：
```xml
<dependency>
  <groupId>commons-dbutils</groupId>
  <artifactId>commons-dbutils</artifactId>
  <version>1.6</version>
</dependency>

<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>6.0.4</version>
</dependency>
```

配置文件名称为`c3p0-config.xml`，名字固定，且只能放在src根目录下面。还需要注意在idea中需要在Project Structure中将src目录表为'Source'，否则配置文件读不到。

配置文件的大概写法如下(因为这里使用的jdbc-connector的版本比较高，driverClass的名字已经由`com.mysql.jdbc.Driver`改为了`com.mysql.cj.jdbc.Driver`)：
```xml
<?xml version="1.0" encoding="utf-8" ?>
<c3p0-config>
    <default-config>
        <property name="jdbcUrl">jdbc:mysql://localhost:3306/customermanagement</property>
        <property name="driverClass">com.mysql.cj.jdbc.Driver</property>
        <property name="user">root</property>
        <property name="password">root</property>
        <property name="acquireIncrement">3</property>
        <property name="initialPoolSize">10</property>
        <property name="minPoolSize">2</property>
        <property name="maxPoolSize">10</property>
    </default-config>
</c3p0-config>
```

---
参考：
[数据库连接池C3P0,DBCP教程详解示例](https://cloud.tencent.com/developer/article/1100009)
[Java 数据库系列教程--C3P0详解](https://blog.csdn.net/wqc19920906/article/details/72825793)

---

### DbUtils
使用DbUtils需要导入jar包`mchange-commons-java-0.2.3.4.jar`，或者maven依赖：
```xml
<dependency>
  <groupId>commons-beanutils</groupId>
  <artifactId>commons-beanutils</artifactId>
  <version>1.9.1</version>
</dependency>
```

---
[dbUtils 中的各种 Handler 什么 意思](https://blog.csdn.net/chuck_kui/article/details/50924101)

---

# Servlet和JSP跳转方法
## forward方式 
形如`request.getRequestDispatcher(“/index.jsp”).forward(request,response);`。页面的路径是相对路径。forward方式只能跳转到本web应用中的页面上。跳转后地址栏不会发生变化。使用这种方式跳转，传值可以使用三种方法：url中带parameter，session，request.setAttribute

## redirect方式 
`response.sendRedirect(“/index.jsp”)`;页面的路径是相对路径。sendRedirect可以将页面跳转到任何页面，不一定局限于web应用中，如：response.sendRedirect（”http://www.baidu.com“）； 

跳转后浏览器地址发生变化，只能在url总带parameter或者放在session中，无法使用request.setAttribute来传递值。 

---
两者比较：

- request.getRequestDispatcher().forward()是容器中控制权的转向，在客户端浏览器地址栏中不会显示出转向后的地址；
- response.sendRedirect()则是完全的跳转，浏览器将会得到跳转的地址，并重新发送请求链接。这样，从浏览器的地址栏中可以看到跳转后的链接地址。

---

## <a href=url></a>
`<a href= "LoginUrl?username=guangchen&pwd=123456" >跳转到Loginservler的doGet</a>`

## ＜jsp:forward page="" /＞
它的底层部分是由RequestDispatcher来实现的，因此它带有RequestDispatcher.forward()方法的印记。

如果在之前有很多输出,前面的输出已使缓冲区满,将自动输出到客户端,那么该语句将不起作用,这一点应该特别注意。

另外要注意：它不能改变浏览器地址，刷新的话会导致重复提交。

## 修改HTTP header的Location属性来重定向
通过设置直接修改地址栏来实现页面的重定向。

`response.setHeader("Location","/index.jsp");`

使用该函数可以实现定时跳转，比如下面的代码：
```xml
response.setHeader("REFRESH","5; url=5_2.jsp");
```
实现5秒后自动跳转到5_2.jsp。

具体可以参见[response.setHeader()的用法](https://www.cnblogs.com/mingforyou/p/4259113.html)。

## 使用JSP的meta标签来定时跳转
`＜meta http-equiv="refresh" content="300; url=target.jsp"＞`

它的含义：在5分钟之后正在浏览的页面将会自动变为target.html这一页。代码中300为刷新的延迟时间，以秒为单位。targer.html为你想转向的目标页,若为本页则为自动刷新本页。 

---
参考：
[JSP跳转方式与Servlet跳转方式的区别](https://blog.csdn.net/weixin_41577923/article/details/82719751)
[Servlet跳转页面的几种方法总结](https://blog.csdn.net/hry1243916844/article/details/71425279)
[jsp与servlet之间页面跳转及参数传递实例](https://blog.csdn.net/ssy_shandong/article/details/9328985/)
[]()

---

# Servlet与JSP的区别
## servlet
Servlet是Java提供的用于开发Web服务器应用程序的一个组件，运行在服务器端，由Servlet容器所管理，用于生成动态的内容。servlet更擅长于逻辑控制，但是很难向网页中输出HTML页面内容。

## jsp
JSP（Java Server Pages）的本质就是Servlet，或者说是简化的servlet。jsp和servlet的关系可以描述为下面的形式：JSP = HTML + Java代码 + JSP标签。显示数据非常方便，像html格式一样；但是封装数据和处理数据比较麻烦。

JSP页面在执行的时候都会被服务器端的JSP引擎转换为Servelet（.java），然后又由JSP引擎调用Java编译器，将Servelet（.java）编译为Class文件（.class），并由Java虚拟机（JVM）解释执行。

jsp更擅长表现于页面显示,servlet更擅长于逻辑控制。

---
参考：
[servlet与JSP区别](https://blog.csdn.net/haofengjiao/article/details/70184189)
[jsp和servlet有什么区别？](https://blog.csdn.net/lengxingxing_/article/details/65444434)
[servlet与jsp区别与联系](https://blog.csdn.net/qq_40373497/article/details/80621777)
[jsp与servlet的区别与联系](https://www.cnblogs.com/sanyouge/p/7325656.html)

---

# BaseServlet
## servlet执行的完整流程
### 浏览器请求
<center>
<img src="https://raw.githubusercontent.com/adamhand/LeetCode-images/master/servlet1.png">
</center>

浏览器向服务器请求时，服务器不会直接执行我们的类，而是到web.xml里寻找路径名 
①：第一步，浏览器输入访问路径后，携带了请求行，头，体 
②：第二步，根据访问路径找到已注册的servlet名称，既图中的demo 
③：第三步，根据映射找到对应的servlet名 
④：第四步，根据根据servlet名找到我们全限定类名，既我们自己写的类


### 服务器创建对象
<center>
<img src="https://raw.githubusercontent.com/adamhand/LeetCode-images/master/servlet2.png">
</center>

服务器找到全限定类名后，通过反射创建对象，同时也创建了servletConfig，里面存放了一些初始化信息（注意服务器只会创建一次servlet对象，所以servletConfig也只有一个）

### 调用init方法
<center>
<img src="https://raw.githubusercontent.com/adamhand/LeetCode-images/master/servlet3.png">
</center>

①：对象创建好之后，首先要执行init方法，但是我们发现我们自定义类下没有init方法，所以程序会到其父类HttpServlet里找 
②：我们发现HttpServlet里也没有init方法，所以继续向上找，既向其父类GenericServlet中继续寻找,在GenericServlet中我们发现了init方法，则执行init方法（对接口Servlet中的init方法进行了重写） 

注意： 
在GenericServlet中执行public void init(ServletConfig config)方法的时候，又调用了自己无参无方法体的init()方法，其目的是为了方便开发者，如果开发者在初始化的过程中需要实现一些功能，可以重写此方法

### 调用service方法
<center>
<img src="https://raw.githubusercontent.com/adamhand/LeetCode-images/master/servlet4.png">
</center>

接着，服务器会先创建两个对象：ServletRequest请求对象和ServletResponse响应对象，用来封装浏览器的请求数据和封装向浏览器的响应数据 

①：接着服务器会默认在我们写的类里寻找service(ServletRequest req, ServletResponse res)方法，但是DemoServlet中不存在，那么会到其父类中寻找 
②：到父类HttpServlet中发现有此方法，则直接调用此方法，并将之前创建好的两个对象传入 
③：然后将传入的两个参数强转，并调用HttpServlet下的另外个service方法 
④：接着执行service(HttpServletRequest req, HttpServletResponse

resp)方法，在此方法内部进行了判断请求方式，并执行doGet和doPost，但是doGet和doPost方法已经被我们自己重写了，所以会执行我们重写的方法 
看到这里，你或许有疑问：为什么我们不直接重写service方法？ 
因为如果重写service方法的话，我们需要将强转，以及一系列的安全保护判断重新写一遍，会存在安全隐患

### 向浏览器响应
<center>
<img src="https://raw.githubusercontent.com/adamhand/LeetCode-images/master/servlet5.png">
</center>

## servlet生命周期
由上面的servlet执行的流程可以总结出servlet的生命周期:

① 初始化阶段，调用init()方法。
② 响应客户请求阶段，调用service()方法。由service(）方法根据提交方式选择执行doGet()或者doPost()方法。
③ 终止阶段，调用destory()方法。
<center>
<img src="https://raw.githubusercontent.com/adamhand/LeetCode-images/master/servlet%20life.jpg">
</center>

## JavaWeb BaseServlet的用法及原理
### 基础
一般写servlet的时候，基本上是每一个功能对应一个servlet程序，这样的结果是servlet和xml文件中的配置都冗余。

### 进阶
一个改进的方法是，将所有功能的servlet集成到一个文件中，使用request.getParameter()获得方法名，然后使用if或switch进行判断。但是这种方法还是有一个缺点：如果方法很多，if或者switch语句就会很多，既不美观，效率也不高。

### 高级
建立BaseServlet，利用反射获得当前的方法，并调用。

① 先获取请求携带的方法参数值 
② 获取指定类的字节码对象 
③ 根据请求携带的方法参数值，再通过字节码对象获取指定的方法 
④ 最后执行指定的方法
```java
public class BaseServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 获取请求标识
            String methodName = request.getParameter("method");
            // 获取指定类的字节码对象
            Class<? extends BaseServlet> clazz = this.getClass();//这里的this指的是继承BaseServlet对象
            // 通过类的字节码对象获取方法的字节码对象
            Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 让方法执行
            method.invoke(this, request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
```

---
参考：
[Java servlet执行的完整流程（图解含源码分析）](https://blog.csdn.net/u010452388/article/details/80395679)
[JavaWeb BaseServlet的用法及原理（含图解执行流程）](https://blog.csdn.net/u010452388/article/details/80723550)
[Servlet执行流程](https://blog.csdn.net/chengqiuming/article/details/78602191)

---

# BeanUtils.populate()封装对象
BeanUtils工具是一种方便我们对JavaBean进行操作的工具。

使用BeanUtils需要导入jar包`commons-beanutils`，或者在maven中导入如下依赖。如果需要使用增强功能的话还需要导入`commons‐logging‐1.2.jar // 日志记录包`和`commons‐collections‐3.2.2.jar // 增强的集合包`两个jar包。
```xml
<dependency>
  <groupId>commons-beanutils</groupId>
  <artifactId>commons-beanutils</artifactId>
  <version>1.9.1</version>
</dependency>
```

常用的功能有三个：

- 对javaBean的属性进行赋值。使用`getProperty()`和`setProperty()`方法。
- 将一个JavaBean所有属性赋值给另一个JavaBean对象中。使用`copyProperties()`方法。
- 将一个MAP集合的数据拷贝到一个javabean对象中。使用`populate()`方法。

可以配合`request.getParameterMap()`方法，将用map获得的bean中的属性拷贝到一个bean中，生成一个对象。
```java
public static <T> T fillBean(HttpServletRequest request, Class<T> clazz){
    T bean = null;
    try {
        bean = clazz.newInstance();

        BeanUtils.populate(bean, request.getParameterMap());

    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (InvocationTargetException e) {
        e.printStackTrace();
    }
    return bean;
}
```

---
参考：
[BeanUtils](https://blog.csdn.net/h294590501/article/details/80740002)
[BeanUtils工具 ](https://www.cnblogs.com/vmax-tam/p/4159985.html)

---

# servlet传递参数的方法
## JSP传值给Servlet 
JSP传值给Servlet有几种形式：Form表单传值，url传值java代码传值。

① form表单传值：
```jsp
<form name="myForm" method="post" action="actionname">
<input name="username"type="text" /> 
<input name="password"type="password" />
</form>
<!--action里面也可以传参数，在method=get下，form表单只接收表单元素”name“属性和相应的”value“。想要通过在action中自己写？的形式传值，method的值必须是=post
如:action="actionname?pass=11"-->
```
在点击提交按钮的时候，数据就会提交到后台，让actionname这个action去处理：
```java
request.getParameter("username") 
```
② url传值：
```jsp
<a href="/myServlet?param1=aa&m2=bb"/> 在后台直接用request.getParameter("param1")得到aa的值
```
③ java代码传值：
```jsp
java片段代码，servlet只能接到 session.setAttribute("testSession","Hello session")的内容，而接不到 request的内容。在 servlet里用 request.getSession().getAttribute("testSession")获取 session内容。
```

## Servlet传值给Jsp 
① redirect 方式
这种方式要传值出去的话，只能在url中带parameter或者放在session中，无法使用request.setAttribute来传递。

② forward方式 
使用这种方式跳转，传值可以使用三种方法：url中带parameter，session，request.setAttribute。

---
注意：如果在jsp中使用${param}接收不到servlet传过来的信息，而是将param以字符串的方式打印，可能是xml版本过低，可以去web.xml中修改，如下：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
```
参考：[jstl的taglib不起作用——c标签不起作用](https://blog.csdn.net/xiakepan/article/details/22599957)

---

## 补充：request.getParameter() 和request.getAttribute() 区别
### **方法成对**
只有getParameter()，没有setParameter()；setAttribute()和getAttribute()是一对；

### **请求方式**
**getParameter():**响应的是两个web组件之间为**链接(重定向)**关系时，如get和post表单提交请求，传递请求参数，注意此种方法是**从web客户端向web服务端传递数据，代表HTTP请求数据**
```jsp
<form name="form1" method="post" action="2.jsp">
    <input type="text" name="username" />
    <input type="submit" value="提交" />
</form>
```

或`1.jsp`中有`<a href="2.jsp?username=accp">2.jsp</a>`，在`2.jsp`中通过`request.getParameter("username")`来获得请求参数`username`

**getAttribute()：**响应的两个web组件之间为**转发**关系时，服务端的转发源通过setAttribute()设置传递的参数，然后转发目的通过getAttribute()获取传递的参数，这样转发时数据就不会丢失，**注意此种方法只存在于web容器内部** 

servlet端代码为：
```jsp
//pageModel是个对象
request.setAttribute("pageModel", pageModel);

request.getRequestDispatcher("/basedata/item_maint.jsp").forward(request, response);
```
另一个.jsp代码为（需要强制转换）：
```jsp
PageModel pageModel = (PageModel) request.getAttribute("pageModel");
```

#### **返回类型**

- getParameter()：返回String类型的数据
- getAttribute()：返回可是String类型的数据，也可以是对象，但是当返回的是对象时需要强制转换

---
参考：
[servlet和jsp之间传值的方式](https://blog.csdn.net/weixin_35982117/article/details/78247527)
[request.getParameter() 和request.getAttribute() 区别](https://blog.csdn.net/wrs120/article/details/79287607)

---

# mvc与三层结构的区别
首先说明，这两种不是一种东西，也没有严格的对应关系。三层结构是一种架构，而mvc是一种设计模式。

## 三层结构
三层架构是界面层（UI）、业务逻辑层（BLL）和数据访问层（DAL）。是为了解决整个应用程序中各个业务操作过程中不同阶段的代码封装的问题，为了使程序员更加专注的处理某阶段的业务逻辑。

## mvc
mvc是Models、Views和Controller。主要是为了解决应用程序用户界面的样式替换问题，把展示数据的 HTML 页面尽可能的和业务代码分离。MVC把纯净的界面展示逻辑（用户界面）独立到一些文件中（Views），把一些和用户交互的程序逻辑（Controller）单独放在一些文件中，在 Views 和 Controller 中传递数据使用一些专门封装数据的实体对象，这些对象，统称为Models。

只所以说MVC和三层毫无关系，是因为它们二者使用范围不同：三层可以应用于任何语言、任何技术的应用程序；而MVC只是为了解决BS应用程序视图层各部分的耦合关系。它们互不冲突，可以同时存在，也可根据情况使用其中一种。

但它们的存在都是为了解耦。

## 补充：关于view、controller、service、dao的理解

- view：视图。一方面提供展现给用户的页面，另一方面将用户的数据传到后台。
- controller：也可以成为action层，业务模块流程。它除了**控制视图的转换**之外，还**控制了业务的逻辑**，但是，这里的控制业务逻辑不是业务逻辑的实现，而仅仅是一个大的模块，你看到之后，知道它实现了这个业务逻辑，但是怎么实现的，不需要关心，仅仅需要调用service层里的一个方法即可，这样使controller层看起来更加清晰。
- service：业务逻辑层。接着controller层中。service层是业务逻辑（商务逻辑）的具体实现。它向上层的controller层提供接口，并且使用dao层提供的接口。存在的必要性：service层中仅仅是调用dao层中的一个方法，那么它是否有必要存在呢？答案是肯定的。因为，假如将来客户的业务有一定的变动，那么这样一来，你只需要在service层中进行一些变动即可。记住，你写程序不应该仅仅为实现功能考虑，更多的还是应该为将来的维护考虑，因为大部分的时间还是在维护上的。
- dao：数据访问对象。它只负责对数据进行访问，而不管其他的什么业务逻辑。在dao层里面要完成的是数据访问逻辑以及对数据的访问。数据访问，大部分情况下就是对数据进行操作。dao层为上层的service层提供接口。dao层在操作完成后，如果是查询，则返回对象，如果是增删改，则仅仅需要返回一个boolean值表示成功失败即可。

---
参考：
[mvc与三层结构终极区别](https://blog.csdn.net/csh624366188/article/details/7183872)
[关于view、controller、service、dao的理解](https://1358440610-qq-com.iteye.com/blog/1871393)

---

# JSP标签
JSP标签也称之为Jsp Action(JSP动作)元素，它用于在Jsp页面中提供业务逻辑功能，避免在JSP页面中直接编写java代码，造成jsp页面难以维护。

使用taglib标签需要在jsp中加入：
```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```
并且在maven中导入
```xml
<dependency>
  <groupId>taglibs</groupId>
  <artifactId>standard</artifactId>
  <version>1.1.0</version>
</dependency>

<dependency>
  <groupId>jstl</groupId>
  <artifactId>jstl</artifactId>
  <version>1.1.0</version>
</dependency>
```

# web.xml文件
## web.xml中常见的元素标签
### **元素1 `<icon>`**
- 含义
icon元素包含`small-icon`和`large-icon`两个子元素。用来指定`web`站台中小图标和大图标的路径。

`<small-icon>/路径/smallicon.gif</small-icon>`。small-icon元素应指向web站台中某个小图标的路径,大小为16 X 16 pixel,但是图象文件必须为GIF或JPEG格式,扩展名必须为:.gif或.jpg。

`<large-icon>/路径/largeicon-jpg</large-icon>`。large-icon元素应指向web站台中某个大图表路径,大小为32 X 32 pixel,但是图象文件必须为GIF或JPEG的格式,扩展名必须为; gif或jpg。

- 范例
```xml
<icon>
   <small-icon>/images/small.gif</small-icon>
   <large-icon>/images/large.gif</large-icon>
</icon>
```

### **元素2、3：`<display-name>`，`<description>`**
- 含义

`<display-name>应用名称</display-name>`。定义应用的名称。

`<description>应用描述</discription>`。对应用做出描述。

- 范例
```xml
<display-name>test</display-name>
<description>测试应用V1.0</discription>
```

### **元素4：`<context-param>`**
- 含义

`<context-param>`元素含有一对参数名和参数值，用作应用的`servlet`上下文初始化参数，参数名在整个`Web`应用中必须是惟一的，在`Web`应用的整个生命周期中上下文初始化参数都存在，任意的`Servlet`和`jsp`都可以随时随地访问它。`<param-name>`子元素包含有参数名，而`<param-value>`子元素包含的是参数值。作为选择，可用`<description>`子元素来描述参数。

**那么。什么情况下使用，为什么使用`<context-param>`**：
比如：定义一个管理员`email`地址用来从程序发送错误，或者与你整个应用程序有关的其他设置。使用自己定义的设置文件需要额外的代码和管理；直接在你的程序中使用硬编码（`Hard-coding`）参数值会给你之后修改程序带来麻烦，更困难的是，要根据不同的部署使用不同的设置；通过这种办法，可以让其他开发人员更容易找到相关的参数，因为它是一个用于设置这种参数的标准位置。

**`Spring`配置文件中该如何配置：**
配置`Spring`，必须需要`<listener>`，而`<context-param>`可有可无，如果在`web.xml`中不写`<context-param>`配置信息，默认的路径是`/WEB-INF/applicationontext.xml`，在`WEB-INF`目录下创建的`xml`文件的名称必须是`applicationContext.xml`。如果是要自定义文件名可以在`web.xml`里加入`contextConfigLocation`这个`context`参数：在`<param-value></param-value>`里指定相应的`xml`文件名，如果有多个`xml`文件，可以写在一起并以“,”号分隔，使用自定义配置例子如下：
```xml
<!-- spring config -->  
<context-param>  
    <param-name>contextConfigLocation</param-name>  
    <param-value>/WEB-INF/spring-configuration/*.xml</param-value>  
</context-param>  
<listener>  
     <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>  
 </listener>
```

`context-param` 元素用来设定web应用的环境参数(context),它包含两个子元素:`param-name`和`param-value`。

`<param-name>参数名称</param-name>`。设定Context名称

`<param-value>值</param-value>`。设定Context名称的值

- 范例
```xml
<context-param>
   <param-name>param_name</param-name>
   <param-value>param_value</param-value>
</context-param>
```
此所设定的参数,在JSP网页中可以使用下列方法来取得:`${initParam.param_name}`

若在Servlet可以使用下列方法来获得:`String param_name=getServletContext().getInitParamter("param_name")`;


### **元素5，6：`<filter>`，`<filter-mapping>`**
- 含义

**`filter`**元素用来设定`web`应用的过滤器，它的两个主要子元素`filter-name`和`filter-class`用来定义`Filter`所对应的`class`

`<filter-name>Filter的名称</filter-name>`。定义Filter的名称

`<filter-class>Filter的类名称</filter-class>`。定义Filter的类名称

**`filter-mapping`** 元素的两个主要子元素`filter-name`和`url-pattern`。用来定义`Filter`所对应的`URL`。

`<filter-name>Filter的名称</filter-name>`。定义`Filter`的名称.

`<url-pattern>URL</url-pattern>`。`Filter`所对应的`RUL`。例如:`<url-pattern>/Filter/*</url-pattern>`。

- 范例
```xml
<filter>
  <filter-name>Encoding</filter-name>
  <filter-class>ghjf.test.filter.SetCharacterEncodingFilter</filter-class>
  <init-param>
     <param-name>encoding</param-name>
     <param-value>GBK</param-value>
  </init-param>
</filter>

<filter-mapping>
   <filter-name>Encoding</filter-name>
   <url-pattern>/*</url-pattern>
</filter-mapping>
```

### **元素7：`<listener>`**

- 含义
`<listener>`为web应用程序定义监听器，监听器用来监听各种事件，比如：`application`和`session`事件，所有的监听器按照相同的方式定义，功能取决去它们各自实现的接口，常用的Web事件接口有如下几个：

    - `ServletContextListener`：用于监听`Web`应用的启动和关闭；
    - `ServletContextAttributeListener`：用于监听`ServletContext`范围（`application`）内属性的改变；
    - `ServletRequestListener`：用于监听用户的请求；
    - `ServletRequestAttributeListener`：用于监听`ServletRequest`范围（`request`）内属性的改变；
    - `HttpSessionListener`：用于监听用户`session`的开始和结束；
    - `HttpSessionAttributeListener`：用于监听`HttpSession`范围（`session`）内属性的改变。

`<listener>`主要用于监听`Web`应用事件，其中有两个比较重要的WEB应用事件：**应用的启动和停止（starting up or shutting down）**和**Session的创建和失效（created or destroyed）**。

`listener`元素用来定义`Listener`接口,它的主要子元素为`<listener-class>`

`<listen-class>Listener的类名称</listener-class>`。定义Listener的类名称

- 范例
```xml
<listener>
  <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

上面这段配置的意思是加载spring的监听器，其中`ContextLoaderListener`的作用就是启动`Web`容器时，自动装配`applicationContext.xml`的配置信息，执行它所实现的方法。

### **元素8、9：`<servlet>`，`<servlet-mapping>`**
- 含义

**`servlet`**元素的两个主要子元素`servlet-name`和`servlet-class`用来定义`servlet`所对应的`class`

`<servlet-name>servlet的名称</servlet-name>`。定义servlet的名称

`<servlet-class>servlet的类名称</servlet-class>`。定义servlet的类名称

**`servlet-mapping`**元素包含两个子元素`servlet-name`和`url-pattern`。用来定义`servlet`所对应`URL`。

`<servlet-name>Servlet的名称</servlet-name>`。定义Servlet的名称.

`<url-pattern>Servlet URL</url-pattern>`。定义Servlet所对应的RUL。例如:`<url-pattern>/Servlet/*</url-pattern>`

- 范例
```xml
<servlet>
 <servlet-name>dwr-invoker</servlet-name>
 <display-name>DWR Servlet</display-name>
 <description>Direct Web Remoter Servlet</description>
 <servlet-class>uk.ltd.getahead.dwr.DWRServlet</servlet-class>
 <init-param>
     <param-name>debug</param-name>
     <param-value>true</param-value>
 </init-param>
</servlet>

<servlet-mapping>
 <servlet-name>dwr-invoker</servlet-name>
 <url-pattern>/dwr/*</url-pattern>
</servlet-mapping>
```

### **元素10：`<session-cofing>`**

- 含义

`session-config`包含一个子元素`session-timeout`。定义`web`应用中的`session`参数。

`<session-timeout>分钟</session-timeout>`。定义这个`web`站台所有`session的有效期限。单位为分钟。该元素值必须为整数。如果 session-timeout元素的值为零或负数，则表示会话将永远不会超时。

- 范例
```xml
<session-config>
   <session-timeout>30</session-timeout>
</session-config>
```

### **元素11：`<mime-mapping>`**

- 含义

`mime-mapping`包含两个子元素`extension`和`mime-type`。定义某一个扩展名和某一`MIME Typ`e做对映。

`<extension>扩展名名称</extension>`。扩展名称
`<mime-type>MIME格式</mime-type>`。MIME格式

- 范例
```xml
<mime-mapping>
   <extension>doc</extension>
   <mime-type>application/vnd.ms-word</mime-type>
</mime-mapping>
<mime-mapping>
   <extension>xls</extension>
   <mime-type>application/vnd.ms-excel</mime-type>
</mime-mapping>
<mime-mapping>
   <extension>ppt</extesnion>
   <mime-type>application/vnd.ms-powerpoint</mime-type>
</mime-mapping>
```

### **元素12：`<welcome-file-list>`**
- 含义

`<welcome-file-list>`包含一个子元素`<welcome-file>`，`<welcome-file>`用来指定首页文件名称。`<welcome-file-list>`元素可以包含一个或多个`<welcome-file>`子元素。如果在第一个`<welcome-file>`元素中没有找到指定的文件，`Web`容器就会尝试显示第二个，以此类推。

- 示例
```xml
<welcome-file-list>
  <welcome-file>index.jsp</welcome-file>
  <welcome-file>index.htm</welcome-file>
</welcome-file-list>
```

### **元素13：`<error-page>`**
- 含义

`error-page`元素包含三个子元素`error-code`,`exception-type`和`location`。将错误代码(`Error Code`)或异常(`Exception`)的种类对应到`web`应用资源路径。

`<error-code>错误代码</error-code>`。HTTP Error code,例如: 404、403

`<exception-type>Exception</exception-type>`。一个完整名称的Java异常类型

`<location>/路径</location>`。在web应用内的相关资源路径

- 范例
```xml
<error-page>
   <error-code>404</error-code>
   <location>/error404.jsp</location>
</error-page>
<error-page>
   <exception-type>java.lang.Exception</exception-type>
   <location>/exception.jsp</location>
</error-page>
```

### **元素14：`<jsp-config>`**
- 含义

`jsp-config`元素主要用来设定`JSP`的相关配置,`<jsp:config>`包括`<taglib>`和`<jsp-property-group>`两个子元素。其中`<taglib>`元素在JSP 1.2时就已经存在了;而`<jsp-property-group>`是JSP 2.0新增的元素。

**taglib元素**包含两个子元素`taglib-uri`和`taglib-location`。用来设定`JSP`网页用到的`Tag Library`路径。

`<taglib-uri>URI</taglib-uri>`。`taglib-uri`定义`TLD`文件的`URI`,`JSP`网页的`taglib`指令可以经由这个`URI`存取到`TLD`文件。

`<taglib-location>/WEB-INF/lib/xxx.tld</taglib-laction>`。`TLD`文件对应`Web`站台的存放位置。

**jsp-property-group元素**包含8个元素,分别为:
`<description>Description</descrition>`。此设定的说明

`<display-name>Name</display-name>`。此设定的名称

`<url-pattern>URL</url-pattern>`。设定值所影响的范围,如:/CH2 或者/*.jsp

`<el-ignored>true|false</el-ignored>`。若为true,表示不支持EL语法.

`<scripting-invalid>true|false</scripting-invalid>`。若为true表示不支持<%scription%>语法.

`<page-encoding>encoding</page-encoding>`。设定JSP网页的编码

`<include-prelude>.jspf</include-prelude>`。设置JSP网页的抬头,扩展名为.jspf

`<include-coda>.jspf</include-coda>`。设置JSP网页的结尾,扩展名为.jspf
</jsp-property-group>
</jsp-config>

- 范例
```xml
<jsp-config>
<taglib>
   <taglib-uri>Taglib</taglib-uri>
   <taglib-location>/WEB-INF/tlds/MyTaglib.tld</taglib-location>
</taglib>
<jsp-property-group>
   <description>
      Special property group for JSP Configuration JSP example.
   </description>
   <display-name>JSPConfiguration</display-name>
   <uri-pattern>/*</uri-pattern>
   <el-ignored>true</el-ignored>
   <page-encoding>GB2312</page-encoding>
   <scripting-inivalid>true</scripting-inivalid>
</jsp-property-group>
</jsp-config>
```

### **元素15：`<resource-env-ref>`**
- 含义

`resource-env-ref`有两个子元素：
```xml
<!--资源的名称 相对于java:comp/env-->
<resource-env-ref-name>资源名</resource-env-ref-name>
<!--当web应用查找该资源的时候，返回的Java类名的全称-->
<resource-env-ref-type>查找资源时返回的资源类名</resource-env-ref-type>
```
- 范例
```xml
<resource-env-ref>
    <resource-env-ref-name>jdbc/mssql</resource-env-ref-name>
    <resource-env-ref-type>javax.sql.DataSource</resource-env-ref-type>
</resource-env-ref>
```
### **元素16：`<resource-ref>`**
- 含义

`resource-ref`元素包括五个子元素`description`,`res-ref-name`,`res-type`,`res-auth`,`res-sharing-scope`。利用`JNDI`取得应用可利用资源。
```xml
<!--资源说明-->
<description>说明</description>
<!--资源名称-->
<rec-ref-name>资源名称</rec-ref-name>
<!--资源种类-->
<res-type>资源种类</res-type>
<!--资源由Application或Container来许可-->
<res-auth>Application|Container</res-auth>
<!--资源是否可以共享。默认值为 Shareable-->
<res-sharing-scope>Shareable|Unshareable</res-sharing-scope>
```
- 范例
```xml
<resource-ref>
   <description>JNDI JDBC DataSource</description>
   <res-ref-name>jdbc/data</res-ref-name>
   <res-type>javax.sql.DataSoruce</res-type>
   <res-auth>Container</res-auth>
</resource-ref>
```

## web.xml加载过程
先说一下，web.xml的家在过程为：`context-param -> listener -> filter -> servlet`。

① 启动WEB项目的时候，容器首先会去它的配置文件web.xml读取两个节点:  `<listener></listener>`和`<context-param></context-param>`。
② 紧接着，容器创建一个**ServletContext（application）**，这个WEB项目所有部分都将共享这个上下文。
③ 容器以    <context-param></context-param> 的`name`作为键，`value`作为值，将其转化为键值对，存入`ServletContext`。
④ 接着，容器会读取<filter></filter>，根据指定的类路径来实例化过滤器。
⑤ 以上都是在WEB项目还没有完全启动起来的时候就已经完成了的工作。如果系统中有Servlet，则Servlet是在第一次发起请求的时候被实例化的，而且一般不会被容器销毁，它可以服务于多个用户的请求。所以，Servlet的初始化都要比上面提到的那几个要迟。。如果系统中有Servlet，则Servlet是在第一次发起请求的时候被实例化的，而且一般不会被容器销毁，它可以服务于多个用户的请求。所以，Servlet的初始化都要比上面提到的那几个要迟。

---
参考：
[java web工程web.xml配置详解](https://blog.csdn.net/ahou2468/article/details/79015251#commentBox)
[web.xml配置详解](https://blog.csdn.net/guihaijinfen/article/details/8363839)

---

# session生命周期
## session简介
session的英文意思是“一段时间”。在计算机专业术语中，Session是指一个终端用户与交互系统进行通信的时间间隔，通常指从注册进入系统到注销退出系统之间所经过的时间以及如果需要的话，可能还有一定的操作空间。

具体到Web中的Session指的就是用户在浏览某个网站时，从进入网站到浏览器关闭所经过的这段时间，也就是用户浏览这个网站所花费的时间。因此从上述的定义中我们可以看到，Session实际上是一个特定的时间概念。

Session是用于存放用户与web服务器之间的会话，即服务器为客户端开辟的存储空间。

由于客户端与服务器之间的会话是**无状态**的机制，Session则可用于关联访问，因此多用与用户登录等功能上。

我们可以调用request的getsession()方法来使用session，通过setAttribute()注入值:
```java
//引号中的"xxx"为页面中name标签里的值，逗号后面的是Servlet中的变量名。
request.getSession().setAttribute("xxx",xxx);//储存到session中
```
之后可以用getAttribute()方法来获取session中的值:
```java
request.getSession().getAttribute("xxx",xxx);//读取session中的值
```
**[注]:无状态的意思是会话之间无关联性，无法识别该用户曾经访问过。**


##session和cookie
Session存在的意义是为了提高安全性，它将关键数据存在服务器端。与cookie不同，cookie则是将数据存在客户端的浏览器中。

因此cookie是较为危险的，若客户端遭遇黑客攻击，信息容易被窃取，数据也可能被篡改，而运用Session可以有效避免这种情况的发生。

具体参见[浅谈Session与Cookie的区别与联系](https://blog.csdn.net/duan1078774504/article/details/51912868)

## session生命周期
### session创建时机

- 浏览器在servlet中第一次使用session的时候会创建；
- 浏览器第一次访问jsp的时候，服务器也会为这个浏览器创建一个session对象；

### session销毁时机

- 程序员调用invalidate方法；（立刻销毁）
- 设置的存活时间到了；（默认是30分钟）。Session生成后，只要用户继续访问，服务器就会更新Session的最后访问时间，并维护该Session。用户每访问服务器一次，无论是否读写Session，服务器都认为该用户的Session"活跃（active）"了一次。
- 服务器非正常关闭；（突然断电）

**注意：**

- 正常关闭服务器，session不会销毁，而是直接序列化到硬盘上，下一次服务器启动的时候，会重新创建出来；
- 如果浏览器单方面关闭会话，服务器上对应的session不会死亡 ，但是会导致服务器给浏览器创建的JSESSIONID的cookie死亡，当cookie死亡后，会导致浏览器无法找到上一个session对象，会造成服务器中session死亡的假象；

### 设置session失效时间
① web.xml中
```xml
<session-config>
    <session-timeout>30</session-timeout>
</session-config>
```
② 在程序中手动设置
```java
session.setMaxInactiveInterval(30 * 60);//设置单位为秒，设置为-1永不过期
request.getSession().setMaxInactiveInterval(-1);//永不过期
```

③ tomcat也可以修改session过期时间，在server.xml中定义context时采用如下定义：
```xml
<Context path="/livsorder" 
docBase="/home/httpd/html/livsorder" 　　defaultSessionTimeOut="3600" 
isWARExpanded="true" 　　
isWARValidated="false" isInvokerEnabled="true" 　　isWorkDirPersistent="false"/>
```

---
[Session的生命周期和工作原理](https://blog.csdn.net/hanziang1996/article/details/78969044)
[Session的生命周期1](https://www.cnblogs.com/chao521/p/9189484.html)
[session的生命周期2](https://blog.csdn.net/hejingyuan6/article/details/17076019)

---

# 遇到的问题
## c3p0启动的时候找不到c3p0-config.xml文件
首先，c3p0-config.xml文件应该放在src目录下面；其次，要将src目录标位source(IDEA中在File/Project Structure/Modules，如下图所示)。

<center>
<img src="https://raw.githubusercontent.com/adamhand/LeetCode-images/master/c3p0-config.png">
</center>

## 连接数据库：ERROR: The server time zone value '�й�׼' is unrecognized or represents more than one time zone

提示系统时区出现错误。
**办法1：**可以在mysql中执行命令： 
`set global time_zone='+8:00';`

**办法2：**或者在数据库驱动的`url`后加上`serverTimezone=UTC`参数：
```xml
<property name="jdbcUrl">jdbc:mysql://localhost:3306/customermanagement?serverTimezone=UTC</property>
```

注意：这种写法的时候，如果该参数是`‘？’`后的第一个，则不会出现问题，但是如果不是第一个，比如下面这种写法：
`jdbc:mysql://localhost:3306/exam?characterEncoding=utf8&serverTimezone=UTC`
会报错的，会提示`The reference to entity “serverTimezone” must end with the ‘;’ delimiter. `
运行后控制台也会出现 `对实体 “serverTimezone” 的引用必须以 ‘;’ 分隔符结尾` 的错误提示。 

将代码改为：
```xml
<property name="jdbcUrl"> jdbc:mysql://localhost:3306/exam?characterEncoding=utf8&amp;serverTimezone=UTC </property>
```

这里的`&amp`;就像相当于`&；`，因为在`html`中`&`需要转义一下， 写为`&amp`。

参考[这里](https://blog.csdn.net/dongsl161/article/details/56495361)

## Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdb

这个问题是因为用了最新的mysql 连接驱动，修改一下driverClass即可。
之前的driverClass:
```xml
<property name="driverClass">com.mysql.jdbc.Driver</property>
```
修改后的driverClass:
```xml
<property name="driverClass">com.mysql.cj.jdbc.Driver</property>
```

