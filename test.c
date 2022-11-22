#include <stdio.h>
#include <termio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>


char str[100];
int d;
void* cntStr(void *);
void gotoxy(int, int);

int getch(void){
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

    pthread_create(&cntStrTh, NULL, cntStr,NULL);
	while (1){
		d = getch();
        sprintf(a, "%c", d);
        strcat(str, a);

		printf("%s", a);
        
        if (d == 10)
            break;

        if (d == 127){
            str[strlen(str)-2] = '\0';
            system("clear");
            printf("%s", str);
        }
	}
    pthread_join(cntStrTh, NULL);

    str[strlen(str)-1] = '\0';
    printf("입력된 문자열: %s\n", str);

	return 0;
}

void* cntStr(void* arg) {
    // include pthread.h 
    // include ncurses.h

    int cnt = 0;

    while(d != 10){
        cnt = strlen(str);
        printf("%d / 50", cnt);
    }
    pthread_exit(NULL);
}
