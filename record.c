#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <string.h>
#include <termio.h>

// 타이핑 계속 받아서 기록조회, 일시정지 기능 쓸 수 있게.
// 기록 시작할 때 받아온 타임값이 00:00:00이 아니라면 지속해서 기록할건지
// 아니면 새로 시작할건지 묻는 여부도

void* timer();
char msg[100];
int d = 0;

int getAscii(void){
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

int main(){
    char a[5];
    char input[100];
    pthread_t thread;

    pthread_create(&thread, NULL, timer, NULL);

    while(1){
        d = getAscii();
        sprintf(a, "%c", d);
        strcat(msg, a);
        printf("\x1b[%d;%ldH", 2,strlen(msg));
        printf("%s", a);

        if (d == 127){
            msg[strlen(msg)-2] = '\0';
            printf("\x1b[%d;%ldH", 2,strlen(msg));
        }

        if (d == 10){
            strcpy(input, msg);
            strcpy(msg, "");
            system("clear");
            printf("\x1b[%d;%ldH", 2,strlen(msg));
            //printf("%s", msg);
        }
    }

    pthread_join(thread, NULL);

    return 0;
}

void* timer(){ 
    int hour, min, sec = 0;

    system("clear");
    printf("00:00:00\n");

    // 1초마다 시간 출력하기
    while(1){
        sleep(1);
        sec++;

        if((sec%60) == 0){
            min++;
            sec = 0;
            if((min%60) == 0){
                hour++;
                min = 0;
            }
        }
            
        printf("\x1b[%d;%dH", 1,1);
        printf("%02d:%02d:%02d\n", hour, min, sec);
        printf("\x1b[%d;%ldH", 2, strlen(msg));
    }
    pthread_exit(NULL);
}
