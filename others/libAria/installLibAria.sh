#!/bin/bash
tar xzvf ARIA-2.8.1+gcc4.6.tgz
rm -f -R /usr/local/Aria
mv -f Aria-2.8.1 /usr/local/Aria
tar xzvf MobileSim-0.7.3+gcc4.6.tgz
rm -f -R /usr/local/MobileSim
mv -f MobileSim-0.7.3 /usr/local/MobileSim
echo '/usr/local/Aria/lib' > /etc/ld.so.conf.d/aria.conf
ldconfig
sudo ln -s -f /usr/local/MobileSim/MobileSim /usr/local/bin/mobilesim
echo 'Librería instalada'
