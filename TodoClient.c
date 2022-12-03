#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <termio.h>
#include <pthread.h>
#include <unistd.h> 
#include <arpa/inet.h>
#include <sys/socket.h>
#define CMDSIZE 300

// 년,월,일 데이터 저장 구조체
typedef struct{
    int year;
    int month;
    int day;
}MYTIME;

void getTime();
void printWelcome();
void printInfo();
int getAscii();
void* cntStr(void *);
void checkChar();
void startRecord(char *);
// 소켓 통신용 함수
void connServer(char *, char*);
void error_handling(char * msg);
int setTimeout(int fd, char *buf, int buf_size, int timeout);

struct sockaddr_in serv_addr;
MYTIME TODAY;

char str[100];
int sock, d, flag = 1;
pthread_t snd_thread, rcv_thread;
void * thread_return;

// 메인함수
int main(int argc, char *argv[]) {

    // 매개변수의 개수가 맞지 않으면 안내문구 출력
	if(argc!=3) {
		printf("Usage : %s <IP> <port>\n", argv[0]);
		exit(1);
	}

	connServer(argv[1], argv[2]);
    
    getTime();
    printWelcome();

    while(1){
        int strCnt;
        char cmd[CMDSIZE];
        printf("\n명령어 입력 >> ");
        fgets(cmd, CMDSIZE, stdin);  
        system("clear");

        if(strcmp(cmd, "도움말\n") == 0)
            printInfo();
        else if(strcmp(cmd, "종료\n") == 0){
            system("clear");
            printf("프로그램을 종료합니다.\n");
            exit(1);
        }

        else{
            write(sock, cmd, strlen(cmd));
            while(setTimeout(sock, cmd, CMDSIZE, 100));
        }
    }

    return 0;
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

void startRecord(char *number){
    
}

void error_handling(char *msg)
{
	fputs(msg, stderr);
	fputc('\n', stderr);
	exit(1);
}

void connServer(char *ip, char *port){
    sock=socket(PF_INET, SOCK_STREAM, 0);
	
	memset(&serv_addr, 0, sizeof(serv_addr));
	serv_addr.sin_family=AF_INET;
	serv_addr.sin_addr.s_addr=inet_addr(ip);
	serv_addr.sin_port=htons(atoi(port));
	
	if(connect(sock, (struct sockaddr*)&serv_addr, sizeof(serv_addr))==-1)
		error_handling("connect() error");
    
}

int setTimeout(int fd, char *buf, int buf_size, int timeout_ms){
    int rx_len = 0;
    struct timeval timeout;
    fd_set readFds;

    timeout.tv_sec  = 0;
    timeout.tv_usec = timeout_ms*1000;

    FD_ZERO(&readFds);
    FD_SET(fd, &readFds);
    select(fd+1, &readFds, NULL, NULL, &timeout);

    if(FD_ISSET(fd, &readFds))
    {
        rx_len = read(fd, buf, buf_size);
        buf[rx_len]=0;

        if (strchr(buf, '#')){
            char *temp = strtok(buf, "#");
            sprintf(str, "%s", temp);

            checkChar();
            write(sock, str, strlen(str));
        }
        else
            printf("%s", buf);
    }

    return rx_len;
}