## Epidemic

这是一个实时查看国内疫情信息的项目，包含了各省份以及各市的疫情信息，最近的疫情趋势，疫情地图数据的来由是通过腾讯实时疫情网站的请求返回的数据，加以处理再展示

CreateTableSQLaboutEpidemic.sql是此项目的建库建表语句

TenEpidemicHandler类用于处理从腾讯实时疫情网站获取的json数据；
GraphHandler类用于处理折线图所需数据并返回整理后的数据；
GetDataByUrl类用于通过url获取数据，上面两个类都有通过这个类的方法来获取数据，再做处理；
SerializeUtil类用于序列化与反序列化，主要用处在于将对象序列化存入redis，和从redis中取出数据反序列化成一个对象。

本项目只是一个练手学习的Java项目，主要使用的框架有SpringBoot,Spring,SpringMVC,Mybatis-plus，前后端交互连接使用的是Thymeleaf，前端使用了BootStrap以及echarts

<img src=".\疫情地图图片.png" style="zoom:75%;" />
