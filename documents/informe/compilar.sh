#!/bin/bash
archivo=articulo
if [ ! -f $archivo.tex ]; then
  echo "Archivo $archivo.tex no existe"
  pause
  exit
fi
borrartemporales(){
  echo "Eliminando archivos temporales"
  rm -f $archivo.aux
  rm -f $archivo.log
  rm -f $archivo.synctex.gz
}
pause(){
  read -p "Presione una tecla para continuar..."
}
borrartemporales
echo "Compilando archivo: $archivo.tex"
pdflatex -synctex=1 -interaction=nonstopmode $archivo
pdflatex -synctex=1 -interaction=nonstopmode $archivo
pdflatex -synctex=1 -interaction=nonstopmode $archivo
echo "Archivo generado: $archivo.pdf"
borrartemporales
pause
exit
