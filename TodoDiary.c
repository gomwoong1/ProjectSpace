#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include "/usr/include/mysql/mysql.h"
#define CMDSIZE 100

// 년,월,일 데이터 저장 구조체
typedef struct{
    int year;
    int month;
    int day;
}MYTIME;

void printWelcome();
void printInfo();
// MYTIME getTime();
void getTime();
void connDB();
void selectQuery(char *);
void makeSql(char *, char *, int);

MYTIME TODAY;
MYSQL *conn;
MYSQL_RES *res;
MYSQL_ROW row;

// 메인함수
int main() {
    getTime();
    printWelcome();

    while(1){
        char cmd[CMDSIZE];
        printf("명령어 입력 >> ");
        fgets(cmd, CMDSIZE, stdin);  

        if(strchr(cmd, ':'))   //명령어중 ':'이 있는지 없는지 확인
        {
            char *temp = strtok(cmd, ":"); // 명령어 종류와 명령어 값 분리
            char *strArr[2];
            int i = 0;
            
            while (temp != NULL) {
                strArr[i] = temp;
                temp = strtok(NULL, " ");
                i++;
            }
            
            if( strcmp(strArr[0], "기록조회") == 0) {
                char sql[100];
                system("clear");
                printf("조회일자: %s\n", strArr[1]);

                connDB();
                makeSql(sql, strArr[1], 1);
                selectQuery(sql);
            }
            else if (strcmp(strArr[0], "추가") == 0) {
                char sql[100];
                system("clear");
                printf("할 일이 추가되었습니다.\n");
                
                connDB();
                makeSql(sql, strArr[1], 2);
            }
        }
        else
        {
            if( strcmp(cmd, "도움말\n") == 0 )
                printInfo();
            else if( strcmp(cmd, "종료\n") == 0 ) {
                system("clear");
                printf("프로그램을 종료합니다.\n");
                break;
            }
            else
                printf("잘못된 명령어입니다. 도움이 필요하면 \'도움말\'을 입력하세요.\n\n");
        }
    }

    return 0;
}

// 프로그램 실행시 안내문구 출력시키는 함수
void printWelcome(){
    system("clear");
    printf("-------- Welcome To TodoDiary --------\n\n");
    printf("오늘은 %d년 %d월 %d일 입니다.\n\n", TODAY.year, TODAY.month, TODAY.day);
}

// 도움말 출력 함수
void printInfo(){
    system("clear");
    printf("-------- 도움말 --------\n\n");
    printf("\'TodoDiary\'는 Todo list와 다이어리를 결합한 프로그램입니다.\n\n");
    printf("기록: 기록은 오늘 날짜만 가능합니다.\n");
    printf("      할 일을 먼저 생성한 뒤, 할 일을 선택하고 시작하면 타이어가 자동 실행됩니다.\n");
    printf("      이 후 할 일 기록을 종료하면 자동으로 진행시간이 저장되고, 메모를 기록할 수 있습니다.\n\n");
    printf("조회: 일자를 지정하면 기록한 내용을 열람할 수 있습니다. \n");
    printf("      조회된 기록은 할 일 목록, 각 할 일의 소요시간 및 메모가 표시됩니다.\n\n");
    printf("제작: 201945018 컴퓨터시스템과 2학년 김지웅\n\n");
    printf("-------- 명령어 --------\n\n");
    printf("기록조회: 입력한 날짜로 기록을 조회합니다.\n");
    printf("          명령어 형식: \'기록조회:2022-11-21\'\n\n");
    printf("기록하기: 할 일의 번호를 입력하고 기록을 시작합니다.\n");
    printf("          명령어 형식: \'기록하기:1'\n\n");
    printf("할 일 생성: 오늘의 할 일 목록에 할 일을 추가합니다.\n");
    printf("            명령어 형식: \'추가:공부하기\'\n\n");
    printf("메모 수정: 기록된 할 일의 번호를 입력하고 메모를 수정합니다.\n");
    printf("          명령어 형식: \'메모수정:1\'\n\n");   
    printf("종료하기: 프로그램을 종료합니다.\n");
    printf("          명령어 형식: \'종료\'\n\n");
}

// 오늘 날짜를 받아오는 함수
void getTime(){
    struct tm* t;
    time_t base = time(NULL);

    t=localtime(&base);
    
    TODAY.year = t-> tm_year + 1900;
    TODAY.month = t -> tm_mon + 1;
    TODAY.day = t -> tm_mday;

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

void selectQuery(char *query){
    int count = 0;
    // 쿼리문 질의. 성공시 false 반환.
    if (mysql_query(conn, query)) {
        printf("Query Error!\n");
    }
    else {
        // 쿼리문 결과 res에 저장
        res = mysql_store_result(conn);

        // 가져온 레코드 출력
        printf("+--------+--------------+------------+----------------------------------------------------------------------------------+\n");
        printf("|  번호　|     날짜     |  경과시간  |  메모                                                                            |\n");
        printf("+--------+--------------+------------+----------------------------------------------------------------------------------+\n");

        while( (row=mysql_fetch_row(res))!=NULL){
            count++;
            printf("|%8s|%14s|%12s|%-82s|\n", row[0], row[1], row[2], row[3]);
        }
        printf("+--------+--------------+------------+----------------------------------------------------------------------------------+\n\n");
        printf("총 %d개의 할 일이 조회되었습니다.\n\n", count);

    }
    mysql_close(conn);
}

// 쿼리 문자열 만들어주는 함수
void makeSql(char * sql, char *value, int flag){
    if(flag == 1){
        strcpy(sql, "select * from list where date='");
        strcat(sql, value);
        sql[strlen(sql)-1] = '\0';
        strcat(sql,"'");
    }
    else if (flag == 2){
        char sql[100] = "select count(number) from list where date=";
        char nowDate[30]="'", year[5], month[5], day[5];
        char max[5];
        sprintf(year, "%d", TODAY.year);
        sprintf(month, "%d", TODAY.month);
        sprintf(day, "%d", TODAY.day);
        
        strcat(nowDate, year);
        strcat(nowDate, "-");
        strcat(nowDate, month);
        strcat(nowDate, "-");
        strcat(nowDate, day);
        strcat(nowDate, "'");
        strcat(sql, nowDate);

        mysql_query(conn, sql);
        res = mysql_store_result(conn);
        while((row=mysql_fetch_row(res))!=NULL){
            strcpy(max, row[0]);
        }
        printf("max값: %s", max);
    }
}