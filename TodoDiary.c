#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include "/usr/include/mysql/mysql.h"
#include <termio.h>
#include <pthread.h>
#define CMDSIZE 100

// 년,월,일 데이터 저장 구조체
typedef struct{
    int year;
    int month;
    int day;
}MYTIME;

void printWelcome();
void printInfo();
void getTime();
void connDB();
void selectQuery(char *);
void insertQuery(char *);
void printList();
void updateMemo(char *, char *);
int getAscii();
void* cntStr(void *);
void checkChar();

MYTIME TODAY;
MYSQL *conn;
MYSQL_RES *res;
MYSQL_ROW row;
char str[100];
int d, flag = 1;

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
            
            for (int cnt=0; cnt < 2; cnt++) {
                strArr[cnt] = temp;
                temp = strtok(NULL, "\0");
            }
            
            if(strcmp(strArr[0], "기록조회") == 0)
                selectQuery(strArr[1]);
            
            else if (strcmp(strArr[0], "추가") == 0)
                insertQuery(strArr[1]);
            
            else if (strcmp(strArr[0], "메모수정") == 0){
                char *tempVal = strtok(strArr[1], ",");
                char *tempArr[2];

                for (int cnt=0; cnt < 2; cnt++) {
                tempArr[cnt] = tempVal;
                tempVal = strtok(NULL, "\0");
                }

                updateMemo(tempArr[0], tempArr[1]);
            }
        }
        else
        {
            if( strcmp(cmd, "도움말\n") == 0 )
                printInfo();
            else if ( strcmp(cmd, "목록\n") == 0 )
                printList();
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
    printf("목록보기: 오늘 할 일의 목록을 확인합니다.\n");
    printf("          명령어 형식: \'목록'\n\n");
    printf("할 일 생성: 오늘의 할 일 목록에 할 일을 추가합니다.\n");
    printf("            명령어 형식: \'추가:공부하기\'\n\n");
    printf("메모 수정: 기록된 할 일의 날짜, 번호를 입력하고 메모를 수정합니다.\n");
    printf("          명령어 형식: \'메모수정:2022-11-21,1\'\n\n");   
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

// select query 생성 및 질의 함수
void selectQuery(char *value){
    int count = 0;
    char sql[255];
    value[strlen(value)-1] = '\0';
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
        }
        printf("+--------+---------------------+--------------+------------+---------------------------------------------------+\n\n");
        printf("총 %d개의 할 일이 조회되었습니다.\n\n", count);

    }
    mysql_close(conn);
}

// insert query 생성 및 질의 함수
void insertQuery(char *value){
    char sql[255];
    value[strlen(value)-1] = '\0';

    system("clear");
    printf("할 일이 추가되었습니다.\n");
    connDB();

    // sprintf("저장공간", 포매팅 형식, 값들);
    sprintf(sql, "select count(number)+1 from list where date='%d-%d-%d'", TODAY.year, TODAY.month, TODAY.day);
    mysql_query(conn, sql);
    res = mysql_store_result(conn);

    while((row=mysql_fetch_row(res))!=NULL){
        sprintf(sql, "insert into list(number, todo, date) values(%s,'%s','%d-%d-%d')", row[0], value, TODAY.year, TODAY.month, TODAY.day);
    }
    
    if(mysql_query(conn, sql))
        printf("Query Error!\n");

    mysql_close(conn);
}

// 오늘의 할 일 리스트 출력하는 함수
void printList(){
    char today[20];
    sprintf(today, "%d-%d-%d ", TODAY.year, TODAY.month, TODAY.day);
    selectQuery(today);
}

// 메모 수정해주는 함수
void updateMemo(char *date, char *number){
    char sql[255];
    number[strlen(number)-1] = '\0';

    connDB();
    sprintf(sql, "select memo from list where date='%s' and number=%s", date, number);
    mysql_query(conn, sql);
    res = mysql_store_result(conn);

    while((row=mysql_fetch_row(res))!=NULL){
        sprintf(str, "%s", row[0]);
    }

    checkChar();

    sprintf(sql, "update list set memo='%s' where date='%s' and number=%s", str, date, number);
    if(mysql_query(conn, sql))
        printf("Query Error!\n");
    
    sprintf(date, "%s\n", date);
    mysql_close(conn);
    
    selectQuery(date);
    //이 함수 끝나기 전에 str 초기화
}

// 메모 작성에 활용.
// 버퍼에 문자 입력받지 않고 문자를 확인함 
// getch() >> 리눅스에선 사용 불가능하기 때문에 사용자함수 작성
int getAscii(){
    // include termio.h
    int ch;

    struct termios old;
    struct termios new;

    tcgetattr(0, &old);
    new = old;

    new.c_lflag &= ~(ICANON|ECHO);
    new.c_cc[VMIN] = 1;
    new.c_cc[VTIME] = 0;

    tcsetattr(0, TCSAFLUSH, &new);

    ch=getchar();

    tcsetattr(0, TCSAFLUSH, &old);

    return ch;
}

// 문자 하나씩 받으면서 글자수세는 함수
void checkChar(){
    pthread_t cntStrTh;
    char a[5];
    system("clear");
    printf("-----메모 수정-----\n\n");

    printf ("\x1b[%d;%dH", 3,1);
    printf("%s\n", str);

    pthread_create(&cntStrTh, NULL, cntStr,NULL);
	while (1){
        d = getAscii();
        sprintf(a, "%c", d);
        strcat(str, a);

        printf ("\x1b[%d;%dH", 3,1);
		printf("%s\n", str);
        
        flag = 1;

        if (d == 10){
            flag = 2;
            // pthread_cancel(cntStrTh);
            break;
        }

        if (d == 127){
            str[strlen(str)-2] = '\0';
            system("clear");
            printf("-----메모 수정-----\n\n");
            printf ("\x1b[%d;%dH", 3,1);
            printf("%s", str);
            flag = 1;
        }

        d = 0;
	}

    pthread_join(cntStrTh, NULL);

    str[strlen(str)-1] = '\0';
}

// 글자수 실시간으로 출력해주는 함수 (쓰레드)
void* cntStr(void* arg) {
    int cnt = 0;
    flag = 1;
    
    do{
        if (flag == 1){
            cnt = strlen(str);
            printf ("\x1b[%d;%dH", 4,1);
            printf("%d / 50\n", cnt);
            flag = 0;
        }
    } while(!(flag==2));
    system("clear");
    pthread_exit(NULL);
}
