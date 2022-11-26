/*
 *  mini_poject.c
 *  Created: 2022-11-26 오후 3:27:52
 *  Author: Kim Jiwoong
 */ 

#include <avr/io.h>
#define F_CPU 14745600UL
#include <util/delay.h>

void setCount(int);

char FND_number[10] = {
	0xc0, // 0  1100 0000
	0xf9, // 1  1111 1001
	0xa4, // 2  1010 0100
	0xb0, // 3  1011 0000
	0x99, // 4  1001 1001
	0x92, // 5  1001 0010
	0x82, // 6  1000 0010
	0xf8, // 7  1111 1000
	0x80, // 8  1000 0000
	0x90  // 9  1001 0000
};

char DOT_number[10] = {
	0x40, // 0. 0100 0000
	0x79, // 1. 0111 1001
	0x24, // 2. 0010 0100
	0x30, // 3. 0011 0000
	0x19, // 4. 0001 1001
	0x12, // 5. 0001 0010
	0x02, // 6. 0000 0010
	0x78, // 7. 0111 1000
	0x00, // 8. 0000 0000
	0x10  // 9. 0001 0000
};

int main(void)
{
	DDRA = 0x0f; // 0~3 OUTPUT, LED + 4~5
    DDRF = 0xf0; // 0~3 INPUT, BUTTON 
	DDRB = 0x0f; // 0~3 OUTPUT, FND(POWER)
	DDRC = 0xff; // OUTPUT, FND(NUMBER)
	
	PORTA = 0x0f; // LED OFF
	
	// BUTTON PULL_UP저항, F0~3에 한해 누르면 0, 안누르면 1인 상태
	// 즉, 0b00001111(2) 또는 0x0f(16) 상태가 되는 것
	PORTF = 0x0f;
	
	int count = 20;
	int num = 0;
	int state = 0;
	int VAL;
	
	while(1) {	
		while(count > -1){
			setCount(count);
			// 버튼의 입력값과 16진수 ff를 and 연산하여
			// 결과값을 변수 VAL에 저장
			//00001111 & 11111111 = 00001111
			VAL = PINF & 0xff;
			
			if (VAL == 0x06)
				state = 1;
				
			if(state && VAL != 0x06){
				count--;
				state = 0;		
			}
		}
		return 0;
		//PORTA = 0xf9;
		//count--;
		//PORTA = 0xff;
				
		//if (VAL == )
		
		//PORTB = 0xfd; // 십의 자리 세그먼트 선택
		//PORTC = FND_number[2]; // 출력
		//_delay_ms(5);
				
		//PORTB = 0xfe; // 일의 자리 세그먼트 선택
		//PORTC = FND_number[0]; // 출력
		//_delay_ms(5);

	}
}

void setCount(int cnt){
	PORTB = 0xf7;
	PORTC = FND_number[cnt/10]; // 출력
	_delay_ms(5);
	
	PORTB = 0xfb;
	PORTC = DOT_number[cnt%10]; // 출력
	_delay_ms(5);
}

void setNumber(int num){
    PORTB = 0xfd;
    PORTC = FND_number[num/10]; // 출력
	_delay_ms(5);

    PORTB = 0xfe;
    PORTC = FND_number[num/10]; // 출력
	_delay_ms(5);
}

void setNumber(int);