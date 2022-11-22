#include <stdio.h>
#include <termio.h>
#include <stdlib.h>
#include <string.h>

int getch(void){
    
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
    char a[5];
    char str[100];
    int d;
    int cnt = 0;

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
        cnt = strlen(str);
	}
    str[strlen(str)-1] = '\0';
    printf("입력된 문자열: %s\n", str);
    printf("문자열 길이: %d\n", cnt);

	return 0;
}

