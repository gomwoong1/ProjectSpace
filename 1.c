#include <avr/io.h>

#define F_CPU 14745600UL // CPU 주파수 설정
#include <util/delay.h>	    // 딜레이를 위해 임포트


int main(void)
{

    // LED를 출력모드로 설정
    // 1은 출력, 0은 입력모드
    DDRA = 0xff;

    while (1) 
    {
        // PORTA에 연결된 LED는 총 0~7 포트로 구성되어있다.
        // 0~7포트는 0b00000000으로 매핑된다.
        // 비트와 포트 번호는 0b76543210 형태로 매핑되어있다.
        // LED는 High: 0, Low: 1이다.
        // 이하는 1초마다 8개의 LED를 점멸한다.
        PORTA ^= 0xff;
        _delay_ms(1000);
    }
}
