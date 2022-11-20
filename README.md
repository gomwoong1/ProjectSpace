# IoT_TermProject
2022년도 2학년 2학기 IoT 텀 프로젝트

mysql info

DB: iot

ID: todo
PW: 2022iot

----------------------------------------------------

우분투 환경에서 C언어에서 Mysql 사용


- 우분투에 mysql server 설치
sudo apt-get install mysql-server


- 코드 작성 관련
mysql.h 찾기
mysql_config --cflags

만약 찾지 못했다면 설치
sudo apt-get install libmysqlclient-dev

.c 파일 내에 헤더파일 include 방법
#include "/usr/include/mysql/mysql.h"

컴파일 할 때 뒤에 붙여쓰기
gcc -o TodoDiary TodoDiary.c -lmysqlclient