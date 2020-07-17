# 准备工作

## 建立数据库

```SQL 
create database hotel character set utf8;
use hotel;
source 附带的hotel.sql的路径
```



## 配置项目

![001](/001.png)

## 添加依赖

```xml
<!--spring-boot-starter-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<!--MyBatis Plus-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.3.1.tmp</version>
</dependency>
<!--lombok-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<!--代码生成器-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.3.1.tmp</version>
</dependency>
```

## 配置文件

```yml application.properties
# 端口配置
server.port=8080
# MySQL配置
spring.datasource.url=jdbc:mysql://localhost:3306/hotel?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# Freemaker配置
spring.freemarker.template-loader-path=classpath:/templates
spring.freemarker.cache=false
spring.freemarker.charset=UTF-8
spring.freemarker.suffix=.ftl
# 添加外部静态目录
spring.resources.static-locations=classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/,file:E:/image/
```

## 代码生成器

这里用了Mybatis Plus的核心功能，会帮你把Entity和Mapper根据数据库写好，其中Mapper类继承了BaseMapper<T>，里面有一些函数可以直接调用，比如selectById，具体可以了解官方文档，下面给出我的示例，有些细节方面的配置还是要看官方文档。

[Mybatis Plus](https://mybatis.plus/)

```java CodeGenerator.ava
package com.manage.hotel.Util;

public class CodeGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("Len");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setIdType(IdType.NONE);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/hotel?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("");
        pc.setParent("com.manage.hotel");
        pc.setMapper("Mapper");
        pc.setEntity("Entity");
        mpg.setPackageInfo(pc);


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setService(null);
        templateConfig.setController(null);
        templateConfig.setXml(null);
        templateConfig.setServiceImpl(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(false);
        strategy.setInclude("admin,jobs,employee,coupon,customer,log".split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }

}
```

然后我们运行它，去生成的Mapper里面手动添加**@Repository**注解，实体类可以把对应的时间戳换成String也可以（不换的话日期和具体时间会有个`T`），至于Getter和Setter不用去管了，因为帮我们生成实体类的上边的注解帮我们完成了。

## MapperScan注解

```java HotelApplication.java
package com.manage.hotel;

@SpringBootApplication
@MapperScan(basePackages = "com.manage.hotel.Mapper")//添加Mapper包
public class HotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class, args);
    }

}
```

# 源文件编写

## 主页及登陆系统

### 前端

由于我们是使用的hAdmin的静态资源，所以我们直接<kbd>Ctrl</kbd>+<kbd>C</kbd> <kbd>Ctrl</kbd>+<kbd>V</kbd>到template文件夹下，然后改为*.ftl，修改为需要的页面，并配置一下链接的href属性即可。如：

```html
<li id="master">
	<a href="#"><i class="fa fa-table"></i> <span class="nav-label">管理员</span><span
                                class="fa arrow"></span></a>
	<ul class="nav nav-second-level">
    	<li>
        	<a class="J_menuItem" href="/adminManage">管理员管理</a>
    	</li>
		<li>
        	<a class="J_menuItem" href="/logs">管理员操作记录</a>
    	</li>
    </ul>
</li>
```

> 这里就不完整贴上来了，href换成目标的get方法（先设计好思路，然后再一个个到Controller实现），自己改一改。
> 	
> 然后编写一个登陆界面，挺简单的，用表单即可实现，当然，我是直接复制的。

一些方法idea会帮你标记淡黄的背景，是因为我们还没有开始和后端匹配。

### 后端

这里讲一下登陆系统的思路。

```flow
st=>start: 开始
end=>end: 结束
login=>inputoutput: 用户输入网址
filter=>operation: 过滤器拦截
cc=>condition: Session是否包含id和name
lg=>operation: 返回主页
lo=>operation: 返回登陆页面
ipop=>inputoutput: 输入用户名密码
cp=>condition: 密码正确
sc=>operation: 设置Session
st->login->filter->cc(yes)->lg->end
cc(no)->lo->cp(yes)->sc(left)->lg
cp(no)->lo
```





代码实现：

先设置拦截器SessionFilter，我们把检查Session是否有效，如果有效则通过，不有效则返回登陆页面

```java SessionFilter
package com.manage.hotel.Filter;

@WebFilter(filterName = "sessionFilter", urlPatterns = {"/*"})
public class SessionFilter implements Filter {
    String[] urls = {"/login", "/doLogin"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String URI = request.getRequestURI();
        if (urls[0].equals(URI) || urls[1].equals(URI))
            filterChain.doFilter(servletRequest, servletResponse);//无需过滤
        else {
            // session中包含user对象,则是登录状态
            if (session != null && session.getAttribute("name") != null && session.getAttribute("id") != null)
                filterChain.doFilter(request, response);
            else
                response.sendRedirect(request.getContextPath() + "/login");//重定向
        }
    }

    @Override
    public void destroy() {

    }
}

```

然后是WebController

```java  WebController.java
package com.manage.hotel.Controller;

@Controller
public class WebController {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private LogMapper logMapper;

    @RequestMapping("/login")//未登录重定向到这里
    public String login() {
        return "login";
    }

    @ResponseBody//前端数据发送到这里
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(String name, String pass, HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        name = name.replace("<", "&lt;");
        name = name.replace(">", "&gt;");
        //这里为了防止有人用html标签搞破坏 TAT
        Admin admin = adminMapper.selectByName(name);
        String pwd = admin.getPass();
        int id = admin.getId();
        if (pwd.equals(pass)) {
            session.setAttribute("id", "" + id);
            session.setAttribute("name", name);
            session.setMaxInactiveInterval(10 * 60);//秒为单位
            Cookie cookie = new Cookie("SessionID", session.getId());
            response.addCookie(cookie);
            return "登陆成功";
        } else
            return "请输入正确的用户名和密码";
    }

    @RequestMapping("/")//拦截器已经帮我们判断了，如果有有效session会继续链接到这里
    public String toIndex() {
        return "index";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setMaxInactiveInterval(0);//清除session
        Cookie cookie = new Cookie("SessionID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);//清除cookie
        return "login";
    }



}

```



## 主要功能

### 服务器响应

看着前端页面警告的地方添加对应方法就好了。

### 处理json

​	还是先设计好前端，我们点开管理员，希望展现出所有管理员的列表，这挺好办，向后端发送请求，返回一个json，这好像是前几篇文章就有的，我们要做的新的工作就是处理好json并添加到表格中，所幸表格的样式hAdmin已经帮我们准备好了，我们只需要自己写个js，利用jQuery即可。下面给个比较复杂的例子，弄懂了一般页面也就好弄了：

```javascript customerManage.ftl
<script>

    $.get("/getAllCustomer", function (data) {//这里的get命令也是自己设计，在Controller完善，data为获取到的json，是列表类型
        $("tbody").empty();//观察上面的表格部分，也可以用class或id添加
        //$(".no-records-found").remove();
        $.each(data, function (i, item) {//将获取到的json循环
            var id = item.id;
            var name = item.name;
            var phoneNumber = item.phoneNumber;
            var coupon = item.coupon;
            var type = item.type;
            var checkin = item.checkin;
            var checkout = item.checkout;
            $.get("/getCouponById?id=" + coupon, function (da) {//内循环判断优惠券的名字
                var couponName = da.name;
                var op = type === 0 ? ' <tr>\n' +
                    '                            <td>' + name + '</td>\n' +
                    '                            <td>' + phoneNumber + '</td>\n' +
                    '                            <td>' + couponName + '</td>\n' +
                    '                            <td>' + checkin + '</td>\n' +
                    '                            <td>入住</td>\n' +
                    '                            <td><a href="/outCheckIn?id=' + id + '">退房</a></td>\n' +
                    '                            <td><a href="/updateCustomer?id=' + id + '">修改</a></td>\n' +
                    '                            <td><a href="/deleteCustomer?id=' + id + '">删除</a></td>\n' +
                    '                        </tr>' : '                        <tr>\n' +
                    '                            <td>' + name + '</td>\n' +
                    '                            <td>' + phoneNumber + '</td>\n' +
                    '                            <td>' + couponName + '</td>\n' +
                    '                            <td>' + checkin + '</td>\n' +
                    '                            <td>已退房(' + checkout + ')</td>\n' +
                    '                            <td><a href="#" style="color:gray;">已退房</a></td>\n' +
                    '                            <td><a href="/updateCustomer?id=3">修改</a></td>\n' +
                    '                            <td><a href="/deleteCustomer?id=3">删除</a></td>\n' +
                    '                        </tr>';
                $("tbody").append(op);

            });


        })
    })
</script>
```

上面警告的内容都不打紧，还能给自己提示要去Controller类添加哪些东西，以及在对应的Mapper添加新方法，比如查询全部，BaseMapper没有提供。这里是因为我还不会Freemaker，所以用jQuery来处理

### @RestController和@Controller

**@RestController**相当于**@Controller**和**@ResponseBody**

通常用来返回json或xml，所以一个Controller里面只需要实现这个功能即可使用**@ResponseBody**注解，但是我需要返回页面呢，就不能用了。

所以我们所有的Controller类还是使用**@Controller**注解即可，用到返回json的数据就在那个方法上添加**@ResponseBody**就好了。比如获取全部顾客并返回json的方法：

```java
@ResponseBody
@GetMapping("/getAllCustomer")
public List<Customer> getAllCustomer() {
    return customerMapper.getAll();//BaseMapper不提供，自己取Mapper手动写SQL语句吧
}
```

### ModelAndView

当我们想写一个页面，并且向页面传递参数时，静态页面无法实现，就需要用到ModelAndView类，

比方说我这里有一个编辑顾客的页面：updateCustomer.ftl（也是要自己写)，里面有一些参数需要我们去提供，比如id，下面给出例子：

```java
@GetMapping("/updateCustomer")
public ModelAndView updateCustomer(int id) {
    ModelAndView modelAndView = new ModelAndView("updateCustomer");
    modelAndView.addObject("id", id);
    return modelAndView;
}
```

我们向服务器发送get请求`/updateCustomer`，并传入一个参数id，我们就new一个ModelAndView对象，ftl页面名为入参（找不到这个页面会有警告），然后添加对象并返回。

在ftl中，凡是用到传入变量的地方用${变量名}来替代，如：

```html
<input type="text" style="display:none" name="id" value=${id}>
```

### WebUploader

hAdmin的资源里包含了这个功能（没有的话取官网下载也行），需要把它和相关资源路径配置好，然后改一改官方提供的脚本，按照官网的指引，在前端页面中引入js、css、swf等，然后再引入脚本的标签后添加官方提供的JavaScript：（我们可以根据注释做一些修改）

```javascript
var uploader = WebUploader.create({

    // swf文件路径
    swf: BASE_URL + '/js/Uploader.swf',

    // 文件接收服务端。
    server: 'http://webuploader.duapp.com/server/fileupload.php',
……
});
……
uploader.on( 'uploadSuccess', function( file ) {
    $( '#'+file.id ).find('p.state').text('已上传');
});
……
```

根据我们需要的修改的地方看注释就知道改哪里了，首先是文件接收服务端，我在WebController中新建的图片接受处理的方法：

```java
@RequestMapping(method = RequestMethod.POST, path = "/fileupload")
@ResponseBody
public String upload(@RequestPart("file") MultipartFile picture) {
    //设置图片为唯一的uuid
    String pictureName = UUID.randomUUID().toString() + ".jpg";
    String fileSavePath = "E:/image/";
    try {
        picture.transferTo(new File(fileSavePath + pictureName));
    } catch (Exception e) {
        System.out.println(e.toString());
    }
    return pictureName;
}
```

因为要返回图片的路径（我们在配置文件中已经配置了静态资源目录

> #添加外部静态目录
>
> spring.resources.static-locations=classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/,file:E:/image/
> > 默认值为
> > classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/

，所以只需要返回文件名）所以要用@ResponseBody。

我们要将文件名返回给前端，所以我们得改一改官方的脚本：

```javascript
uploader.on('uploadSuccess', function (file, response) {
    $('#path').val(response._raw);//获取文件名
    $('#' + file.id).addClass('upload-state-done');
});
```

我这里的id为path的组件是一个不可见文本框，是表单中的元素，数据库存储的就是文件名。

> 原本我想将图片直接存储到数据库的，但是搜了一下相关资料...太麻烦了，还是存路径吧。

由于我们配置了静态路径，所以回显只需要直接设置src为图片名即可（这里还是用jQuery实现，还可以设置有图片上传时将现在的图片组件remove）。

### 统计图表

参考ModeAndView的用法，改一改hAdmin提供的demo就好了。

# 备注

- 用户输入特殊字符比如html标签时会导致的表格不显示bug可以利用String类的replace方法处理；
- Cookie有时间还要加密，并且设置Session数据库比较好，直接cookie能强行登陆，这个问题有时间再解决；

- 编译器的报错警告有时候不全，有些bug在浏览器中按F12就知道问题在哪里了。

# **[测试链接 用户名：admin 密码：admin](http://39.97.171.86:8080/)**