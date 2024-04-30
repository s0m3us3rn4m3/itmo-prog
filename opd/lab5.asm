		ORG	0x543
PTR:	WORD	0x593
MASK:	WORD	0x00FF
START:	CLA
CYCLE:	LD (PTR)
		PUSH
		CALL PRINT
		POP
		LD (PTR)+
		SWAB
		PUSH
		CALL PRINT
		POP
		JUMP CYCLE
EXIT:	HLT

PRINT:	AND MASK
		BEQ EXIT
PRINT3:	IN	7
		AND	#0x40
		BEQ PRINT3
PRINT5:	IN	13
		AND #0x40
		BEQ PRINT5
		LD (SP+1)
		OUT 6
		OUT 12
		RET

		ORG 0x593
STRING:	WORD	0xDEC0
		WORD	0xD0E1
		WORD	0xDEE2
		WORD	0x00DC
