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

에러가 있다면 mysql 리스타트
sudo service mysql restart

* 우분투에 설치한 것과 윈도우에 설치한 것은 서로 다름.
* 만약 윈도우에 계정 생성 및 DB구축, 테이블 생성 등등 했다면 우분투에서 다시 할 것.

- 코드 작성 관련
mysql.h 찾기
mysql_config --cflags

만약 찾지 못했다면 설치
sudo apt-get install libmysqlclient-dev

.c 파일 내에 헤더파일 include 방법
#include "/usr/include/mysql/mysql.h"

컴파일 할 때 뒤에 붙여쓰기
gcc -o TodoDiary TodoDiary.c -lmysqlclient