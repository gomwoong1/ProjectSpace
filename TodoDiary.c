#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// 년,월,일 데이터 저장 구조체 (전역)
typedef struct{
    int year;
    int month;
    int day;
}MYTIME;

void printWelcome();
void printInfo();
MYTIME getTime();
MYTIME TM;

int main() {
    TM = getTime();
    printWelcome();
    printf("오늘 날짜는 %d년 %d월 %d일 입니다.", TM.year, TM.month, TM.day);
    
    return 0;
}

// 프로그램 실행시 안내문구 출력시키는 함수
void printWelcome(){
    system("clear");
    printf("-------- Welcome To TodoDiary --------\n\n");
}

// 도움말 출력 함수
void printInfo(){
    system("clear");
    printf("-------- 도움말 --------\n\n");
    printf("사용중 날짜를 변경하려면 \n");
    printf("\n\n");
    printf("제작: 201945018 컴퓨터시스템과 2학년김지웅");
}

// 오늘 날짜를 받아오는 함수
MYTIME getTime(){
    struct tm* t;
    time_t base = time(NULL);

    MYTIME time;

    t=localtime(&base);
    
    time.year = t-> tm_year + 1900;
    time.month = t -> tm_mon + 1;
    time.day = t -> tm_mday;

    return time;
}