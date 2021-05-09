Run SQL Container
`docker run --name mysqltqs -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=tqscardemo -e MYSQL_USER=admin -e MYSQL_PASSWORD=pass -p 3306:3306 -d mysql/mysql-server:5.7`

