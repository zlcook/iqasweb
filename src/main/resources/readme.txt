--------------项目运行前提，需做好以下步骤--------------------------
1.装maven3
2.将6个本地架包上传到maven仓库中
3.装wordnet
4.装mysql数据库,用户名:root  密码:123456，并创建一个名为iqasdb数据库
5.安装eclipse
6.在eclipse上安装git插件
7.在eclipse上添加maven插件
8.将iqasweb导入eclipse中，并设置编码为utf-8
具体安装步骤参考iqasweb运行说明.doc文档中。

一、说明：
1.首先该工程是使用maven来管理的，所以对于项目中需要的jar包都需要在pom.xml文件中添加依赖。
2.项目采用了SpringMVC3+Hibernate3+Mysql+Maven3.3
3.项目采用了jdk1.7，编码采用了UTF-8
4.项目发布在tomcat上的访问路径：http://loaclhost:8080/iqasweb/

二、个性化配置：
1.在jdbc.properties中配置了本地mysql数据库的连接信息。
2.savepath.properties文件中配置了wordnet在本地的存放路径和版本信息。

三、配置文件
log4j.properties和log4j2.xml：本体操作中使用的log4j，而项目的其它模块使用log4j2日志。     
catalog-v001.xml、OnlyClass.owl、OnlyClassSentence.owl、Data文件夹：本体涉及到的文件
english-left3words-distim.tagger：WordNet涉及到的文件。
applicatonContext.xml：Spring配置文档。
jdbc.properties：为数据源配置信息。
savepath.properties：为资源实际存放路径的配置,包括项目文件存放跟路径、项目logo,单词的图片、绘本、视频、声音等资源的存放路径，通过PropertyUtils工具类读取。


三、2中方式运行项目（开发阶段使用第二种）：
前提：项目运行前先运行fuseki数据库。

1.项目热部署到tomcat7上，但是在eclipse中修改代码信息，页面不会变化，需要重新发布才可以（重新发布不需要重启tomcat），所以对于一些信息可以业务方法的验证可以采用单元测试方法。
方法：先运行tomcat，选中pom.xml文件右击->maven build（第二个）  在Goals中输入：tomcat7:deploy   最后点击run  。下次在运行选中第一个maven build
访问路径：http://localhost:8080/iqasweb/

2.项目发布到jetty容器上，实现修改代码后可以立即在网页上看到效果。（可以用于方便测试项目）
方法：选中pom.xml文件右击->maven build  在Main页的Goals中输入：clean jetty:run   在JRE页的VM argument中输入：-Xms900m -Xmx900m -XX:PermSize=900m -XX:MaxPermSize=900m   最后点击run。。下次在运行选中第一个maven build
访问路径：http://localhost:8080/iqasweb/



                   --------------------------访问http连接----------------------------
初始化管理员账号：
http://localhost:8080/iqasweb/initSystem/initadmin.html

网页端访问：
用户登录：http://localhost:8080/iqasweb/user/loginUI.html
注册：http://localhost:8080/iqasweb/user/registerUI.html
管理员登录连接：http://localhost:8080/iqasweb/admin/loginUI.html



	 