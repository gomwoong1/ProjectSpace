#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

void* timer();

int main(){
    pthread_t thread;

    pthread_create(&thread, NULL, timer, NULL);
    pthread_join(thread, NULL);

    return 0;
}

void* timer(){ 
    int i = 0;
    // 1초마다 시간 출력하기
    while(1){
        sleep(1);
        i++;
        printf("child: %d초\n", i);
    }
    pthread_exit(NULL);
}
