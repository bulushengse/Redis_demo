# redis_demo

redis nosql 缓存

redis window版本下载地址https://github.com/microsoftarchive/redis/releases 

下载Redis-x64-3.2.100.zip并解压，打开redis.windows.conf

配置内存大小，在526行添加：
maxmemory 1024000000

配置客户端密码，在444行添加：（可以不设置）
requirepass 12345

启动redis服务
redis目录下运行 redis-server.exe redis.windows.conf

启动客户端
redis目录下运行 redis-cli.exe -h 127.0.0.1 -p 6379

设置键值对 set key value

取出键值对 get key

redis可视化工具 redis-desktop-manager-0.8.8.384.exe

将redis安装成系统服务：redis-server --service-install redis.windows-service.conf

启动服务：redis-server --service-start

停止服务：redis-server --service-stop
