#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

// 타이핑 계속 받아서 기록조회, 일시정지 기능 쓸 수 있게.
// 기록 시작할 때 받아온 타임값이 00:00:00이 아니라면 지속해서 기록할건지
// 아니면 새로 시작할건지 묻는 여부도

void* timer();
char msg[100];

int main(){
    pthread_t thread;

    pthread_create(&thread, NULL, timer, NULL);

    while(1){
        printf("입력해주세요: ");
        fgets(msg, 100, stdin);
        printf("입력한 메시지: %s\n\n", msg);
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
        printf("\x1b[%d;%dH", 2, strlen(msg));
    }
    pthread_exit(NULL);
}
