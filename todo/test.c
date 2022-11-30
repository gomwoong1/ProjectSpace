#include <stdio.h>
#include <termio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>

char str[100];
int d = 0, flag = 0;
void* cntStr(void *);

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

int main(int argc, char* argv[]){
    pthread_t cntStrTh;
    char a[5];
    system("clear");

    pthread_create(&cntStrTh, NULL, cntStr,NULL);
	while (1){
        d = getAscii();
        printf("%d ", d);
        sprintf(a, "%c", d);
        strcat(str, a);

        printf ("\x1b[%d;%dH", 1,1);
		printf("%s", str);
        flag = 1;

        // if (d == 10){
        if (strcmp(a, "\n") == 0){
            flag = 2;
            break;
        }

        if (d == 127){
            // strncpy(str, str, strlen(str)-2);
            str[strlen(str)-2] = '\0';
            system("clear");
            printf ("\x1b[%d;%dH", 1,1);
            printf("%s", str);
            flag = 1;
        }

        d = 0;
	}
    pthread_join(cntStrTh, NULL);

    str[strlen(str)-1] = '\0';
    printf("입력된 문자열: %s\n", str);

	return 0;
}

void* cntStr(void* arg) {
    // include pthread.h 
    int cnt = 0;

    while(! (flag == 2)){
        if (flag){
            cnt = strlen(str);
            printf ("\x1b[%d;%dH", 2,1);
            printf("%d / 50\n", cnt);
            flag = 0;
        }
    }   

    pthread_exit(NULL);
}