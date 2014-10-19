@echo off
set archivo=articulo
if not exist %archivo%.tex (
  echo Archivo %archivo%.tex no existe
  pause
  exit
)
call:borrartemporales
echo Compilando archivo: %archivo%.tex
pdflatex -synctex=1 -interaction=nonstopmode %archivo%
pdflatex -synctex=1 -interaction=nonstopmode %archivo%
pdflatex -synctex=1 -interaction=nonstopmode %archivo%
echo Archivo generado: %archivo%.pdf
call:borrartemporales
pause
exit
:borrartemporales
  echo Eliminando archivos temporales
  del 2>nul %archivo%.aux
  del 2>nul %archivo%.log
  del 2>nul %archivo%.synctex.gz
goto:eof