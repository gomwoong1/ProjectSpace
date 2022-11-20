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
MYTIME getTime();
MYTIME TODAY, SELDATE;

int main() {
    TODAY = getTime();
    printWelcome();

    while(1){
        char cmd[CMDSIZE];
        printf("명령어 입력 >> ");
        fgets(cmd, CMDSIZE, stdin);

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

    return 0;
}

// 프로그램 실행시 안내문구 출력시키는 함수
void printWelcome(){
    system("clear");
    printf("-------- Welcome To TodoDiary --------\n\n");
    printf("오늘은 %d년 %d월 %d일 입니다.\n", TODAY.year, TODAY.month, TODAY.day);
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
    printf("-------- 명령어 --------\n");
    printf("기록조회: 입력한 날짜로 기록을 조회합니다.\n");
    printf("          명령어 형식: \'기록조회: 2022-11-21\'\n\n");
    printf("기록하기: 할 일의 번호를 입력하고 기록을 시작합니다.\n");
    printf("          명령어 형식: \'기록하기: 1'\n\n");
    printf("할 일 생성: 오늘의 할 일 목록에 할 일을 추가합니다.\n");
    printf("            명령어 형식: \'할일: 공부하기\'\n\n");
    printf("메모 수정: 기록된 할 일의 번호를 입력하고 메모를 수정합니다.\n");
    printf("          명령어 형식: \'메모수정: 1\'\n\n");   
    printf("종료하기: 프로그램을 종료합니다.\n");
    printf("          명령어 형식: \'종료\'\n\n");
}

// 오늘 날짜를 받아오는 함수
MYTIME getTime(){
    struct tm* t;
    time_t base = time(NULL);

    t=localtime(&base);
    
    SELDATE.year = t-> tm_year + 1900;
    SELDATE.month = t -> tm_mon + 1;
    SELDATE.day = t -> tm_mday;

    return SELDATE;
}