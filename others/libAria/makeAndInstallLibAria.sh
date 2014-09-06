#!/bin/bash
pathScript=$(readlink -f "$0")
path=`dirname "$pathScript"`
swigPath=/usr/local/swig
ariaPath=/usr/local/Aria
mobileSimPath=/usr/local/MobileSim
export JAVA_INCLUDE="/usr/lib/jvm/java-7-openjdk-i386/include"
export JAVA_BIN="/usr/lib/jvm/java-7-openjdk-i386/bin"
echo "$ariaPath/lib" > /etc/ld.so.conf.d/aria.conf
rm -f -R $ariaPath
rm -f -R $mobileSimPath
rm -f -R $swigPath
tar xzvf ARIA-2.8.1+gcc4.6.tgz
tar xzvf MobileSim-0.7.3+gcc4.6.tgz
tar xzvf swig-1.3.40.tar.gz
mv -f Aria-2.8.1 $ariaPath
mv -f MobileSim-0.7.3 $mobileSimPath
mv -f swig-1.3.40 $swigPath
cd $swigPath
$swigPath/./configure
make -C $swigPath
make -C $swigPath install
make -C $ariaPath
make -C $ariaPath java
make -C $ariaPath/ArNetworking
make -C $ariaPath/ArNetworking java
ldconfig
sudo ln -s -f $mobileSimPath/MobileSim /usr/bin/mobilesim
cd $path
echo 'Librería instalada'
