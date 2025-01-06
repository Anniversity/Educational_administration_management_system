1、打开Navicat连接数据库
2、下载Redis，找到Redis文件夹，先运行redis-server.exe，再运行redis-cli.exe（都不要关闭，放在后台运行即可）
3、打开idea，部署Maven，确保项目是以Maven项目打开的；
  打开配置文件edu-backend/src/main/resources/application.yml,将username修改为数据库的本地名，password修改为数据库的密码
4、电脑终端打开PowerShell，cd到项目中edu-forward-final-versio所在目录，然后输入npm run dev执行
5、浏览器中打开步骤4出现的网址

### 环境配置
1. lntelli IDEA (Ultimate Edition)  >=2022.3
2. Maven >=3.9
3. JDK 19 ~1.8
4. Redis  >=3.0
5. MySql >=8.0

