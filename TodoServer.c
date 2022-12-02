#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <pthread.h>
#include "/usr/include/mysql/mysql.h"

#define CMDSIZE 100

void error_handling(char * msg);
void connDB();
void selectQuery(char *);
void insertQuery(char *);
void printList();
void updateMemo(char *, char *);

pthread_mutex_t mutx;
char cmd[CMDSIZE];

MYSQL *conn;
MYSQL_RES *res;
MYSQL_ROW row;
int serv_sock, clnt_sock;

int main(int argc, char *argv[])
{
	struct sockaddr_in serv_adr, clnt_adr;
	int clnt_adr_sz, str_len;
	pthread_t t_id;

	// 매개변수의 개수가 맞지 않으면 안내문구 출력
	if(argc!=2) {
		printf("Usage : %s <port>\n", argv[0]);
		exit(1);
	}

	serv_sock=socket(PF_INET, SOCK_STREAM, 0);

	memset(&serv_adr, 0, sizeof(serv_adr));
	serv_adr.sin_family=AF_INET; 
	serv_adr.sin_addr.s_addr=htonl(INADDR_ANY);
	serv_adr.sin_port=htons(atoi(argv[1]));
	
	if(bind(serv_sock, (struct sockaddr*) &serv_adr, sizeof(serv_adr))==-1)
		error_handling("bind() error");
	if(listen(serv_sock, 5)==-1)
		error_handling("listen() error");
	
	clnt_adr_sz=sizeof(clnt_adr);
	clnt_sock=accept(serv_sock, (struct sockaddr*)&clnt_adr,&clnt_adr_sz);
	
	while(1)
	{
		printf("값 받기 대기중\n");
		str_len=read(clnt_sock, cmd, CMDSIZE);
		cmd[str_len-1] = 0;
		printf("받아온 값: %s!\n", cmd);
		if(strchr(cmd, ':'))   //명령어중 ':'이 있는지 없는지 확인
		{
			char *temp = strtok(cmd, ":"); // 명령어 종류와 명령어 값 분리
			char *strArr[2];

			for (int cnt=0; cnt < 2; cnt++) {
				strArr[cnt] = temp;
				temp = strtok(NULL, "\0");
			}

			printf("[0]:%s!\n", strArr[0]);
			printf("[1]:%s!\n", strArr[1]);

			if(strcmp(strArr[0], "기록조회") == 0){
				printf("쿼리를 실행합니다.\n");
				selectQuery(strArr[1]);
				// write(clnt_sock, cmd, strlen(cmd));
			}
			
			else if (strcmp(strArr[0], "추가") == 0){
				sprintf(cmd, "[0]:%s, [1]:%s", strArr[0], strArr[1]);
				write(clnt_sock, cmd, strlen(cmd));
				// insertQuery(strArr[1]);
			}
			
			else if (strcmp(strArr[0], "메모수정") == 0){
				char *tempVal = strtok(strArr[1], ",");
				char *tempArr[2];

				for (int cnt=0; cnt < 2; cnt++) {
				tempArr[cnt] = tempVal;
				tempVal = strtok(NULL, "\0");
				}

				sprintf(cmd, "[0]:%s, [1]:%s, [2]:%s", strArr[0], tempArr[1], tempArr[2]);
				write(clnt_sock, cmd, strlen(cmd));

				//updateMemo(tempArr[0], tempArr[1]);
			}

			else if(strcmp(strArr[0], "기록하기") == 0){
				sprintf(cmd, "[0]:%s, [1]:%s", strArr[0], strArr[1]);
				write(clnt_sock, cmd, strlen(cmd));
			}
				
				//startRecord(strArr[1]);

			else{
				sprintf(cmd, "%s","도움말");
				write(clnt_sock, cmd, strlen(cmd));
			}
		}
		else
		{
			if ( strcmp(cmd, "목록") == 0 ){

				//printList();
			}
			else{
				sprintf(cmd, "%s","도움말");
				write(clnt_sock, cmd, strlen(cmd));
			}
		}
		printf("한바퀴돌았다\n");
	}
	close(serv_sock);
	return 0;
}

void error_handling(char * msg)
{
	fputs(msg, stderr);
	fputc('\n', stderr);
	exit(1);
}

void connDB(){
    char *server = "localhost";
    char *user = "todo";
    char *password = "2022iot";
    char *database = "iot";

    // 연결 객체 초기화
    conn = mysql_init(NULL);

    // Mysql 연결
    if (!mysql_real_connect(conn, server, user, password, NULL, 0, NULL, 0)) {
        printf("DB 연결 에러! 에러코드: ");
        fprintf(stderr, "%s\n", mysql_error(conn));
        exit(1);   
    }

    // Database 연결
    if(mysql_select_db(conn, database) != 0){
        mysql_close(conn);
        printf("DB가 없습니다. 프로그램을 종료합니다.\n");
        exit(1);
    }
}

// select query 생성 및 질의 함수
void selectQuery(char *value){
    int count = 0;
    char sql[255];
	char result[300];
    sprintf(sql, "select * from list where date='%s'", value);

    connDB();

    // 쿼리문 질의. 성공시 false 반환.
    if (mysql_query(conn, sql)) {
        printf("Query Error!\n");
    }
    else {
        // 쿼리문 결과 res에 저장
        res = mysql_store_result(conn);

        system("clear");
        // 가져온 레코드 출력
        printf("조회일자: %s\n", value);
        printf("+--------+---------------------+--------------+------------+---------------------------------------------------+\n");
        printf("|  번호　|        할 일        |     날짜     |  경과시간  |  메모                                             |\n");
        printf("+--------+---------------------+--------------+------------+---------------------------------------------------+\n");

        while( (row=mysql_fetch_row(res))!=NULL){
			count++;
            printf("| %-7s| %-20s| %-13s| %-11s| %-50s|\n", row[0], row[1], row[2], row[3], row[4]);
			sprintf(result, "| %-7s| %-20s| %-13s| %-11s| %-50s|\n", row[0], row[1], row[2], row[3], row[4]);
			write(clnt_sock, result, strlen(result));
        }
		
		//sprintf(result, "%s", "\0");
		//write(clnt_sock, result, strlen(result));
        printf("+--------+---------------------+--------------+------------+---------------------------------------------------+\n\n");
        printf("총 %d개의 할 일이 조회되었습니다.\n\n", count);
    }
    mysql_close(conn);
	printf("쿼리종료\n");
}

// // insert query 생성 및 질의 함수
// void insertQuery(char *value){
//     char sql[255];
//     value[strlen(value)-1] = '\0';
//     char today[30];

//     system("clear");
//     connDB();

//     // sprintf("저장공간", 포매팅 형식, 값들);
//     sprintf(sql, "select count(number)+1 from list where date='%d-%d-%d'", TODAY.year, TODAY.month, TODAY.day);
//     mysql_query(conn, sql);
//     res = mysql_store_result(conn);

//     while((row=mysql_fetch_row(res))!=NULL){
//         sprintf(sql, "insert into list(number, todo, date) values(%s,'%s','%d-%d-%d')", row[0], value, TODAY.year, TODAY.month, TODAY.day);
//     }
    
//     if(mysql_query(conn, sql))
//         printf("Query Error!\n");

//     sprintf(today, "%d-%d-%d\n", TODAY.year, TODAY.month, TODAY.day);
//     selectQuery(today);
// }

// // 오늘의 할 일 리스트 출력하는 함수
// void printList(){
//     char today[20];
//     sprintf(today, "%d-%d-%d ", TODAY.year, TODAY.month, TODAY.day);
//     selectQuery(today);
// }

// // 메모 수정해주는 함수
// void updateMemo(char *date, char *number){
//     char sql[255];
//     number[strlen(number)-1] = '\0';

//     connDB();
//     sprintf(sql, "select memo from list where date='%s' and number=%s", date, number);
//     mysql_query(conn, sql);
//     res = mysql_store_result(conn);

//     while((row=mysql_fetch_row(res))!=NULL){
//         sprintf(str, "%s", row[0]);
//     }

//     checkChar();

//     sprintf(sql, "update list set memo='%s' where date='%s' and number=%s", str, date, number);
//     if(mysql_query(conn, sql))
//         printf("Query Error!\n");
    
//     sprintf(date, "%s\n", date);
//     mysql_close(conn);
    
//     selectQuery(date);
// }