TEX = platex
RM = rm -f
MV = mv -f
TARGET = Thesis
SRC = $(TARGET).tex Table_of_Contents.tex conf/*.tex conf/*.sty article/*.tex

.PHONY : all pdf nkf code clean

all: $(TARGET).dvi
pdf: $(TARGET).pdf

$(TARGET).dvi: $(SRC)
	- ebb figure/*.png
	- ebb.exe figure/*.png
	- extractbb figure/*.png
	- extractbb.exe figure/*.png
	- $(MV) *.bb figure/
	- $(MV) *.xbb figure/
	$(TEX) $< && $(TEX) $<

$(TARGET).pdf: $(TARGET).dvi
	dvipdfmx $<
	- open $(TARGET).pdf

nkf:
	nkf -w -Lu --overwrite *.tex
	nkf -w -Lu --overwrite conf/*
	nkf -w -Lu --overwrite article/*
	nkf -w -Lu --overwrite src/*

listings:
	cp -rf ./listings /cygdrive/c/w32tex/share/texmf/tex/platex/misc/

clean:
	$(RM) *.aux *.log *.lof *.lot *.out *.toc *.dvi *.pdf *~ figure/*.bb figure/*.xbb
