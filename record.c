#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

void* timer(void *);

int main(){
    pthread_t thread;

    pthread_create(&thread, NULL, timer,NULL);
    pthread_join(thread, NULL);
    return 0;
}

void* timer(void* arg){
    
    pthread_exit(NULL);
}