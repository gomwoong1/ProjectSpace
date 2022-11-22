#include <stdio.h>
#include <termio.h>
#include <stdlib.h>

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
    char a;
    char str[100];
     d;

	while (1){
		d = getch();
        a = (char) d;
        // sprintf(str,"%d", d);

		printf("입력된 키 값: %c\n", a);
        
        if (d == 10)
            break;

	}
	return 0;
}

