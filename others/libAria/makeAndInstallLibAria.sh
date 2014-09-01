#!/bin/bash
export JAVA_INCLUDE="/usr/lib/jvm/java-7-openjdk-i386/include"
export JAVA_BIN="/usr/lib/jvm/java-7-openjdk-i386/bin"
tar xzvf ARIA-2.8.1+gcc4.6.tgz
make -C Aria-2.8.1
make -C Aria-2.8.1 java
make -C Aria-2.8.1/ArNetworking
make -C Aria-2.8.1/ArNetworking java
rm -f -R /usr/local/Aria
make install -C Aria-2.8.1
rm -f -R Aria-2.8.1
echo '/usr/local/Aria/lib' > /etc/ld.so.conf.d/aria.conf
ldconfig
tar xzvf MobileSim-0.7.3+gcc4.6.tgz
rm -f -R /usr/local/MobileSim
mv -f MobileSim-0.7.3 /usr/local/MobileSim
sudo ln -s -f /usr/local/MobileSim/MobileSim /usr/local/bin/mobilesim
echo 'Librería instalada'
