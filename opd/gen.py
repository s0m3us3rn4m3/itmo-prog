#!/usr/bin/env python3
from pwn import *

def enter_addr(addr: int):
    assert addr < (1 << 11)
    io.sendline(hex(addr)[2:].encode())
    io.sendline(b'a')

def write(cmd: str):
    io.sendline(cmd.encode())
    io.sendline(b'w')

def write_trace():
    io.sendline(b'ru')
    io.recvuntil('Останов\n'.encode())
    with open('./trace.txt', 'w') as f:
        while True:
            io.sendline(b'c')
            io.recvline()
            s = io.recvline().strip().decode()
            s = '\t'.join(s.split())
            f.write(s + '\n')
            if s.split('\t')[1] == '0100':
                break


io = process(['java', '-jar', '-Dmode=cli', './bcomp-ng.jar'])

'''
545 0200 546 0200 545 0200 000 0545 0000 0100
546 A8FC 547 A8FC 593 DEC0 000 FFFC DEC0 1000
547 0C00 548 0C00 7FF DEC0 7FF 0547 DEC0 1000 7FF DEC0
548 DE08 551 DE08 7FE 0549 7FE 0551 DEC0 1000 7FE 0549
551 2EF2 552 2EF2 544 00FF 7FE FFF2 00C0 0000
552 F0FD 553 F0FD 552 F0FD 7FE 0552 00C0 0000
553 1207 554 1207 553 1207 7FE 0553 0040 0000
554 2F40 555 2F40 554 0040 7FE 0040 0040 0000
555 F0FD 556 F0FD 555 F0FD 7FE 0555 0040 0000
556 120D 557 120D 556 120D 7FE 0556 0040 0000
557 2F40 558 2F40 557 0040 7FE 0040 0040 0000
558 F0FD 556 F0FD 558 F0FD 7FE 0556 0040 0000
559 AC01 55A AC01 7FF DEC0 7FE 0001 DEC0 1000
55A 1306 55B 1306 55A 1306 7FE 055A DEC0 1000
55B 130C 55C 130C 55B 130C 7FE 055B DEC0 1000
55C 0A00 549 0A00 7FE 0549 7FF 055C DEC0 1000
549 0800 54A 0800 7FF DEC0 000 0549 DEC0 1000
54A AAF8 54B AAF8 593 DEC0 000 FFF8 DEC0 1000 543 0594
54B 0680 54C 0680 54B 0680 000 054B C0DE 1000
54C 0C00 54D 0C00 7FF C0DE 7FF 054C C0DE 1000 7FF C0DE
54D DE03 551 DE03 7FE 054E 7FE 0551 C0DE 1000 7FE 054E
551 2EF2 552 2EF2 544 00FF 7FE FFF2 00DE 0000
552 F0FD 553 F0FD 552 F0FD 7FE 0552 00DE 0000
553 1207 554 1207 553 1207 7FE 0553 0040 0000
554 2F40 555 2F40 554 0040 7FE 0040 0040 0000
555 F0FD 556 F0FD 555 F0FD 7FE 0555 0040 0000
556 120D 557 120D 556 120D 7FE 0556 0040 0000
557 2F40 558 2F40 557 0040 7FE 0040 0040 0000
558 F0FD 556 F0FD 558 F0FD 7FE 0556 0040 0000
559 AC01 55A AC01 7FF C0DE 7FE 0001 C0DE 1000
55A 1306 55B 1306 55A 1306 7FE 055A C0DE 1000
55B 130C 55C 130C 55B 130C 7FE 055B C0DE 1000
55C 0A00 549 0A00 7FE 0549 7FF 055C C0DE 1000
'''
main = [
    '0200',
    'EE18',
    'AE15',
    '0C00',
    'D6E9',
    '0800',
    '6E13',
    'EE12',
    'AE0E',
    '0C00',
    'D6E9',
    '0800',
    '0700',
    '6E0C',
    'EE0B',
    'AE09',
    '0740',
    '0C00',
    'D6E9',
    '0800',
    '6E05',
    'EE04',
    '0100',
    '3A99', # 'ZZZZ',
    'FFEF', # 'YYYY',
    'F000', # 'XXXX',
    'FF5E',
]
func = [
    'AC01',
    'F001',
    'F306',
    '7E08',
    'F804',
    'F003',
    '4C01',
    '6E05',
    'CE01',
    'AE02',
    'EC01',
    '0A00',
    'F0E2',
    '009F',
]

# 1f3-209
enter_addr(0x1f3)
for command in main: write(command)

# 6e9-6f4
enter_addr(0x6e9)
for command in func: write(command)

enter_addr(0x1f3)

io.sendline(b'run')
#write_trace()

def check(x, y, z):
    def f(x):
        return 0xf0e2 if 0 < x <= 0xf0e2 else 2*x-0x009f
    return hex((f(x-1) - f(z) - 1 + f(y)) & 0xffff)



io.interactive(prompt='')

