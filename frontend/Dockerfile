# 使用官方的Nginx镜像作为基础
FROM nginx

# 删除默认的Nginx配置文件
RUN rm /etc/nginx/conf.d/default.conf

# 将React应用的静态文件复制到Nginx的默认网站目录
COPY ./build/ /usr/share/nginx/html

# 暴露80端口
EXPOSE 80

# 启动Nginx
CMD ["nginx", "-g", "daemon off;"]